package com.ecwid.warehouse.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ecwid.warehouse.R
import com.ecwid.warehouse.utils.LoadingState
import com.ecwid.warehouse.viewmodel.AddProductViewModel
import com.ecwid.warehouse.viewmodel.ProductDetailViewModel
import kotlinx.android.synthetic.main.fragment_product_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.nanoseconds


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

/*
        name.text = viewModel.product.value!!.name

        val image0 = viewModel.product.value!!.image
        val bmp = BitmapFactory.decodeByteArray(image0, 0, image0!!.size)
        image.setImageBitmap(bmp)*/


        viewModel.product.observe(viewLifecycleOwner, Observer {
            name.text = it.name

            val image0 = viewModel.product.value!!.image
            val bmp = BitmapFactory.decodeByteArray(image0, 0, image0!!.size)
            image.setImageBitmap(bmp)

        })

    }




}
