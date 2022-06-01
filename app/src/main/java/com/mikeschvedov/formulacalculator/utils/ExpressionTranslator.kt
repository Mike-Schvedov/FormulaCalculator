package com.mikeschvedov.formulacalculator.utils

import com.mikeschvedov.formulacalculator.model.MathOperation
import java.util.*

class ExpressionTranslator(var operationsMap: MutableMap<String, MathOperation>) {

    fun expressionStringToList(expression: String): List<String> {

        val tempList: MutableList<String> = mutableListOf()
        var previousChar: String = "" //"3×5+(9+4)" [3][X][5][+][][][] pre=""
        for (char in expression) {
            if (char.isDigit() || char == '.') {
                previousChar += char
            } else {
                if (previousChar.isNotEmpty()) {
                    tempList.add(previousChar)
                }
                tempList.add(char.toString())
                previousChar = ""
            }
        }
        if (previousChar.isNotEmpty()) {
            tempList.add(previousChar)
        }
        return tempList
    }


    fun infixToPostfix(expression: List<String>): List<String> { // 5 * 5 + 1.5
        // initializing empty String for result
        val resultList: MutableList<String> = mutableListOf()//list [5][5][*]   //stack [+]

        // initializing empty stack
        val stack = Stack<String>()
        for (element in expression) {
            // If the scanned character is an operand, add it to output.
            if (element.isStringDigit()) {
                resultList.add(element)
            }
            // if it is a '(' push into stack
            else if (element == "(") {
                stack.push(element)
            }
            // if it is a ')'
            else if (element == ")") {
                // while there is something inside the stack
                // and the highest element is not '('
                //
                while (!stack.isEmpty() &&
                    stack.peek() != "("
                ) {
                    resultList.add(stack.pop())
                }
                //if it is '(' then remove it from the stack
                stack.pop()
            }
            // the element is an operator
            else {
                // while there is something inside the stack AND this operator is "weaker" then the one in the stack
                while (!stack.isEmpty() && checkPriority(element)
                    <= checkPriority(stack.peek())
                ) {
                    //take the operator out of the stack and put into the result
                    resultList.add(stack.pop())
                }
                //also place this operator into the stack
                stack.push(element)
            }
        }

        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == "(") return mutableListOf()
            resultList.add(stack.pop())
        }
        return resultList
    }

    private fun checkPriority(ch: String): Int {
        when (ch) {
            Operators.ADDITION.value.toString(), Operators.SUBTRACTION.value.toString() -> return 1
            Operators.MULTIPLY.value.toString(), Operators.DIVISION.value.toString() -> return 2
            "^" -> return 3
        }
        return -1
    }

    fun evaluatePostfix(exp: List<String>): Double {
        //create a stack
        val stack = Stack<Double>()
        // Scan all characters one by one
        for (element in exp) {
            // If the scanned character is an operand (number here),
            // push it to the stack.
            if (element.isStringDigit()) {
                stack.push(element.toDouble())
            } else {
                val val1 = stack.pop()
                val val2 = stack.pop()
                val operationType = operationsMap[element.toString()]
                    ?: throw RuntimeException("No such operator :$element")
                when (operationType) {
                    is MathOperation.Binary -> stack.push(operationType.op.calculate(val2, val1))

                    else -> return 0.0
                }
            }
        }
        return stack.pop()
    }

}