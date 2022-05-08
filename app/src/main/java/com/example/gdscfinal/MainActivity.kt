package com.example.gdscfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    lateinit var taskListViewGlob: ListView
    private val taskOper = TaskOper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)
        val taskList = taskOper.returnTaskList()
        for(x in taskList){
            myAdapter.add(x.name)
        }
        val taskListView = findViewById<ListView>(R.id.taskListView)
        taskListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        taskListViewGlob = taskListView
        taskListView.adapter = myAdapter
    }

    fun Btn1_click(view: android.view.View) {
        //for testing
        Log.d("taskList: ",taskOper.returnTaskList().toString())
    }

    fun Btn2_click(view: android.view.View) {//show select items, still developing...(button for testing)
        val count = taskListViewGlob.count
        val arr = taskListViewGlob.checkedItemPositions
        var selected:String = ""
        for(i in 0 until count){
            if(arr.get(i)){
                selected+=taskListViewGlob.getItemAtPosition(i).toString()+"\n"
            }
        }
        Toast.makeText(this,selected,Toast.LENGTH_SHORT).show()
    }


}