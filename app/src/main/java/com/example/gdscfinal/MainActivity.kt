package com.example.gdscfinal

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup


data class News (var title: String = "", var url: String = "", var image: String = ""){
}

class NewsAdapter(context : Context, resourceId: Int, objects: Array<News>): BaseAdapter(){
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): Any {
        return ""
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.i("debugView", "in getView")
        val curNews: News = convertView?.getTag(position) as News
        val newsImage = convertView?.findViewById(R.id.newsImage) as ImageView
        val newsText = convertView?.findViewById(R.id.newsText) as TextView
//        when(position) {
//            0 -> Picasso.get().load(curNews.url).into(newsImage)
//
//            1 -> newsText.text = curNews.title
//        }
        Picasso.get().load(curNews.url).into(newsImage);
        newsText.text = curNews.title
        Log.i("debugView", curNews.title)
//        Picasso.with(this@MainActivity).load(currentItem.getImageURL()).into(newsImage)
        return convertView
    }
}

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
            val arrayAdapter = ArrayAdapter<News>(this, R.layout.news_view, )
            var curNews = News()
            for (x in span) {
                if(x.attr("title").toString()!="")
                    curNews.title = x.attr("title").toString()
                if(x.attr("data-src").toString()!="")
                    curNews.image =  x.attr("data-src").toString()
                if(x.attr("href").toString()!="")
                    curNews.url = ("https://udn.com/"+x.attr("href").toString())
                arrayAdapter.add(curNews)
            }
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
//            startActivity(browserIntent)
            Log.i("debug", 1.toString())
            runOnUiThread{
                list.adapter=arrayAdapter
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
