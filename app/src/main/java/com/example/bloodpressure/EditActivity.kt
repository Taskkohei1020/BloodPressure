package com.example.bloodpressure

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {
    private var tag = "BloodPressure"
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        realm = Realm.getDefaultInstance()

        val bpId = intent.getLongExtra("id",0L)
        if(bpId > 0L){
            val bloodPress = realm.where<BloodPress>()
                .equalTo("id",bpId).findFirst()
            maxEdit.setText(bloodPress?.max.toString())
            minEdit.setText(bloodPress?.min.toString())
            pulseEdit.setText(bloodPress?.pulse.toString())
            deleteBtn.visibility = View.VISIBLE
        }else{
            deleteBtn.visibility = View.INVISIBLE
        }

        saveBtn.setOnClickListener{
            var max: Long = 0
            var min: Long = 0
            var pulse: Long = 0

            if (!maxEdit.text.isNullOrEmpty()) {
                max = maxEdit.text.toString().toLong()
            }
            if (!minEdit.text.isNullOrEmpty()) {
                min = minEdit.text.toString().toLong()
            }
            if (!pulseEdit.text.isNullOrEmpty()) {
                pulse = pulseEdit.text.toString().toLong()
            }

            when(bpId){
                0L ->{
                    realm.executeTransaction {
                        var maxId = realm.where<BloodPress>().max("id")
                        var nextId = (maxId?.toLong() ?: 0L) + 1L
                        var bloodPress = realm.createObject<BloodPress>(nextId)
                        bloodPress.datetime = Date()
                        bloodPress.max = max
                        bloodPress.min = min
                        bloodPress.pulse = pulse
                    }
                }
                else ->{
                    realm.executeTransaction {
                        var bloodPress = realm.where<BloodPress>()
                            .equalTo("id", bpId).findFirst()
                        bloodPress?.max = max
                        bloodPress?.min = min
                        bloodPress?.pulse = pulse
                    }
                }
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }

        deleteBtn.setOnClickListener {
            realm.executeTransaction {
                var bloodPress = realm.where<BloodPress>()
                    .equalTo("id", bpId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }
}
