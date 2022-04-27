package com.example.gdscfinal



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.logging.Level.INFO
import com.example.gdscfinal.FileManipulate
import com.example.gdscfinal.Task

class insertPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_page)

    }

    fun onClick(view: android.view.View) {
        when(view?.id){
            R.id.button1->
                {
                    val fileStream = FileManipulate()
                    val newTask = TaskOper()

                    Toast.makeText(this,newTask.newTask("Task1","20220501"),Toast.LENGTH_LONG).show()
                }
        }
        }


}

