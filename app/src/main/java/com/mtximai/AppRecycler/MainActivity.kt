package com.mtximai.AppRecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mooveit.library.Fakeit
import com.mtximai.AppRecycler.model.email
import com.mtximai.AppRecycler.model.fakeEmails
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.email_item.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EmailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fakeit.init()

        setContentView(R.layout.activity_main)

        adapter = EmailAdapter(fakeEmails())

        recycler_view_main.adapter = adapter
        recycler_view_main.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            addEmail()
            recycler_view_main.scrollToPosition(0)
        }

        val helper = androidx.recyclerview.widget.ItemTouchHelper(
            ItemTouchHelper(
                androidx.recyclerview.widget.ItemTouchHelper.UP or androidx.recyclerview.widget.ItemTouchHelper.DOWN,
                androidx.recyclerview.widget.ItemTouchHelper.LEFT )
            )
        helper.attachToRecyclerView(recycler_view_main)
    }

    inner class ItemTouchHelper(dragDirs: Int, swipeDirs: Int) : androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback (
        dragDirs, swipeDirs
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition

            // altera a lista
            Collections.swap(adapter.emails, from, to)

            // altera a view
            adapter.notifyItemMoved(from, to)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }
    }


    fun addEmail() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt","BR")).parse (
            Fakeit.dateTime().dateFormatter()
        )

        adapter.emails.add(0, email{
            stared = false
            unread = true
            user = Fakeit.name().firstName()
            subject = Fakeit.company().name()
            date = SimpleDateFormat("d MMM", Locale("pt","BR")).format(sdf)
            preview = mutableListOf<String>().apply{
                repeat(10) {
                    add(Fakeit.lorem().words())
                }
            }.joinToString (" ")
        })

        adapter.notifyItemInserted(0)
    }

}
