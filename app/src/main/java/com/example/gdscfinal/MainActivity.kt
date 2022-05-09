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
        fun initializeTaskListView(taskList: ArrayList<Task>){
            val myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
            val myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)


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
        initializeTaskListView(taskOper.returnTaskList())
        fun initializeFinishListView(taskList :ArrayList<Task>){
            val myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
            val myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)


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
            val count = finishListViewGlob.count
            for(i in 0 until count){
                finishListViewGlob.setItemChecked(i,true)
            }
        }
        initializeFinishListView(taskOper.returnTaskList())
    }
    fun updateListView(taskList :ArrayList<Task>){
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
        val count = finishListViewGlob.count
        for(i in 0 until count){
            finishListViewGlob.setItemChecked(i,true)
        }
    }
    fun NewTaskBtn_click(view: android.view.View) {
        //**new task**
        taskOper.clearJsonFile()
        val task1 = Task("test1",Time(2020,2,1),Time(2021,2,3),false)
        val task2 = Task("test2",Time(2021,2,2),Time(2022,3,2),true)
        val task3 = Task("test3",Time(2022,2,2),Time(2022,3,2),true)
        taskOper.newTask(task1)
        taskOper.newTask(task2)
        taskOper.newTask(task3)
        updateListView(taskOper.returnTaskList())
    }

    fun ShowBtn_click(view: android.view.View) {//show select items names and ids, still developing...(button for testing) ****Abandoned****
        //http://android-coding.blogspot.com/2011/09/listview-with-multiple-choice.html
        Log.d("test","${taskOper.returnTaskList()}")
        updateListView(taskOper.returnTaskList())
    }

    fun MarkBtn_click(view: android.view.View) {//Make change to taskList and finishList,
        val taskListCount = taskListViewGlob.count

            val taskListArr = taskListViewGlob.checkedItemPositions
            var selectedName: String = ""
            var selectedID = arrayListOf<Task>()
            for (i in 0 until taskListCount) {
                Log.d("Oper","for loop operated ${taskListArr.get(i)}")
                if (taskListArr.get(i)) {

                    selectedName += taskListViewGlob.getItemAtPosition(i).toString() + "\n"
                    selectedID += idListViewGlob.getItemAtPosition(i) as Task
                    Log.d("Selected: ","$selectedID")
                }
            }
            for (x in selectedID) {
                taskOper.markTaskFinished(x)
            }
            Toast.makeText(this, "Mark Finished: $selectedName", Toast.LENGTH_SHORT).show()
            Log.d("Marked finished", "${taskOper.returnTaskList()}")


        val finishListCount = finishListViewGlob.count

            val finishListArr = finishListViewGlob.checkedItemPositions
            var unSelectedName: String = ""
            var unSelectedID = arrayListOf<Task>()
            for (i in 0 until finishListCount) {
                if (!finishListArr.get(i)) {
                    unSelectedName += finishListViewGlob.getItemAtPosition(i).toString() + "\n"
                    unSelectedID += finishIdListViewGlob.getItemAtPosition(i) as Task
                }
            }
            for (x in unSelectedID) {
                taskOper.markTaskUnFinished(x)
            }
            Toast.makeText(this, "Mark Unfinished: $unSelectedName", Toast.LENGTH_SHORT).show()
            Log.d("Marked unfinished", "${taskOper.returnTaskList()}")
            updateListView(taskOper.returnTaskList())


    }



}