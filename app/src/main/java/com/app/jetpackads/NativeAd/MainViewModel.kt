package com.app.jetpackads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _nativeAdState = MutableStateFlow(AdState())
    val nativeAdState = _nativeAdState.asStateFlow()
    fun updateNativeAdState(nativeAdState: AdState) = viewModelScope.launch {
        _nativeAdState.update { nativeAdState }
    }
}