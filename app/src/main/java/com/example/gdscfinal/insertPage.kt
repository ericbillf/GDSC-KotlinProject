package com.example.gdscfinal

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class insertPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_page)

                val btn_pick: Button = findViewById(R.id.button_pick_date)
                val text_date: TextView = findViewById(R.id.textView_date_show)
                val formatedate = SimpleDateFormat("yyyy/mm/dd", Locale.TAIWAN)
                // Button click to show calendar:
                btn_pick.setOnClickListener({
                    val cal = Calendar.getInstance()
                    val dpick = DatePickerDialog(this, android.R.style.Holo_Light_ButtonBar, { datePicker, i1, i2, i3 ->
                            val selectdate : Calendar = Calendar.getInstance()
                            selectdate.set(Calendar.YEAR,i1)
                            selectdate.set(Calendar.MONTH,i2)
                            selectdate.set(Calendar.DAY_OF_MONTH,i3)
                            val date : String = formatedate.format(selectdate.time)
                            text_date.text = " 你挑選的日期為:" + date
                        },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                    dpick.show()
                })
            }
}