package com.example.gdscfinal

import android.os.Bundle
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_page)

        buttonpickgo = findViewById(R.id.button_pick_date_go)
        buttonpickend = findViewById(R.id.button_pick_date_end)
        textdateviewgo = findViewById(R.id.textView_date_show_go)
        textdateviewend = findViewById(R.id.textView_date_show_end)
        datecheck = findViewById(R.id.button_date_check)
        datecheckshow = findViewById(R.id.textView_date_check)
        missubmit = findViewById(R.id.button_submit)


        val myCalendargo = Calendar.getInstance()
        val myCalendarend = Calendar.getInstance()

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





////////////////////////////////////////////////////////////////////////////////////////////////////
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

        // 比較日期
        datecheck.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val startday: String = textdateviewgo.text.toString()
            val endday: String = textdateviewend.text.toString()
            val d1: Date = sdf.parse(startday)
            val d2: Date = sdf.parse(endday)
            val comp = d1.compareTo(d2)
            when {
                comp > 0 -> {
                    // 這個時間選擇是不被允許的
                    Toast.makeText(this,"錯誤的選擇!",Toast.LENGTH_SHORT).show()
                    textdateviewend.setText("")
                }
                comp < 0 -> {
                    // 轉到下一PAGE?
                }
                else -> {

                }

        }

    }
        // 新增任務
        missubmit.setOnClickListener {


        }




















            }
        }

////////////////////////////////////////////////////////////////////////////////////////////////////