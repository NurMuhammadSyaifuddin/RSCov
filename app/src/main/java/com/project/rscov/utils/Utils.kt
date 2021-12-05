package com.project.rscov.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.project.rscov.R
import com.project.rscov.databinding.LayoutDialogErrorBinding

fun showErrorDialog(context: Context?, message: String){
    val binding = LayoutDialogErrorBinding.inflate(LayoutInflater.from(context))
    binding.textViewError.text = message

    AlertDialog
        .Builder(context)
        .setView(binding.root)
        .setCancelable(true)
        .create()
        .show()
}

fun ImageView.loadImage(url: String){
    Glide
        .with(this.context)
        .load(url)
        .placeholder(R.color.grey)
        .into(this)
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun ProgressBar.showLoading(state: Boolean){
    if (state) visible() else gone()
}