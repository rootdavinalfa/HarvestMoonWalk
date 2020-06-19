package xyz.dvnlabs.hm_btnwalk.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.hm_btnwalk.model.Tools
import xyz.dvnlabs.hm_btnwalk.utils.AssetParser

class ToolViewModel(private val context: Context) : ViewModel(), KoinComponent {
    private val assetParser: AssetParser by inject { parametersOf(context) }
    val toolsList: List<Tools> = getJSON()!!


    private fun getJSON(): List<Tools>? {
        val data = assetParser.getJsonAssets("data/tools.json")
        try {
            if (data != null) {
                val json = JSONObject(data)
                return parseJson(json.getJSONArray("tools"))
            } else {
                throw Exception("Data not found!")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun parseJson(array: JSONArray): List<Tools>? {
        try {
            val list: ArrayList<Tools> = ArrayList()
            for (i in 0 until array.length()) {
                val x = array.getJSONObject(i)
                val name = x.getString("name")
                val description = x.getString("description")
                val how2get = x.getString("how2get")
                val price = x.getString("price")
                val upgrade = x.getString("upgrade")
                list.add(
                    Tools(
                        name, description, how2get, price, upgrade
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