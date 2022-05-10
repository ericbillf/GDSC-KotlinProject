package com.example.gdscfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val thread = Thread{

            val doc = Jsoup.connect("https://udn.com/news/breaknews/1").get()
            val span=doc.select("h2 > a")

            Log.i("----------", "---------")
            for(x in span) {
                Log.i("result", x.attr("title").toString())
            }
            //正在研究怎麼把抓下來的新聞標題放到頁面
            var array= arrayOf<String>()
            for(x in span) {
                for (y in 1..10) {
                    array[y] = x.attr("title").toString()
                }
            }

            val list=findViewById<ListView>(R.id.listview)
            val arrayAdapter= ArrayAdapter(this, android.R.layout.simple_list_item_1,array)
            list.adapter=arrayAdapter
        }
        thread.start()
    }
}


