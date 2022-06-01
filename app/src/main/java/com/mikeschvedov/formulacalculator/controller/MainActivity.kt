package com.mikeschvedov.formulacalculator.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.helper.widget.Carousel
import androidx.constraintlayout.helper.widget.Flow
import com.mikeschvedov.formulacalculator.R
import com.mikeschvedov.formulacalculator.databinding.ActivityMainBinding
import com.mikeschvedov.formulacalculator.model.Calculator
import com.mikeschvedov.formulacalculator.model.CalculatorBase
import com.mikeschvedov.formulacalculator.model.MemoryManager

import com.mikeschvedov.formulacalculator.utils.formatFromDoubleToString
import java.util.*

class MainActivity : AppCompatActivity() {

    var calculator: CalculatorBase = Calculator()

    /* ----------- Set up Binding ----------- */
    private var _binding: ActivityMainBinding? = null //we want it to be nullable
    private val binding
        //we force this version to not be nullable
        get() = _binding!!

    /* ----------- Computed Properties ----------- */
    // If we are going to use something a lot, we can turn it into a computed property
    private var formulaInput: String
        get() = binding.editResult.text?.toString()!!
        set(value) {
            binding.editResult.text = value
        }

    private var topResult: String
        get() = binding.editResultTop.text?.toString()!!
        set(value) {
            binding.editResultTop.text = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewsIntoLists()
        //TODO read edittext filter //validate formula
        //TODO motionLayout

    }

    // This callback is called only when there is a saved instance that is previously saved by using
    // onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the one used in onCreate().
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState)
        with(savedInstanceState) {
            // Restore state members from saved instance of MemoryManager
            val savedManager = getParcelable<MemoryManager>("memoryManagerInstance")
            savedManager?.let {
                calculator.setNewMemoryManager(it)
            }
            topResult = getString("topResultField").toString()
            formulaInput = getString("formulaInputField").toString()
            notifyAC()
        }
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    override fun onSaveInstanceState(outState: Bundle) {
        // Save the user's current game state
        outState.run {
            putParcelable("memoryManagerInstance", calculator.memoryManager)
            putString("formulaInputField", formulaInput)
            putString("topResultField", topResult)
        }

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState)
    }


    private fun digitTapped(view: View) {
        with(view as Button) {
            when (text) {
                "." -> dotTapped(text)
                "⌫" -> backSpaceTapped()
                "AC", "C" -> clearTapped(text)
                "0" -> zeroTapped()
                else -> {
                    printDigit(text)
                }
            }
        }
    }

    private fun zeroTapped() {
        if (formulaInput == "0") {
            formulaInput = "0"
        } else {
            formulaInput += "0"
        }
    }

    private fun dotTapped(text: CharSequence) {
        if (formulaInput == "" || formulaInput == "0") {
            formulaInput = "0$text"
        } else {
            formulaInput += "$text"
        }
        notifyAC()
    }

    private fun clearTapped(text: CharSequence) {
        // if there is nothing in the formula preform as AC
        // if there is something in the formula perform as C
        if (text == "AC") {
            topResult = ""
            calculator.wipeMemory()
        } else {
            formulaInput = ""
        }
        notifyAC()
    }

    private fun backSpaceTapped() {
        formulaInput = formulaInput.dropLast(1)
        notifyAC()
    }

    private fun printDigit(text: CharSequence) {
        if (formulaInput == "0") {
            formulaInput = "$text"
        } else {
            formulaInput += text
        }
        notifyAC()
    }

    private fun notifyAC() {
        // switch between the clear/all clear button
        if (formulaInput.isEmpty()) {
            binding.btnAc.text = getString(R.string.ac)
        } else {
            binding.btnAc.text = getString(R.string.c)
        }
    }

    private fun setViewsIntoLists() {
        val buttonOthers: List<Button> =
            binding.groupOtherButtons.referencedIds?.map(this::findViewById) ?: listOf()
        buttonOthers.forEach { it.setOnClickListener(this::digitTapped) }

        binding.btnEquals.setOnClickListener { equalsTapped() }

        binding.brnUndo.setOnClickListener { undoTapped() }

        binding.btnRedo.setOnClickListener { redoTapped() }
    }

    private fun undoTapped() {
        formulaInput = calculator.performUndo()
        notifyAC()
    }

    private fun redoTapped() {
        formulaInput = calculator.performRedo()
        notifyAC()
    }

    private fun equalsTapped() {
        if (calculator.validator.expressionIsValid(formulaInput)) {
            topResult = calculator.equalTapped(formulaInput).formatFromDoubleToString()
            calculator.addToUndoList(formulaInput)
        }
    }
}

