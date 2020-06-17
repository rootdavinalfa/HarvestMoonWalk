package xyz.dvnlabs.hm_btnwalk.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.dvnlabs.hm_btnwalk.R
import xyz.dvnlabs.hm_btnwalk.base.BaseActivity
import xyz.dvnlabs.hm_btnwalk.databinding.MainActivityBinding
import xyz.dvnlabs.hm_btnwalk.ui.recyclerview.CharaListAdapter
import xyz.dvnlabs.hm_btnwalk.ui.viewmodel.CharacterViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding : MainActivityBinding
    private val charaVM : CharacterViewModel by viewModel()
    private var adapter : CharaListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBar(this, R.color.white)
        initLayout()
    }

    private fun initLayout(){
        val layoutManager = LinearLayoutManager(this)
        adapter = CharaListAdapter(R.layout.rv_chara)
        binding.charaList.layoutManager = layoutManager
        binding.charaList.adapter = adapter
        charaVM.changeSelected(R.id.selectNormal)
        charaVM.currentSelected.observe(this, Observer {
            when(it){
                R.id.selectMaiden ->{
                    adapter?.setCharacterList("maiden",charaVM.maidenCharaList)
                }
                R.id.selectNormal ->{
                    adapter?.setCharacterList("normal",charaVM.normalCharaList)
                }
                R.id.selectSpirit->{
                    adapter?.setCharacterList("spirit",charaVM.spiritCharaList)
                }
            }
        })

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            println("CHANGED TO: ${checkedId}")
            charaVM.changeSelected(checkedId)
        }


    }
}