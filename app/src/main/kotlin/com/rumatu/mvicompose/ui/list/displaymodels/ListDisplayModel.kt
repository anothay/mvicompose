package com.rumatu.mvicompose.ui.list.displaymodels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListDisplayModel(
    val title: String,
    val body: String,
    val commentCount: Int,
    val username: String
) : Parcelable
