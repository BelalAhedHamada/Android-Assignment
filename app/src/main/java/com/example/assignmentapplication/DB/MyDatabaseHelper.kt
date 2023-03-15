

package com.example.assignmentapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.assignmentapplication.com.example.assignmentapplication.Modle.SiteModel

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME " +
                    "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT, $COL_LINK TEXT, $COL_FOUNDER TEXT, $COL_YEAR TEXT, $COL_IMAGE TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addData(name: String, link: String, founder: String, year: String, image: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_LINK, link)
        values.put(COL_FOUNDER, founder)
        values.put(COL_YEAR, year)
        values.put(COL_IMAGE, image)
        val result = db.insert(TABLE_NAME, null, values)
        return result != -1L
    }

    fun getCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    @SuppressLint("Range")
    fun getAllData(): ArrayList<SiteModel> {
        val list = ArrayList<SiteModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                val link = cursor.getString(cursor.getColumnIndex(COL_LINK))
                val founder = cursor.getString(cursor.getColumnIndex(COL_FOUNDER))
                val year = cursor.getString(cursor.getColumnIndex(COL_YEAR))
                val image = cursor.getString(cursor.getColumnIndex(COL_IMAGE))

                val siteModel = SiteModel(id, name, link, founder, year, image)
                list.add(siteModel)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    fun deleteSite(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
        return result != -1
    }



    companion object {
        private const val DATABASE_NAME = "sites.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "sites"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_LINK = "link"
        const val COL_FOUNDER = "founder"
        const val COL_YEAR = "year"
        const val COL_IMAGE = "image"
    }
}
