package com.ecwid.warehouse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ecwid.warehouse.database.DatabaseQueryClass
import com.ecwid.warehouse.model.Product
import kotlinx.android.synthetic.main.activity_add_product.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class AddProductActivity : AppCompatActivity() {


    private lateinit var byteArray: ByteArray
    private val PICK_IMAGE = 1;

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            // I'M GETTING THE URI OF THE IMAGE AS DATA AND SETTING IT TO THE IMAGEVIEW
            val uri = data?.data
            val iStream: InputStream? = contentResolver.openInputStream(uri!!)
            byteArray = this.getBytes(iStream!!)!!
            imageView.setImageURI(uri)
        }

    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        imageView.setImageResource(R.drawable.ic_shopping_cart)

        imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        btnInsertData.setOnClickListener {
            val nameString = etName.text.toString()
            val coast = etCoast.text.toString()
            // val pathToImageString = dialog.etPathToImage.text.toString()
            val pathToImageString = ""

            if (true) {
                val product =
                    Product(-1, nameString, byteArray, coast)

                val databaseQueryClass = DatabaseQueryClass(this)

                val id = databaseQueryClass.insertProduct(product)

                if (id > 0) {
                    product.id = id

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    this.startActivity(intent)
                }
            }
        }


    }


}