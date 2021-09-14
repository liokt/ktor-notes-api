package com.liot.data

import com.liot.data.collections.Note
import com.liot.data.collections.User
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("NotesDatabase")
private val users = database.getCollection<User>()
private val notes = database.getCollection<Note>()

suspend fun registerUser(user: User): Boolean {
    return users.insertOne(user).wasAcknowledged()
}
suspend fun checkIfUserExists(email: String): Boolean {
    //Compares the "email of each user (User::email)" with the email we passed
    return users.findOne(User::email eq email) != null
}
