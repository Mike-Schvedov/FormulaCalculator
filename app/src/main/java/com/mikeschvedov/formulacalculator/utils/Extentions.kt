package com.mikeschvedov.formulacalculator.utils

class Extentions {
}

// Checks if a double has to stay as a Double (has something in the decimal)
// or can be converted into an Int.
// Then we convert the result to a String either way.
fun Double.formatFromDoubleToString(): String{
    val list = this.toString().split(".")
    // If there is something other than 0 after the decimal point,
    // return it as a Double to String
    // else return it as an Int to String
    return if (list[1].toInt() == 0){
        list[0]
    }else{
        this.toString()
    }
}

// Checking if a String is composed out of digit only characters
fun String.isStringDigit(): Boolean{
    val stringAsList = this

    for (char in stringAsList){
        if (!char.isDigit() && char != '.'){
            return false
        }
    }
    return true
}