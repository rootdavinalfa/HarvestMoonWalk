package xyz.dvnlabs.hm_btnwalk.ui.fragment

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import xyz.dvnlabs.hm_btnwalk.R

open class FragmentHost : Fragment() {
    private val appBarConfig = AppBarConfiguration(setOf(R.id.fragmentMenu))
    private lateinit var toolbar: Toolbar

    override fun onStart() {
        super.onStart()
        // setup navigation with toolbar
        toolbar = requireActivity().findViewById(R.id.dashboardToolbar)
        val navController = requireActivity().findNavController(R.id.dashboardNavHost)
        visibilityNavElements(navController)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfig)
    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentMenu -> toolbar.visibility = View.GONE
                R.id.characterList -> toolbar.visibility = View.GONE
                else -> toolbar.visibility = View.VISIBLE
            }
        }
    }

}