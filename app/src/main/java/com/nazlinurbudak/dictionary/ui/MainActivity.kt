package com.nazlinurbudak.dictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nazlinurbudak.dictionary.R
import com.nazlinurbudak.dictionary.common.observeTextChanges
import com.nazlinurbudak.dictionary.common.okWith
import com.nazlinurbudak.dictionary.common.viewBinding
import com.nazlinurbudak.dictionary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        searchQuery()
        collectSearchQueryState()
    }


    private fun searchQuery() = with(binding) {
        searchInput.editText?.setOnEditorActionListener { v, actionId, event ->
            searchEditText.text = searchInput.editText!!.text
            false
        }

        searchInput
            .editText?.observeTextChanges()
            ?.filter { it okWith MINIMUM_SEARCH_LENGTH }
            ?.debounce(SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS)
            ?.distinctUntilChanged()
            ?.onEach {
                if (it.isNotBlank()) {
                    viewModel.searchWord(it)
                }
            }?.launchIn(lifecycleScope) ?: ""
    }

    private fun collectSearchQueryState() = with(binding) {
      lifecycleScope.launch {
            viewModel.searchWord
                .collectLatest {
                    when (it) {
                        is WordUiState.Error -> {

                        }
                        is WordUiState.Loading -> {

                        }
                        is WordUiState.Success -> {
                            Log.d("WORD" , it.data.toString())
                        }
                    }
                }
        }
    }


    companion object {
        private const val MINIMUM_SEARCH_LENGTH = 2
        private const val SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS = 300L

    }
}