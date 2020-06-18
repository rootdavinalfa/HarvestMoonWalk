package xyz.dvnlabs.hm_btnwalk.ui.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.databinding.FragmentCharaDetailBinding
import xyz.dvnlabs.hm_btnwalk.model.Characters
import xyz.dvnlabs.hm_btnwalk.ui.viewmodel.CharacterViewModel
import xyz.dvnlabs.hm_btnwalk.utils.AssetParser
import java.util.*

class CharacterDetail : FragmentHost() {
    private val assetParser: AssetParser by inject()
    private val args: CharacterDetailArgs by navArgs()
    private val charaVM: CharacterViewModel by sharedViewModel()
    private var currentData: Characters? = null
    private var binding: FragmentCharaDetailBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharaDetailBinding.bind(
            inflater.inflate(
                R.layout.fragment_chara_detail,
                container,
                false
            )
        )
        return binding!!.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (args.charaType) {
            "maiden" -> {
                //binding!!.root.background = ContextCompat.getDrawable(requireContext(),R.color.pink)
                binding!!.charaDetailType.setChipBackgroundColorResource(R.color.darkPink)
                currentData = charaVM.maidenCharaList[args.index]
            }
            "normal" -> {
                //binding!!.root.background = ContextCompat.getDrawable(requireContext(),R.color.lightBlue)
                binding!!.charaDetailType.setChipBackgroundColorResource(R.color.darkBlue)
                currentData = charaVM.normalCharaList[args.index]
            }
            "spirit" -> {
                //binding!!.root.background = ContextCompat.getDrawable(requireContext(),R.color.lightYellow)
                binding!!.charaDetailType.setChipBackgroundColorResource(R.color.darkYellow)
                currentData = charaVM.spiritCharaList[args.index]
            }
        }
        binding!!.charaDetailDescription.movementMethod = ScrollingMovementMethod()
        binding!!.charaDetailBirthday.text = currentData?.birthday
        binding!!.charaDetailName.text = currentData?.name
        binding!!.charaDetailType.text = args.charaType.capitalize(Locale.ROOT)
        binding!!.charaDetailDescription.text = currentData?.description
        binding!!.charaDetailGreat.text = currentData?.greatGift
        binding!!.charaDetailGood.text = currentData?.goodGift
        binding!!.charaDetailDislike.text = currentData?.dislike
        binding!!.charaDetailDisgusting.text = currentData?.disgusting
        binding!!.charaDetailRoutine.text = currentData?.routine
        var imageData: ByteArray? = null
        when (args.charaType) {
            "maiden" -> {
                imageData =
                    assetParser.getImageAsset("img/Character/Waifu/${currentData?.name}.png")
            }
            "normal" -> {
                imageData = assetParser.getImageAsset("img/Character/${currentData?.name}.png")
            }
            "spirit" -> {
                imageData =
                    assetParser.getImageAsset("img/Character/Spirit/${currentData?.name}.png")
            }

        }
        Glide.with(requireContext())
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
            ).into(binding!!.charaDetailImage)
        super.onViewCreated(view, savedInstanceState)
    }
}