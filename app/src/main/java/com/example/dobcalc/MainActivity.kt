package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMin : TextView? = null

//    the initial choosen value of the date is null, we are not assigning it anything

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnpicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMin = findViewById(R.id.tvAgeInMinutes)
        btnpicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){

//        using the in-built class calendar to get access to the date/month/year
//        myCalendar is the object of the Calendar class and we are accessing the year and month and day

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

//        Android DatePicker is a user interface control which is used
//        to select the date by day, month and year in our android application.

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ view, selectedYear, selectedMonth, selectedDayOfMonth ->

                Toast.makeText(this, "Year selected is $selectedYear Month selected " +
                        "is ${selectedMonth+1} and Day selected is $selectedDayOfMonth"
                    , Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
//                selected date is a string
                tvSelectedDate?.text = selectedDate
//                since tvSelectedDate is null initially so we are using a ?

//                this allows us to define us a pattern of the date format we want to use
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
//              selected date is a string and now we are converting it into a format that we want to
//              use in the date object

                val selectedDateInMin = theDate.time / 60000
//                this is the time that has passed since 1st jan 1970 uptill the selected b'day date
//               we are using the inbuilt time property to convert the date into time
//               the time we are getting is in miliseconds, so converting it into
//               seconds by dividing by 1000 and then into minutes by dividing by 60

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
//                this will give the time which has passed since 1st jan 1970 till current time
                val currentDateInMin = currentDate.time / 60000

                val diffInMin = currentDateInMin - selectedDateInMin
                tvAgeInMin?.text = diffInMin.toString()
//                diffInMin is of type long and we are converting it into string

            },
            year,
            month,
            day
        )
// we are now restricting that we won't be able to select a day in future
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
//        since there are 86400000 miliseconds in 1 day
        dpd.show()
//        to display the date picker dialog we are using the show method here



    }
}