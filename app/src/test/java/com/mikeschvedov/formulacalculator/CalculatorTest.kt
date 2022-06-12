package com.mikeschvedov.formulacalculator

import com.mikeschvedov.formulacalculator.model.Calculator
import com.mikeschvedov.formulacalculator.utils.ExpressionTranslator
import org.junit.Assert.*
import org.junit.Test

class CalculatorTest{

    var cal = Calculator()
    var expressionTranslator = ExpressionTranslator(cal.operationsMap)

    @Test
    fun expressionStringToList(){
        assertEquals(listOf("10", "+", "5"), expressionTranslator.expressionStringToList("10+5"))
        assertEquals(listOf("1.5", "+", "5"), expressionTranslator.expressionStringToList("1.5+5"))
        assertEquals(listOf("3", "×", "5", "+", "(","9","+","4",")"), expressionTranslator.expressionStringToList("3×5+(9+4)"))
    }

    @Test
    fun infixToPostfix() {
        assertEquals( listOf("5", "5", "×", "1.5", "+"), expressionTranslator.infixToPostfix(listOf("5", "×", "5", "+", "1.5")))
        assertEquals( listOf("10", "5", "+"), expressionTranslator.infixToPostfix(listOf("10", "+", "5")))
        assertEquals( listOf("3", "5", "×","9","4","+","+"), expressionTranslator.infixToPostfix(listOf("3", "×", "5","+","(","9","+","4",")")))
    }

    @Test
    fun evaluatePostfix(){
        assertEquals(15.0, expressionTranslator.evaluatePostfix(listOf("10", "5", "+")), 0.0)
        assertEquals(28.0, expressionTranslator.evaluatePostfix(listOf("3", "5", "×","9","4","+","+")), 0.0)
    }

    @Test
    fun equalTapped(){
        assertEquals(15.0, cal.equalTapped("10+5"), 0.0)
        assertEquals(28.0, cal.equalTapped("3×5+(9+4)"), 0.0)
        assertEquals(27.0, cal.equalTapped("3×5+(3×2)+6"), 0.0)
    }

    @Test
    fun equalTapped_with_just_one_operand(){
        assertEquals(34.0, cal.equalTapped("34"), 0.0)
    }

    @Test
    fun equalTapped_with_division(){
        assertEquals(0.833, cal.equalTapped("5÷6"), 0.0)
    }

}

