package com.example.smsbankinganalitics.models

import com.example.smsbankinganalitics.R


enum class AssociationTerminal(val noAssociatedArray: Array<String>, val resId: Int) {

    PRODUCT_SHOPS(
        arrayOf(
            "gippo",
            "belvillesden",
            "santa",
            "evroopt",
            "sosedy",
            "zorina",
            "gipermarket",
        ), R.string.product_shops
    ),
    INTERNET_SHOPS(
        arrayOf(
            "wildberries",
            "ozon",
            "21 vek",
            "eBay",
            "aliexpress",
        ), R.string.internet_shops
    ),
    OTHER(arrayOf(), R.string.other),
    TRANSPORT(
        arrayOf(
            "uber",
            "yandex go",
            "taxi",
            "transport",
            "zapravka",
            "azs"
        ), R.string.transport
    );
}

