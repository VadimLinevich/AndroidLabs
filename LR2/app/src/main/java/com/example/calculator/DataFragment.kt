package com.example.calculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.max
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var edittext1: EditText
    private lateinit var textview1: TextView
    private var openBrackets: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edittext1 = view.findViewById(R.id.editTextNumberDecimal)
        textview1 = view.findViewById(R.id.textView)
        edittext1.showSoftInputOnFocus = false
        edittext1.customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                menu.clear()
                return false
            }
            override fun onDestroyActionMode(mode: ActionMode) {}
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return true
            }
            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        }
    }

    fun setDescription(buttonIndex: Int) {
        val length: Int = edittext1.text.length
        val start: Int = edittext1.selectionStart
        val end: Int = edittext1.selectionEnd
        when (buttonIndex) {
            1 -> {
                if(edittext1.isFocused == true)
                {
                    if(length > 0) {
                        edittext1.text.delete(0, length)
                        textview1.setText("")
                        openBrackets = 0
                    }
                }
            }
            2 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if (start == 0){
                            totalbrackets(start, end)
                            openBrackets++
                            edittext1.text.replace(
                                start.coerceAtMost(end), start.coerceAtLeast(end),
                                "(", 0, 1
                            )
                        }
                        else {
                            when (edittext1.text.toString()[start - 1]) {
                                '-', '+', '×', '÷', '(', '√' -> {
                                    totalbrackets(start, end)
                                    openBrackets++
                                    edittext1.text.replace(
                                        start.coerceAtMost(end), start.coerceAtLeast(end),
                                        "(", 0, 1
                                    )
                                }
                                else -> {
                                    if(end != edittext1.length() && edittext1.text.toString()[end] == '.')
                                    {
                                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                                    }
                                    else
                                    {
                                        if (openBrackets == 0 || edittext1.text.toString().substring(0, start).count { it == ')' } >= edittext1.text.toString().substring(0, start).count { it == '(' }){
                                            totalbrackets(start, end)
                                            edittext1.text.replace(
                                                start.coerceAtMost(end), start.coerceAtLeast(end),
                                                "×(", 0, 2
                                            )
                                            openBrackets++
                                        }
                                        else {
                                            if (openBrackets > 0){
                                                when (edittext1.text.toString()[start - 1]){
                                                    '-', '+', '×', '÷', '(' -> {
                                                    }
                                                    else -> {
                                                        if (end == edittext1.text.length){
                                                            totalbrackets(start, end)
                                                            edittext1.text.replace(
                                                                start.coerceAtMost(end),
                                                                start.coerceAtLeast(end),
                                                                ")",
                                                                0,
                                                                1
                                                            )
                                                        }
                                                        else {
                                                            when (edittext1.text.toString()[end])
                                                            {
                                                                '-', '+', '×', '÷', ')', '%' -> {
                                                                    totalbrackets(start, end)
                                                                    edittext1.text.replace(
                                                                        start.coerceAtMost(end),
                                                                        start.coerceAtLeast(end),
                                                                        ")",
                                                                        0,
                                                                        1
                                                                    )
                                                                }
                                                                else -> {
                                                                    totalbrackets(start, end)
                                                                    edittext1.text.replace(
                                                                        start.coerceAtMost(end),
                                                                        start.coerceAtLeast(end),
                                                                        ")×",
                                                                        0,
                                                                        2
                                                                    )
                                                                }
                                                            }
                                                        }
                                                        openBrackets--
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            3 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if(start == 0)
                        {
                            Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            if(start > 0 && edittext1.text[start - 1] == '%')
                            {
                                Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                if(start != edittext1.length() && edittext1.text[start] == '%')
                                {
                                    Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                                }
                                else
                                {
                                    if(start != edittext1.length() && (edittext1.text[start] == '+' || edittext1.text[start] == '÷' || edittext1.text[start] == '-' || edittext1.text[start] == '×'))
                                    {
                                        totalbrackets(start, end)
                                        edittext1.text.replace(min(start, end), max(start, end), "%", 0, 1)
                                    }
                                    else {
                                        updatetext(start, end, "%")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            4 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.selectionEnd - edittext1.selectionStart > 0) {
                        if (start > 0) {
                            when (edittext1.text.toString()[start - 1]) {
                                'a', 's', 'i', 'n', 'c', 'o', 't', 'l', 'g' -> {
                                    var leftSide = start
                                    var rightSide = end
                                    val text = edittext1.text.toString()
                                    while (text[leftSide - 1] == 'a' || text[leftSide - 1] == 's'
                                        || text[leftSide - 1] == 'i' || text[leftSide - 1] == 'n'
                                        || text[leftSide - 1] == 'c' || text[leftSide - 1] == 'o'
                                        || text[leftSide - 1] == 't' || text[leftSide - 1] == 'l'
                                        || text[leftSide - 1] == 'g'
                                    ) {
                                        leftSide--
                                        if (leftSide == 0) {
                                            break
                                        }
                                    }
                                    if (rightSide != text.length) {
                                        while (text[rightSide] == 'a' || text[rightSide] == 's'
                                            || text[rightSide] == 'i' || text[rightSide] == 'n'
                                            || text[rightSide] == 'c' || text[rightSide] == 'o'
                                            || text[rightSide] == 't' || text[rightSide] == 'l'
                                            || text[rightSide] == 'g' || text[rightSide] == '('
                                        ) {
                                            rightSide++
                                            if (text[rightSide - 1] == '(') {
                                                break
                                            }
                                        }
                                    }
                                    totalbrackets(leftSide, rightSide)
                                    edittext1.text.delete(leftSide, rightSide)
                                    return
                                }
                                '(' -> {
                                    if (start - 2 >= 0) {
                                        when (edittext1.text.toString()[start - 2]) {
                                            'a', 's', 'i', 'n', 'c', 'o', 't', 'l', 'g' -> {
                                                var leftSide = start - 1
                                                var rightSide = end
                                                val text = edittext1.text.toString()
                                                while (text[leftSide - 1] == 'a' || text[leftSide - 1] == 's'
                                                    || text[leftSide - 1] == 'i' || text[leftSide - 1] == 'n'
                                                    || text[leftSide - 1] == 'c' || text[leftSide - 1] == 'o'
                                                    || text[leftSide - 1] == 't' || text[leftSide - 1] == 'l'
                                                    || text[leftSide - 1] == 'g'
                                                ) {
                                                    leftSide--
                                                    if (leftSide == 0) {
                                                        break
                                                    }
                                                }
                                                totalbrackets(leftSide, rightSide)
                                                edittext1.text.delete(leftSide, rightSide)
                                                return
                                            }
                                        }
                                    }
                                }
                                else -> {
                                    when (edittext1.text.toString()[end - 1]) {
                                        'a', 's', 'i', 'n', 'c', 'o', 't', 'l', 'g' -> {
                                            var rightSide = end
                                            val text = edittext1.text.toString()
                                            if (rightSide != text.length) {
                                                while (text[rightSide] == 'a' || text[rightSide] == 's'
                                                    || text[rightSide] == 'i' || text[rightSide] == 'n'
                                                    || text[rightSide] == 'c' || text[rightSide] == 'o'
                                                    || text[rightSide] == 't' || text[rightSide] == 'l'
                                                    || text[rightSide] == 'g' || text[rightSide] == '('
                                                ) {
                                                    rightSide++
                                                    if (text[rightSide - 1] == '(') {
                                                        break
                                                    }
                                                }
                                            }
                                            totalbrackets(start, rightSide)
                                            edittext1.text.delete(start, rightSide)
                                            return
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            when (edittext1.text.toString()[end - 1]) {
                                'a', 's', 'i', 'n', 'c', 'o', 't', 'l', 'g' -> {
                                    var rightSide = end
                                    val text = edittext1.text.toString()
                                    if (rightSide != text.length) {
                                        while (text[rightSide] == 'a' || text[rightSide] == 's'
                                            || text[rightSide] == 'i' || text[rightSide] == 'n'
                                            || text[rightSide] == 'c' || text[rightSide] == 'o'
                                            || text[rightSide] == 't' || text[rightSide] == 'l'
                                            || text[rightSide] == 'g' || text[rightSide] == '('
                                        ) {
                                            rightSide++
                                            if (text[rightSide - 1] == '(') {
                                                break
                                            }
                                        }
                                    }
                                    totalbrackets(start, rightSide)
                                    edittext1.text.delete(start, rightSide)
                                    return
                                }
                            }
                        }
                        totalbrackets(start, end)
                        edittext1.text.delete(edittext1.selectionStart, edittext1.selectionEnd)
                        if (edittext1.text.isNotEmpty() && (edittext1.text.toString()[0] == '.' || edittext1.text.toString()[0] == '+' || edittext1.text.toString()[0] == '×' ||
                                    edittext1.text.toString()[0] == '÷')){
                            edittext1.text.delete(0, 1)
                        }
                    }
                    //Удаление без выделения
                    else {
                        if (edittext1.selectionStart > 0)
                        {
                            when (edittext1.text.toString()[start - 1]){
                                'a', 's', 'i', 'n', 'c', 'o', 't', 'l', 'g' -> {
                                    var leftSide = start
                                    var rightSide = end
                                    val text = edittext1.text.toString()
                                    while (text[leftSide - 1] == 'a' || text[leftSide - 1] == 's'
                                        || text[leftSide - 1] == 'i' || text[leftSide - 1] == 'n'
                                        || text[leftSide - 1] == 'c' || text[leftSide - 1] == 'o'
                                        || text[leftSide - 1] == 't' || text[leftSide - 1] == 'l'
                                        || text[leftSide - 1] == 'g'){
                                        leftSide--
                                        if (leftSide == 0){
                                            break
                                        }
                                    }
                                    if (rightSide != text.length) {
                                        while (text[rightSide] == 'a' || text[rightSide] == 's'
                                            || text[rightSide] == 'i' || text[rightSide] == 'n'
                                            || text[rightSide] == 'c' || text[rightSide] == 'o'
                                            || text[rightSide] == 't' || text[rightSide] == 'l'
                                            || text[rightSide] == 'g' || text[rightSide] == '('
                                        ) {
                                            rightSide++
                                            if (text[rightSide - 1] == '('){
                                                break
                                            }
                                        }
                                    }
                                    edittext1.text.delete(leftSide, rightSide)
                                    openBrackets--
                                    return
                                }
                                '(' -> {
                                    if (start - 2 >= 0){
                                        when (edittext1.text.toString()[start - 2]){
                                            'a', 's', 'i', 'n', 'c', 'o', 't', 'l', 'g' -> {
                                                var leftSide = start - 1
                                                var rightSide = end
                                                val text = edittext1.text.toString()
                                                while (text[leftSide - 1] == 'a' || text[leftSide - 1] == 's'
                                                    || text[leftSide - 1] == 'i' || text[leftSide - 1] == 'n'
                                                    || text[leftSide - 1] == 'c' || text[leftSide - 1] == 'o'
                                                    || text[leftSide - 1] == 't' || text[leftSide - 1] == 'l'
                                                    || text[leftSide - 1] == 'g'){
                                                    leftSide--
                                                    if (leftSide == 0){
                                                        break
                                                    }
                                                }
                                                edittext1.text.delete(leftSide, rightSide)
                                                openBrackets--
                                                return
                                            }
                                        }
                                    }
                                }
                            }
                            if (edittext1.text.toString()[start - 1] == '('){
                                openBrackets--
                            }
                            if (edittext1.text.toString()[start - 1] == ')'){
                                openBrackets++
                            }
                            edittext1.text.delete(edittext1.selectionStart - 1, edittext1.selectionStart)
                            if (edittext1.text.isNotEmpty() && (edittext1.text.toString()[0] == '.' || edittext1.text.toString()[0] == '+' || edittext1.text.toString()[0] == '×' ||
                                        edittext1.text.toString()[0] == '÷')){
                                edittext1.text.delete(0, 1)
                            }
                        }
                    }
                }
            }
            5 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×1", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "1", 0, 1)
                        }
                    }
                }
            }
            6 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×2", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "2", 0, 1)
                        }
                    }
                }
            }
            7 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×3", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "3", 0, 1)
                        }
                    }
                }
            }
            8 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (start == 1 && edittext1.text[start - 1] == '-') {
                            Toast.makeText(
                                activity?.applicationContext,
                                "Invalid format",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (start >= 2 && edittext1.text[start - 2] == '(' && edittext1.text[start - 1] == '-') {
                                Toast.makeText(
                                    activity?.applicationContext,
                                    "Invalid format",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                updatetext(start, end, "÷")
                            }
                        }
                    }
                    /*if(start != 0) {
                        edittext1.text.replace(min(start, end), max(start, end), "÷", 0, 1)
                    }*/
                }
            }
            9 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×4", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "4", 0, 1)
                        }
                    }
                }
            }
            10 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×5", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "5", 0, 1)
                        }
                    }
                }
            }
            11 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×6", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "6", 0, 1)
                        }
                    }
                }
            }
            12 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (start == 1 && edittext1.text[start - 1] == '-') {
                            Toast.makeText(
                                activity?.applicationContext,
                                "Invalid format",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (start >= 2 && edittext1.text[start - 2] == '(' && edittext1.text[start - 1] == '-') {
                                Toast.makeText(
                                    activity?.applicationContext,
                                    "Invalid format",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                updatetext(start, end, "×")
                            }
                        }
                    }
                }
            }
            13 -> {
                if(isfun(start, end))
                {
                    Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                    {
                        totalbrackets(start, end)
                        edittext1.text.replace(min(start, end), max(start, end), "×7", 0, 2)
                    }
                    else
                    {
                        totalbrackets(start, end)
                        edittext1.text.replace(min(start, end), max(start, end), "7", 0, 1)
                    }
                }
            }
            14 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×8", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "8", 0, 1)
                        }
                    }
                }
            }
            15 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×9", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "9", 0, 1)
                        }
                    }
                }
            }
            16 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if(start == 0 && edittext1.length() == 0)
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "-", 0, 1)
                        }
                        else
                        {
                            if(start == 0 && edittext1.text[start] != '-')
                            {
                                totalbrackets(start, end)
                                edittext1.text.replace(min(start, end), max(start, end), "-", 0, 1)
                            }
                            else
                            {
                                if(start != 0 && edittext1.text[start - 1] == '(')
                                {
                                    if(start < edittext1.length() && edittext1.text[start] != '-')
                                    {
                                        totalbrackets(start, end)
                                        edittext1.text.replace(min(start, end), max(start, end), "-", 0, 1)
                                    }
                                    else
                                    {
                                        if(start == edittext1.length())
                                        {
                                            totalbrackets(start, end)
                                            edittext1.text.replace(min(start, end), max(start, end), "-", 0, 1)
                                        }
                                    }
                                }
                                else
                                {
                                    if(start != 0)
                                    {
                                        updatetext(start, end, "-")
                                    }
                                    else
                                    {
                                        if(start != end)
                                        {
                                            totalbrackets(start, end)
                                            edittext1.text.replace(min(start, end), max(start, end), "-", 0, 1)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            17 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if (edittext1.text.isNotEmpty())
                        {
                            var leftSide = start - 1
                            var rightSide = end
                            val text = edittext1.text.toString()
                            if (leftSide >= 0)
                            {
                                while (text[leftSide] != '+' && text[leftSide] != '-' && text[leftSide] != '×' && text[leftSide] != '÷' && text[leftSide] != '%' && text[leftSide] != '(' && text[leftSide] != '^' && text[leftSide] != '√' )
                                {
                                    if(text[leftSide] == '.'){
                                        Toast.makeText(activity?.applicationContext, "Invalid format used", Toast.LENGTH_SHORT).show()
                                        return
                                    }
                                    leftSide--
                                    if (leftSide < 0){
                                        break
                                    }
                                }
                            }

                            if (rightSide != text.length) {
                                while (text[rightSide] != '+' && text[rightSide] != '-' && text[rightSide] != '×' && text[rightSide] != '÷' && text[rightSide] != '%' && text[rightSide] != '(' && text[rightSide] != '^' && text[rightSide] != '√') {
                                    if (text[rightSide] == '.') {
                                        Toast.makeText(activity?.applicationContext, "Invalid format used", Toast.LENGTH_SHORT).show()
                                        return
                                    }
                                    rightSide++
                                    if (rightSide == text.length) {
                                        break
                                    }
                                }
                            }
                        }

                        if (edittext1.text.toString() == ""){
                            totalbrackets(start, end)
                            edittext1.text.replace(
                                start.coerceAtMost(end), start.coerceAtLeast(end),
                                "0.", 0, 2
                            )
                        }
                        else{
                            if (edittext1.text.isNotEmpty() && start == 0){
                                totalbrackets(start, end)
                                edittext1.text.replace(
                                    start.coerceAtMost(end), start.coerceAtLeast(end),
                                    "0.", 0, 2
                                )
                            }
                            else {
                                when (edittext1.text.toString()[start - 1]) {
                                    '-', '+', '×', '÷', '(', '√', '^' -> {
                                        totalbrackets(start, end)
                                        edittext1.text.replace(
                                            start.coerceAtMost(end), start.coerceAtLeast(end),
                                            "0.", 0, 2
                                        )
                                    }
                                    '%', ')', 'e', 'π', '!'  -> {
                                        totalbrackets(start, end)
                                        edittext1.text.replace(
                                            start.coerceAtMost(end), start.coerceAtLeast(end),
                                            "×0.", 0, 3
                                        )
                                    }
                                    else -> {
                                        totalbrackets(start, end)
                                        edittext1.text.replace(
                                            start.coerceAtMost(end), start.coerceAtLeast(end),
                                            ".", 0, 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            18 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if((start > 0) && (edittext1.text[start - 1] == '%' || edittext1.text[start - 1] == ')' || edittext1.text[start - 1] == 'e' || edittext1.text[start - 1] == 'π' || edittext1.text[start - 1] == '!'))
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "×0", 0, 2)
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "0", 0, 1)
                        }
                    }
                }
            }
            19 -> {
                var editExp = edittext1.text.toString()
                var brackets = openBrackets
                if (brackets > 0) {
                    while (brackets != 0){
                        editExp = editExp + ')'
                        brackets--
                    }
                }
                var cursor: Int = 0
                var index: Int = 0
                while (index < editExp.length){
                    cursor++
                    if (editExp[index] == '!')
                    {
                        editExp = StringBuilder(editExp).insert(cursor, ')').toString()
                        var newCursor = cursor
                        while (editExp[newCursor] != '(' && editExp[newCursor] != '+'
                            && editExp[newCursor] != '-' && editExp[newCursor] != '×'
                            && editExp[newCursor] != '÷'){
                            newCursor--
                            if (newCursor == 0){
                                break
                            }
                        }
                        if (newCursor != 0 && (editExp[newCursor] != '-' && editExp[newCursor - 1] != '(')){
                            newCursor += 1
                        }
                        editExp = StringBuilder(editExp).insert(newCursor, '(').toString()
                        cursor++
                        index++
                    }
                    index++
                }
                editExp = editExp.replace("÷", "/")
                editExp = editExp.replace("×", "*")
                val exp = Expression(editExp)
                var result = exp.calculate().toString()
                if (result == "NaN" || result == "Infinity") {
                    textview1.setText("")
                    Toast.makeText(activity?.applicationContext, "Unable to show undefined result", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    result = result.toBigDecimal().toPlainString()
                    textview1.setText(result)
                }
            }
            20 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (start == 1 && edittext1.text[start - 1] == '-') {
                            Toast.makeText(
                                activity?.applicationContext,
                                "Invalid format",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (start >= 2 && edittext1.text[start - 2] == '(' && edittext1.text[start - 1] == '-') {
                                Toast.makeText(
                                    activity?.applicationContext,
                                    "Invalid format",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                updatetext(start, end, "+")
                            }
                        }
                    }
                    /*if(start != 0)
                    {
                        if(start == end)
                        {
                            if (edittext1.text[start - 1] != '+' && edittext1.text[start - 1] != '×' && edittext1.text[start - 1] != '÷' && edittext1.text[start - 1] != '-') {
                                if (start != edittext1.length())
                                {
                                    if (edittext1.text[start] != '+' && edittext1.text[start] != '×' && edittext1.text[start] != '÷' && edittext1.text[start] != '-')
                                    {
                                        edittext1.text.replace(min(start, end), max(start, end), "+", 0, 1)
                                        if (edittext1.text[start - 1] == '×' || edittext1.text[start - 1] == '÷' || edittext1.text[start - 1] == '-' || edittext1.text[start - 1] == '+')
                                        {
                                            edittext1.text.delete(start - 1, start)
                                        }
                                    }
                                }
                                else
                                {
                                    edittext1.text.replace(min(start, end), max(start, end), "+", 0, 1)
                                    if (edittext1.text[start - 1] == '×' || edittext1.text[start - 1] == '÷' || edittext1.text[start - 1] == '-' || edittext1.text[start - 1] == '+')
                                    {
                                        edittext1.text.delete(start - 1, start)
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(edittext1.text[start - 1] != '+' && edittext1.text[start - 1] != '×' && edittext1.text[start - 1] != '÷' && edittext1.text[start - 1] != '-')
                            {
                                if(end != edittext1.length())
                                {
                                    if (edittext1.text[end] != '+' && edittext1.text[end] != '×' && edittext1.text[end] != '÷' && edittext1.text[end] != '-')
                                    {
                                        edittext1.text.replace(min(start, end), max(start, end), "+", 0, 1)
                                        if (edittext1.text[start - 1] == '×' || edittext1.text[start - 1] == '÷' || edittext1.text[start - 1] == '-' || edittext1.text[start - 1] == '+')
                                        {
                                            edittext1.text.delete(start - 1, start)
                                        }
                                    }
                                }
                                else
                                {
                                    edittext1.text.replace(min(start, end), max(start, end), "+", 0, 1)
                                    if (edittext1.text[start - 1] == '×' || edittext1.text[start - 1] == '÷' || edittext1.text[start - 1] == '-' || edittext1.text[start - 1] == '+')
                                    {
                                        edittext1.text.delete(start - 1, start)
                                    }
                                }
                            }
                        }
                    }*/
                }
            }
            21 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×√(", 0, 3)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "√(", 0, 2)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "√(", 0, 2)
                        }
                        openBrackets++
                    }
                }
            }
            22 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×π", 0, 2)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "π", 0, 1)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "π", 0, 1)
                        }
                    }
                }
            }
            23 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '(', '^', '+', '-', '×', '÷', '√' -> {
                                    Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "^(", 0, 2)
                                    openBrackets++
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            24 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '(', '^', '+', '-', '×', '÷', '√' -> {
                                    Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "!", 0, 1)
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            25 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×sin(", 0, 5)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "sin(", 0, 4)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "sin(", 0, 4)
                        }
                        openBrackets++
                    }
                }
            }
            26 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×cos(", 0, 5)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "cos(", 0, 4)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "cos(", 0, 4)
                        }
                        openBrackets++
                    }
                }
            }
            27 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×tan(", 0, 5)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "tan(", 0, 4)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "tan(", 0, 4)
                        }
                        openBrackets++
                    }
                }
            }
            28 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×e", 0, 2)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "e", 0, 1)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "e", 0, 1)
                        }
                    }
                }
            }
            29 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×ln(", 0, 4)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "ln(", 0, 3)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "ln(", 0, 3)
                        }
                        openBrackets++
                    }
                }
            }
            30 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×lg(", 0, 4)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "lg(", 0, 3)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "lg(", 0, 3)
                        }
                        openBrackets++
                    }
                }
            }
            31 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×asin(", 0, 6)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "asin(", 0, 5)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "asin(", 0, 5)
                        }
                        openBrackets++
                    }
                }
            }
            32 -> {
                if(edittext1.isFocused == true)
                {
                    if(isfun(start, end))
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if(start > 0)
                        {
                            when (edittext1.text[start - 1]) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', 'e', 'π', '.', ')', '!' -> {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "×acos(", 0, 6)
                                }
                                else ->
                                {
                                    totalbrackets(start, end)
                                    edittext1.text.replace(min(start, end), max(start, end), "acos(", 0, 5)
                                }
                            }
                        }
                        else
                        {
                            totalbrackets(start, end)
                            edittext1.text.replace(min(start, end), max(start, end), "acos(", 0, 5)
                        }
                        openBrackets++
                    }
                }
            }
            else -> {
            }
        }
    }

    private fun updatetext(startcrs: Int, endcrs: Int, symbol: String)
    {
        if(startcrs != 0)
        {
            if(startcrs == endcrs)
            {
                if ((edittext1.text[startcrs - 1] == '×' || edittext1.text[startcrs - 1] == '÷' || edittext1.text[startcrs - 1] == '-' || edittext1.text[startcrs - 1] == '+') && symbol != "%")
                {
                    totalbrackets(startcrs, endcrs)
                    edittext1.text.replace(startcrs - 1, startcrs, symbol, 0, 1)
                }
                else
                {
                    if (edittext1.text[startcrs - 1] != '+' && edittext1.text[startcrs - 1] != '×' && edittext1.text[startcrs - 1] != '÷' && edittext1.text[startcrs - 1] != '-' && edittext1.text[startcrs - 1] != '(') {
                        if (startcrs != edittext1.length())
                        {
                            if (edittext1.text[startcrs] != '+' && edittext1.text[startcrs] != '×' && edittext1.text[startcrs] != '÷' && edittext1.text[startcrs] != '-' && edittext1.text[startcrs] != '.' && edittext1.text[startcrs - 1] != '(')
                            {
                                totalbrackets(startcrs, endcrs)
                                edittext1.text.replace(min(startcrs, endcrs), max(startcrs, endcrs), symbol, 0, 1)
                            }
                            else
                            {
                                Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else
                        {
                            totalbrackets(startcrs, endcrs)
                            edittext1.text.replace(min(startcrs, endcrs), max(startcrs, endcrs), symbol, 0, 1)
                        }
                    }
                    else
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                if(edittext1.text[startcrs - 1] != '+' && edittext1.text[startcrs - 1] != '×' && edittext1.text[startcrs - 1] != '÷' && edittext1.text[startcrs - 1] != '-')
                {
                    if(endcrs != edittext1.length())
                    {
                        if (edittext1.text[endcrs] != '+' && edittext1.text[endcrs] != '×' && edittext1.text[endcrs] != '÷' && edittext1.text[endcrs] != '-')
                        {
                            totalbrackets(startcrs, endcrs)
                            edittext1.text.replace(min(startcrs, endcrs), max(startcrs, endcrs), symbol, 0, 1)
                        }
                        else
                        {
                            Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        totalbrackets(startcrs, endcrs)
                        edittext1.text.replace(min(startcrs, endcrs), max(startcrs, endcrs), symbol, 0, 1)
                    }
                }
                else
                {
                    if ((edittext1.text[startcrs - 1] == '×' || edittext1.text[startcrs - 1] == '÷' || edittext1.text[startcrs - 1] == '-' || edittext1.text[startcrs - 1] == '+') && symbol != "%")
                    {
                        totalbrackets(startcrs, endcrs)
                        edittext1.text.replace(min(startcrs, endcrs) - 1, max(startcrs, endcrs), symbol, 0, 1)
                        //edittext1.text.replace(startcrs, startcrs, symbol, 0, 1)
                    }
                    else
                    {
                        Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else
        {
            Toast.makeText(activity?.applicationContext, "Invalid format", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isfun(startcrs: Int, endcrs: Int): Boolean
    {
        var rtrn: Boolean = true
        if(startcrs == endcrs)
        {
            if (startcrs != edittext1.length())
            {
                if (startcrs > 0)
                {
                    when (edittext1.text[startcrs - 1])
                    {
                        's', 'i', 'n', 'c', 'o', 't', 'a', 'l', 'g' -> {
                            rtrn = true
                        }
                        else -> {
                            rtrn = false
                        }
                    }
                }
                else
                {
                    rtrn = false
                }
            }
            else
            {
                rtrn = false
            }
        }
        else
        {
            if(endcrs != edittext1.length() && edittext1.text[endcrs] == '(')
            {
                if(startcrs > 0) {
                    when (edittext1.text[startcrs - 1]) {
                        's', 'i', 'n', 'c', 'o', 't', 'a', 'l', 'g' -> {
                            rtrn = true
                        }
                        else -> {
                            rtrn = false
                        }
                    }
                }
                else
                {
                    rtrn = false
                }
            }
            else
            {
                if (endcrs != edittext1.length() && startcrs != 0)
                {
                    if (endcrs > 0)
                    {
                        when (edittext1.text[endcrs - 1])
                        {
                            's', 'i', 'n', 'c', 'o', 't', 'a', 'l', 'g' -> {
                                rtrn = true
                            }
                            else -> {
                                if(startcrs > 0)
                                {
                                    when (edittext1.text[startcrs - 1])
                                    {
                                        's', 'i', 'n', 'c', 'o', 't', 'a', 'l', 'g' -> {
                                            rtrn = true
                                        }
                                        else -> {
                                            rtrn = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        rtrn = false
                    }
                }
                else
                {
                    if (endcrs == edittext1.length() && startcrs == 0) {
                        rtrn = false
                    }
                    else
                    {
                        if(startcrs > 0)
                        {
                            when (edittext1.text[startcrs - 1])
                            {
                                's', 'i', 'n', 'c', 'o', 't', 'a', 'l', 'g' -> {
                                    rtrn = true
                                }
                                else -> {
                                    rtrn = false
                                }
                            }
                        }
                        else
                        {
                            when (edittext1.text[endcrs - 1])
                            {
                                's', 'i', 'n', 'c', 'o', 't', 'a', 'l', 'g' -> {
                                    rtrn = true
                                }
                                else -> {
                                    rtrn = false
                                }
                            }
                        }
                    }
                }
            }
        }
        return rtrn
    }

    private fun totalbrackets(startcrs: Int, endcrs: Int)
    {
        val selectedText = edittext1.text.toString().substring(startcrs, endcrs)
        val brackets = selectedText.count { it == '(' }
        val closedBrackets = selectedText.count { it == ')' }
        openBrackets -= brackets
        openBrackets += closedBrackets
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textview", textview1.text.toString())
        outState.putInt("brackets", openBrackets)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getString("textview")?.let { textview1.setText(it) }
        savedInstanceState?.getInt("brackets")?.let { openBrackets = it }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}