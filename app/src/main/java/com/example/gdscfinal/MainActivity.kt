package com.example.gdscfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val thread = Thread(Runnable{
            val doc = Jsoup.connect("https://udn.com/news/breaknews/1").get()
            val span = doc.select("h2 > a")
            val img = doc.select("picture > img")
            Log.i("----------", "---------")
            for (x in span) {
                Log.i("result", x.attr("title").toString())
            }
            for(x in img){
                Log.i("img",x.attr("data-src").toString())
            }
        }).start()


//報錯都有這行,不裝在thread裡就會報錯,打不開app val doc = Jsoup.connect("https://udn.com/news/breaknews/1").get()
            val span = doc.select("h2 > a")
            val list = findViewById<ListView>(R.id.listview)
            val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
            for (x in span) {
                arrayAdapter.add(x.attr("title").toString())
            }
            list.adapter = arrayAdapter


//不用arrayAdapter的方法,但還是在43行報錯
        val textview = findViewById<TextView>(R.id.textview)
        val imgview = findViewById<ImageView>(R.id.imageView)
        val doc = Jsoup.connect("https://udn.com/news/breaknews/1").get()
        val span = doc.select("h2 > a")
        val img = doc.select("picture > img")
        for (x in span) {
            textview.setText(x.attr("title").toString())

        }
    }
}

