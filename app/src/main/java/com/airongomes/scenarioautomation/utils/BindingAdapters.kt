package com.airongomes.scenarioautomation.utils

import android.net.Uri
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.Project
import com.bumptech.glide.Glide
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
@BindingAdapter("projectDate")
fun TextView.projectDate(dateMilli: Long?){
    dateMilli?.let {
        val calendar = Calendar.getInstance()
        calendar.time = Date(dateMilli)
        val dateFormatter = formatDateTimeStyle(calendar, context)
        val textString = "${resources.getText(R.string.modified_date)} $dateFormatter"
        text = textString
    }

}

/** --------- Adapter para Item Project ------------ */
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
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
                .load(imageUri)
                .apply(
                        RequestOptions()
                                .placeholder(R.drawable.animation_loading)
                                .error(R.drawable.ic_broken)
                ).into(this)

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

/** --------- Adapter para New Project & Item Project ------------ */
@BindingAdapter("projectName")
fun TextView.projectName(project: Project?){
    project?.let {
        text = project.projectName
    }
}

/** --------- Adapter para New Project & Item Project ------------ */
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

/** --------- Adapter para New Environment ------------ */
@BindingAdapter("environmentName")
fun TextView.environmentName(environment: Environment?){
    environment?.let {
        text = environment.environmentName
    }
}

/** --------- Adapter para Item Environment & New Environment ------------ */

@BindingAdapter("imageEnvironment")
fun ImageView.imageEnvironment(environment: Environment?){
    environment?.let {
        if (it.imageUri != null) {
            val imageUri = Uri.parse(it.imageUri)
            Glide.with(context)
                    .load(imageUri)
                    .apply(
                            RequestOptions()
                                    .error(R.drawable.ic_broken)

                    )
                    .into(this)
        }
    }
    if (environment?.imageUri == null) {
        setImageResource(R.drawable.ic_image)
    }
}

/** --------- Adapter para New Environment ------------ */

@BindingAdapter("imageUri")
fun ImageView.imageUri(uri: Uri?){
    uri?.let {
        Glide.with(context)
                .load(it)
                .apply(
                        RequestOptions()
                                .error(R.drawable.ic_broken))
                .into(this)

    }
    if (uri == null) {
        setImageResource(R.drawable.ic_image)
    }
}