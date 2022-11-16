package com.example.converter

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
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
    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var spinner3: Spinner
    private lateinit var textview1: TextView

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
        spinner = view.findViewById(R.id.spinner2)
        spinner2 = view.findViewById(R.id.spinner3)
        spinner3 = view.findViewById(R.id.spinner4)
        textview1 = view.findViewById(R.id.textView)
        val lengthnames = resources.getStringArray(R.array.LengthNames)
        val weightnames = resources.getStringArray(R.array.WeightNames)
        val speednames = resources.getStringArray(R.array.SpeedNames)
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
        ArrayAdapter.createFromResource(
            view.context,
            R.array.categoryNames,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            view.context,
            R.array.LengthNames,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            view.context,
            R.array.LengthNames,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner3.adapter = adapter
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val choose = resources.getStringArray(R.array.categoryNames)
                var names = R.array.LengthNames
                val spinnerselecteditem1 = spinner2.selectedItemPosition
                val spinnerselecteditem2 = spinner3.selectedItemPosition
                if (choose[spinner.selectedItemPosition] == choose[0]) {
                    names = R.array.LengthNames
                } else {
                    if (choose[spinner.selectedItemPosition] == choose[1]) {
                        names = R.array.WeightNames
                    } else {
                        if (choose[spinner.selectedItemPosition] == choose[2]) {
                            names = R.array.SpeedNames
                        }
                    }
                }
                view?.context?.let {
                    ArrayAdapter.createFromResource(
                        it,
                        names,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner2.adapter = adapter
                    }
                }

                view?.context?.let {
                    ArrayAdapter.createFromResource(
                        it,
                        names,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner3.adapter = adapter
                    }
                }
                spinner2.setSelection(spinnerselecteditem1)
                spinner3.setSelection(spinnerselecteditem2)

            }

        }

        spinner2?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(edittext1.text.toString() != "") {
                    var value = edittext1.text.toString().toDoubleOrNull()
                    if(value == null)
                    {
                        textview1.setText("")
                    }
                    else {
                        if (spinner2.selectedItem.toString() == spinner3.selectedItem.toString()) {
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[2] && spinner3.selectedItem.toString() == lengthnames[1]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[2] && spinner3.selectedItem.toString() == lengthnames[0]) {
                            value *= 100000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[1] && spinner3.selectedItem.toString() == lengthnames[2]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[1] && spinner3.selectedItem.toString() == lengthnames[0]) {
                            value *= 100
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[0] && spinner3.selectedItem.toString() == lengthnames[2]) {
                            value /= 100000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[0] && spinner3.selectedItem.toString() == lengthnames[1]) {
                            value /= 100
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[2] && spinner3.selectedItem.toString() == weightnames[1]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[2] && spinner3.selectedItem.toString() == weightnames[0]) {
                            value *= 1000000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[1] && spinner3.selectedItem.toString() == weightnames[2]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[1] && spinner3.selectedItem.toString() == weightnames[0]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[0] && spinner3.selectedItem.toString() == weightnames[2]) {
                            value /= 1000000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[0] && spinner3.selectedItem.toString() == weightnames[1]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[2] && spinner3.selectedItem.toString() == speednames[1]) {
                            value /= 3600
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[2] && spinner3.selectedItem.toString() == speednames[0]) {
                            value /= 3.6
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[1] && spinner3.selectedItem.toString() == speednames[2]) {
                            value *= 3600
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[1] && spinner3.selectedItem.toString() == speednames[0]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[0] && spinner3.selectedItem.toString() == speednames[2]) {
                            value *= 3.6
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[0] && spinner3.selectedItem.toString() == speednames[1]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                    }
                }
                else
                {
                    textview1.setText(edittext1.text.toString())
                }
            }

        }

        spinner3?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(edittext1.text.toString() != "") {
                    var value = edittext1.text.toString().toDoubleOrNull()
                    if(value == null)
                    {
                        textview1.setText("")
                    }
                    else {
                        if (spinner2.selectedItem.toString() == spinner3.selectedItem.toString()) {
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[2] && spinner3.selectedItem.toString() == lengthnames[1]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[2] && spinner3.selectedItem.toString() == lengthnames[0]) {
                            value *= 100000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[1] && spinner3.selectedItem.toString() == lengthnames[2]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[1] && spinner3.selectedItem.toString() == lengthnames[0]) {
                            value *= 100
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[0] && spinner3.selectedItem.toString() == lengthnames[2]) {
                            value /= 100000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[0] && spinner3.selectedItem.toString() == lengthnames[1]) {
                            value /= 100
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[2] && spinner3.selectedItem.toString() == weightnames[1]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[2] && spinner3.selectedItem.toString() == weightnames[0]) {
                            value *= 1000000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[1] && spinner3.selectedItem.toString() == weightnames[2]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[1] && spinner3.selectedItem.toString() == weightnames[0]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[0] && spinner3.selectedItem.toString() == weightnames[2]) {
                            value /= 1000000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[0] && spinner3.selectedItem.toString() == weightnames[1]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[2] && spinner3.selectedItem.toString() == speednames[1]) {
                            value /= 3600
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[2] && spinner3.selectedItem.toString() == speednames[0]) {
                            value /= 3.6
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[1] && spinner3.selectedItem.toString() == speednames[2]) {
                            value *= 3600
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[1] && spinner3.selectedItem.toString() == speednames[0]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[0] && spinner3.selectedItem.toString() == speednames[2]) {
                            value *= 3.6
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[0] && spinner3.selectedItem.toString() == speednames[1]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                    }
                }
                else
                {
                    textview1.setText(edittext1.text.toString())
                }
            }

        }

        edittext1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if(s.toString() != "") {
                    var value = s.toString().toDoubleOrNull()
                    if(value == null)
                    {
                        textview1.setText("")
                    }
                    else {
                        if (spinner2.selectedItem.toString() == spinner3.selectedItem.toString()) {
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[2] && spinner3.selectedItem.toString() == lengthnames[1]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[2] && spinner3.selectedItem.toString() == lengthnames[0]) {
                            value *= 100000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[1] && spinner3.selectedItem.toString() == lengthnames[2]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[1] && spinner3.selectedItem.toString() == lengthnames[0]) {
                            value *= 100
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[0] && spinner3.selectedItem.toString() == lengthnames[2]) {
                            value /= 100000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == lengthnames[0] && spinner3.selectedItem.toString() == lengthnames[1]) {
                            value /= 100
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[2] && spinner3.selectedItem.toString() == weightnames[1]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[2] && spinner3.selectedItem.toString() == weightnames[0]) {
                            value *= 1000000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[1] && spinner3.selectedItem.toString() == weightnames[2]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[1] && spinner3.selectedItem.toString() == weightnames[0]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[0] && spinner3.selectedItem.toString() == weightnames[2]) {
                            value /= 1000000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == weightnames[0] && spinner3.selectedItem.toString() == weightnames[1]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[2] && spinner3.selectedItem.toString() == speednames[1]) {
                            value /= 3600
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[2] && spinner3.selectedItem.toString() == speednames[0]) {
                            value /= 3.6
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[1] && spinner3.selectedItem.toString() == speednames[2]) {
                            value *= 3600
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[1] && spinner3.selectedItem.toString() == speednames[0]) {
                            value *= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                        if (spinner2.selectedItem.toString() == speednames[0] && spinner3.selectedItem.toString() == speednames[2]) {
                            value *= 3.6
                            textview1.setText(value.toBigDecimal().toPlainString())
                        }
                        if (spinner2.selectedItem.toString() == speednames[0] && spinner3.selectedItem.toString() == speednames[1]) {
                            value /= 1000
                            if (value.toBigDecimal().toPlainString().length <= 16) {
                                textview1.setText(value.toBigDecimal().toPlainString())
                            }
                            else
                            {
                                textview1.setText("Too big value")
                            }
                        }
                    }
                }
                else
                {
                    textview1.setText(s.toString())
                }
            }
        })
    }

    fun setDescription(buttonIndex: Int) {
        val length: Int = edittext1.text.length
        val start: Int = edittext1.selectionStart
        val end: Int = edittext1.selectionEnd
        when (buttonIndex) {
            1 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "1", 0, 1)
                }
            }
            2 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "2", 0, 1)
                }
            }
            3 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "3", 0, 1)
                }
            }
            4 -> {
                if(edittext1.isFocused == true)
                {
                    if(length > 0) {
                        edittext1.text.delete(0, length)
                    }
                }
            }
            5 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "4", 0, 1)
                }
            }
            6 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "5", 0, 1)
                }
            }
            7 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "6", 0, 1)
                }
            }
            8 -> {
                if(edittext1.isFocused == true)
                {
                    if(edittext1.selectionEnd - edittext1.selectionStart > 0) {
                        edittext1.text.delete(edittext1.selectionStart, edittext1.selectionEnd)
                    }
                    else
                    {
                        if(edittext1.selectionStart > 0) {
                            edittext1.text.delete(edittext1.selectionStart - 1, edittext1.selectionStart)
                        }
                    }
                }
            }
            9 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "7", 0, 1)
                }
            }
            10 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "8", 0, 1)
                }
            }
            11 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "9", 0, 1)
                }
            }
            12 -> {
                val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val item = clipboard.primaryClip?.getItemAt(0)
                if (item != null)
                {
                    if (item.text.toString().toDoubleOrNull() != null) {
                        if (item.text.toString().contains(".") && edittext1.text.toString().contains("."))
                        {
                            Toast.makeText(activity?.applicationContext, "Incorrect clipboard value", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            edittext1.text.replace(
                                min(start, end),
                                max(start, end),
                                item.text,
                                0,
                                item.text.length
                            )
                        }
                    }
                    else
                    {
                        Toast.makeText(activity?.applicationContext, "Clipboard value is not a number", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            13 -> {
                /*val temp1 = textview1.text
                textview1.setText(edittext1.text)
                edittext1.setText(temp1)*/
                val temp2 = spinner2.selectedItemId.toInt()
                spinner2.setSelection(spinner3.selectedItemId.toInt())
                spinner3.setSelection(temp2)
            }
            14 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), "0", 0, 1)
                }
            }
            15 -> {
                if(edittext1.isFocused == true)
                {
                    if (edittext1.text.length == 16)
                    {
                        Toast.makeText(activity?.applicationContext, "The number of the maximum length is entered", Toast.LENGTH_SHORT).show()
                    }
                    edittext1.text.replace(min(start, end), max(start, end), ".", 0, 1)
                    if(edittext1.text.toString().count { it == '.' } == 2)
                    {
                        edittext1.text.delete(edittext1.selectionStart - 1, edittext1.selectionStart)
                    }
                }
            }
            16 -> {
                if(textview1.text.toString() != "") {
                    val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("label", textview1.text.toString())
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(activity?.applicationContext, "Conversion result copied to the clipboard", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(activity?.applicationContext, "Incorrect value", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("spinner2", spinner2.selectedItemPosition)
        outState.putInt("spinner3", spinner3.selectedItemPosition)
        outState.putInt("spinner", spinner.selectedItemPosition)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getInt("spinner2")?.let { spinner2.setSelection(it) }
        savedInstanceState?.getInt("spinner3")?.let { spinner3.setSelection(it) }
        savedInstanceState?.getInt("spinner")?.let { spinner.setSelection(it) }
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