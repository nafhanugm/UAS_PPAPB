package com.iqonic.phonphon_store.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Discount :Serializable{

    @SerializedName("discount")
    var discount:String?=null

    var isSelected:Boolean?=false


}