package com.lumu.snail.generalWebView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lumu.snail.R
import com.lumu.snail.databinding.ActivityWebviewBinding
import com.lumu.snail.tableOfContents.Category

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private var currentWebURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentWebURL =
                savedInstanceState.getString(
                    "CURRENT_NAV_STATE", currentWebURL)?:
                    URLs[getPageTitle()].toString()
        }

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolBar = binding.toolbar
        val pageTitle = getPageTitle()
        toolBar.title = pageTitle

        toolBar.setNavigationOnClickListener {
            finish()
        }
        currentWebURL = URLs[pageTitle].toString()

        val fragment: WebViewFragment = if (isStringInAMSArticles(pageTitle)){
            WebViewFragment(
                currentWebURL, "AMSArticles")
        } else {
            WebViewFragment(currentWebURL, pageTitle)
        }

        replaceFragmentContainer(
            R.id.fragment_content_notes,
            fragment
        )

        currentWebURL = fragment.getcurrentWebPageURL()

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.refresh -> {
                    replaceFragmentContainer(
                        R.id.fragment_content_notes,
                        WebViewFragment(currentWebURL, JavaScriptCodes[pageTitle].toString())
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun isStringInAMSArticles(input: String): Boolean {
        return Category.AMSArticles.chapterList.contains(input)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            putString("CURRENT_NAV_STATE", currentWebURL)
        }
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun replaceFragmentContainer(oldFragment: Int, newFragment: Fragment) {

        // Get the FragmentManager
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(oldFragment, newFragment)

        // Commit the transaction
        fragmentTransaction.commit()
   }

    private fun getPageTitle():String{
        return intent?.getStringExtra(PAGETITLE).toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webview_menu, menu)
        return true
    }

    companion object{
        const val PAGETITLE = "pageTitle"
        fun newIntent(context: Context, pageTitle: String): Intent {
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra(PAGETITLE, pageTitle)
            }
        }
    }
}