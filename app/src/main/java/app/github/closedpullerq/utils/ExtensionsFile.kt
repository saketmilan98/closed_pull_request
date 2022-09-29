package app.github.closedpullerq.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import app.github.closedpullerq.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

val outputFormat = SimpleDateFormat("hh:mm a | dd MMM", Locale.US)
val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
    timeZone = TimeZone.getTimeZone("IST")
} //2022-09-28T11:53:47Z

fun Context.showToast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(path: String?) {
    try {
        if (!path.isNullOrEmpty()) {
            if (path.toIntOrNull() != null) {
                Glide.with(context).load(path.toInt()).placeholder(R.drawable.ic_github_icon).into(this)
            } else {
                Glide.with(context).load(path).placeholder(R.drawable.ic_github_icon).into(this)
            }
        } else {
            setImageResource(R.drawable.ic_github_icon)
        }
    } catch (ex: Exception) {

    }
}