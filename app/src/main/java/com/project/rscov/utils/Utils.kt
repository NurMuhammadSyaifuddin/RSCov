package com.project.rscov.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
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