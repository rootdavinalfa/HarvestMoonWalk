package xyz.dvnlabs.hm_btnwalk.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.hm_btnwalk.model.Characters
import xyz.dvnlabs.hm_btnwalk.utils.AssetParser

class CharacterViewModel(private val context: Context) : ViewModel(), KoinComponent {
    private val assetParser: AssetParser by inject { parametersOf(context) }

    val currentSelected : MutableLiveData<Int> = MutableLiveData()
    val normalCharaList: List<Characters> = getJSON("normal_chara")!!
    val maidenCharaList: List<Characters> = getJSON("maiden_chara")!!
    val spiritCharaList: List<Characters> = getJSON("spirit_chara")!!

    fun changeSelected(id : Int){
        currentSelected.value = id
    }

    private fun getJSON(arrayName: String): List<Characters>? {
        val data = assetParser.getJsonAssets("data/chara.json")
        try {
            if (data != null) {
                val json = JSONObject(data)
                return parseArrayChara(json.getJSONArray(arrayName))
            } else {
                throw Exception("Data not found!")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun parseArrayChara(array: JSONArray): List<Characters>? {
        try {
            val list: ArrayList<Characters> = ArrayList()
            for (i in 0 until array.length()) {
                val x = array.getJSONObject(i)
                val name = x.getString("name")
                val birthday = x.getString("birthday")
                val description = x.getString("description")
                val routine = x.getString("routine")
                val greatGift = x.getString("great_gift")
                val goodGift = x.getString("good_gift")
                val dislike = x.getString("dislike")
                val disgusting = x.getString("disgusting")
                list.add(
                    Characters(
                        name,
                        birthday,
                        description,
                        routine,
                        greatGift,
                        goodGift,
                        dislike,
                        disgusting
                    )
                )
            }
            return list
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

}