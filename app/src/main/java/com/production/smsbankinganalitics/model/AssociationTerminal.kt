package com.production.smsbankinganalitics.model

import com.production.smsbankinganalitics.R


enum class AssociationTerminal(val noAssociatedArray: Array<String>, val resId: Int) {
    MARKETS(
        arrayOf(
            "almi",
            "akd",
            "belmarket",
            "brt",
            "gippo",
            "ganna",
            "groshyk",
            "belvillesden",
            "dobronom",
            "kopeechka",
            "santa",
            "spar",
            "svetofor",
            "centraln",
            "green",
            "evroopt",
            "khit",
            "vitalur",
            "sosedi",
            "martinn",
            "mila",
            "prostore",
            "prodtovary",
            "perekrestok",
            "korona",
            "zorina",
            "gipermarket",
            "tri tseny"
        ), R.string.product_shops
    ),
    INTERNET_SHOPS(
        arrayOf(
            "goldapple",
            "wildberries",
            "ozon",
            "21 vek",
            "eBay",
            "aliexpress",
        ), R.string.internet_shops
    ),
    REST(
        arrayOf(
            "food",
            "kafe",
            "bar",
            "restoran",
            "coff",
        ), R.string.rest
    ),
    TRANSPORT(
        arrayOf(
            "uber",
            "yandex go",
            "taxi",
            "transport",
            "zapravka",
            "azs"
        ), R.string.transport
    ),
    HEALTH(
        arrayOf(
            "apteka",
            "doktor",
            "sinevo",
            "farm",
            "lode",
            "med",
        ), R.string.health
    ),
    OTHER(arrayOf(), R.string.other);
}

