package ru.veider.about

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import ru.veider.about.databinding.DialogAboutBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AboutDialog(context: Context) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder = DialogAboutBinding.inflate(layoutInflater)
        setContentView(binder.root)
    }
}