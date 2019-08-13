package com.example.bloodpressure

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class BloodPress : RealmObject(){
    @PrimaryKey
    var id:Long = 0
    var datetime:Date = Date()
    var max:Long = 0
    var min:Long = 0
    var pulse:Long = 0
}