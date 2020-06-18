package xyz.dvnlabs.hm_btnwalk.ui.activity

import android.os.Bundle
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.base.BaseActivity
import xyz.dvnlabs.hm_btnwalk.databinding.MainActivityBinding

class MainActivity : BaseActivity() {
    private lateinit var binding : MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBar(this, R.color.white)
    }
}