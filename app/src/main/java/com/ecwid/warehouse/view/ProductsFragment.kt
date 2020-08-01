package com.ecwid.warehouse.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecwid.warehouse.R
import com.ecwid.warehouse.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_products.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : Fragment() ,
    RecyclerAdapter.ProductItemListener {

    companion object {
        private const val TAG: String = "ProductsFragment"
    }

    private val productsViewModel by viewModel<ProductsViewModel>()

    private val adapter by lazy {
        RecyclerAdapter(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        subscribeObservers()

        fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_productsFragment_to_addProductFragment
            )

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }
    private fun subscribeObservers() {
        //Если дата изменилась, то обновляем ресайкл вью
        productsViewModel.data.observe(viewLifecycleOwner, Observer { products ->
            adapter.setProducts(products)
            adapter.notifyDataSetChanged()
        })

    }

    private fun initRecycler() {

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
    }

    override fun onClickedProduct(productId: Long) {
        findNavController().navigate(
            R.id.action_productsFragment_to_productDetailFragment,
            bundleOf("id" to productId)
        )
    }

}