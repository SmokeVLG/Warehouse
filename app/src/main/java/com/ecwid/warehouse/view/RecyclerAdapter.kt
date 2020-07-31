package com.ecwid.warehouse.view

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecwid.warehouse.databinding.ItemCharacterBinding
import com.ecwid.warehouse.model.entity.Product
import kotlinx.android.synthetic.main.item_character.view.*
import java.util.*


class RecyclerAdapter(private val listener: ProductItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var products: List<Product> = ArrayList()

    interface ProductItemListener {
        fun onClickedProduct(productId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterBinding =
            ItemCharacterBinding.inflate(
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

        holder.itemView.image.setImageBitmap(bitmap)



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
        private val binding: ItemCharacterBinding,
        private val listener: ProductItemListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var product: Product
        init {
            binding.root.setOnClickListener(this)
        }
        fun bind(product: Product) {
            this.product = product
            binding.user = product
        }

        override fun onClick(v: View?) {
            listener.onClickedProduct(product.id)
        }

    }


}