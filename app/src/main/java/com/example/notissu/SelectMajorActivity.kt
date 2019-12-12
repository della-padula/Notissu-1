package com.example.notissu

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.notissu.data.MajorData
import com.example.notissu.presenter.SelectMajorContract
import com.example.notissu.presenter.SelectMajorPresenter
import com.example.notissu.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_select_major.*

class SelectMajorActivity : AppCompatActivity(), SelectMajorContract.View {

    private lateinit var presenter: SelectMajorPresenter
    private var majorNumber: Int = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_major)
        val window: Window = window

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.white)

        presenter = SelectMajorPresenter()

        presenter.view = this@SelectMajorActivity

        presenter.data = MajorData.getInstance()

        presenter.loadItem()

        startButton.setOnClickListener {
            SharedPreferenceUtil.setIntValue("major", majorNumber)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun update(item: List<String>) {
        val majorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, item)

        spinner.adapter = majorAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                majorNumber = position
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}
