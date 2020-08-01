package com.ecwid.warehouse.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ecwid.warehouse.R
import com.ecwid.warehouse.model.entity.Product
import com.ecwid.warehouse.utils.getByteArrayFromImage
import com.ecwid.warehouse.viewmodel.ProductDetailViewModel
import kotlinx.android.synthetic.main.fragment_product_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductDetailFragment : Fragment() {



    private val viewModel by viewModel<ProductDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLong("id")?.let { viewModel.start(it) }


        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            if (product != null) {
                et_product_name?.setText(product.name)
                iv_product_image.setImageBitmap(BitmapFactory.decodeByteArray(product.image, 0, product.image!!.size))
                et_product_coast?.setText(product.coast.toString())
                btn_product_delete.setOnClickListener {
                    viewModel.deleteProduct()
                    findNavController().navigate(
                        R.id.productsFragment
                    )
                }
                fab_product_save.setOnClickListener {

                   val productTemp = Product(product.id,et_product_name.text.toString(),getByteArrayFromImage(iv_product_image.drawable.toBitmap()),et_product_coast.text.toString().toDouble() )

                    viewModel.updateProduct(productTemp)
                    findNavController().navigate(
                        R.id.productsFragment
                    )
                }


            }
        })

    }




}
