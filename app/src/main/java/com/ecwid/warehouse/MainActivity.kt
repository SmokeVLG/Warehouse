package com.ecwid.warehouse

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecwid.warehouse.model.Product
import com.ecwid.warehouse.adapter.WarehouseListAdapter
import com.ecwid.warehouse.database.DatabaseQueryClass
import com.ecwid.warehouse.listener.OnItemClickListener
import com.ecwid.warehouse.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.dialog_add_product.*

class MainActivity : AppCompatActivity(), OnItemClickListener {


    private val mAdapter by lazy {
        WarehouseListAdapter(this, this)
    }
    private val databaseQueryClass = DatabaseQueryClass(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportActionBar?.title = getString(R.string.app_name)

        rvShowList.layoutManager = LinearLayoutManager(this)
        rvShowList.setHasFixedSize(true)
        rvShowList.adapter = mAdapter

        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                openDialog()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setContentView(R.layout.dialog_add_product)

        dialog.show()
        dialog.btnInsertData.setOnClickListener {
            val nameString = dialog.etName.text.toString()
            val coast = dialog.etCoast.text.toString()
            val pathToImageString = dialog.etPathToImage.text.toString()


            if (checkValidation(nameString, coast, pathToImageString)) {
                val product =
                    Product(-1, nameString, pathToImageString, coast)

                val databaseQueryClass = DatabaseQueryClass(this)

                val id = databaseQueryClass.insertProduct(product)

                if (id > 0) {
                    product.id = id
                    getData()
                    dialog.dismiss()
                }
            }
        }
        dialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun checkValidation(
        nameString: String,
        coast: String,
        pathToImage: String
    ): Boolean {
        return when {
            nameString.isEmpty() -> {
                toast(this, getString(R.string.enter_name))
                false
            }
            coast.isEmpty() -> {
                toast(this, getString(R.string.enter_coast))
                false
            }
            pathToImage.isEmpty() -> {
                toast(this, getString(R.string.enter_path_to_image))
                false
            }


            else -> {
                true
            }
        }
    }

    private fun getData() {
        mAdapter.setList(databaseQueryClass.allProductList)
    }


    override fun onEditClicked(pos: Int, student: Product) {
        onUpdateData(pos, student.id)
    }

    private fun onUpdateData(pos: Int, registrationNumber: Long) {

        val mStudent = databaseQueryClass.getProductById(registrationNumber)

        if (mStudent != null) {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setContentView(R.layout.dialog_add_product)
            dialog.btnInsertData.text = getString(R.string.update_data)
            dialog.etName.setText(mStudent.name)
            dialog.etCoast.setText(mStudent.coast)
            dialog.etPathToImage.setText(mStudent.pathToImage)

            dialog.show()

            dialog.btnInsertData.setOnClickListener {
                val nameString = dialog.etName.text.toString()
                val coast = dialog.etCoast.text.toString()
                val pathToImageString = dialog.etPathToImage.text.toString()


                if (checkValidation(nameString, coast, pathToImageString)) {
                    mStudent.name = nameString
                    mStudent.pathToImage = pathToImageString
                    mStudent.coast = coast

                    val id = databaseQueryClass.updateProductInfo(mStudent)

                    if (id > 0) {
                        mAdapter.update(pos, mStudent)
                        dialog.dismiss()

                    }
                }
            }
            dialog.btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        }
    }

    override fun onDeleteClicked(adapterPosition: Int, studentBean: Product) {
        val count = databaseQueryClass.deleteProductById(studentBean.id)

        if (count > 0) {
            mAdapter.removeAt(adapterPosition)

            Toast.makeText(this, getString(R.string.deleted_success), Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
    }
}