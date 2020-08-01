package com.ecwid.warehouse.view

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecwid.warehouse.databinding.ItemProductBinding
import com.ecwid.warehouse.model.entity.Product
import kotlinx.android.synthetic.main.item_product.view.*
import java.util.*


class RecyclerAdapter(private val listener: ProductItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var products: List<Product> = ArrayList()

    interface ProductItemListener {
        fun onClickedProduct(productId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductBinding =
            ItemProductBinding.inflate(
                layoutInflater, parent, false
            )
        return ViewHolder(
            binding, listener
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ViewHolder).bind(products[position])

        val bitmap = BitmapFactory.decodeByteArray(
            products[position].image,
            0,
            products[position].image!!.size
        )

        holder.itemView.iv_product_image.setImageBitmap(bitmap)
        holder.itemView.et_product_coast.text = products[position].coast.toString()


    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(products: List<Product>) {
        this.products = products
    }

    /**
     * Inner ViewHolder class
     */
    class ViewHolder(
        private val binding: ItemProductBinding,
        private val listener: ProductItemListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var product: Product
        init {
            binding.root.setOnClickListener(this)
        }
        fun bind(product: Product) {
            this.product = product
            binding.product = product
        }

        override fun onClick(v: View?) {
            listener.onClickedProduct(product.id)
        }

    }


}