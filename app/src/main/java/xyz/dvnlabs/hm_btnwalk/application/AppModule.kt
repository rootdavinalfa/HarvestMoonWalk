package xyz.dvnlabs.hm_btnwalk.application

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import xyz.dvnlabs.hm_btnwalk.ui.viewmodel.CharacterViewModel
import xyz.dvnlabs.hm_btnwalk.ui.viewmodel.ToolViewModel
import xyz.dvnlabs.hm_btnwalk.utils.AssetParser

val appModule = module {
    //Here we need to inject module for the rest of application
    single { AssetParser(androidContext()) }
    viewModel { CharacterViewModel(androidContext()) }
    viewModel { ToolViewModel(androidContext()) }
}