package com.mikeschvedov.formulacalculator.utils

import org.junit.Assert.*
import org.junit.Test

class ValidatorTest{

    // TESTING THE VALIDATOR CLASS
    var validator = Validator()

    @Test
    fun validator_main_function(){
        assertTrue("should be valid", validator.expressionIsValid("(9+4)"))
        assertFalse("should not be valid", validator.expressionIsValid("(94)"))
        assertFalse("should not be valid", validator.expressionIsValid("+9+4)"))
    }

    @Test
    fun validator_has_even_amount_of_parenthesis(){
        assertTrue("should be valid", validator.areParenthesisValid("5+5"))
        assertTrue("should be valid", validator.areParenthesisValid("(5+5)"))
        assertTrue("should be valid", validator.areParenthesisValid("(5+5 * (4+2))"))
        assertFalse("should be not be valid", validator.areParenthesisValid("(5+5"))
        assertFalse("should be not be valid", validator.areParenthesisValid("(5+5))"))
        assertFalse("should be not be valid", validator.areParenthesisValid("(5+5) + (3/2"))
    }

    @Test
    fun validator_first_character_is_digit_or_parenthesis(){
        assertTrue("this should be valid", validator.startsWithDigitOrParenthesis("9+4"))
        assertTrue("this should be valid", validator.startsWithDigitOrParenthesis("(9+4)"))
        assertFalse("this should not be valid", validator.startsWithDigitOrParenthesis("+3+4"))
        assertFalse("this should not be valid", validator.startsWithDigitOrParenthesis(")3+4)"))
    }

}