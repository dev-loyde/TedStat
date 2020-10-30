package com.devloyde.healthguard.networking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.ConnectivityManager.EXTRA_NETWORK_INFO
import android.net.NetworkInfo
import com.devloyde.healthguard.networking.ConnectivityProvider.NetworkState.*


@Suppress("DEPRECATION")
class ConnectivityProviderLegacyImpl(
    private val context: Context,
    private val cm: ConnectivityManager
) : ConnectivityProviderBaseImpl() {

    private val receiver = ConnectivityReceiver()

    override fun subscribe() {
        context.registerReceiver(receiver, IntentFilter(CONNECTIVITY_ACTION))
    }

    override fun unsubscribe() {
        context.unregisterReceiver(receiver)
    }

    override fun getNetworkState(): ConnectivityProvider.NetworkState {
        val activeNetworkInfo = cm.activeNetworkInfo
        return if (activeNetworkInfo != null) {
            ConnectedState.ConnectedLegacy(activeNetworkInfo)
        } else {
            NotConnectedState
        }
    }

    private inner class ConnectivityReceiver : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            // on some devices ConnectivityManager.getActiveNetworkInfo() does not provide the correct network state
            // https://issuetracker.google.com/issues/37137911
            val networkInfo = cm.activeNetworkInfo
            val fallbackNetworkInfo: NetworkInfo? = intent.getParcelableExtra(EXTRA_NETWORK_INFO)
            // a set of dirty workarounds
            val state: ConnectivityProvider.NetworkState =
                if (networkInfo?.isConnectedOrConnecting == true) {
                    ConnectedState.ConnectedLegacy(networkInfo)
                } else if (networkInfo != null && fallbackNetworkInfo != null &&
                    networkInfo.isConnectedOrConnecting != fallbackNetworkInfo.isConnectedOrConnecting
                ) {
                    ConnectedState.ConnectedLegacy(fallbackNetworkInfo)
                } else {
                    val state = networkInfo ?: fallbackNetworkInfo
                    if (state != null) ConnectivityProvider.NetworkState.ConnectedState.ConnectedLegacy(
                        state
                    ) else ConnectivityProvider.NetworkState.NotConnectedState
                }
            dispatchChange(state)
        }
    }
}