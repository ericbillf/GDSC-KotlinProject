package com.example.gdscfinal

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        抓取天氣資訊
        val todayWeather = 1;
        var todayWeatherImageView:ImageView = findViewById(R.id.imageView)
        val tmp:Map<String, Int> =  mapOf("晴天" to R.drawable.a5, "晴時多雲" to R.drawable.a2,"多雲時晴" to R.drawable.a3)
        todayWeatherImageView.setImageResource(tmp[catchData()]!!)

    }
    private fun catchData(): String {
        var parameterName: String = ""
        val catchData: String =
            "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-B9C18838-0D7F-4F0B-9E6E-BBF8A927E5DE&locationName=%E9%AB%98%E9%9B%84%E5%B8%82";
        object : Thread() {
            override fun run() {
                val url = URL(catchData)
                Log.i("showUrl", url.toString())
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

                Log.i("showConnection", connection.toString())
                val `is`: InputStream = connection.inputStream
                Log.i("showIs", `is`.toString())
                val `in` = BufferedReader(InputStreamReader(`is`))
                var line: String = `in`.readLine().toString()
                val json = StringBuffer()
                json.append(line)
                Log.i("showJson", json.toString())
                val jsonObject: JSONObject = JSONObject(json.toString())
                Log.i("showJsonObject", jsonObject.toString())
                parameterName = jsonObject.getJSONObject("records").getJSONArray("location").getJSONObject(0).getJSONArray("weatherElement").getJSONObject(0).getJSONArray("time").getJSONObject(0).getJSONObject("parameter").getString("parameterName")
                Log.i("showParameterName", parameterName.toString())
            }
        }.start()
        return parameterName.toString()
    }
}