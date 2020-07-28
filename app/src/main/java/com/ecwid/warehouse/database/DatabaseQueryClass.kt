package com.ecwid.warehouse.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import android.widget.Toast
import com.ecwid.warehouse.model.Product


import java.util.ArrayList

class DatabaseQueryClass(private val context: Context) {
    private val TAG = DatabaseQueryClass::class.java.simpleName

    val allProductList: ArrayList<Product>
        get() {

            val databaseHelper = DatabaseHelper.getInstance(context)
            val sqLiteDatabase = databaseHelper.readableDatabase

            var cursor: Cursor? = null

            try {
                cursor = sqLiteDatabase.query(Config.TABLE_WAREHOUSE, null, null, null, null, null, null, null)

                if (cursor != null)
                    if (cursor.moveToFirst()) {
                        val productList = ArrayList<Product>()
                        do {
                            val id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_ID))
                            val name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_NAME))
                            val pathToImage = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_PATH_TO_IMAGE))
                            val coast = cursor.getString(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_COAST))

                            productList.add(Product(id, name, pathToImage, coast))
                        } while (cursor.moveToNext())

                        return productList
                    }
            } catch (e: Exception) {
                Log.d(TAG, "Exception: " + e.message)
                Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show()
            } finally {
                cursor?.close()
                sqLiteDatabase.close()
            }

            return ArrayList()
        }

    fun insertProduct(product: Product): Long {

        var id: Long = -1
        val databaseHelper = DatabaseHelper.getInstance(context)
        val sqLiteDatabase = databaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Config.COLUMN_WAREHOUSE_NAME, product.name)
        contentValues.put(Config.COLUMN_WAREHOUSE_PATH_TO_IMAGE, product.pathToImage)
        contentValues.put(Config.COLUMN_WAREHOUSE_COAST, product.coast)

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_WAREHOUSE, null, contentValues)
        } catch (e: SQLiteException) {
            Log.d(TAG, "Exception: " + e.message)
            Toast.makeText(context, "Operation failed: " + e.message, Toast.LENGTH_LONG).show()
        } finally {
            sqLiteDatabase.close()
        }

        return id
    }

    fun getProductById(idOfProduct: Long): Product? {

        val databaseHelper = DatabaseHelper.getInstance(context)
        val sqLiteDatabase = databaseHelper.readableDatabase

        var cursor: Cursor? = null
        var product: Product? = null
        try {

            cursor = sqLiteDatabase.query(
                Config.TABLE_WAREHOUSE, null,
                Config.COLUMN_WAREHOUSE_ID + " = ? ", arrayOf(idOfProduct.toString()), null, null, null
            )

            if (cursor!!.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_ID))
                val name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_NAME))
                val pathToImage = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_PATH_TO_IMAGE))

                val coast = cursor.getString(cursor.getColumnIndex(Config.COLUMN_WAREHOUSE_COAST))

                product = Product(id, name, pathToImage, coast)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception: " + e.message)
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show()
        } finally {
            cursor?.close()
            sqLiteDatabase.close()
        }

        return product
    }

    fun updateProductInfo(product: Product): Long {

        var rowCount: Long = 0
        val databaseHelper = DatabaseHelper.getInstance(context)
        val sqLiteDatabase = databaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Config.COLUMN_WAREHOUSE_NAME, product.name)
        contentValues.put(Config.COLUMN_WAREHOUSE_PATH_TO_IMAGE, product.pathToImage)

        contentValues.put(Config.COLUMN_WAREHOUSE_COAST, product.coast)

        try {
            rowCount = sqLiteDatabase.update(
                Config.TABLE_WAREHOUSE, contentValues,
                Config.COLUMN_WAREHOUSE_ID + " = ? ",
                arrayOf(product.id.toString())
            ).toLong()
        } catch (e: SQLiteException) {
            Log.d(TAG, "Exception: " + e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        } finally {
            sqLiteDatabase.close()
        }

        return rowCount
    }

    fun deleteProductById(idOfProduct: Long): Long {
        var deletedRowCount: Long = -1
        val databaseHelper = DatabaseHelper.getInstance(context)
        val sqLiteDatabase = databaseHelper.writableDatabase

        try {
            deletedRowCount = sqLiteDatabase.delete(
                Config.TABLE_WAREHOUSE,
                Config.COLUMN_WAREHOUSE_ID + " = ? ",
                arrayOf(idOfProduct.toString())
            ).toLong()
        } catch (e: SQLiteException) {
            Log.d(TAG, "Exception: " + e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        } finally {
            sqLiteDatabase.close()
        }

        return deletedRowCount
    }



}