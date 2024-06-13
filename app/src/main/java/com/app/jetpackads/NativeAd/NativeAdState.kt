package com.app.jetpackads

import android.app.Activity
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.app.jetpackads.LibraryUtils.getActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

@Composable
fun rememberNativeAdCustom(
    adUInt: String,
    adListener: AdListener? = null,
    adOptions: NativeAdOptions? = null
) = LocalContext.current.getActivity()?.let {
    remember(adUInt) {
        NativeAdState(
            activity = it,
            adUInt = adUInt,
            adListener = adListener,
            adOptions = adOptions
        )

    }
}

class NativeAdState(
    activity: Activity,
    adUInt: String,
    adListener: AdListener?,
    adOptions: NativeAdOptions?
) {
    val nativeAd = MutableLiveData<NativeAd?>()

    init {
        AdLoader.Builder(activity, adUInt).let {
            if (adOptions != null)
                it.withNativeAdOptions(adOptions)
            else
                it
        }.let {
            if (adListener != null)
                it.withAdListener(adListener)
            else
                it
        }.forNativeAd { 
                nativeAd ->
            if (activity.isDestroyed){
                nativeAd.destroy()
                return@forNativeAd
            }
            this.nativeAd.postValue(nativeAd)
        }.build().loadAd(AdRequest.Builder().build())
    }
}