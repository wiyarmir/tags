package es.guillermoorellana.tags.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.guillermoorellana.tags.R
import es.guillermoorellana.tags.data.Tag
import kotlinx.android.synthetic.main.activity_place_list.*
import kotlinx.android.synthetic.main.place_list.*
import kotlinx.android.synthetic.main.place_list_content.view.*

class TagListActivity : AppCompatActivity() {

    private val adapter by lazy { StateAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        ViewModelProviders.of(this)
                .get(TagListViewModel::class.java)
                .data
                .observe(this, Observer {
                    Log.d("Activity", it.toString())
                    adapter.values = it!!
                    adapter.notifyDataSetChanged()
                })

        setupRecyclerView(place_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    class StateAdapter(
            internal var values: List<Tag> = emptyList()
    ) : RecyclerView.Adapter<StateAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.place_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.id.toString()
            holder.contentView.text = item.tag
        }

        override fun getItemCount(): Int = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}
