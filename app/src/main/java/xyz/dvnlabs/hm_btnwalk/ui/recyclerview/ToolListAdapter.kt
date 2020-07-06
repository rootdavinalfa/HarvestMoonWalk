package xyz.dvnlabs.hm_btnwalk.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.rv_tool.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.model.Tools
import xyz.dvnlabs.hm_btnwalk.ui.fragment.ToolListDirections
import xyz.dvnlabs.hm_btnwalk.utils.AssetParser

class ToolListAdapter(private val itemRes: Int) :
    RecyclerView.Adapter<ToolListAdapter.ToolListViewHolder>(), KoinComponent {
    private val assetParser: AssetParser by inject { parametersOf(context) }
    private var tools: List<Tools> = emptyList()
    private var context: Context? = null

    override fun onBindViewHolder(holder: ToolListViewHolder, position: Int) {
        val data = tools[position]
        holder.name.text = data.name
        val imageData = assetParser.getImageAsset("img/Tools/${data.name}.jpg")
        Glide.with(context!!)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .error(R.drawable.ic_agriculture)
            )
            .load(imageData)
            .transition(
                DrawableTransitionOptions()
                    .crossFade()
            )
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
            ).into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val view = inflater.inflate(itemRes, parent, false)
        return ToolListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tools.size
    }

    internal fun setCharacterList( tools: List<Tools>) {
        val diffCallback = CharactersDiff(tools, this.tools)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.tools = tools
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ToolListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val name = itemView.toolName
        val image = itemView.toolImage

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val navController = itemView.findNavController()
            val action = ToolListDirections.actionToolListFragmentToToolDetail()
                .setSelectedIndex(adapterPosition)
            navController.navigate(action)
        }
    }

    inner class CharactersDiff(
        private val newList: List<Tools>,
        private val oldList: List<Tools>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        @Nullable
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }

    }
}