package com.example.gdscfinal

import com.google.gson.Gson
import java.io.File
import java.nio.charset.Charset

class FileManipulate {
    fun writeFile(text: String, destFile: String) {
        val f = File(destFile)
        if (!f.exists()) {
            f.createNewFile()
        }
        f.writeText(text, Charset.defaultCharset())
    }
    fun getFileContent(filename: String): String {
        val f = File(filename) //path: data/data/com.example.gdscfinal/files
        if (!f.exists()) {
            f.createNewFile()
        }
        return f.readText(Charset.forName("UTF-8"))
    }
}

data class Task(var name:String, var dateTime:String)
class TaskOper{
    private fun taskListToJson(taskList: ArrayList<Task>): String {
        return Gson().toJson(taskList)
    }
    private fun jsonToTaskList(jsonStr:String): List<Task> {
        return Gson().fromJson(jsonStr, Array<Task>::class.java).asList()
    }
    fun returnTaskList(): ArrayList<Task?>? {
        val f = FileManipulate()
        var jsonStr = ""
        jsonStr+=f.getFileContent("data/data/com.example.gdscfinal/files/dataBase.json")
        return if(jsonStr == "") null
        else ArrayList(jsonToTaskList(jsonStr))
    }
    fun clearJsonFile(){
        val f = FileManipulate()
        f.writeFile("","data/data/com.example.gdscfinal/files/dataBase.json")
    }
    fun newTask(name:String, dateTime:String){
        val task = Task(name,dateTime)
        val file = FileManipulate()
        var jsonStr = ""
        jsonStr+=file.getFileContent("data/data/com.example.gdscfinal/files/dataBase.json")
        if(jsonStr == ""){
            val taskList = arrayListOf<Task>(task)
            file.writeFile(taskListToJson(taskList),"data/data/com.example.gdscfinal/files/dataBase.json")
        }else{
            var taskList = ArrayList(jsonToTaskList(jsonStr))
            taskList+=task
            file.writeFile(taskListToJson(taskList),"data/data/com.example.gdscfinal/files/dataBase.json")
        }

    }

}
