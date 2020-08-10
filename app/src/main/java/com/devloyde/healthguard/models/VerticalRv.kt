package com.devloyde.healthguard.models

import com.devloyde.healthguard.listeners.HomeDetailNavigationListener

data class VerticalRv(
    var title: String,
    var verticalItems: List<HealthCard>
)

