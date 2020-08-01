package com.ecwid.warehouse.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        val inflate = inflater.inflate(R.layout.fragment_product_detail, container, false)


        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong("id")?.let { viewModel.start(it) }


        viewModel.product.observe(viewLifecycleOwner, Observer {
            name.text = it.name

            val image0 = viewModel.product.value!!.image
            val bmp = BitmapFactory.decodeByteArray(image0, 0, image0!!.size)
            image.setImageBitmap(bmp)
            coast.text = it.coast.toString()

        })

    }




}
