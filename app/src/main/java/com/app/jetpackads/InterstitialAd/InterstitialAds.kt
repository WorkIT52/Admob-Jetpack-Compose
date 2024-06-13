package com.app.jetpackads

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


var minterstitialAd: InterstitialAd? = null
val adUnitId: String = "ca-app-pub-3940256099942544/1033173712"
fun loadInterstitialAd(context: Context, adStatus: (Boolean) -> Unit) {
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(p0: LoadAdError) {
            super.onAdFailedToLoad(p0)
            minterstitialAd = null
            adStatus.invoke(false)
        }

        override fun onAdLoaded(p0: InterstitialAd) {
            super.onAdLoaded(p0)
            minterstitialAd = p0
            adStatus.invoke(true)
        }
    })
}

fun showInterstitialAd(context: Context) {
    minterstitialAd.let { ad ->

        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    minterstitialAd = null
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                }
            }

            ad.show(context as Activity)
        }
    } ?: kotlin.run {

    }
}