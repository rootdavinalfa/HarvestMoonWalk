package xyz.dvnlabs.hm_btnwalk.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.databinding.FragmentMenuBinding

class FragmentMenu : FragmentHost() {
    private var binding: FragmentMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentMenuBinding.bind(inflater.inflate(R.layout.fragment_menu, container, false))
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = requireActivity().findNavController(R.id.dashboardNavHost)
        binding!!.characterMenu.setOnClickListener {
            navController.navigate(R.id.action_fragmentMenu_to_characterList)
        }
        binding!!.about.setOnClickListener {
            navController.navigate(R.id.action_fragmentMenu_to_about2)
        }

    }
}