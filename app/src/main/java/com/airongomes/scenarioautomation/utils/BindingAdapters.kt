package com.airongomes.scenarioautomation.utils

import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airongomes.scenarioautomation.R
import java.util.*

// --------- Adapter para Item Project ------------
@BindingAdapter("projectUser")
fun TextView.projectUser(userName: String){
    val textString = "${resources.getText(R.string.user)} $userName"
    text = textString
}

@BindingAdapter("projectAddress")
fun TextView.projectAddress(dateMilli: Long){
    val calendar = Calendar.getInstance()
    calendar.time = Date(dateMilli)
    val dateFormatter = DateFormat.getDateFormat(context).format(calendar.time)
    val textString = "${resources.getText(R.string.modified_date)} $dateFormatter"
    text = textString
}

@BindingAdapter("projectType")
fun ImageView.projectType(type: ProjectType){
    setImageResource(when(type) {
        ProjectType.HOME -> R.drawable.ic_home
        ProjectType.STORE -> R.drawable.ic_store
        else -> R.drawable.ic_building
    })
}