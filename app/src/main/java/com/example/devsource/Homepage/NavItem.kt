package com.example.devsource.Homepage

import android.widget.QuickContactBadge
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label:String,
    val icon:ImageVector,
    val badgeCount:Int
)
