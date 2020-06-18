package xyz.dvnlabs.hm_btnwalk.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.databinding.FragmentCharaListBinding
import xyz.dvnlabs.hm_btnwalk.ui.recyclerview.CharaListAdapter
import xyz.dvnlabs.hm_btnwalk.ui.viewmodel.CharacterViewModel

class CharacterList : Fragment() {
    private var binding: FragmentCharaListBinding? = null
    private val charaVM: CharacterViewModel by sharedViewModel()
    private var adapter: CharaListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharaListBinding.bind(
            inflater.inflate(
                R.layout.fragment_chara_list,
                container,
                false
            )
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        val navController = requireActivity().findNavController(R.id.dashboardNavHost)
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = CharaListAdapter(R.layout.rv_chara)
        binding?.charaList?.layoutManager = layoutManager
        binding?.charaList?.adapter = adapter
        charaVM.changeSelected(R.id.selectNormal)
        charaVM.currentSelected.observe(viewLifecycleOwner, Observer {
            when (it) {
                R.id.selectMaiden -> {
                    adapter?.setCharacterList("maiden", charaVM.maidenCharaList)
                }
                R.id.selectNormal -> {
                    adapter?.setCharacterList("normal", charaVM.normalCharaList)
                }
                R.id.selectSpirit -> {
                    adapter?.setCharacterList("spirit", charaVM.spiritCharaList)
                }
            }
        })

        binding?.chipGroup?.setOnCheckedChangeListener { group, checkedId ->
            charaVM.changeSelected(checkedId)
        }

        binding?.charaBack?.setOnClickListener {
            navController.navigateUp()
        }
    }
}