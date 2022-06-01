package com.mikeschvedov.formulacalculator.model

sealed class MathOperation {
    class Binary(var op: BiFun) : MathOperation()
    class Unary(var op: UnaryFun) : MathOperation()
    class Const(var op: ConstFun) : MathOperation()

    fun interface BiFun {
        fun calculate(first:Double, second:Double): Double
    }

    fun interface ConstFun {
        fun calculate() : Double
    }

    fun interface UnaryFun {
        fun calculate(x:Double): Double
    }

}