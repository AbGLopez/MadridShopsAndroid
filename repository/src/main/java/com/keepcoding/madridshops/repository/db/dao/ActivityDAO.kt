package com.keepcoding.madridshops.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.keepcoding.madridshops.repository.db.DBConstants
import com.keepcoding.madridshops.repository.db.DBHelper
import com.keepcoding.madridshops.repository.model.ActivityEntity
import com.keepcoding.madridshops.repository.db.dao.DAOPersistable

/**
 * Se importa el DBHelper para poder conectar con la BD
 */
internal class ActivityDAO(
        val dbHelper: DBHelper
) : DAOPersistable<ActivityEntity> {
    private val dbReadOnlyConnection: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase

    override fun query(id: Long): ActivityEntity {
        val cursor = queryCursor(id)

        cursor.moveToFirst()

        return entityFromCursor(cursor)!!

    }

    override fun query(): List<ActivityEntity> {
        val queryResult = ArrayList<ActivityEntity>()

        val cursor = dbReadOnlyConnection.query(
                DBConstants.TABLE_ACTIVITY,
                DBConstants.ALL_COLUMNS_ACTIVITY,
                null,
                null,
                "",
                "",
                DBConstants.KEY_ACTIVITY_DATABASE_ID
        )

        while (cursor.moveToNext()){
            val result = entityFromCursor(cursor)!!

            queryResult.add(result)
        }

        return queryResult
    }

    fun entityFromCursor (cursor: Cursor): ActivityEntity? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return  null
        }

        return ActivityEntity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_DESCRIPTION_EN)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_DESCRIPTION_ES)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_OPENING_HOURS_EN)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_OPENING_HOURS_ES)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_TELEPHONE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_URL))
        )
    }

    override fun queryCursor(id: Long): Cursor {

        val cursor = dbReadOnlyConnection.query(
                DBConstants.TABLE_ACTIVITY,
                DBConstants.ALL_COLUMNS_ACTIVITY,
                DBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString()),
                "",
                "",
                DBConstants.KEY_ACTIVITY_DATABASE_ID
        )

        return cursor
    }

    /**
     * insert the element in DB
     */
    override fun insert(element: ActivityEntity): Long {
        var id: Long = 0

        id = dbReadWriteConnection.insert(
                DBConstants.TABLE_ACTIVITY,
                null,
                contentValues(element))


        return id
    }

     fun contentValues(activityEntity: ActivityEntity): ContentValues {
         val content = ContentValues()

         // El id de la BBDD lo genera automaticamente si no se le indica
         content.put(DBConstants.KEY_ACTIVITY_ID_JSON, activityEntity.id)
         content.put(DBConstants.KEY_ACTIVITY_NAME, activityEntity.name)
         content.put(DBConstants.KEY_ACTIVITY_DESCRIPTION_EN, activityEntity.description_en)
         content.put(DBConstants.KEY_ACTIVITY_DESCRIPTION_ES, activityEntity.description_es)
         content.put(DBConstants.KEY_ACTIVITY_LATITUDE, activityEntity.latitude)
         content.put(DBConstants.KEY_ACTIVITY_LONGITUDE, activityEntity.longitude)
         content.put(DBConstants.KEY_ACTIVITY_IMAGE_URL, activityEntity.img)
         content.put(DBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL, activityEntity.logo)
         content.put(DBConstants.KEY_ACTIVITY_ADDRESS, activityEntity.address)
         content.put(DBConstants.KEY_ACTIVITY_OPENING_HOURS_EN, activityEntity.openingHours_en)
         content.put(DBConstants.KEY_ACTIVITY_OPENING_HOURS_ES, activityEntity.openingHours_es)
         content.put(DBConstants.KEY_ACTIVITY_TELEPHONE, activityEntity.telephone)
         content.put(DBConstants.KEY_ACTIVITY_URL, activityEntity.url)

        return content
    }

    override fun update(id: Long, element: ActivityEntity): Long {
        val numberOfRecordsUpdate = dbReadWriteConnection.update(
                DBConstants.TABLE_ACTIVITY,
                contentValues(element),
                DBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString())
        )
        return numberOfRecordsUpdate.toLong()
    }

    /**
     * deletes the element passed from DB
     */
    override fun delete(element: ActivityEntity): Long {
        if (element.databaseId > 1) return 0

        return delete(element.databaseId)
    }

    /**
     * deletes the element with id from DB
     */
    override fun delete(id: Long): Long {

        return dbReadWriteConnection.delete(
                DBConstants.TABLE_ACTIVITY,
                DBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString())
        ).toLong()

    }

    override fun deleteAll(): Boolean {
        return dbReadWriteConnection.delete(
                DBConstants.TABLE_ACTIVITY,
                null,
                null
        ).toLong() >= 0
    }

}