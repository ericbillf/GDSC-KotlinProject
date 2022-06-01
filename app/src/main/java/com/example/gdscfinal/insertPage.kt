package com.example.gdscfinal

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class insertPage : AppCompatActivity() {



    // Button
    private lateinit var buttonpickgo: Button
    private lateinit var buttonpickend: Button
    private lateinit var datecheck: Button
    private lateinit var missubmit: Button


    // TextView
    private lateinit var textdateviewgo: TextView
    private lateinit var textdateviewend: TextView
    private lateinit var datecheckshow: TextView


    // SimpleDateFormat
    private lateinit var startYear: SimpleDateFormat
    private lateinit var startMonth: SimpleDateFormat
    private lateinit var startDay: SimpleDateFormat
    private lateinit var endYear: SimpleDateFormat
    private lateinit var endMonth: SimpleDateFormat
    private lateinit var endDay: SimpleDateFormat

    private lateinit var editText :EditText
    val taskOper = TaskOper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_page)
        var filePath:String = filesDir.absolutePath
        taskOper.setFilePath("$filePath/dataBase.json")
        buttonpickgo = findViewById(R.id.button_pick_date_go)
        buttonpickend = findViewById(R.id.button_pick_date_end)
        textdateviewgo = findViewById(R.id.textView_date_show_go)
        textdateviewend = findViewById(R.id.textView_date_show_end)
        missubmit = findViewById(R.id.button_submit)
        editText = findViewById(R.id.editTextTextPersonName)

        val myCalendargo = Calendar.getInstance()
        val myCalendarend = Calendar.getInstance()
        var dateIsValid: Boolean = false
        var nameIsValid :Boolean = false
        fun startDate(myCalendargo: Calendar) {
            val start_year = "yyyy"
            val start_month = "MM"
            val start_day = "dd"
            startYear = SimpleDateFormat(start_year, Locale.TAIWAN)
            startMonth = SimpleDateFormat(start_month, Locale.TAIWAN)
            startDay = SimpleDateFormat(start_day, Locale.TAIWAN)
            textdateviewgo.setText(
                startYear.format(myCalendargo.time) + "/" +
                        startMonth.format(myCalendargo.time) + "/" +
                        startDay.format(myCalendargo.time))

        }

        val date_start = DatePickerDialog.OnDateSetListener { datePickerstart, i, i2, i3 ->
            myCalendargo.set(Calendar.YEAR, i)
            myCalendargo.set(Calendar.MONTH, i2)
            myCalendargo.set(Calendar.DAY_OF_MONTH, i3)
            startDate(myCalendargo)
        }


        fun endDate(myCalendarend: Calendar) {
            val end_year = "yyyy"
            val end_month = "MM"
            val end_day = "dd"
            endYear = SimpleDateFormat(end_year, Locale.TAIWAN)
            endMonth = SimpleDateFormat(end_month, Locale.TAIWAN)
            endDay = SimpleDateFormat(end_day, Locale.TAIWAN)
            textdateviewend.setText(
                endYear.format(myCalendarend.time) + "/" +
                        endMonth.format(myCalendarend.time) + "/" +
                        endDay.format(myCalendarend.time))

        }


        val date_end = DatePickerDialog.OnDateSetListener { datePickerend, i, i2, i3 ->
            myCalendarend.set(Calendar.YEAR, i)
            myCalendarend.set(Calendar.MONTH, i2)
            myCalendarend.set(Calendar.DAY_OF_MONTH, i3)
            endDate(myCalendarend)
        }

        buttonpickgo.setOnClickListener {
            DatePickerDialog(
                this, date_start,
                myCalendargo.get(Calendar.YEAR),
                myCalendargo.get(Calendar.MONTH),
                myCalendargo.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        buttonpickend.setOnClickListener {
            DatePickerDialog(
                this, date_end,
                myCalendarend.get(Calendar.YEAR),
                myCalendarend.get(Calendar.MONTH),
                myCalendarend.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 新增任務
        missubmit.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val startday: String = textdateviewgo.text.toString()
            val endday: String = textdateviewend.text.toString()
            if(startday=="" || endday==""){
                Toast.makeText(this,"日期錯誤",Toast.LENGTH_SHORT).show()
                textdateviewend.setText("")
            }else {
                val d1: Date = sdf.parse(startday)
                val d2: Date = sdf.parse(endday)
                val comp = d1.compareTo(d2)
                when {
                    comp > 0 -> {
                        // 這個時間選擇是不被允許的
                        Toast.makeText(this, "日期錯誤", Toast.LENGTH_SHORT).show()
                        textdateviewend.setText("")
                    }
                    comp < 0 -> {
                        // 轉到下一PAGE?
                        dateIsValid = true
                    }
                    comp == 0 -> {
                        dateIsValid = true
                    }
                    else -> {
                        Toast.makeText(this, "日期錯誤", Toast.LENGTH_SHORT).show()
                        textdateviewend.setText("")
                    }
                }
            }
            if(!dateIsValid){
                Toast.makeText(this,"日期錯誤",Toast.LENGTH_SHORT).show()
            }else{
                val yearDf = SimpleDateFormat("yyyy")
                val monthDf = SimpleDateFormat("MM")
                val dayDf = SimpleDateFormat("dd")
                val startDate = textdateviewgo.text.toString().split("/").toTypedArray()
                val endDate = textdateviewend.text.toString().split("/").toTypedArray()
                var start_Year = startDate[0].toInt()
                var start_Month = startDate[1].toInt()
                var start_Day = startDate[2].toInt()
                var end_Year = endDate[0].toInt()
                var end_Month = endDate[1].toInt()
                var end_Day = endDate[2].toInt()

                var startTime = Time(start_Year,start_Month,start_Day)
                var endTime = Time(end_Year,end_Month,end_Day)
                var newTask = Task(editText.text.toString(),startTime,endTime,
                    isFinished = false,
                    isOverTime = false
                )

                if(!taskOper.newTask(newTask)){
                    Toast.makeText(this,"任務名稱錯誤",Toast.LENGTH_SHORT).show()
                    editText.setText("")
                }else{
                    nameIsValid=true
                    taskOper.newTask(newTask)
                }
                if(nameIsValid)
                {
                    val intent = Intent(this@insertPage, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"任務創建完成!",Toast.LENGTH_SHORT).show()
                }
            }

        }












    }
}


