package com.mtximai.AppRecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.view.ActionMode
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

// 16/03/20
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EmailAdapter
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fakeit.init()

        setContentView(R.layout.activity_main)

        adapter = EmailAdapter(fakeEmails())

        recycler_view_main.adapter = adapter
        recycler_view_main.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            addEmail()

            // Execute scroll after item inserted
            recycler_view_main.scrollToPosition(0)
        }

        val helper = androidx.recyclerview.widget.ItemTouchHelper(
            ItemTouchHelper(
                androidx.recyclerview.widget.ItemTouchHelper.UP or androidx.recyclerview.widget.ItemTouchHelper.DOWN,
                androidx.recyclerview.widget.ItemTouchHelper.LEFT
            )
        )

        helper.attachToRecyclerView(recycler_view_main)

        adapter.onItemClick = {
            Log.i("mylog","onItemClick")
            enableActionMode(it)
        }

        adapter.onItemLongClick = {
            Log.i("mylog","onItemLongClick")
            enableActionMode(it)
        }

    }

    private  fun enableActionMode(position: Int) {
        if (actionMode == null)
            actionMode = startSupportActionMode(object: ActionMode.Callback {
                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    if (item?.itemId == R.id.action_delete) {
                        adapter.deleteEmails()
                        mode?.finish()
                        return true
                    }
                    return false
                }

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    adapter.selectedItems.clear()
                    adapter.emails
                        .filter { it.selected }
                        .forEach {it.selected = false}

                    adapter.notifyDataSetChanged()
                    actionMode = null
                }

            })

        adapter.toggleSelection(position)
        val size: Int = adapter.selectedItems.size()

        if (size == 0) {
            actionMode?.finish()
        } else {
            actionMode?.title = "$size"
            actionMode?.invalidate()
        }




    }


    inner class ItemTouchHelper(dragDirs: Int, swipeDirs: Int) :
        androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(
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

        // 17/03/20
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            adapter.emails.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    private fun addEmail() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).parse(
            Fakeit.dateTime().dateFormatter()
        )

        // Add a email using index 0
        adapter.emails.add(0, email {
            stared = false
            unread = true
            user = Fakeit.name().firstName()
            subject = Fakeit.company().name()
            date = SimpleDateFormat("d MMM", Locale("pt", "BR")).format(sdf)

            preview = mutableListOf<String>().apply {
                repeat(10) {
                    add(Fakeit.lorem().words())
                }
            }.joinToString(" ")
        })

        // Reload only one item
        adapter.notifyItemInserted(0)
    }

}
