/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * https://dvnlabs.xyz All right reserved
 * Class Helper for AssetParser
 */


package xyz.dvnlabs.hm_btnwalk.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class AssetParser(private val context: Context) {
    /**
     * Asset Parser for getting Image in ByteArray,use it with your favorite ImageLoader
     *
     *  [context] requester context
     *
     *  [filename] location of file
     *
     * */
    fun getImageAsset(filename: String): ByteArray? {
        return try {
            val `is`: InputStream = context.assets.open(filename)

            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            buffer
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Asset Parser for getting Json String will returning jsonString
     *
     * [context] requester context
     *
     * [filename] location of file
     *
     * */
    fun getJsonAssets(filename: String): String? {
        val jsonString: String
        try {
            val `is`: InputStream = context.assets.open(filename)

            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            jsonString = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}