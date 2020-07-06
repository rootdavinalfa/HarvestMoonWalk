package xyz.dvnlabs.hm_btnwalk.ui.fragment

import android.os.Bundle
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
import xyz.dvnlabs.hm_btnwalk.databinding.FragmentToolDetailBinding
import xyz.dvnlabs.hm_btnwalk.ui.viewmodel.ToolViewModel
import xyz.dvnlabs.hm_btnwalk.utils.AssetParser

class ToolDetail : FragmentHost() {
    private val assetParser: AssetParser by inject()
    private var binding : FragmentToolDetailBinding? = null
    private val args: ToolDetailArgs by navArgs()
    private val toolVM : ToolViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tool_detail,container,false)
        binding = FragmentToolDetailBinding.bind(view)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val index = args.selectedIndex
        val tool = toolVM.toolsList[index]
        binding?.toolDetailMoney?.text = tool.price
        binding?.toolDetailName?.text = tool.name
        binding?.toolDetailHow2Get?.text = tool.how2get
        val description = if (tool.description.isEmpty()){
            "No detail provided!"
        }else{
            tool.description
        }
        binding?.toolDetailTextDetail?.text = description
        binding?.toolDetailUpgrade?.text = tool.upgrade
        val imageData: ByteArray? = assetParser.getImageAsset("img/Tools/${tool.name}.jpg")
        binding?.toolDetailImage?.let {
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
                ).into(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}