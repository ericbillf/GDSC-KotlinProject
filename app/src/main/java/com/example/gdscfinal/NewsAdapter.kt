package com.example.gdscfinal

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class NewsAdapter (context : Context, var resources: Int, var newsArray: List<News>): ArrayAdapter<News>(context, R.layout.news_view, R.id.listview, newsArray){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(resources, null)
        val newsImageView = view.findViewById(R.id.newsImage) as ImageView
        val newsTextView = view.findViewById(R.id.newsText) as TextView

        Log.i("debugItemPosition", position.toString())
        Log.i("show", "Ready to show")
        var curNews:News = newsArray[position]

        newsTextView.text = curNews!!.title
        Picasso.get().load(curNews!!.imageUrl).into(newsImageView);

        return view
    }
}