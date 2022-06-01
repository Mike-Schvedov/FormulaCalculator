package com.mikeschvedov.formulacalculator.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MemoryManager(
    var undoStack: Stack<String> = Stack<String>(),
    var redoStack: Stack<String> = Stack<String>()
) : Parcelable
