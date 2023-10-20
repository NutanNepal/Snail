package com.lumu.snail.generalWebView

import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

open class GeneralWebViewClient(
        private val javaScriptInjectionCode: String) : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String?) {
                view.setInitialScale(400)
                view.evaluateJavascript(javaScriptInjectionCode, null)
                super.onPageFinished(view, url)
                view.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(
                view: WebView, request: WebResourceRequest): Boolean {
                return false
        }

        override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
        ) {
                super.onReceivedError(view, request, error)
                // Handle WebView errors here
        }
}
