package com.example.finalprojectcse226

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelperComplaint(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_NAME = "Complaintapp.db"
        private const val DATABASE_VERSION = 5
        private const val TABLE_NAME = "AllComplaint"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "Name"
        private const val COLUMN_REG = "Reg_No"
        private const val COLUMN_PHONE = "Phone_No"
        private const val COLUMN_SUB = "Sub"
        private const val COLUMN_DESC = "Desc"
        private const val TABLE_NAME1 = "ResolvedComplaint"

        private const val TABLE_NOTIFICATIONS = "Notifications"
        private const val COLUMN_NOTIFICATION_ID = "id"
        private const val COLUMN_NOTIFICATION_TITLE = "title"
        private const val COLUMN_NOTIFICATION_MESSAGE = "message"

        private const val TABLE_FEEDBACK = "Feedback"
        private const val COLUMN_FEEDBACK_ID = "id"
        private const val COLUMN_FEEDBACK_MESSAGE = "message"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createComplaintTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_REG TEXT,$COLUMN_PHONE TEXT,$COLUMN_SUB TEXT,$COLUMN_DESC TEXT)"
        db?.execSQL(createComplaintTableQuery)

        val createResolvedTableQuery = "CREATE TABLE $TABLE_NAME1($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_REG TEXT,$COLUMN_PHONE TEXT,$COLUMN_SUB TEXT,$COLUMN_DESC TEXT)"
        db?.execSQL(createResolvedTableQuery)

        val createNotificationsTableQuery = """
        CREATE TABLE $TABLE_NOTIFICATIONS(
            $COLUMN_NOTIFICATION_ID INTEGER PRIMARY KEY,
            $COLUMN_NOTIFICATION_TITLE TEXT,
            $COLUMN_NOTIFICATION_MESSAGE TEXT
        )"""
        db?.execSQL(createNotificationsTableQuery)

        val createFeedbackTableQuery = """
    CREATE TABLE $TABLE_FEEDBACK(
        $COLUMN_FEEDBACK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_FEEDBACK_MESSAGE TEXT
    )
"""
        db?.execSQL(createFeedbackTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 4) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME1")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTIFICATIONS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_FEEDBACK")
            onCreate(db)
        }
    }



    fun insertNote(complaint: Complaint){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, complaint.Name)
            put(COLUMN_REG, complaint.Reg_No)
            put(COLUMN_PHONE, complaint.Phone_No)
            put(COLUMN_SUB, complaint.Sub)
            put(COLUMN_DESC, complaint.Desc)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()

    }
    fun getAllComplaints(): List<Complaint> {
        val complaintList = mutableListOf<Complaint>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val regNo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REG))
                val phoneNo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                val sub = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUB))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))

                val complaint = Complaint(id, name, regNo, phoneNo, sub, desc)
                complaintList.add(complaint)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return complaintList
    }

    fun getComplaintById(complaintId: Int): Complaint? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $complaintId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val reg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REG))
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
        val sub = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUB))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
        cursor.close()
        db.close()
        return Complaint(id, name, reg, phone, sub, desc)
    }
    fun deleteComplaint(complaintId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(complaintId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun moveComplaintToResolved(complaint: Complaint) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, complaint.Name)
            put(COLUMN_REG, complaint.Reg_No)
            put(COLUMN_PHONE, complaint.Phone_No)
            put(COLUMN_SUB, complaint.Sub)
            put(COLUMN_DESC, complaint.Desc)
        }
        db.insert(TABLE_NAME1, null, contentValues)
        db.close()
    }

    fun getAllResolvedComplaints(): List<Complaint> {
        val resolvedList = mutableListOf<Complaint>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME1, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val regNo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REG))
            val phoneNo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            val subject = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUB))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
            resolvedList.add(Complaint(id, name, regNo, phoneNo, subject, description))
        }
        cursor.close()
        db.close()
        return resolvedList
    }

    // Insert a notification into the notifications table
    fun insertNotification(title: String, message: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOTIFICATION_TITLE, title)
            put(COLUMN_NOTIFICATION_MESSAGE, message)
        }
        val result = db.insert(TABLE_NOTIFICATIONS, null, values)
        db.close()

        if (result == -1L) {
            Log.e("DB", "Failed to insert notification")
        } else {
            Log.d("DB", "Notification inserted with ID: $result")
        }
    }

    // Get all notifications
    fun getAllNotifications(): List<Pair<String, String>> {
        val notifications = mutableListOf<Pair<String, String>>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NOTIFICATIONS, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTIFICATION_TITLE))
            val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTIFICATION_MESSAGE))
            notifications.add(Pair(title, message))
        }
        cursor.close()
        db.close()
        return notifications
    }

    fun insertFeedback(message: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FEEDBACK_MESSAGE, message)
        }
        db.insert(TABLE_FEEDBACK, null, values)
        db.close()
    }

    fun getAllFeedback(): List<String> {
        val feedbackList = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.query(TABLE_FEEDBACK, arrayOf(COLUMN_FEEDBACK_MESSAGE), null, null, null, null, null)
        while (cursor.moveToNext()) {
            feedbackList.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEEDBACK_MESSAGE)))
        }
        cursor.close()
        db.close()
        return feedbackList
    }

}