package com.lumu.snail

import SettingsFragment
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.view.Menu
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lumu.snail.categoriesfragment.CategoriesFragment
import com.lumu.snail.categoriesfragment.MyCategoriesRecyclerViewAdapter
import com.lumu.snail.chaptersfragment.ChaptersFragment
import com.lumu.snail.chaptersfragment.MyChaptersRecyclerViewAdapter
import com.lumu.snail.flashcardsActivity.FlashcardActivity
import com.lumu.snail.generalWebView.WebViewActivity
import com.lumu.snail.sage.GlossaryView
import com.lumu.snail.sage.SageActivity
import com.lumu.snail.tableOfContents.Categories
import com.lumu.snail.tableOfContents.Category
import com.lumu.snail.tableOfContents.Subjects
import java.io.File


class MainActivity : AppCompatActivity(),
    MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener,
    MyChaptersRecyclerViewAdapter.OnChapterItemClickListener {

    private var currentTitle = "Home"

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            putString("CURRENT_NAV_STATE", currentTitle)
        }
        super.onSaveInstanceState(savedInstanceState)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //DynamicColors.applyToActivityIfAvailable(this)

        val topAppBar = this.findViewById<MaterialToolbar>(R.id.toolbar)
        val bottomAppBar = this.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //supportActionBar?.title = currentTitle

        topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    // Handle edit text press
                    true
                }
                R.id.favorite -> {
                    // Handle favorite icon press
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                else -> false
            }
        }

        bottomAppBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
                    true
                }
                R.id.navigation_dashboard -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.navigation_sync -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.navigation_settings -> {
                    replaceFragmentContainer(R.id.fragmentContainerView, SettingsFragment())
                    true
                }
                else -> false
            }
        }
        /*

        bottomAppBar.setOnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
                }
                R.id.navigation_dashboard -> {
                }
                R.id.navigation_sync -> {
                }
                R.id.navigation_settings -> {
                    replaceFragmentContainer(R.id.fragmentContainerView, SettingsFragment())
                }
            }
        }

         */

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    if (currentTitle == "Home") {
                        // If already on the CategoriesFragment
                        finish()
                    } else {
                        // Otherwise, go back to the CategoriesFragment and update the title
                        currentTitle = "Home"
                        replaceFragmentContainer(
                            R.id.fragmentContainerView,
                            CategoriesFragment()
                        )
                    }
                }
            }

        // Add the callback to the activity's back stack
        onBackPressedDispatcher.addCallback(this, callback)


        if (savedInstanceState != null) {
            currentTitle =
                savedInstanceState.getString("CURRENT_NAV_STATE", currentTitle) ?: "Home"
        }

        if (currentTitle == "Home") {
            replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
        } else {
            onCategoryItemClick(Category.valueOf(currentTitle))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onCategoryItemClick(category: Category) {
        currentTitle = Categories[category.toString()].toString()
        this.supportActionBar?.title = currentTitle
        // Replace the current fragment with a new ChaptersFragment for the selected category
        val fragment = ChaptersFragment(category)
        replaceFragmentContainer(R.id.fragmentContainerView, fragment)
    }

    override fun onChapterItemClick(chapter: String, category: Category) {

        if (category.toString() == "Sage") {
            if (chapter == "(Incomplete) Glossary") {
                startActivity(
                    GlossaryView.newIntent(this@MainActivity)
                )
            }
            else {
                startActivity(
                    SageActivity.newIntent(this@MainActivity, chapter),
                    ActivityOptions.makeSceneTransitionAnimation(
                        this@MainActivity
                    ).toBundle()
                )
            }
        }
        else if (category.toString()== "Notes"){
            startActivity(
                WebViewActivity.newIntent(this@MainActivity, chapter)
            )
        }
        else if (category.toString() == "OnlineResources"){
            startActivity(
                WebViewActivity.newIntent(this@MainActivity, chapter)
            )
        }
        else if (category.toString() == "AMSArticles"){
            startActivity(
                WebViewActivity.newIntent(
                    this@MainActivity, chapter)
            )
        }
        else{
            // Start the FlashcardActivity for the selected chapter
            startActivity(
                FlashcardActivity.newIntent(this@MainActivity, chapter),
                ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity).toBundle()
            )
        }
    }

    fun replaceFragmentContainer(oldFragment: Int, newFragment: Fragment) {

        // Get the FragmentManager
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the default fragment container with the list fragment
        fragmentTransaction.replace(oldFragment, newFragment)

        // Add the current fragment to the back stack
        fragmentTransaction.addToBackStack(null)

        // Commit the transaction
        fragmentTransaction.commit()

        // Set the navigation icon visibility based on the current fragment
        setNavigationIconVisibility(newFragment is ChaptersFragment)

        // Update the action bar title when the fragment changes
        if (newFragment is ChaptersFragment) {
            supportActionBar?.title = currentTitle
        }
    }

    private fun setNavigationIconVisibility(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
        // You can also set a custom icon here if needed
        // supportActionBar?.setHomeAsUpIndicator(R.drawable.your_custom_icon)
    }

    fun getFlashcardsData(){
        val flashcardsDir = File(applicationContext.filesDir, "Flashcards")
        if (!flashcardsDir.exists()) {
            flashcardsDir.mkdir()
        }
        if (flashcardsDir.exists() && flashcardsDir.isDirectory){
            for (filesOrFolders in flashcardsDir.listFiles()!!){
                if (filesOrFolders.isDirectory){
                    val folderName = filesOrFolders.name
                    Subjects.addNewSubject(folderName,
                        getFlashcardsfromFolder(folderName, flashcardsDir)
                    )
                }
            }
        }
    }

    private fun getFlashcardsfromFolder(
        foldername: String,
        flashcardsDir: File
    ): MutableList<String>{
        val subjectDir = File(flashcardsDir, foldername)

        val fileNames = mutableListOf<String>()

        if (subjectDir.exists() && subjectDir.isDirectory) {
            // Get a list of files and folders inside the "flashcards" directory
            val filesAndFolders: Array<out File>? = subjectDir.listFiles()

            // Loop through the array to differentiate between files and folders
            if (filesAndFolders != null) {
                for (fileOrFolder in filesAndFolders) {
                    if (fileOrFolder.isFile) {
                        fileNames.add(fileOrFolder.name)
                        // Do something with the file name
                    }
                }
            }
        }
        return fileNames
    }
}