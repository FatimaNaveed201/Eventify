package com.example.eventify

//Creating a data class to store the details of each event
data class EventDetails(
    val eventHeading: String,
    val teaser: String,
    val description: String,
    val price: String,
    val date: String,
    val imageResourceName: String,
    val ticketImageResourceName: String
)
