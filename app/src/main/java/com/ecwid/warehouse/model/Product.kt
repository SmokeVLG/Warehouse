package com.ecwid.warehouse.model

class Product(id: Int, var name: String?, var pathToImage: ByteArray, var coast: String?) {
    var id: Long = 0

    init {
        this.id = id.toLong()
    }
}
