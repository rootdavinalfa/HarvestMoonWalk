package xyz.dvnlabs.hm_btnwalk.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
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
import xyz.dvnlabs.hm_btnwalk.ui.fragment.CharacterListDirections
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

        var imageData: ByteArray? = null
        when (viewType) {
            "maiden" -> {
                imageData = assetParser.getImageAsset("img/Character/Waifu/${data.name}.png")
            }
            "normal" -> {
                imageData = assetParser.getImageAsset("img/Character/${data.name}.png")
            }
            "spirit" -> {
                imageData = assetParser.getImageAsset("img/Character/Spirit/${data.name}.png")
            }

        }
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
        val diffCallback = CharactersDiff(character, this.characters)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.characters = character
        this.viewType = type
        diffResult.dispatchUpdatesTo(this)
    }


    inner class CharaListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val root = itemView.charaRoot
        val name = itemView.charaName
        val birthday = itemView.charaBirthday
        val charaImage = itemView.charaImage

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val navController = itemView.findNavController()
            val action = CharacterListDirections.actionCharacterListToCharacterDetail()
                .setCharaType(viewType!!)
                .setIndex(adapterPosition)
            navController.navigate(action)
        }
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