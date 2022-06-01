package com.mikeschvedov.formulacalculator.utils

import java.util.*

class Validator {

    fun expressionIsValid(expression: String): Boolean {
        val operatorStack = Stack<Char>()
        val numberStack = Stack<Char>()

        for (element in expression) {
            if (element.isDigit()) {
                numberStack.push(element)
            } else if (element == '(' || element == ')') {
                //specialStack.push(element)
            } else {
                operatorStack.push(element)
            }
        }
        // parenthesis are not in pairs,
        if (!areParenthesisValid(expression)) {
            return false
        }
        // there is not at least one operator
        else if (operatorStack.isEmpty()) {
            return false
        }
        // the first character is not a digit or opening parenthesis
        else if (!startsWithDigitOrParenthesis(expression)) {
            return false
        }
        return true
    }

    fun areParenthesisValid(expression: String): Boolean {
        val specialStack = Stack<Char>()

        for (element in expression) {
            if (element == '(' || element == ')') {
                specialStack.push(element)
            }
        }
        return specialStack.size % 2 == 0
    }

    fun startsWithDigitOrParenthesis(expression: String): Boolean {
        return expression.first().isDigit() || expression.first() == '('
    }

}