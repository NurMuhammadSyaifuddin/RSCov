package com.project.rscov.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.project.rscov.R
import com.project.rscov.databinding.LayoutDialogErrorBinding

fun showErrorDialog(context: Context?, message: String){
    val binding = LayoutDialogErrorBinding.inflate(LayoutInflater.from(context))
    binding.tvMessage.text = message

    AlertDialog
        .Builder(context)
        .setView(binding.root)
        .setCancelable(true)
        .create()
        .show()
}

fun hideSoftKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun ImageView.loadImage(url: String){
    Glide
        .with(this.context)
        .load(url)
        .placeholder(R.color.grey)
        .into(this)
}

fun popUpImage(context: Context, url: String) {
    ImagePopup(context).apply {
        windowWidth = WindowManager.LayoutParams.MATCH_PARENT
        windowHeight = WindowManager.LayoutParams.WRAP_CONTENT
        minimumHeight = 200
        maxHeight = 600
        backgroundColor = Color.BLACK
        isHideCloseIcon = true
        initiatePopupWithGlide(url)
        viewPopup()
    }
}


fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.enabled(){
    isEnabled = true
}

fun View.disabled(){
    isEnabled = false
}