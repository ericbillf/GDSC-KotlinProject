package com.example.gdscfinal

import com.google.gson.Gson
import java.io.File
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*


class FileManipulate {
    fun writeFile(text: String, destFile: String) {
        val f = File(destFile)
        if (!f.exists()) {
            f.createNewFile()
        }
        f.writeText(text, Charset.defaultCharset())
    }
    fun getFileContent(filename: String): String {
        val f = File(filename) //Internal storage path: data/data/com.example.gdscfinal/files
        if (!f.exists()) {
            f.createNewFile()
        }
        return f.readText(Charset.forName("UTF-8"))
    }
}
data class Time(var year:Int,var month:Int, var day:Int)//https://ithelp.ithome.com.tw/articles/10206960
data class Task(var name:String, var startTime:Time, var endTime:Time, var isFinished: Boolean, var isOverTime: Boolean)

class TaskOper{
    lateinit var jsonFilePath:String
    fun setFilePath(filePath :String){
        jsonFilePath = filePath
    }
    private fun taskListToJson(taskList: ArrayList<Task>): String {
        return Gson().toJson(taskList)
    }
    private fun jsonToTaskList(jsonStr:String): List<Task> {
        return Gson().fromJson(jsonStr, Array<Task>::class.java).asList()
    }
    fun returnTaskList(): ArrayList<Task> { // Return the task list in dataBase.json as an ArrayList<Task>
        val f = FileManipulate()
        var jsonStr = ""
        jsonStr+=f.getFileContent(jsonFilePath)
        return if(jsonStr == "") ArrayList<Task>()
        else ArrayList(jsonToTaskList(jsonStr))
    }
    fun clearJsonFile(){//Clear dataBase.json
        val f = FileManipulate()
        f.writeFile("",jsonFilePath)
    }
    private fun taskIsInJson(task :Task): Boolean {//Check if the task is in dataBase.json
        val file = FileManipulate()
        var jsonStr = ""
        jsonStr+=file.getFileContent(jsonFilePath)
        if(jsonStr == ""){
            return false
        }else{
            var taskList = ArrayList(jsonToTaskList(jsonStr))
            for(x in taskList){
                if(task == x)return true
            }
            return false
        }
    }
    fun newTask(task: Task): Boolean {//Input a Task object and add it to dataBase.json
        return if(taskIsInJson(task)) false
        else{
            val file = FileManipulate()
            var jsonStr = ""
            jsonStr+=file.getFileContent(jsonFilePath)
            if(jsonStr == ""){
                val taskList = arrayListOf<Task>(task)
                file.writeFile(taskListToJson(taskList),jsonFilePath)
            }else{
                var taskList = ArrayList(jsonToTaskList(jsonStr))
                taskList+=task //Append task to taskList
                file.writeFile(taskListToJson(taskList),jsonFilePath)
            }
            true
        }
    }
    fun removeTask(task: Task): Boolean {//Remove the task from dataBase.json
        return if(!taskIsInJson(task)) false //Return false if the task is not in dataBase.json
        else{
            val file = FileManipulate()
            var jsonStr = ""
            jsonStr+=file.getFileContent(jsonFilePath)
            val taskList = jsonToTaskList(jsonStr)
            var newTaskList = arrayListOf<Task>()
            for(x in taskList){
                if(x != task){
                    newTaskList+=x
                }
            }
            file.writeFile(taskListToJson(newTaskList), jsonFilePath)
            true
        }
    }
    fun editTask(targetTask:Task,editedTask :Task): Boolean {//Replace the targetTask with editedTask in dataBase.json
        return if(!taskIsInJson(targetTask)) false
        else{
            val file = FileManipulate()
            var jsonStr = ""
            jsonStr+=file.getFileContent(jsonFilePath)
            val taskList = jsonToTaskList(jsonStr)
            var newTaskList = arrayListOf<Task>()
            for(x in taskList){
                newTaskList += if(x != targetTask)x else editedTask
            }
            file.writeFile(taskListToJson(newTaskList), jsonFilePath)
            true
        }

    }
    fun markTaskFinished(task :Task): Boolean {
        return if(!taskIsInJson(task)) false //Return false if the task is not in dataBase.json
        else{
            val file = FileManipulate()
            var jsonStr = ""
            jsonStr+=file.getFileContent(jsonFilePath)
            val taskList = jsonToTaskList(jsonStr)
            var newTaskList = arrayListOf<Task>()
            for(x in taskList){
                if(x != task){
                    newTaskList+=x
                }
                else{
                    x.isFinished=true
                    newTaskList+=x
                }
            }
            file.writeFile(taskListToJson(newTaskList), jsonFilePath)
            true
        }
    }
    fun markTaskUnFinished(task :Task): Boolean {
        return if(!taskIsInJson(task)) false //Return false if the task is not in dataBase.json
        else{
            val file = FileManipulate()
            var jsonStr = ""
            jsonStr+=file.getFileContent(jsonFilePath)
            val taskList = jsonToTaskList(jsonStr)
            var newTaskList = arrayListOf<Task>()
            for(x in taskList){
                if(x != task){
                    newTaskList+=x
                }
                else{
                    x.isFinished=false
                    newTaskList+=x
                }
            }
            file.writeFile(taskListToJson(newTaskList), jsonFilePath)
            true
        }
    }
    fun markTaskOverTime(task: Task,today :ArrayList<Int>): Boolean {
        if (!taskIsInJson(task)) return false
        else {
            val d1: String = task.endTime.year.toString() + "/" + task.endTime.month.toString() + "/" + task.endTime.day
            val d2: String = today[0].toString() + "/" + today[1].toString() + "/" + today[2].toString()

            val sdf = SimpleDateFormat("yyyy/MM/dd")

            val firstDate: Date = sdf.parse(d1)
            val secondDate: Date = sdf.parse(d2)
            val cmp = firstDate.compareTo(secondDate)

            val f = FileManipulate()
            var jsonStr = ""
            jsonStr+=f.getFileContent(jsonFilePath)
            val taskList = jsonToTaskList(jsonStr)
            var newTaskList = arrayListOf<Task>()
            when {
                cmp >= 0 -> {
                    return false
                }
                cmp < 0 -> {
                    for(x in taskList){
                        if(x!=task){
                            newTaskList+=x
                        }
                        else{
                            x.isOverTime = true
                            val overTime = "(Overtime!!)"
                            if(overTime !in x.name)
                                x.name = "${x.name}(Overtime!!)"
                            newTaskList+=x
                        }
                    }
                    f.writeFile(taskListToJson(newTaskList), jsonFilePath)
                    return true
                }
                else -> {
                    return false
                }
            }
        }
    }
}