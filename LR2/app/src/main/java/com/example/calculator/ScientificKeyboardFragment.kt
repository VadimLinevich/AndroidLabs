package com.example.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScientificKeyboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */



class ScientificKeyboardFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private fun translateIdToIndex(id: Int): Int {
        var index = -1
        when (id) {
            R.id.button1 -> index = 1
            R.id.button2 -> index = 2
            R.id.button3 -> index = 3
            R.id.button4 -> index = 4
            R.id.button5 -> index = 5
            R.id.button6 -> index = 6
            R.id.button7 -> index = 7
            R.id.button8 -> index = 8
            R.id.button9 -> index = 9
            R.id.button10 -> index = 10
            R.id.button11 -> index = 11
            R.id.button12 -> index = 12
            R.id.button13 -> index = 13
            R.id.button14 -> index = 14
            R.id.button15 -> index = 15
            R.id.button16 -> index = 16
            R.id.button17 -> index = 17
            R.id.button18 -> index = 18
            R.id.button19 -> index = 19
            R.id.button20 -> index = 20
            R.id.button21 -> index = 21
            R.id.button22 -> index = 22
            R.id.button23 -> index = 23
            R.id.button24 -> index = 24
            R.id.button25 -> index = 25
            R.id.button26 -> index = 26
            R.id.button27 -> index = 27
            R.id.button28 -> index = 28
            R.id.button29 -> index = 29
            R.id.button30 -> index = 30
            R.id.button31 -> index = 31
            R.id.button32 -> index = 32
        }
        return index
    }

    override fun onClick(v: View?) {
        val buttonIndex = translateIdToIndex(v!!.id)

        val listener = activity as OnSelectedButtonListener?
        listener?.onButtonSelected(buttonIndex)
    }

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
        return inflater.inflate(R.layout.fragment_scientific, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn1: Button = view.findViewById(R.id.button1)
        val btn2: Button = view.findViewById(R.id.button2)
        val btn3: Button = view.findViewById(R.id.button3)
        val btn4: Button = view.findViewById(R.id.button4)
        val btn5: Button = view.findViewById(R.id.button5)
        val btn6: Button = view.findViewById(R.id.button6)
        val btn7: Button = view.findViewById(R.id.button7)
        val btn8: Button = view.findViewById(R.id.button8)
        val btn9: Button = view.findViewById(R.id.button9)
        val btn10: Button = view.findViewById(R.id.button10)
        val btn11: Button = view.findViewById(R.id.button11)
        val btn12: Button = view.findViewById(R.id.button12)
        val btn13: Button = view.findViewById(R.id.button13)
        val btn14: Button = view.findViewById(R.id.button14)
        val btn15: Button = view.findViewById(R.id.button15)
        val btn16: Button = view.findViewById(R.id.button16)
        val btn17: Button = view.findViewById(R.id.button17)
        val btn18: Button = view.findViewById(R.id.button18)
        val btn19: Button = view.findViewById(R.id.button19)
        val btn20: Button = view.findViewById(R.id.button20)
        val btn21: Button = view.findViewById(R.id.button21)
        val btn22: Button = view.findViewById(R.id.button22)
        val btn23: Button = view.findViewById(R.id.button23)
        val btn24: Button = view.findViewById(R.id.button24)
        val btn25: Button = view.findViewById(R.id.button25)
        val btn26: Button = view.findViewById(R.id.button26)
        val btn27: Button = view.findViewById(R.id.button27)
        val btn28: Button = view.findViewById(R.id.button28)
        val btn29: Button = view.findViewById(R.id.button29)
        val btn30: Button = view.findViewById(R.id.button30)
        val btn31: Button = view.findViewById(R.id.button31)
        val btn32: Button = view.findViewById(R.id.button32)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        btn10.setOnClickListener(this)
        btn11.setOnClickListener(this)
        btn12.setOnClickListener(this)
        btn13.setOnClickListener(this)
        btn14.setOnClickListener(this)
        btn15.setOnClickListener(this)
        btn16.setOnClickListener(this)
        btn17.setOnClickListener(this)
        btn18.setOnClickListener(this)
        btn19.setOnClickListener(this)
        btn20.setOnClickListener(this)
        btn21.setOnClickListener(this)
        btn22.setOnClickListener(this)
        btn23.setOnClickListener(this)
        btn24.setOnClickListener(this)
        btn25.setOnClickListener(this)
        btn26.setOnClickListener(this)
        btn27.setOnClickListener(this)
        btn28.setOnClickListener(this)
        btn29.setOnClickListener(this)
        btn30.setOnClickListener(this)
        btn31.setOnClickListener(this)
        btn32.setOnClickListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScientificFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScientificKeyboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}