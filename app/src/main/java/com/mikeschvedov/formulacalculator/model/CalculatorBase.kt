package com.mikeschvedov.formulacalculator.model

import com.mikeschvedov.formulacalculator.utils.ExpressionTranslator
import com.mikeschvedov.formulacalculator.utils.Validator
import java.math.BigDecimal
import java.math.RoundingMode


abstract class CalculatorBase(var operationsMap: MutableMap<String, MathOperation>) {

    var memoryManager = MemoryManager()
    val validator = Validator()
    private val converter = ExpressionTranslator(operationsMap)

    fun setNewMemoryManager(_memoryManager: MemoryManager){
        memoryManager = _memoryManager
    }

    fun equalTapped(expression: String): Double {
        val sss = converter.evaluatePostfix(
            converter.infixToPostfix(
                converter.expressionStringToList(expression)
            )
        )
        return BigDecimal(sss).setScale(3, RoundingMode.HALF_EVEN).toDouble()
    }

    fun addToUndoList(formula_input: String) {
        memoryManager.undoStack.push(formula_input) //[0] 5X5 [1] 5X5
    }

    fun performUndo() : String{
        if (memoryManager.undoStack.isNotEmpty()){
            val temp = memoryManager.undoStack.pop()
            memoryManager.redoStack.push(temp)
            return temp
        }
        return ""
    }

    fun performRedo(): String{
        if (memoryManager.redoStack.isNotEmpty()) {
            val temp = memoryManager.redoStack.pop()
            memoryManager.undoStack.push(temp)
            return temp
        }
        return ""
    }

    fun wipeMemory() {
        memoryManager.undoStack.clear()
        memoryManager.redoStack.clear()
    }
}