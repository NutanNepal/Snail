package com.lumu.snail

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.view.Menu
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.appbar.MaterialToolbar
import com.lumu.snail.categoriesfragment.CategoriesFragment
import com.lumu.snail.categoriesfragment.MyCategoriesRecyclerViewAdapter
import com.lumu.snail.chaptersfragment.ChaptersFragment
import com.lumu.snail.chaptersfragment.MyChaptersRecyclerViewAdapter
import com.lumu.snail.flashcardsActivity.FlashcardActivity
import com.lumu.snail.sage.GlossaryView
import com.lumu.snail.sage.SageActivity
import com.lumu.snail.tableOfContents.Category
import com.lumu.snail.tableOfContents.Subjects
import java.io.File

class MainActivity : AppCompatActivity(),
    MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener,
    MyChaptersRecyclerViewAdapter.OnChapterItemClickListener {

    private var currentTitle = "Home"
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var actionBarMenu: Menu

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

        topAppBar = this.findViewById(R.id.toolbar)
        topAppBar.title = currentTitle
        setSupportActionBar(topAppBar)


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

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    if (currentTitle == "Home") {
                        // If already on the CategoriesFragment, call the default back behavior (exit the app)
                        finish()
                    } else {
                        // Otherwise, go back to the CategoriesFragment and update the title
                        currentTitle = "Home"
                        replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
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
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onCategoryItemClick(category: Category) {
        // Update the title with the selected category name
        currentTitle = category.toString()
        topAppBar.title = currentTitle

        // Replace the current fragment with a new ChaptersFragment for the selected category
        val fragment = ChaptersFragment.newInstance(category)
        //findNavController(R.id.fragmentContainerView).navigate(fragment.id)

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
        topAppBar.title = currentTitle

        // Get the FragmentManager
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the default fragment container with the list fragment
        fragmentTransaction.replace(oldFragment, newFragment)

        // Add the current fragment to the back stack
        fragmentTransaction.addToBackStack(null)

        // Commit the transaction
        fragmentTransaction.commit()
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
                    Subjects.addNewSubject(folderName, getFlashcardsfromFolder(folderName, flashcardsDir))
                }
            }
        }
    }

    private fun getFlashcardsfromFolder(foldername: String, flashcardsDir: File): MutableList<String>{
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