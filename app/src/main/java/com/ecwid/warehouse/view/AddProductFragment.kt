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
import java.io.IOException
import java.io.InputStream


class AddProductFragment : Fragment() {

    private val PICK_IMAGE = 1;
    private lateinit var byteArray: ByteArray
    private val addProductViewModel by viewModel<AddProductViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_save.setOnClickListener {

            val id = addProductViewModel.getMaxId()

            var imageInByte: ByteArray?
            imageInByte = null
            if (choose_image.getDrawable() != null) {

                val bitmap = (choose_image.getDrawable() as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                imageInByte = baos.toByteArray()
            }


            val product =
                Product(
                    (id + 1),
                    species.text.toString(),
                    imageInByte,
                    coast.text.toString().toDouble()
                )

            addProductViewModel.insertUser(product)

            findNavController().navigate(
                R.id.productsFragment
            )

        }


        choose_image.setOnClickListener {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Выберете изображение"), PICK_IMAGE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            val uri = data?.data
            val iStream: InputStream? = requireActivity().contentResolver.openInputStream(uri!!)
            byteArray = this.getBytes(iStream!!)!!
            choose_image.setImageURI(uri)

        }

    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

}