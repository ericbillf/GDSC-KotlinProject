package com.example.gdscfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    lateinit var taskListViewGlob: ListView
    lateinit var idListViewGlob: ListView
    lateinit var finishListViewGlob: ListView
    lateinit var finishIdListViewGlob: ListView
    private val taskOper = TaskOper()
    lateinit var nameAdapterGlobe: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fun initializeTaskListView(){
            val myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
            val myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)

            val taskList = taskOper.returnTaskList()
            for(x in taskList){
                if(!x.isFinished)
                    myNameAdapter.add(x.name)
            }
            for(x in taskList){
                if(!x.isFinished)
                    myObjectAdapter.add(x)
            }

            val taskListView = findViewById<ListView>(R.id.taskListView)
            val idListView = findViewById<ListView>(R.id.IdListView)
            nameAdapterGlobe = myNameAdapter

            idListView.visibility = View.GONE
            taskListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
            taskListViewGlob = taskListView
            idListViewGlob = idListView

            taskListView.adapter = myNameAdapter
            idListView.adapter = myObjectAdapter

        }
        initializeTaskListView()
        fun initializeFinishListView(){
            val myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
            val myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)

            val taskList = taskOper.returnTaskList()
            for(x in taskList){
                if(x.isFinished)
                    myNameAdapter.add(x.name)
            }
            for(x in taskList){
                if(x.isFinished)
                    myObjectAdapter.add(x)
            }

            val finishTaskListView = findViewById<ListView>(R.id.finishTaskListView)
            val finishIdListView = findViewById<ListView>(R.id.finishIdListView)


            finishIdListView.visibility = View.GONE
            finishTaskListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
            finishListViewGlob = finishTaskListView
            finishIdListViewGlob = finishIdListView

            finishTaskListView.adapter = myNameAdapter
            finishIdListView.adapter = myObjectAdapter
        }
        initializeFinishListView()
    }
    fun updateListView(){
        val taskList = taskOper.returnTaskList()
        var myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
        var myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)

        for(x in taskList){
            if(!x.isFinished)
                myNameAdapter.add(x.name)
        }
        for(x in taskList){
            if(!x.isFinished)
                myObjectAdapter.add(x)
        }
        taskListViewGlob.adapter = myNameAdapter
        idListViewGlob.adapter = myObjectAdapter
        myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)
        myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)
        for(x in taskList){
            if(x.isFinished)
                myNameAdapter.add(x.name)
        }
        for(x in taskList){
            if(x.isFinished)
                myObjectAdapter.add(x)
        }
        finishListViewGlob.adapter = myNameAdapter
        finishIdListViewGlob.adapter = myObjectAdapter
    }
    fun DeleteBtn_click(view: android.view.View) {
        //for testing
        val count = taskListViewGlob.count
        val arr = taskListViewGlob.checkedItemPositions
        var selectedName:String = ""
        var selectedID = arrayListOf<Task>()
        for(i in 0 until count){
            if(arr.get(i)){
                selectedName+=taskListViewGlob.getItemAtPosition(i).toString()+"\n"
                selectedID += idListViewGlob.getItemAtPosition(i) as Task
            }
        }
        for(x in selectedID){
            taskOper.removeTask(x)
        }
        Toast.makeText(this,"Deleted: $selectedName\n$selectedID",Toast.LENGTH_SHORT).show()
        updateListView()
    }

    fun ShowBtn_click(view: android.view.View) {//show select items names and ids, still developing...(button for testing)
        //http://android-coding.blogspot.com/2011/09/listview-with-multiple-choice.html
        Log.d("test","${taskOper.returnTaskList()}")
        updateListView()
    }

    fun MarkBtn_click(view: android.view.View) {
        val count = taskListViewGlob.count
        val arr = taskListViewGlob.checkedItemPositions
        var selectedName:String = ""
        var selectedID = arrayListOf<Task>()

        for(i in 0 until count){
            if(arr.get(i)){
                selectedName+=taskListViewGlob.getItemAtPosition(i).toString()+"\n"
                selectedID += idListViewGlob.getItemAtPosition(i) as Task
            }
        }
        val oldList = selectedID
        var isOper:Boolean = false
        for(x in selectedID){//Problem here
            taskOper.markTaskFinished(x)
        }
        Toast.makeText(this,"Mark Finished: $selectedName\n$selectedID Operated: $isOper",Toast.LENGTH_SHORT).show()
        Log.d("Marked","${taskOper.returnTaskList()}")
        updateListView()
    }


}