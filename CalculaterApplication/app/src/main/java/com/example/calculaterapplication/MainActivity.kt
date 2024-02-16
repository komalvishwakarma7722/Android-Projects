package com.example.calculaterapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculaterapplication.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.ArithmeticException

class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
    private var lastnumeric = false
     private var  statError=false
    private var lastDot=false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onOperatorClick(view: View) {
        if (!statError && lastnumeric){
            binding.dataTv.append((view as Button).text)
            lastDot=false
            lastnumeric=false
            OnEqual()
        }
    }

    fun onBackClick(view: View) {
        binding.dataTv.text=binding.dataTv.text.toString().dropLast(1)
        try {
            val lastchar=binding.dataTv.text.toString().last()
            if (lastchar.isDigit()){
                OnEqual()
            }
        }catch (e:Exception){
            binding.resultTv.text=""
            binding.resultTv.visibility=View.GONE
            Log.e("last char error",e.toString())

        }
    }

    fun onDigitClick(view: View) {
        if (statError){
            binding.dataTv.text=((view as Button).text)
            statError=false
        }else{
            binding.dataTv.append((view as Button).text)
        }
        lastnumeric=true
        OnEqual()
    }

    fun onAllclearClick(view: View) {
        binding.dataTv.text=""
        binding.resultTv.text=""
        statError=false
        lastDot=false
        lastnumeric=false
        binding.resultTv.visibility=View.GONE
    }

    fun onequalClick(view: View) {
        OnEqual()
        binding.dataTv.text=binding.resultTv.text.toString().drop(1)
    }

    fun onclearClick(view: View) {
        binding.dataTv.text=""
        lastnumeric=false
    }

    fun OnEqual(){
        if (lastnumeric && !statError){
            val txt = binding.dataTv.text.toString()
            expression=ExpressionBuilder(txt).build()

            try {
                val result=expression.evaluate()
                binding.resultTv.visibility=View.VISIBLE
                binding.resultTv.text="=" + result.toString()
            }catch (ex:ArithmeticException){
                Log.e("evaluate",ex.toString())
                binding.resultTv.text="Error"
                statError=true
                lastnumeric = false
            }
        }
    }
}