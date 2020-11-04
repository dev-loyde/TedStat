package com.devloyde.tedstat.models

data class HorizontalSingle(var title: String,var link: String?,var image: Int)

data class HorizontalBanner(var title: String,var link: String?,var image: Int)

data class HorizontalRv(var title: String?, var horizontalItems: ArrayList<HorizontalSingle>)

data class InfoRv(var title: String?, var infoItems: List<Any>)

data class Welcome(var img: Int,var title:String,var description: String)

