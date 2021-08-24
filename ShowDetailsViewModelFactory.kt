package com.example.basic_try.view_model_factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basic_try.UserDataRepository
import com.example.basic_try.view_models.ShowDetailsViewModel

class ShowDetailsViewModelFactory(private val userDataRepository: UserDataRepository, private val application: Application):
    ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowDetailsViewModel(userDataRepository,application) as T
    }
}