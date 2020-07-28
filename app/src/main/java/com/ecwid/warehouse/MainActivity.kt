package com.ecwid.warehouse

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecwid.warehouse.adapter.WarehouseListAdapter
import com.ecwid.warehouse.database.DatabaseQueryClass
import com.ecwid.warehouse.listener.OnItemClickListener
import com.ecwid.warehouse.model.Product
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class MainActivity : AppCompatActivity(), OnItemClickListener {


    private val databaseQueryClass = DatabaseQueryClass(this)

    private val mAdapter by lazy {
        WarehouseListAdapter(this, this)
    }

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
                //openDialog()
                try {
                    val intent = Intent(this, AddProductActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    this.startActivity(intent)

                } catch (e: Exception) {
                    // Nothing to do
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        mAdapter.setList(databaseQueryClass.allProductList)
    }







    override fun onEditClicked(pos: Int, product: Product) {
       // TODO("Not yet implemented")
           // var i = 0;
    }


    override fun onDeleteClicked(adapterPosition: Int, productBean: Product) {
        val count = databaseQueryClass.deleteProductById(productBean.id)

        if (count > 0) {
            mAdapter.removeAt(adapterPosition)

            Toast.makeText(this, getString(R.string.deleted_success), Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
    }


}