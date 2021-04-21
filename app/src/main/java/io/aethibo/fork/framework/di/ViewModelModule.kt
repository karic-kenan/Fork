package io.aethibo.fork.framework.di

import io.aethibo.fork.ui.auth.viewmodel.AuthViewModel
import io.aethibo.fork.ui.feed.viewmodel.FeedViewModel
import io.aethibo.fork.ui.notifications.viewmodel.NotificationsViewModel
import io.aethibo.fork.ui.profile.viewmodel.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AuthViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { FeedViewModel(get(), get()) }
    viewModel { NotificationsViewModel(get()) }
}