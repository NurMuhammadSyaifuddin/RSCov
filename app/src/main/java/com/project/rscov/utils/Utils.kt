package com.project.rscov.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
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