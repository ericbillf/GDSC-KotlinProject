package com.example.gdscfinal

import android.graphics.ImageFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import org.jsoup.Jsoup
import android.content.Intent
import android.net.Uri
import android.view.View


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable{
            val list=findViewById<ListView>(R.id.listview)
            val doc = Jsoup.connect("https://udn.com/news/breaknews/1").get()
            val span = doc.select("h2 > a")
            val img = doc.select("picture > img")
            Log.i("----------", "---------")
            val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
            for (x in span) {
                if(x.attr("title").toString()!=""){
                    Log.i("result",x.attr("title").toString())
                    arrayAdapter.add(x.attr("title").toString())
                }
            }
            for (x in img) {
                if(x.attr("data-src").toString()!=""){
                Log.i("img", x.attr("data-src").toString())
                }
            }
            for (x in span) {
                if(x.attr("href").toString()!=""){
                    Log.i("link","https://udn.com/"+x.attr("href").toString())
                }
            }
            runOnUiThread{
                list.adapter=arrayAdapter
                /*list.setOnItemClickListener(

                )*/
            }
            /*fun getUrlFromIntent(view: View) {
                val url = "http://www.google.com"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }*/
        }).start()

        }
    }


//https://udn.com/
