package com.ecwid.warehouse.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecwid.warehouse.R
import com.ecwid.warehouse.listener.OnItemClickListener
import com.ecwid.warehouse.model.Product
import kotlinx.android.synthetic.main.row_product_list.view.*


class WarehouseListAdapter(
    private var context: Context,
    private var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<WarehouseListAdapter.ViewHolder>() {

    private var mArrayList: ArrayList<Product> = ArrayList()

    fun setList(mArrayList: ArrayList<Product>) {
        this.mArrayList.clear()
        this.mArrayList = mArrayList
        notifyDataSetChanged()
    }

    fun update(pos: Int, studentBean: Product) {
        this.mArrayList[pos] = studentBean
        notifyItemChanged(pos)
    }

    fun removeAt(pos: Int) {
        this.mArrayList.removeAt(pos)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_product_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mArrayList[position])
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(productBean: Product) {
            itemView.tvName.text = productBean.name

            itemView.tvPathToImage.text = productBean.pathToImage
            itemView.tvProductCoast.text = productBean.coast

            itemView.ivDelete.setOnClickListener {
                onItemClickListener.onDeleteClicked(adapterPosition, productBean)
            }

            itemView.ivEdit.setOnClickListener {
                onItemClickListener.onEditClicked(adapterPosition, productBean)
            }
        }
    }
}
