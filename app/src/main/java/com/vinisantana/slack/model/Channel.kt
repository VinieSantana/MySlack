package com.vinisantana.slack.model

class Channel( val document: String,
//               val users: ArrayList<String>,
               val users: String,
               val lastMessage: String? = null )