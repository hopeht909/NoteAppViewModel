package com.example.noteappviewmodel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



//The class name must match the one we created before
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    //an object from NoteDoa Class so we can all it in the MainActivity
    abstract fun noteDao(): NoteDao

    // a constant code to activate the database
    companion object{
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database" // databaseName
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}