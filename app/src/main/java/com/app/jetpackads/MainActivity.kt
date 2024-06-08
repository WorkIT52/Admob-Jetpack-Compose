package com.app.jetpackads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.app.jetpackads.ui.theme.JetpackAdsTheme

class MainActivity : ComponentActivity() {

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
                            Text(text = "Load Ad")
                        }
                    }
                }
            }
        }
    }
}


