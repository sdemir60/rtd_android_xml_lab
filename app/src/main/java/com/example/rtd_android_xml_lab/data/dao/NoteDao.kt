package com.example.rtd_android_xml_lab.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rtd_android_xml_lab.data.entity.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>
}