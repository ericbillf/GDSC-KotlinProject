package com.example.gdscfinal

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
//import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import android.widget.ArrayAdapter





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable{
            val list = findViewById<ListView>(R.id.listview)
            var newsList = mutableListOf<News>()
//            val arrayAdapter = ArrayAdapter<String>(this, R.layout.news_view)
            val doc = Jsoup.connect("https://udn.com/news/breaknews/1").get()
//            Log.i("showJsoup", doc.toString())

            val span = doc.select("div.story-list__image > a")
//            Log.i("showImg", img.toString())
            Log.i("----------", "---------")
            var step = 0
            for (x in span) {
                val title = x.attr("aria-label")
                val imageUrl = x.select("picture > *").attr("data-srcset")
                val url = "https://udn.com/"+x.attr("href")
                if(title  == "" || imageUrl == "" || url == "")
                    continue
                newsList.add(News(x.attr("aria-label"),"https://udn.com/"+x.attr("href"), x.select("picture > *").attr("data-srcset")))
                Log.i("showImg", imageUrl)
                Log.i("showTitle", title)
                Log.i("showUrl", url)
//                Log.i("showSpan", newsList[step].title)
//                Log.i("showUrl", newsList[step].url)
                step += 1
            }
            list.setOnItemClickListener { parent:AdapterView<*>, view:View, position:Int, id:Long->
                if (position!=null){
                    val intent =Intent()
                    intent.action=Intent.ACTION_VIEW
                    intent.data= Uri.parse(newsList[position].url)
                    startActivity(intent)
                }
                when(position){
                    0 -> Toast.makeText(this, "you click 0", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(this, "you click 1", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this, "you click 2", Toast.LENGTH_SHORT).show()
                }
            }
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
//            startActivity(browserIntent)
            Log.i("debug", 1.toString())
            runOnUiThread{
                list.adapter = NewsAdapter(this, R.layout.news_view, newsList)
                /*list.setOnItemClickListener(
                )*/
            }
            Log.i("debug", "afterRunOnUiThread")
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
