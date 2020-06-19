package xyz.dvnlabs.hm_btnwalk.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.databinding.FragmentToolListBinding
import xyz.dvnlabs.hm_btnwalk.ui.recyclerview.ToolListAdapter
import xyz.dvnlabs.hm_btnwalk.ui.view.AutoGridLayoutManager
import xyz.dvnlabs.hm_btnwalk.ui.viewmodel.ToolViewModel

class ToolList : FragmentHost() {
    private var binding : FragmentToolListBinding? = null
    private val toolVM : ToolViewModel by sharedViewModel()
    private var adapter : ToolListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToolListBinding.bind(inflater.inflate(R.layout.fragment_tool_list,container,false))
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        adapter = ToolListAdapter(R.layout.rv_tool)
        val layoutManager = AutoGridLayoutManager(requireContext(),500)
        binding?.toolList?.layoutManager = layoutManager
        binding?.toolList?.adapter = adapter
        adapter?.setCharacterList(toolVM.toolsList)
    }

}