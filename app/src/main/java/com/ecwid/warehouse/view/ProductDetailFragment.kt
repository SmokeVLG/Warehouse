package com.ecwid.warehouse.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ecwid.warehouse.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong("id")?.let { viewModel.start(it) }


        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            if (product != null) {

                name.text = product.name
                image.setImageBitmap(BitmapFactory.decodeByteArray(product.image, 0, product.image!!.size))
                et_product_coast.text = product.coast.toString()
                btn_product_delete.setOnClickListener {
                    viewModel.deleteProduct()
                    findNavController().navigate(
                        R.id.productsFragment
                    )
                }
            }
        })

    }




}
