package com.mtximai.AppRecycler.model

data class Email (
    val user: String,
    val subject: String,
    val preview: String,
    val date: String,
    val stared: Boolean,
    val unread: Boolean,
    var selected: Boolean = false
    )

class EmailBuilder {
    var user: String = ""
    var subject: String = ""
    var preview: String = ""
    var date: String = ""
    var stared: Boolean = false
    var unread: Boolean = false

    fun build() : Email = Email (user, subject, preview, date, stared, unread, false)

}

fun email(block: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(block).build()

fun fakeEmails(): MutableList<Email> = mutableListOf(
    email {
        user = "Facebook"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = false
    },
    email {
        user = "Yahoo"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = true
    },
    email {
        user = "Gmail"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = false
    },
    email {
        user = "Facebook"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = false
    },
    email {
        user = "Facebook"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = false
    },
    email {
        user = "Yahoo"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = true
    },
    email {
        user = "Gmail"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = false
    },
    email {
        user = "Facebook"
        subject = "subject as fas fas fas fa sds as fa a"
        preview = "preview a fs f asf asf ssa as f af"
        date = "26 jun"
        stared = false
    }

)

