package com.example.gdscfinal


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var taskListViewGlob: ListView
    lateinit var idListViewGlob: ListView
    lateinit var finishListViewGlob: ListView
    lateinit var finishIdListViewGlob: ListView
    val taskOper =TaskOper()
    lateinit var nameAdapterGlobe: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var filePath:String = filesDir.absolutePath
        taskOper.setFilePath("$filePath/dataBase.json")
        fun initializeTaskListView(taskList: ArrayList<Task>){
            val myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
            val myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)

            for(x in taskList){
                if(!x.isFinished)
                    myNameAdapter.add(x.name+" EndTime: ${x.endTime.year}/${x.endTime.month}/${x.endTime.day}")
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
        initializeTaskListView(getTodayTaskList())
        fun initializeFinishListView(taskList :ArrayList<Task>){
            val myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
            val myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)


            for(x in taskList){
                if(x.isFinished)
                    myNameAdapter.add(x.name+" EndTime: ${x.endTime.year}/${x.endTime.month}/${x.endTime.day}")
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
        initializeFinishListView(getTodayTaskList())
    }
    fun getTime():String{//For debugging
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val time = sdf.format(Date())
        return time.toString()
    }
    fun getDate(): ArrayList<Int> {
        val yearDf = SimpleDateFormat("yyyy")
        val monthDf = SimpleDateFormat("MM")
        val dayDf = SimpleDateFormat("dd")

        val year = yearDf.format((Date()))
        val month = monthDf.format(Date())
        val day = dayDf.format(Date())
        return arrayListOf<Int>(year.toInt(),month.toInt(),day.toInt())
    }
    fun getTodayTaskList(): ArrayList<Task> {
        var taskList = taskOper.returnTaskList()
        for(x in taskList){
            taskOper.markTaskOverTime(x,getDate())
        }
        taskList = taskOper.returnTaskList()
        var todayTaskList = arrayListOf<Task>()
        var today = getDate()
        for(task in taskList){//today>startTime && today<endTime
            val d1:String = today[0].toString() + "/" + today[1].toString() + "/" + today[2].toString()
            val d2:String = task.startTime.year.toString() + "/" + task.startTime.month.toString() + "/" + task.startTime.day
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val firstDate: Date = sdf.parse(d1)
            val secondDate: Date = sdf.parse(d2)
            val cmp = firstDate.compareTo(secondDate)
            when{
                cmp>=0->{
                    if(!task.isOverTime)
                        todayTaskList+=task
                    else
                        continue
                }
                cmp<0->{
                    continue
                }
            }
        }
        for(task in taskList){
            if(task.isOverTime){
                todayTaskList+=task
            }
        }
        todayTaskList = taskOper.sortTaskList(todayTaskList)
        return todayTaskList
    }
    fun updateListView(){
        var myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)//For storing names(visible)
        var myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)//For storing ids(gone)
        var taskList = getTodayTaskList()
        val finishTaskList = arrayListOf<Task>()
        var finishTaskCount :Int=0
        for(x in taskList){
            if(x.isFinished){
                finishTaskCount+=1
                finishTaskList+=x
            }
        }
        if(finishTaskCount>50){//remove all finished tasks if the number of them is over 50
            for(x in finishTaskList){
                taskOper.removeTask(x)
            }
        }
        for(x in taskList){
            if(!x.isFinished){
                myNameAdapter.add(x.name+" EndTime: ${x.endTime.year}/${x.endTime.month}/${x.endTime.day}")
                myObjectAdapter.add(x)
            }

        }

        taskListViewGlob.adapter = myNameAdapter
        idListViewGlob.adapter = myObjectAdapter
        myNameAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)
        myObjectAdapter = ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1)
        for(x in taskList){
            if(x.isFinished){
                myNameAdapter.add(x.name+" EndTime: ${x.endTime.year}/${x.endTime.month}/${x.endTime.day}")
                myObjectAdapter.add(x)
            }
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
        for(x in 20 downTo  1){
            var task = Task("task$x",Time(2022,5,x),Time(2022,5,x),
                isFinished = false,
                isOverTime = false
            )
            taskOper.newTask(task)
        }
        Log.d("List","${taskOper.returnTaskList()}")
        updateListView()
    }


    fun MarkBtn_click(view: android.view.View) {//Make change to taskList and finishList,
        val taskListCount = taskListViewGlob.count
        val taskListArr = taskListViewGlob.checkedItemPositions
        var selectedName: String = ""
        var selectedID = arrayListOf<Task>()
        for (i in 0 until taskListCount) {
            if (taskListArr.get(i)) {
                selectedName += taskListViewGlob.getItemAtPosition(i).toString() + " "
                selectedID += idListViewGlob.getItemAtPosition(i) as Task
            }
        }
        for (x in selectedID) {
            taskOper.markTaskFinished(x)
        }
        if(selectedName!="")
            Toast.makeText(this, "Mark Finished: $selectedName", Toast.LENGTH_SHORT).show()
        val finishListCount = finishListViewGlob.count
        val finishListArr = finishListViewGlob.checkedItemPositions
        var unSelectedName: String = ""
        var unSelectedID = arrayListOf<Task>()
        for (i in 0 until finishListCount) {
            if (!finishListArr.get(i)) {
                unSelectedName += finishListViewGlob.getItemAtPosition(i).toString() + " "
                unSelectedID += finishIdListViewGlob.getItemAtPosition(i) as Task
            }
        }
        for (x in unSelectedID) {
            taskOper.markTaskUnFinished(x)
        }
        if(unSelectedName!="")
            Toast.makeText(this, "Mark Unfinished: $unSelectedName", Toast.LENGTH_SHORT).show()
        if(unSelectedName=="" && selectedName=="")
            Toast.makeText(this,"Nothing selected.",Toast.LENGTH_SHORT).show()
        updateListView()
    }

    fun DeleteBtn_click(view: android.view.View) {
        val taskListCount = taskListViewGlob.count
        val taskListArr = taskListViewGlob.checkedItemPositions
        var selectedName: String = ""
        var selectedID = arrayListOf<Task>()
        for (i in 0 until taskListCount) {
            if (taskListArr.get(i)) {
                selectedName += taskListViewGlob.getItemAtPosition(i).toString() + " "
                selectedID += idListViewGlob.getItemAtPosition(i) as Task
            }
        }
        for (x in selectedID) {
            taskOper.removeTask(x)
        }
        if(selectedName=="")
            Toast.makeText(this,"Nothing selected.",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,"Deleted: $selectedName",Toast.LENGTH_SHORT).show()
        updateListView()
    }


}