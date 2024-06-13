package com.app.jetpackads

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.nativead.NativeAd

@Composable
fun ShowNativeAdView(nativeAd: NativeAd?) {

    if (nativeAd != null) {
        NativeAdViewComponse { nativeAdView ->
            nativeAdView.setNativeAd(nativeAd)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    NativeAdViewLayout(getView = {
                        nativeAdView.iconView = it
                    }) {
                        NativeAdImageView(
                            drawable = nativeAd.icon?.drawable,
                            contentDescription = "Icon",
                            modifier = Modifier.size(45.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(start = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NativeAdViewLayout(getView = {
                            nativeAdView.headlineView = it
                        }) {
                            Text(
                                text = nativeAd.headline ?: "-",
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Row(
                        Modifier
                            .wrapContentSize()
                            .background(Color.Black)
                            .clip(RoundedCornerShape(2.dp))
                    ) {
                        Text(
                            text = "Ad",
                            fontSize = 15.sp,
                            color = Color.White,
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(5.dp)
                ) {
                    nativeAd.mediaContent?.let { mediaContent ->
                        NativeAdMediaView(
                            nativeAd = nativeAd,
                            mediaContent = mediaContent,
                            scalType = ImageView.ScaleType.FIT_CENTER
                        )
                    }


                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(top = 5.dp)
                ) {
                    NativeAdViewLayout(getView = { nativeAdView.bodyView = it }) {
                        Text(text = nativeAd.body ?: "-" , color = Color.Black)
                    }
                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(top = 5.dp),
                    contentPadding = PaddingValues(3.dp)
                ) {
                    Text(text = nativeAd.callToAction ?: "-")

                }
            }
        }
    }
}



