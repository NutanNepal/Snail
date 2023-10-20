package com.lumu.snail.generalWebView

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.lumu.snail.databinding.FragmentWebviewNotesBinding


class WebViewFragment(
    url: String,
    private val javaScriptInjectionKey: String) : Fragment() {

    private var _binding: FragmentWebviewNotesBinding? = null
    private var currentWebPageUrl: String = url
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewNotesBinding.inflate(inflater, container, false)
        val webview = binding.webViewNotes

        // Set up a callback for handling back button presses
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (webview.canGoBack()) {
                // If the WebView can go back, navigate to the previous page
                webview.goBack()
                currentWebPageUrl = webview.url.toString()
            }
            else {
                isEnabled = false
                requireActivity().finish()
            }
        }

        webview.settings.isAlgorithmicDarkeningAllowed = true

        enableSettings(webview)
        webview.webViewClient = GeneralWebViewClient(
            JavaScriptCodes[javaScriptInjectionKey].toString()
        )
        renderWebView(webview, currentWebPageUrl)

        return binding.root
    }

    fun getcurrentWebPageURL(): String{
        return currentWebPageUrl
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderWebView(webview: WebView, pageURL: String){
        webview.visibility = View.INVISIBLE
        webview.loadUrl(pageURL)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun enableSettings(view: WebView){
        view.settings.javaScriptEnabled=true
        view.clearCache(true)
        view.settings.domStorageEnabled=true
        view.settings.allowFileAccess = true
        view.settings.allowContentAccess = true
        view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        view.settings.displayZoomControls = false
        view.settings.builtInZoomControls = false
        view.settings.setSupportZoom(false)
        //view.settings.useWideViewPort = false
    }
}