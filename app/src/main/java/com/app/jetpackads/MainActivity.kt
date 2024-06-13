package com.app.jetpackads

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.jetpackads.ui.theme.JetpackAdsTheme
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAdOptions
import java.util.*

class MainActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    val nativeAdId = "ca-app-pub-3940256099942544/2247696110"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackAdsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var adStatus by remember {
                        mutableStateOf(false)
                    }
                    Column {

                        val context = LocalContext.current


                        ////Banner Ad

                        Box(Modifier.height(50.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            BannerView(modifier = Modifier.fillMaxWidth())
                        }


                        /////App Open Ad
                        Box(Modifier.height(50.dp))
                        createTimer(5)
//                        Text(text = "App Open will load in 5 Second ")


                        //// InterStitial Ad
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = {
                                if (adStatus) {
                                    showInterstitialAd(context)
                                    loadInterstitialAd(
                                        context, adStatus = { adStatus = it })
                                } else {
                                    loadInterstitialAd(
                                        context, adStatus = { adStatus = it })

                                }
                            }) {
                                Text(text = "Load Interstitial Ad")
                            }
                        }

                        //// Native Ad
                        Box(Modifier.height(100.dp))
                        val nativeAdState by mainViewModel.nativeAdState.collectAsState()
                        val rememberCustomNativeAd = rememberNativeAdCustom(
                            adUInt = nativeAdId,
                            adOptions = NativeAdOptions.Builder()
                                .setVideoOptions(
                                    VideoOptions.Builder().setStartMuted(true)
                                        .setClickToExpandRequested(true).build()
                                ).setRequestMultipleImages(true).build(),
                            adListener = object : AdListener() {
                                override fun onAdLoaded() {
                                    super.onAdLoaded()
                                    mainViewModel.updateNativeAdState(AdState(isSuccess = true))
                                }

                                override fun onAdFailedToLoad(p0: LoadAdError) {
                                    super.onAdFailedToLoad(p0)
                                    mainViewModel.updateNativeAdState(
                                        AdState(
                                            isError = true,
                                            errorMessage = p0.message
                                        )
                                    )
                                }
                            }
                        )

                        //Load Native Ad
                        NativeAdViewSection(
                            nativeAdState = nativeAdState,
                            rememberNativeAdState = rememberCustomNativeAd
                        )
                    }
                }
            }
        }
    }


    private fun createTimer(seconds: Long) {

        var number: Long
        val countDownTimer: CountDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                number = (millisUntilFinished / 1000 + 1)

            }

            override fun onFinish() {
                number = 0L
                val application = application
                if (application !is App) {
                    Log.e("LOG_TAG", "Failed to cast application to MyApplication.")
//                    startMainActivity()
                    return
                }

                // Show the app open ad.
                (application as App)
                    .showAdIfAvailable(
                        this@MainActivity,
                        object : App.OnShowAdCompleteListener {
                            override fun onShowAdComplete() {
//                                startMainActivity()
                            }
                        })
            }
        }
        countDownTimer.start()
    }


}




