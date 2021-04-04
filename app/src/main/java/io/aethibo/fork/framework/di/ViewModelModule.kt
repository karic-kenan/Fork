package io.aethibo.fork.framework.di

import io.aethibo.fork.ui.auth.viewmodel.AuthViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AuthViewModel(get()) }
}