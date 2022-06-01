package com.mikeschvedov.formulacalculator.model

import com.mikeschvedov.formulacalculator.utils.ExpressionTranslator
import com.mikeschvedov.formulacalculator.utils.Validator
import kotlinx.parcelize.Parcelize


abstract class CalculatorBase(var operationsMap: MutableMap<String, MathOperation>) {

    var memoryManager = MemoryManager()
    val validator = Validator()
    private val converter = ExpressionTranslator(operationsMap)

    fun setNewMemoryManager(_memoryManager: MemoryManager){
        memoryManager = _memoryManager
    }

    /*private fun performOperation(operation: String) {
        val operationType = operationsMap[operation] ?: throw RuntimeException("No such operator")
        val result = when (operationType) {
            is MathOperation.Binary -> operationType.op.calculate(
                memoryManager.memory_slot1,
                memoryManager.memory_slot2
            )
            is MathOperation.Unary -> operationType.op.calculate(memoryManager.memory_slot1)
            is MathOperation.Const -> operationType.op.calculate()
            else -> return
        }
        // Saving the result
        memoryManager.memory_slot1 = result
    }*/

    fun equalTapped(expression: String): Double {
        return converter.evaluatePostfix(converter.infixToPostfix(converter.expressionStringToList(expression)))
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