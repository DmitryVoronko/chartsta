package com.dmitriivoronko.chartsta.core.context

import android.content.Context
import android.content.pm.ApplicationInfo

val Context.isDebuggableApp: Boolean
    get() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0