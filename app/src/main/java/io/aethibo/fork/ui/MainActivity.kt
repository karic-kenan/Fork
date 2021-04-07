package io.aethibo.fork.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pandora.bottomnavigator.BottomNavigator
import io.aethibo.fork.R
import io.aethibo.fork.ui.feed.view.FeedFragment
import io.aethibo.fork.ui.home.view.HomeFragment
import io.aethibo.fork.ui.notifications.view.NotificationsFragment
import io.aethibo.fork.ui.profile.view.ProfileFragment
import io.aethibo.fork.ui.search.view.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navigator: BottomNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Fork)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        navigator = BottomNavigator.onCreate(
                activity = this,
                rootFragmentsFactory = mapOf(
                        R.id.home to { HomeFragment.newInstance() },
                        R.id.search to { SearchFragment.newInstance() },
                        R.id.feed to { FeedFragment.newInstance() },
                        R.id.notifications to { NotificationsFragment.newInstance() },
                        R.id.profile to { ProfileFragment.newInstance() },
                ),
                defaultTab = R.id.home,
                fragmentContainer = R.id.fragment_container,
                bottomNavigationView = findViewById(R.id.navigation)
        )
    }
}