package io.aethibo.fork.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.aethibo.fork.R
import io.aethibo.fork.ui.auth.view.AuthFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()

        replaceFragment(AuthFragment.newInstance())
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