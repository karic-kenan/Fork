package io.aethibo.fork.ui.auth.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.aethibo.fork.ui.MainActivity
import io.aethibo.fork.R
import io.aethibo.fork.framework.utils.AppConst
import io.aethibo.fork.ui.auth.view.AuthFragment
import org.koin.android.ext.android.inject

class AuthActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Fork)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()

        checkIfAccessTokenExists()

        replaceFragment(AuthFragment.newInstance())
    }

    private fun checkIfAccessTokenExists() {
        val token = sharedPreferences.getString(AppConst.authAccessToken, "")

        if (!token.isNullOrEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    /**
     * this function serves to replace fragment on the [AuthActivity] inside FrameLayout
     */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navigation_fragment_holder, fragment)
            .commit()
    }
}