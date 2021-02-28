package com.airongomes.scenarioautomation.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airongomes.scenarioautomation.R

@BindingAdapter("projectUser")
fun TextView.projectUser(userName: String){
    val textString = "Usuário: $userName"
    text = textString
}

@BindingAdapter("projectAddress")
fun TextView.projectAddress(address: String){
    text = if(address.isBlank()) {
        context.getString(R.string.address_not_registered)
    } else {
        val textString = "Endereço: $address"
        textString
    }


}