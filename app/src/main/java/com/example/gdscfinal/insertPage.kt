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
import com.example.gdscfinal.TaskOper

class insertPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_page)

    }

    fun onClick(view: android.view.View) {
        when(view?.id){
            R.id.button1->
                {
                    val taskOper = TaskOper()

                    Toast.makeText(this,"taskList: ${taskOper.returnTaskList()}",Toast.LENGTH_LONG).show()
                }
        }
        }


}

