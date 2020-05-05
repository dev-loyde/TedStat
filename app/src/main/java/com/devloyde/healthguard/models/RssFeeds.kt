package com.devloyde.healthguard.models

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml
class WhoRssFeed{

    @Element
    lateinit var channel: RssChannel

//{
//
////    override fun toString(): String {
////        return "RssFeed [channel=$channel]"
////    }
}


@Xml
class RssChannel{
    @PropertyElement
    var title: String = ""

    @Element
    lateinit var item: List<RssItem>

//{
////    override fun toString(): String {
////        return "Channel [item=$item]"
////    }
}

@Xml
class RssItem {
    @PropertyElement
    var title: String = ""

    @PropertyElement
    var link: String = ""

    @PropertyElement
    var pubDate: String = ""

    @PropertyElement
    var description: String = ""

}
//{
//    var image: String?
//        get() {
//            if (description != null) {
//                if (description.contains("<img ")) {
//                    var imgLink: String = description.substring(description.indexOf("<img "))
//                    var cleanUp = imgLink.substring(0, imgLink.indexOf("1") + 1)
//                    imgLink = imgLink.substring(imgLink.indexOf("src=") + 5)
//                    var indexOf: Int = imgLink.indexOf("'")
//                    if (indexOf == -1) {
//                        indexOf = imgLink.indexOf("\"")
//                    }
//                    imgLink = imgLink.substring(0, indexOf)
//                    return imgLink
//                }
//            }
//            return null
//        }
//
//    override fun toString(): String {
//        return ("RssItem [title=" + title + ", link=" + link + ", image=" + image + ", pubDate=" + pubDate
//                + ", description=" + description + "]")
//    }
//}