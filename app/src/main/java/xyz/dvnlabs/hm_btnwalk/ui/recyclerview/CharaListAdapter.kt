package xyz.dvnlabs.hm_btnwalk.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.rv_chara.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.model.Characters
import xyz.dvnlabs.hm_btnwalk.utils.AssetParser

class CharaListAdapter(private val itemRes: Int) :
    RecyclerView.Adapter<CharaListAdapter.CharaListViewHolder>(), KoinComponent {
    private val assetParser: AssetParser by inject { parametersOf(context) }
    private var characters: List<Characters> = emptyList()
    private var context: Context? = null
    private var viewType: String? = null

    override fun onBindViewHolder(holder: CharaListViewHolder, position: Int) {
        when (viewType) {
            "normal" -> {
                holder.root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.lightBlue
                    )
                )
            }
            "maiden" -> {
                holder.root.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.pink))
            }
            "spirit" -> {
                holder.root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.lightYellow
                    )
                )
            }
        }
        val data = characters[position]
        holder.name.text = data.name
        holder.birthday.text = data.birthday

        var imageLocation = assetParser.getImageAsset("img/Character/${data.name}.png")
        if (viewType == "maiden") {
            imageLocation = assetParser.getImageAsset("img/Character/Waifu/${data.name}.png")
        }
        Glide.with(context!!)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .error(R.drawable.ic_agriculture)
            )
            .load(imageLocation)
            .transition(
                DrawableTransitionOptions()
                    .crossFade()
            )
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            ).into(holder.charaImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharaListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val view = inflater.inflate(itemRes, parent, false)
        return CharaListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    internal fun setCharacterList(type: String, character: List<Characters>) {
        println("ADAPTER TYPE: $type")
        val diffCallback = CharactersDiff(character, this.characters)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.characters = character
        this.viewType = type
        diffResult.dispatchUpdatesTo(this)
    }


    inner class CharaListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root = itemView.charaRoot
        val name = itemView.charaName
        val birthday = itemView.charaBirthday
        val charaImage = itemView.charaImage
    }

    inner class CharactersDiff(
        private val newList: List<Characters>,
        private val oldList: List<Characters>
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