package com.lumu.snail.sage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.amrdeveloper.codeview.CodeView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.color.DynamicColors
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lumu.snail.R


class SageActivity : FragmentActivity() {

    private lateinit var codeView: CodeView
    private lateinit var sageView: WebView
    private lateinit var codeViewCardView: CardView
    private lateinit var sageViewCardView: CardView


    private var isShowingEditor = true
    private var animationDuration: Long = 150

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view for this activity
        setContentView(R.layout.activity_sage)
        //DynamicColors.applyToActivityIfAvailable(this)
        val sageTitle = getSageMode()

        val topAppBar = this.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.title = sageTitle

        codeView = findViewById(R.id.sageEditor)
        configCodeView()
        codeView.text = SpannableStringBuilder(SageCodes[sageTitle])
        sageView = findViewById(R.id.sageView)
        enableSettings(sageView)
        codeViewCardView = findViewById(R.id.codeView_cardView)
        sageViewCardView = findViewById(R.id.sageView_cardView)

        topAppBar.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        val scale = resources.displayMetrics.density
        val distance = 15000
        codeViewCardView.cameraDistance = distance * scale
        sageViewCardView.cameraDistance = distance * scale

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.evaluate -> {
                    // Handle edit text press
                    // Get a reference to the input method manager
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    // Hide the keyboard
                    imm.hideSoftInputFromWindow(topAppBar.windowToken, 0)

                    inputSage = codeView.text.toString()
                    renderStrings(inputSage)
                    true
                }
                else -> false
            }
        }

        /*
        findViewById<FloatingActionButton>(R.id.flip_fab).setOnClickListener {
            // Handle the click event for the FloatingActionButton
            flipflip()
        }
         */
    }

    private fun configCodeView() {

        // Change default font to JetBrains Mono font
        val jetBrainsMono = ResourcesCompat.getFont(this, R.font.jetbrains_mono_medium)
        codeView.typeface = jetBrainsMono

        // Setup Line number feature
        //codeView.setEnableLineNumber(true)
        //codeView.setLineNumberTextColor(Color.GRAY)
        //codeView.setLineNumberTextSize(30f)

        // Setup highlighting current line
        codeView.setEnableHighlightCurrentLine(true)
        //codeView.setHighlightCurrentLineColor(R.color.launcher)

        // Setup Auto indenting feature
        codeView.setTabLength(4)
        codeView.setEnableAutoIndentation(true)

        // Setup auto pair complete
        val pairCompleteMap: MutableMap<Char, Char> = HashMap()
        pairCompleteMap['{'] = '}'
        pairCompleteMap['['] = ']'
        pairCompleteMap['('] = ')'
        pairCompleteMap['<'] = '>'
        pairCompleteMap['"'] = '"'
        pairCompleteMap['\''] = '\''
        codeView.setPairCompleteMap(pairCompleteMap)
        codeView.enablePairComplete(true)
        codeView.enablePairCompleteCenterCursor(true)

        codeView.setTextIsSelectable(false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun enableSettings(view: WebView) {
        view.webViewClient = MyWebClient()
        view.settings.javaScriptEnabled = true
        view.settings.domStorageEnabled = true
        view.settings.allowFileAccess = true
        view.settings.allowContentAccess = true
        view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        view.settings.displayZoomControls = false
        view.settings.builtInZoomControls = false
        view.settings.setSupportZoom(false)
        view.settings.useWideViewPort = false
    }

    private fun renderStrings(inputSage: String) {
        sageView.loadDataWithBaseURL(
            "https://sagecell.sagemath.org/",
            SageParse.generateHtmlContent(inputSage),
            "text/html",
            "utf-8",
            null
        )
    }

    private fun flipflip() {
        if (isShowingEditor) {
            flipCard()
        } else {
            flipCardBack()
        }
    }

    private fun flipCard() {
        ViewCompat.animate(codeViewCardView)
            .rotationY(-90f)
            .setDuration(animationDuration)
            .withEndAction {
                isShowingEditor = false
                codeViewCardView.visibility = View.GONE
                sageViewCardView.visibility = View.VISIBLE
                // Set rotationY value here
                sageViewCardView.rotationY = 90f
                // Start second animation
                ViewCompat.animate(sageViewCardView)
                    .rotationY(0f)
                    .setDuration(animationDuration)
                    .start()
            }
            .start()
    }

    private fun flipCardBack() {
        ViewCompat.animate(sageViewCardView)
            .rotationY(90f)
            .setDuration(animationDuration)
            .withEndAction {
                isShowingEditor = true
                sageViewCardView.visibility = View.GONE
                codeViewCardView.visibility = View.VISIBLE
                // Set rotationY value here
                codeViewCardView.rotationY = -90f
                // Start second animation
                ViewCompat.animate(codeViewCardView)
                    .rotationY(0f)
                    .setDuration(animationDuration)
                    .start()
            }
            .start()
    }

    private fun getSageMode(): String{
        return intent?.getStringExtra(SAGEMODE).toString()
    }

    companion object {
        private const val SAGEMODE = "sageMode"
        var inputSage: String = ""
        fun newIntent(context: Context, sageMode: String): Intent {
            return Intent(context, SageActivity::class.java).apply {
                putExtra(SAGEMODE, sageMode)
            }
        }
    }
}
