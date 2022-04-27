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
        return f.readText(Charset.forName("UTF-8"))
    }
}

class Task(nameIn: String, dateTimeIn: String) {
    var name: String ?= nameIn
    var dateTime :String ?= dateTimeIn
    override fun toString(): String {
        return "name = $name, dateTime = $dateTime"
    }

}
class TaskOper{
    fun newTask(name :String, dateTime :String):String{//待完成
        val f = FileManipulate()
        val gson = Gson()
        val task = Task(name,dateTime)
        val jsonString :String = gson.toJson(task)
        return jsonString
    }

}
