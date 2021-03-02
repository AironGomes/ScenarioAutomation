package com.airongomes.scenarioautomation.utils

import android.text.format.DateFormat
import android.util.Log
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.database.Project
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.stream.MediaStoreImageThumbLoader
import com.bumptech.glide.request.RequestOptions
import java.util.*

/** --------- Adapter para Item Project ------------ */
@BindingAdapter("projectUser")
fun TextView.projectUser(userName: String?){
    userName?.let {
        val textString = "${resources.getText(R.string.user)} $userName"
        text = textString
    }
}

/** --------- Adapter para Item Project ------------ */
@BindingAdapter("projectAddress")
fun TextView.projectAddress(dateMilli: Long?){
    dateMilli?.let {
        val calendar = Calendar.getInstance()
        calendar.time = Date(dateMilli)
        val dateFormatter = DateFormat.getDateFormat(context).format(calendar.time)
        val textString = "${resources.getText(R.string.modified_date)} $dateFormatter"
        text = textString
    }

}

/** --------- Adapter para Item Project & FragmentDetailProject ------------ */
@BindingAdapter("projectType")
fun ImageView.projectType(type: ProjectType?){
    type?.let {
        setImageResource(when(type) {
            ProjectType.HOME -> R.drawable.ic_home
            ProjectType.STORE -> R.drawable.ic_store
            else -> R.drawable.ic_building
        })
    }

}

/** --------- Adapter para Fragment About ------------ */
@BindingAdapter("imageAbout")
fun ImageView.imageAbout(imageUrl: String?){
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(context)
                .load(imageUri)
                .apply(
                        RequestOptions()
                                .placeholder(R.drawable.animation_loading)
                                .error(R.drawable.ic_broken))
                .into(this)

    }
}

/** --------- Adapter para New Project ------------ */
@BindingAdapter("titleNewProject")
fun TextView.titleNewProject(project: Project?){
    text = if(project == null) {
        context.getText(R.string.title_new_project)
    } else {
        context.getText(R.string.title_edit_project)
    }
}

/** --------- Adapter para New Project ------------ */
@BindingAdapter("projectName")
fun TextView.projectName(project: Project?){
    project?.let {
        text = project.projectName
    }
}

/** --------- Adapter para New Project ------------ */
@BindingAdapter("projectUser")
fun TextView.projectUser(project: Project?){
    project?.let {
        text = project.userName
    }
}

/** --------- Adapter para New Project ------------ */
@BindingAdapter("projectAddress")
fun TextView.projectAddress(project: Project?){
    project?.let {
        text = project.address
    }
}

/** --------- Adapter para New Project ------------ */
@BindingAdapter("chooseTypeProject")
fun RadioGroup.chooseTypeProject(project: Project?){
    check(
            if(project == null) {
                R.id.type_house
            } else {
                when(project.type) {
                    ProjectType.HOME -> R.id.type_house
                    ProjectType.STORE -> R.id.type_store
                    else -> R.id.type_building
                }
            }
    )
}