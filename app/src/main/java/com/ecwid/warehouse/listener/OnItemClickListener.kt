package com.ecwid.warehouse.listener

import com.ecwid.warehouse.model.Product


interface OnItemClickListener {
    fun onEditClicked(pos: Int, student: Product)

    fun onDeleteClicked(adapterPosition: Int, studentBean: Product)
}