package com.mikeschvedov.formulacalculator.model

import android.os.Parcelable
import com.mikeschvedov.formulacalculator.utils.Operators
import kotlinx.parcelize.Parcelize
import java.lang.Math.*
import kotlin.math.ln
import kotlin.math.pow


class Calculator(): CalculatorBase(operations())


fun operations(): MutableMap<String, MathOperation>{

    val operationsMap: MutableMap<String, MathOperation> = mutableMapOf()

    // Instantiating the Classes / Telling what they will do

    //const
    operationsMap["e"] = MathOperation.Const { E }
    operationsMap["π"] = MathOperation.Const { PI }
    //unary
    operationsMap["⁺∕₋"] = MathOperation.Unary { x -> -1 * x }
    operationsMap["√"] = MathOperation.Unary { x -> sqrt(x) }
    operationsMap["y√x"] = MathOperation.Unary { x -> sqrt(x) }
    operationsMap["2√x"] = MathOperation.Unary { x -> sqrt(x) }
    operationsMap["3√x"] = MathOperation.Unary { x -> sqrt(x) }

    operationsMap["%"] = MathOperation.Unary { x -> x / 100 }
    operationsMap["x²"] = MathOperation.Unary { x -> x * x }
    operationsMap["x³"] = MathOperation.Unary { x -> x * x * x }
    operationsMap["x⁻¹"] = MathOperation.Unary { x -> 1 / x }
    operationsMap["sin"] = MathOperation.Unary { x -> sin(x) }
    operationsMap["tan"] = MathOperation.Unary { x -> tan(x) }
    operationsMap["cos"] = MathOperation.Unary { x -> cos(x) }
    operationsMap["sinh"] = MathOperation.Unary { x -> asin(x) }
    operationsMap["conh"] = MathOperation.Unary { x -> acos(x) }
    operationsMap["tanh"] = MathOperation.Unary { x -> atan(x) }
    operationsMap["ln"] = MathOperation.Unary { x -> ln(x) }
    operationsMap["eˣ"] = MathOperation.Unary { x -> E.pow(x) }
    //binary
    operationsMap[Operators.ADDITION.value.toString()] = MathOperation.Binary { x, y -> x + y }
    operationsMap[Operators.SUBTRACTION.value.toString()] = MathOperation.Binary { x, y -> x - y }
    operationsMap[Operators.MULTIPLY.value.toString()] = MathOperation.Binary { x, y -> x * y }
    operationsMap[Operators.DIVISION.value.toString()] = MathOperation.Binary { x, y -> x / y }
    operationsMap["Xʸ"] = MathOperation.Binary { x, y -> x.pow(y) }

    return operationsMap
}
