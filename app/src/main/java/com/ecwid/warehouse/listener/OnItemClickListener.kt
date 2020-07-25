package com.ecwid.warehouse.listener

import com.ecwid.warehouse.model.Product


interface OnItemClickListener {
    fun onEditClicked(pos: Int, product: Product)
    fun onDeleteClicked(adapterPosition: Int, productBean: Product)
}