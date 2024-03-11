package com.guru.androidtv.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.guru.androidtv.R
import com.guru.androidtv.databinding.ActivityMainBinding
import com.guru.androidtv.ui.fragments.HelpFragment
import com.guru.androidtv.ui.fragments.LanguageFragment
import com.guru.androidtv.ui.fragments.MovieFragment
import com.guru.androidtv.ui.fragments.SearchFragment
import com.guru.androidtv.ui.fragments.SettingsFragment
import com.guru.androidtv.ui.fragments.SportsFragment
import com.guru.androidtv.ui.fragments.TvShowFragment
import com.guru.androidtv.utils.Common
import com.guru.androidtv.utils.Constants

class MainActivity : FragmentActivity(), View.OnKeyListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var lastSelectedMenu: View
    var SIDE_MENU = false
    var selectedMenu = Constants.MENU_HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            val buttons = arrayOf(
                btnSearch,
                btnHome,
                btnTv,
                btnMovies,
                btnSports,
                btnSettings,
                btnLanguage,
                btnGenre
            )

            buttons.forEach { it.setOnKeyListener(this@MainActivity) }
        }

        lastSelectedMenu = binding.btnHome
        lastSelectedMenu.isActivated = true
        changeFragment(HomeFragment())

        binding.root.setOnTouchListener { _, event ->
            handleTouchClick(event.x, event.y)
            true
        }
    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        closeMenu()
    }

    override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
        when (i) {
            KeyEvent.KEYCODE_DPAD_CENTER -> {
                lastSelectedMenu.isActivated = false
                view?.isActivated = true
                lastSelectedMenu = view!!

                when (view.id) {
                    R.id.btn_search -> {
                        selectedMenu = Constants.MENU_SEARCH
                        changeFragment(SearchFragment())
                    }
                    R.id.btn_home -> {
                        selectedMenu = Constants.MENU_HOME
                        changeFragment(HomeFragment())
                    }
                    R.id.btn_tv -> {
                        selectedMenu = Constants.MENU_TV
                        changeFragment(TvShowFragment())
                    }
                    R.id.btn_movies -> {
                        selectedMenu = Constants.MENU_MOVIE
                        changeFragment(MovieFragment())
                    }
                    R.id.btn_sports -> {
                        selectedMenu = Constants.MENU_SPORTS
                        changeFragment(SportsFragment())
                    }
                    R.id.btn_settings -> {
                        selectedMenu = Constants.MENU_SETTINGS
                        changeFragment(SettingsFragment())
                    }
                    R.id.btn_language -> {
                        selectedMenu = Constants.MENU_LANGUAGE
                        changeFragment(LanguageFragment())
                    }
                    R.id.btn_genre -> {
                        selectedMenu = Constants.MENU_GENRES
                        changeFragment(HelpFragment())
                    }
                }
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (!SIDE_MENU) {
                    switchToLastSelectedMenu()
                    openMenu()
                    SIDE_MENU = true
                }
            }
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && SIDE_MENU) {
            SIDE_MENU = false
            closeMenu()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (SIDE_MENU) {
            SIDE_MENU = false
            closeMenu()
        } else {
            super.onBackPressed()
        }
    }

    private fun switchToLastSelectedMenu() {
        when (selectedMenu) {
            Constants.MENU_SEARCH -> binding.btnSearch.requestFocus()
            Constants.MENU_HOME -> binding.btnHome.requestFocus()
            Constants.MENU_TV -> binding.btnTv.requestFocus()
            Constants.MENU_MOVIE -> binding.btnMovies.requestFocus()
            Constants.MENU_SPORTS -> binding.btnSports.requestFocus()
            Constants.MENU_LANGUAGE -> binding.btnLanguage.requestFocus()
            Constants.MENU_GENRES -> binding.btnGenre.requestFocus()
            Constants.MENU_SETTINGS -> binding.btnSettings.requestFocus()
        }
    }

    private fun openMenu() {
        binding.blfNavBar.requestLayout()
        binding.blfNavBar.layoutParams.width = Common.getWidthInPercent(this, 16)
    }

    private fun closeMenu() {
        binding.blfNavBar.requestLayout()
        binding.blfNavBar.layoutParams.width = 0
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchClick(event.x, event.y)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun handleTouchClick(x: Float, y: Float) {
        val clickedView = findViewAtCoordinates(x, y)

        when (clickedView?.id) {
            R.id.btn_search -> {
                selectedMenu = Constants.MENU_SEARCH
                changeFragment(SearchFragment())
            }
            R.id.btn_home -> {
                selectedMenu = Constants.MENU_HOME
                changeFragment(HomeFragment())
            }
            R.id.btn_tv -> {
                selectedMenu = Constants.MENU_TV
                changeFragment(TvShowFragment())
            }
            R.id.btn_movies -> {
                selectedMenu = Constants.MENU_MOVIE
                changeFragment(MovieFragment())
            }
            R.id.btn_sports -> {
                selectedMenu = Constants.MENU_SPORTS
                changeFragment(SportsFragment())
            }
            R.id.btn_settings -> {
                selectedMenu = Constants.MENU_SETTINGS
                changeFragment(SettingsFragment())
            }
            R.id.btn_language -> {
                selectedMenu = Constants.MENU_LANGUAGE
                changeFragment(LanguageFragment())
            }
            R.id.btn_genre -> {
                selectedMenu = Constants.MENU_GENRES
                changeFragment(HelpFragment())
            }
        }

        if (SIDE_MENU) {
            SIDE_MENU = false
            closeMenu()
        }
    }

    private fun findViewAtCoordinates(x: Float, y: Float): View? {
        val rootView = window.decorView.rootView as View
        return rootView.findViewUnder(x.toInt(), y.toInt())
    }

    private fun View.findViewUnder(x: Int, y: Int): View? {
        val viewLocation = IntArray(2)
        this.getLocationOnScreen(viewLocation)

        return if (x >= viewLocation[0] && x <= viewLocation[0] + this.width &&
            y >= viewLocation[1] && y <= viewLocation[1] + this.height
        ) {
            this
        } else {
            if (this is ViewGroup) {
                for (i in 0 until this.childCount) {
                    val child = this.getChildAt(i)
                    val foundView = child.findViewUnder(x, y)
                    if (foundView != null) {
                        return foundView
                    }
                }
            }
            null
        }
    }
}
