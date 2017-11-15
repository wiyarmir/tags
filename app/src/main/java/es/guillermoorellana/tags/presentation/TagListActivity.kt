package es.guillermoorellana.tags.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import es.guillermoorellana.tags.R
import es.guillermoorellana.tags.domain.model.SelectedTag
import es.guillermoorellana.tags.domain.model.Tag
import kotlinx.android.synthetic.main.activity_tag_list.*
import kotlinx.android.synthetic.main.tag_list_content.view.*

class TagListActivity : AppCompatActivity() {

    private lateinit var listAdapter: TagListAdapter
    private lateinit var tagsAdapter: SelectedTagsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        val viewModel = ViewModelProviders.of(this)
                .get(TagListViewModel::class.java)


        listAdapter = TagListAdapter(onClickAction = viewModel::tapTag)
        tag_list.adapter = listAdapter

        tagsAdapter = SelectedTagsAdapter(onClickAction = viewModel::removeTag)
        selection_list.adapter = tagsAdapter
        selection_list.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)

        viewModel.data.observe(this, Observer {
            it?.let {
                listAdapter.tags = it.tags
                listAdapter.notifyDataSetChanged()

                tagsAdapter.tags = it.selectedTags
                tagsAdapter.notifyDataSetChanged()
            }
        })
    }

    class TagListAdapter(
            internal var tags: List<Tag> = emptyList(),
            private val onClickAction: (Int) -> Unit
    ) : RecyclerView.Adapter<TagListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tag_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = tags[position]
            holder.contentView.text = item.name
            holder.checkView.visibility = if (item.selected) View.VISIBLE else View.GONE
            holder.root.setOnClickListener {
                onClickAction(item.id)
            }
        }

        override fun getItemCount(): Int = tags.size

        inner class ViewHolder(
                val root: View,
                val contentView: TextView = root.content,
                val checkView: View = root.check
        ) : RecyclerView.ViewHolder(root)
    }

    class SelectedTagsAdapter(
            internal var tags: List<SelectedTag> = emptyList(),
            private val onClickAction: (Int) -> Unit
    ) : RecyclerView.Adapter<SelectedTagsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.selected_tags_content, parent, false)
            return ViewHolder(view as TextView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = tags[position]
            holder.root.text = item.name

            updateColor(holder.root.background, Color.parseColor("#${item.color}"))
            holder.root.setOnClickListener {
                onClickAction(item.id)
            }
        }

        private fun updateColor(background: Drawable?, color: Int) = when (background) {
            is ShapeDrawable -> background.paint.color = color
            is GradientDrawable -> background.setColor(color)
            is ColorDrawable -> background.color = color
            else -> {
            }
        }

        override fun getItemCount(): Int = tags.size

        inner class ViewHolder(
                val root: TextView
        ) : RecyclerView.ViewHolder(root)
    }
}
