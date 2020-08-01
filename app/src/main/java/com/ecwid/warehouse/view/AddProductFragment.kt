package com.ecwid.warehouse.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ecwid.warehouse.R
import com.ecwid.warehouse.model.entity.Product
import com.ecwid.warehouse.viewmodel.AddProductViewModel
import kotlinx.android.synthetic.main.fragment_add_product.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream


class AddProductFragment : Fragment() {

    private val PICK_IMAGE = 1;
    private val addProductViewModel by viewModel<AddProductViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_product_save.setOnClickListener {

            addProductViewModel.insertUser(
                Product(
                    (addProductViewModel.getMaxId() + 1),
                    et_product_name.text.toString(),
                    getByteArrayFromImage(),
                    et_product_coast.text.toString().toDouble()
                )
            )

            findNavController().navigate(
                R.id.productsFragment
            )

        }

        iv_product_image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Выберете изображение"), PICK_IMAGE)

        }
    }

    private fun getByteArrayFromImage(): ByteArray? {
        val bitmap = (iv_product_image.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            val uri = data?.data
            iv_product_image.setImageURI(uri)
        }

    }

}