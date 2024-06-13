package com.app.jetpackads

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.android.gms.ads.MediaContent
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NativeAdViewSection(nativeAdState: AdState, rememberNativeAdState: NativeAdState?) {

    rememberNativeAdState?.let {
        val nativeAd by it.nativeAd.observeAsState()
        AnimatedContent(targetState = nativeAdState.isSuccess, label = "") { success ->
            if (success) {
                // NativeADView is Here
                Column(modifier = Modifier.wrapContentSize()){
                    ShowNativeAdView(nativeAd = nativeAd)
                }
            } else {
                Box(modifier = Modifier.size(20.dp)) {
                    CircularProgressIndicator(color = Color.Blue)
                }
            }

        }
    }

}

@Composable
fun NativeAdViewComponse(
    modifier: Modifier = Modifier, content: @Composable (nativeAdView: NativeAdView) -> Unit
) = AndroidView(modifier = modifier, factory = {
    NativeAdView(it)
}, update = {
    val componseView = ComposeView(it.context)
    it.removeAllViews()
    it.addView(componseView)
    componseView.setContent { content(it) }

})


@Composable
fun NativeAdMediaView(
    nativeAdView: NativeAdView,
    mediaContent: MediaContent,
    scalType: ImageView.ScaleType
) = AndroidView(factory = { MediaView(it) }, update = {
    nativeAdView.mediaView = it
    nativeAdView.mediaView?.mediaContent = mediaContent
    nativeAdView.mediaView?.setImageScaleType(scalType)
})

@Composable
fun NativeAdMediaView(
    mediaContent: MediaContent,
    nativeAd: NativeAd,
    scalType: ImageView.ScaleType
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.layout_native_ad, null)
            view
        }){
            val nativeAdView = it.findViewById<NativeAdView>(R.id.nativeAdView)
            val mediaView = it.findViewById<MediaView>(R.id.mediaView)
            nativeAdView.mediaView =  mediaView
            nativeAdView.mediaView?.setMediaContent(mediaContent)
            nativeAdView.mediaView?.setImageScaleType(ImageView.ScaleType.FIT_CENTER)
            nativeAdView.setNativeAd(nativeAd)
        }
    }
}

@Composable
fun NativeAdImageView(
    modifier: Modifier = Modifier,
    drawable: Drawable?,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) = Image(
    painter = rememberDrawablePainter(drawable = drawable),
    contentDescription = contentDescription,
    modifier = modifier,
    colorFilter = colorFilter,
    contentScale = contentScale,
    alignment = alignment,
    alpha = alpha
)


@Composable
fun NativeAdViewLayout(
    getView: (ComposeView) -> Unit, content: @Composable () -> Unit
) = AndroidView(factory = { ComposeView(it) }, update = {
    it.setContent(content)
    getView(it)
}
)