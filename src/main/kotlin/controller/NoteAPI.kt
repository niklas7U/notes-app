package controller

import models.Note
import persistence.Serializer
import utils.IndexChecker.isValidIndex
import kotlin.jvm.Throws

@Suppress("UNCHECKED_CAST")
class NoteAPI(serializerType: Serializer){
    private var serializer: Serializer = serializerType
    private var notes = ArrayList<Note>()

    // main functions
    fun add(note: Note): Boolean =
        notes.add(note)

    fun listAllNotes(): String =
        if (notes.isEmpty()) {
            "No notes stored"
        } else {
            formatListString(notes)
        /*var listOfNotes = ""
        for (i in notes.indices) {
            listOfNotes += "${i}: ${notes[i]} \n"
        }
        listOfNotes*/
    }
    fun listActiveNotes(): String =
        if (numberOfActiveNotes() == 0) {
            "No active notes stored"
        } else {
            formatListString(notes.filter { !it.isNoteArchived})
        }
    fun listArchivedNotes(): String =
        if (numberOfArchivedNotes() == 0) {
            "No archived notes stored"
        } else {
            formatListString(notes.filter { it.isNoteArchived})
        }
    fun listDoneNotes(): String =
        if (numberOfDoneNotes() == 0) {
            "No done notes yet"
        } else {
            formatListString(notes.filter {it.isNoteDone})
        }
    fun listNotesBySelectedPriority(priority: Int): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            if (numberOfNotesByPriority(priority) == 0) {
                "no notes with priority: $priority"
            } else {
                formatListString(notes.filter { it.notePriority == priority })
            }
            // obsolete:
            /*val notesByPrio =
            notesByPrio.ifEmpty { */
        }
    }

    fun deleteNote(indexToDelete: Int): Note? =
        if (isValidIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveNote(indexToArchive: Int): Boolean {
        val foundNote = findNote(indexToArchive)
        return if (foundNote != null && !foundNote.isNoteArchived) {
            foundNote.isNoteArchived = true
            true
        } else {
            false
        }
    }

    fun doneNote(indexToBeDone: Int): Boolean {
        val foundNote = findNote(indexToBeDone)
        return if (foundNote != null && !foundNote.isNoteDone) {
            foundNote.isNoteDone = true
            true
        } else {
            false
        }
    }

    fun searchByTitle(noteTitle: String) : String =
        formatListString(notes.filter { it.noteTitle.lowercase().contains(noteTitle.lowercase()) })

    // Counting functions
    fun numberOfNotes(): Int =
        notes.size
    fun numberOfArchivedNotes(): Int =
        countListToInt(notes.filter { it.isNoteArchived })
    fun numberOfActiveNotes(): Int =
        countListToInt(notes.filter{!it.isNoteArchived})
    fun numberOfDoneNotes(): Int =
        countListToInt(notes.filter{it.isNoteDone})
    fun numberOfNotesByPriority(priority: Int): Int =
        countListToInt(notes.filter{it.notePriority == priority})

    // helper
    fun getNotes(): ArrayList<Note> {
        return notes
    }
    fun findNote(index: Int): Note? =
        if (isValidIndex(index, notes)) {
            notes[index]
        } else null

    private fun formatListString(notesToFormat : List<Note>) : String =
        notesToFormat.joinToString{ "\n${notes.indexOf(it)} : $it" }
    private fun countListToInt(notesToCount: List<Note>) : Int =
        notesToCount.stream().count().toInt()

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note> /* = java.util.ArrayList<models.Note> */
    }
    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }
}
