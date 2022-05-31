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
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
//import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import android.widget.ArrayAdapter

var parameterName: String = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val todayWeatherImageView:ImageView = findViewById(R.id.show_weather_picture_imageView)
        val toWeatherPictureId:Map<String, Int> =  mapOf("晴天" to R.drawable.a1, "晴時多雲" to R.drawable.a2,"多雲時晴" to R.drawable.a3, "多雲" to R.drawable.a4, "多雲時陰" to R.drawable.a5, "陰時多雲" to R.drawable.a6, "陰天" to R.drawable.a7, "多雲陣雨" to R.drawable.a8, "多雲短暫雨" to R.drawable.a8, "多雲短暫陣雨" to R.drawable.a8, "午後短暫陣雨" to R.drawable.a8, "短暫陣雨" to R.drawable.a8, "多雲時晴短暫陣雨" to R.drawable.a8, "多雲時晴短暫雨" to R.drawable.a8, "晴時多雲短暫陣雨" to R.drawable.a8, "晴短暫陣雨" to R.drawable.a8, "短暫雨" to R.drawable.a8, "多雲時陰短暫雨" to R.drawable.a9, "多雲時陰短暫陣雨" to R.drawable.a9, "陰時多雲短暫雨" to R.drawable.a10, "陰時多雲短暫陣雨" to R.drawable.a10, "雨天" to R.drawable.a11, "晴午後陰短暫雨" to R.drawable.a11, "晴午後陰短暫陣雨" to R.drawable.a11, "陰短暫雨" to R.drawable.a11, "陰短暫陣雨" to R.drawable.a11, "陰午後短暫陣雨" to R.drawable.a11, "多雲時陰有雨" to R.drawable.a12, "多雲時陰陣雨" to R.drawable.a12, "晴時多雲陣雨" to R.drawable.a12, "多雲時晴陣雨" to R.drawable.a12, "陰時多雲有雨" to R.drawable.a13, "陰時多雲有陣雨" to R.drawable.a13, "陰時多雲陣雨" to R.drawable.a13, "陰有雨" to R.drawable.a14, "陰有陣雨" to R.drawable.a14, "陰雨" to R.drawable.a14, "陰陣雨" to R.drawable.a14, "陣雨" to R.drawable.a14, "午後陣雨" to R.drawable.a14, "有雨" to R.drawable.a14, "多雲陣雨或雷雨" to R.drawable.a15, "多雲短暫陣雨或雷雨" to R.drawable.a15, "多雲短暫雷陣雨" to R.drawable.a15, "多雲雷陣雨" to R.drawable.a15, "短暫陣雨或雷雨後多雲" to R.drawable.a15, "短暫雷陣雨後多雲" to R.drawable.a15, "短暫陣雨或雷雨" to R.drawable.a15, "晴時多雲短暫陣雨或雷雨" to R.drawable.a15, "晴短暫陣雨或雷雨" to R.drawable.a15, "多雲時晴短暫陣雨或雷雨" to R.drawable.a15, "午後短暫雷陣雨" to R.drawable.a15, "多雲時陰陣雨或雷雨" to R.drawable.a16, "多雲時陰短暫陣雨或雷雨" to R.drawable.a16, "多雲時陰短暫雷陣雨" to R.drawable.a16, "多雲時陰雷陣雨" to R.drawable.a16, "晴陣雨或雷雨" to R.drawable.a16, "晴時多雲陣雨或雷雨" to R.drawable.a16, "多雲時晴陣雨或雷雨" to R.drawable.a16, "陰時多雲有雷陣雨" to R.drawable.a17, "陰時多雲陣雨或雷雨" to R.drawable.a17, "陰時多雲短暫陣雨或雷雨" to R.drawable.a17, "陰時多雲短暫雷陣雨" to R.drawable.a17, "陰時多雲雷陣雨" to R.drawable.a17, "陰有陣雨或雷雨" to R.drawable.a18, "陰有雷陣雨" to R.drawable.a18, "陰陣雨或雷雨" to R.drawable.a18, "陰雷陣雨" to R.drawable.a18, "晴午後陰短暫陣雨或雷雨" to R.drawable.a18, "晴午後陰短暫雷陣雨" to R.drawable.a18, "陰短暫陣雨或雷雨" to R.drawable.a18, "陰短暫雷陣雨" to R.drawable.a18, "雷雨" to R.drawable.a18, "陣雨或雷雨後多雲" to R.drawable.a18, "陰陣雨或雷雨後多雲" to R.drawable.a18, "陰短暫陣雨或雷雨後多雲" to R.drawable.a18, "陰短暫雷陣雨後多雲" to R.drawable.a18, "陰雷陣雨後多雲" to R.drawable.a18, "雷陣雨後多雲" to R.drawable.a18, "陣雨或雷雨" to R.drawable.a18, "雷陣雨" to R.drawable.a18, "午後雷陣雨" to R.drawable.a18, "晴午後多雲局部雨" to R.drawable.a19, "晴午後多雲局部陣雨" to R.drawable.a19, "晴午後多雲局部短暫雨" to R.drawable.a19, "晴午後多雲局部短暫陣雨" to R.drawable.a19, "晴午後多雲短暫雨" to R.drawable.a19, "晴午後多雲短暫陣雨" to R.drawable.a19, "晴午後局部雨" to R.drawable.a19, "晴午後局部陣雨" to R.drawable.a19, "晴午後局部短暫雨" to R.drawable.a19, "晴午後局部短暫陣雨" to R.drawable.a19, "晴午後陣雨" to R.drawable.a19, "晴午後短暫雨" to R.drawable.a19, "晴午後短暫陣雨晴時多雲午後短暫陣雨" to R.drawable.a19, "多雲午後局部雨" to R.drawable.a20, "多雲午後局部陣雨" to R.drawable.a20, "多雲午後局部短暫雨" to R.drawable.a20, "多雲午後局部短暫陣雨" to R.drawable.a20, "多雲午後陣雨" to R.drawable.a20, "多雲午後短暫雨" to R.drawable.a20, "多雲午後短暫陣雨" to R.drawable.a20, "多雲時陰午後短暫陣雨" to R.drawable.a20, "陰時多雲午後短暫陣雨" to R.drawable.a20, "多雲時晴午後短暫陣雨" to R.drawable.a20, "晴午後多雲陣雨或雷雨" to R.drawable.a21, "晴午後多雲雷陣雨" to R.drawable.a21, "晴午後陣雨或雷雨" to R.drawable.a21, "晴午後雷陣雨" to R.drawable.a21, "晴午後多雲局部陣雨或雷雨" to R.drawable.a21, "晴午後多雲局部短暫陣雨或雷雨" to R.drawable.a21, "晴午後多雲局部短暫雷陣雨" to R.drawable.a21, "晴午後多雲局部雷陣雨" to R.drawable.a21, "晴午後多雲短暫陣雨或雷雨" to R.drawable.a21, "晴午後多雲短暫雷陣雨" to R.drawable.a21, "晴午後局部短暫雷陣雨" to R.drawable.a21, "晴午後局部雷陣雨" to R.drawable.a21, "晴午後短暫雷陣雨" to R.drawable.a21, "晴雷陣雨" to R.drawable.a21, "晴時多雲雷陣雨" to R.drawable.a21, "晴時多雲午後短暫雷陣雨" to R.drawable.a21, "多雲午後局部陣雨或雷雨" to R.drawable.a22, "多雲午後局部短暫陣雨或雷雨" to R.drawable.a22, "多雲午後局部短暫雷陣雨" to R.drawable.a22, "多雲午後局部雷陣雨" to R.drawable.a22, "多雲午後陣雨或雷雨" to R.drawable.a22, "多雲午後短暫陣雨或雷雨" to R.drawable.a22, "多雲午後短暫雷陣雨" to R.drawable.a22, "多雲午後雷陣雨" to R.drawable.a22, "多雲時晴雷陣雨" to R.drawable.a22, "多雲時晴午後短暫雷陣雨" to R.drawable.a22, "多雲時陰午後短暫雷陣雨" to R.drawable.a22, "陰時多雲午後短暫雷陣雨" to R.drawable.a22, "陰午後短暫雷陣雨" to R.drawable.a22, "多雲局部陣雨或雪" to R.drawable.a23, "多雲時陰有雨或雪" to R.drawable.a23, "多雲時陰短暫雨或雪" to R.drawable.a23, "多雲短暫雨或雪" to R.drawable.a23, "陰有雨或雪" to R.drawable.a23, "陰時多雲有雨或雪" to R.drawable.a23, "陰時多雲短暫雨或雪" to R.drawable.a23, "陰短暫雨或雪" to R.drawable.a23, "多雲時陰有雪" to R.drawable.a23, "多雲時陰短暫雪" to R.drawable.a23, "多雲短暫雪" to R.drawable.a23, "陰有雪" to R.drawable.a23, "陰時多雲有雪" to R.drawable.a23, "陰時多雲短暫雪" to R.drawable.a23, "陰短暫雪" to R.drawable.a23, "有雨或雪" to R.drawable.a23, "有雨或短暫雪" to R.drawable.a23, "陰有雨或短暫雪" to R.drawable.a23, "陰時多雲有雨或短暫雪" to R.drawable.a23, "多雲時陰有雨或短暫雪" to R.drawable.a23, "多雲有雨或短暫雪" to R.drawable.a23, "多雲有雨或雪" to R.drawable.a23, "多雲時晴有雨或雪" to R.drawable.a23, "晴時多雲有雨或雪" to R.drawable.a23, "晴有雨或雪" to R.drawable.a23, "短暫雨或雪" to R.drawable.a23, "多雲時晴短暫雨或雪" to R.drawable.a23, "晴時多雲短暫雨或雪" to R.drawable.a23, "晴短暫雨或雪" to R.drawable.a23, "有雪" to R.drawable.a23, "多雲有雪" to R.drawable.a23, "多雲時晴有雪" to R.drawable.a23, "晴時多雲有雪" to R.drawable.a23, "晴有雪" to R.drawable.a23, "短暫雪" to R.drawable.a23, "多雲時晴短暫雪" to R.drawable.a23, "晴時多雲短暫雪" to R.drawable.a23, "晴短暫雪" to R.drawable.a23, "晴有霧" to R.drawable.a24, "晴晨霧" to R.drawable.a24, "晴時多雲有霧" to R.drawable.a25, "晴時多雲晨霧" to R.drawable.a25, "多雲時晴有霧" to R.drawable.a26, "多雲時晴晨霧" to R.drawable.a26, "多雲有霧" to R.drawable.a27, "多雲晨霧" to R.drawable.a27, "有霧" to R.drawable.a27, "陰有霧" to R.drawable.a28, "陰晨霧" to R.drawable.a28, "多雲時陰有霧" to R.drawable.a28, "多雲時陰晨霧" to R.drawable.a28, "陰時多雲有霧" to R.drawable.a28, "陰時多雲晨霧" to R.drawable.a28, "多雲局部雨" to R.drawable.a29, "多雲局部陣雨" to R.drawable.a29, "多雲局部短暫雨" to R.drawable.a29, "多雲局部短暫陣雨" to R.drawable.a29, "多雲時陰局部雨" to R.drawable.a30, "多雲時陰局部陣雨" to R.drawable.a30, "多雲時陰局部短暫雨" to R.drawable.a30, "多雲時陰局部短暫陣雨" to R.drawable.a30, "晴午後陰局部雨" to R.drawable.a30, "晴午後陰局部陣雨" to R.drawable.a30, "晴午後陰局部短暫雨" to R.drawable.a30, "晴午後陰局部短暫陣雨" to R.drawable.a30, "陰局部雨" to R.drawable.a30, "陰局部陣雨" to R.drawable.a30, "陰局部短暫雨" to R.drawable.a30, "陰局部短暫陣雨" to R.drawable.a30, "陰時多雲局部雨" to R.drawable.a30, "陰時多雲局部陣雨" to R.drawable.a30, "陰時多雲局部短暫雨" to R.drawable.a30, "陰時多雲局部短暫陣雨" to R.drawable.a30, "多雲有霧有局部雨" to R.drawable.a31, "多雲有霧有局部陣雨" to R.drawable.a31, "多雲有霧有局部短暫雨" to R.drawable.a31, "多雲有霧有局部短暫陣雨" to R.drawable.a31, "多雲有霧有陣雨" to R.drawable.a31, "多雲有霧有短暫雨" to R.drawable.a31, "多雲有霧有短暫陣雨" to R.drawable.a31, "多雲局部雨有霧" to R.drawable.a31, "多雲局部雨晨霧" to R.drawable.a31, "多雲局部陣雨有霧" to R.drawable.a31, "多雲局部陣雨晨霧" to R.drawable.a31, "多雲局部短暫雨有霧" to R.drawable.a31, "多雲局部短暫雨晨霧" to R.drawable.a31, "多雲局部短暫陣雨有霧" to R.drawable.a31, "多雲局部短暫陣雨晨霧" to R.drawable.a31, "多雲陣雨有霧" to R.drawable.a31, "多雲短暫雨有霧" to R.drawable.a31, "多雲短暫雨晨霧" to R.drawable.a31, "多雲短暫陣雨有霧" to R.drawable.a31, "多雲短暫陣雨晨霧" to R.drawable.a31, "有霧有短暫雨" to R.drawable.a31, "有霧有短暫陣雨" to R.drawable.a31, "多雲時陰有霧有局部雨" to R.drawable.a32, "多雲時陰有霧有局部陣雨" to R.drawable.a32, "多雲時陰有霧有局部短暫雨" to R.drawable.a32, "多雲時陰有霧有局部短暫陣雨" to R.drawable.a32, "多雲時陰有霧有陣雨" to R.drawable.a32, "多雲時陰有霧有短暫雨" to R.drawable.a32, "多雲時陰有霧有短暫陣雨" to R.drawable.a32, "多雲時陰局部雨有霧" to R.drawable.a32, "多雲時陰局部陣雨有霧" to R.drawable.a32, "多雲時陰局部短暫雨有霧" to R.drawable.a32, "多雲時陰局部短暫陣雨有霧" to R.drawable.a32, "多雲時陰陣雨有霧" to R.drawable.a32, "多雲時陰短暫雨有霧" to R.drawable.a32, "多雲時陰短暫雨晨霧" to R.drawable.a32, "多雲時陰短暫陣雨有霧" to R.drawable.a32, "多雲時陰短暫陣雨晨霧" to R.drawable.a32, "陰有霧有陣雨" to R.drawable.a32, "陰局部雨有霧" to R.drawable.a32, "陰局部陣雨有霧" to R.drawable.a32, "陰局部短暫陣雨有霧" to R.drawable.a32, "陰時多雲有霧有局部雨" to R.drawable.a32, "陰時多雲有霧有局部陣雨" to R.drawable.a32, "陰時多雲有霧有局部短暫雨" to R.drawable.a32, "陰時多雲有霧有局部短暫陣雨" to R.drawable.a32, "陰時多雲有霧有陣雨" to R.drawable.a32, "陰時多雲有霧有短暫雨" to R.drawable.a32, "陰時多雲有霧有短暫陣雨" to R.drawable.a32, "陰時多雲局部雨有霧" to R.drawable.a32, "陰時多雲局部陣雨有霧" to R.drawable.a32, "陰時多雲局部短暫雨有霧" to R.drawable.a32, "陰時多雲局部短暫陣雨有霧" to R.drawable.a32, "陰時多雲陣雨有霧" to R.drawable.a32, "陰時多雲短暫雨有霧" to R.drawable.a32, "陰時多雲短暫雨晨霧" to R.drawable.a32, "陰時多雲短暫陣雨有霧" to R.drawable.a32, "陰時多雲短暫陣雨晨霧" to R.drawable.a32, "陰陣雨有霧" to R.drawable.a32, "陰短暫雨有霧" to R.drawable.a32, "陰短暫雨晨霧" to R.drawable.a32, "陰短暫陣雨有霧" to R.drawable.a32, "陰短暫陣雨晨霧" to R.drawable.a32, "多雲局部陣雨或雷雨" to R.drawable.a33, "多雲局部短暫陣雨或雷雨" to R.drawable.a33, "多雲局部短暫雷陣雨" to R.drawable.a33, "多雲局部雷陣雨" to R.drawable.a33, "多雲時陰局部陣雨或雷雨" to R.drawable.a34, "多雲時陰局部短暫陣雨或雷雨" to R.drawable.a34, "多雲時陰局部短暫雷陣雨" to R.drawable.a34, "多雲時陰局部雷陣雨" to R.drawable.a34, "晴午後陰局部陣雨或雷雨" to R.drawable.a34, "晴午後陰局部短暫陣雨或雷雨" to R.drawable.a34, "晴午後陰局部短暫雷陣雨" to R.drawable.a34, "晴午後陰局部雷陣雨" to R.drawable.a34, "陰局部陣雨或雷雨" to R.drawable.a34, "陰局部短暫陣雨或雷雨" to R.drawable.a34, "陰局部短暫雷陣雨" to R.drawable.a34, "陰局部雷陣雨" to R.drawable.a34, "陰時多雲局部陣雨或雷雨" to R.drawable.a34, "陰時多雲局部短暫陣雨或雷雨" to R.drawable.a34, "陰時多雲局部短暫雷陣雨" to R.drawable.a34, "陰時多雲局部雷陣雨" to R.drawable.a34, "多雲有陣雨或雷雨有霧" to R.drawable.a35, "多雲有雷陣雨有霧" to R.drawable.a35, "多雲有霧有陣雨或雷雨" to R.drawable.a35, "多雲有霧有雷陣雨" to R.drawable.a35, "多雲局部陣雨或雷雨有霧" to R.drawable.a35, "多雲局部短暫陣雨或雷雨有霧" to R.drawable.a35, "多雲局部短暫雷陣雨有霧" to R.drawable.a35, "多雲局部雷陣雨有霧" to R.drawable.a35, "多雲陣雨或雷雨有霧" to R.drawable.a35, "多雲短暫陣雨或雷雨有霧" to R.drawable.a35, "多雲短暫雷陣雨有霧" to R.drawable.a35, "多雲雷陣雨有霧" to R.drawable.a35, "多雲時晴短暫陣雨或雷雨有霧" to R.drawable.a35, "多雲時陰有陣雨或雷雨有霧" to R.drawable.a36, "多雲時陰有雷陣雨有霧" to R.drawable.a36, "多雲時陰有霧有陣雨或雷雨" to R.drawable.a36, "多雲時陰有霧有雷陣雨" to R.drawable.a36, "多雲時陰局部陣雨或雷雨" to R.drawable.a36, "有霧多雲時陰局部短暫陣雨或雷雨有霧" to R.drawable.a36, "多雲時陰局部短暫雷陣雨有霧" to R.drawable.a36, "多雲時陰局部雷陣雨有霧" to R.drawable.a36, "多雲時陰陣雨或雷雨有霧" to R.drawable.a36, "多雲時陰短暫陣雨或雷雨有霧" to R.drawable.a36, "多雲時陰短暫雷陣雨有霧" to R.drawable.a36, "多雲時陰雷陣雨有霧" to R.drawable.a36, "陰局部陣雨或雷雨有霧" to R.drawable.a36, "陰局部短暫陣雨或雷雨有霧" to R.drawable.a36, "陰局部短暫雷陣雨有霧" to R.drawable.a36, "陰局部雷陣雨有霧" to R.drawable.a36, "陰時多雲有陣雨或雷雨有霧" to R.drawable.a36, "陰時多雲有雷陣雨有霧" to R.drawable.a36, "陰時多雲有霧有陣雨或雷雨" to R.drawable.a36, "陰時多雲有霧有雷陣雨" to R.drawable.a36, "陰時多雲局部陣雨或雷雨有霧" to R.drawable.a36, "陰時多雲局部短暫陣雨或雷雨有霧" to R.drawable.a36, "陰時多雲局部短暫雷陣雨有霧" to R.drawable.a36, "陰時多雲局部雷陣雨有霧" to R.drawable.a36, "陰時多雲陣雨或雷雨有霧" to R.drawable.a36, "陰時多雲短暫陣雨或雷雨有霧" to R.drawable.a36, "陰時多雲短暫雷陣雨有霧" to R.drawable.a36, "陰時多雲雷陣雨有霧" to R.drawable.a36, "陰短暫陣雨或雷雨有霧" to R.drawable.a36, "陰短暫雷陣雨有霧" to R.drawable.a36, "雷陣雨有霧" to R.drawable.a36, "多雲局部雨或雪有霧" to R.drawable.a37, "多雲時陰局部雨或雪有霧" to R.drawable.a37, "陰時多雲局部雨或雪有霧" to R.drawable.a37, "陰局部雨或雪有霧" to R.drawable.a37, "短暫雨或雪有霧" to R.drawable.a37, "有雨或雪有霧" to R.drawable.a37, "短暫陣雨有霧" to R.drawable.a38, "短暫陣雨晨霧" to R.drawable.a38, "短暫雨有霧" to R.drawable.a38, "短暫雨晨霧" to R.drawable.a38, "有雨有霧" to R.drawable.a39, "陣雨有霧" to R.drawable.a39, "短暫陣雨或雷雨有霧" to R.drawable.a41, "陣雨或雷雨有霧" to R.drawable.a41, "下雪" to R.drawable.a42, "積冰" to R.drawable.a42, "暴風雪" to R.drawable.a42)
        catchWeatherData()

        Thread(Runnable{
            val list = findViewById<ListView>(R.id.listview)
            val newsList = mutableListOf<News>()
            val doc = Jsoup.connect("https://udn.com/news/breaknews/1").get()
            val span = doc.select("div.story-list__image > a")
            var step = 0
            for (x in span) {
                val title = x.attr("aria-label")
                val imageUrl = x.select("picture > *").attr("data-srcset")
                val url = "https://udn.com/"+x.attr("href")
                if(title  == "" || imageUrl == "" || url == "")
                    continue
                newsList.add(News(x.attr("aria-label"),"https://udn.com/"+x.attr("href"), x.select("picture > *").attr("data-srcset")))

                step += 1
            }

            list.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(newsList[position].url)
                startActivity(intent)
            }

            runOnUiThread{
                list.adapter = NewsAdapter(this, R.layout.news_view, newsList)
            }
        }).start()

        while(parameterName == ""){}
        toWeatherPictureId[parameterName]?.let { todayWeatherImageView.setImageResource(it) }

    }

    private fun catchWeatherData(): String {
        val catchData: String ="https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-B9C18838-0D7F-4F0B-9E6E-BBF8A927E5DE&locationName=%E9%AB%98%E9%9B%84%E5%B8%82";
        object : Thread() {
            override fun run() {
                val url = URL(catchData)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                val jsonObject = JSONObject(InputStreamReader(connection.inputStream).readText())
                parameterName =
                    jsonObject.getJSONObject("records").getJSONArray("location").getJSONObject(0)
                        .getJSONArray("weatherElement").getJSONObject(0).getJSONArray("time")
                        .getJSONObject(0).getJSONObject("parameter").getString("parameterName")
                Log.i("showParameterName", parameterName)
            }
        }.start()

        return parameterName
    }

    fun ToTask_click(view: android.view.View) {
        val intent = Intent(this@MainActivity, ShowTask::class.java)
        startActivity(intent)
    }
}


