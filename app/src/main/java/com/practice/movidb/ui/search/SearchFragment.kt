package com.practice.movidb.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.practice.movidb.ShowApplication
import com.practice.movidb.databinding.FragmentSearchBinding
import com.practice.movidb.network.search.domain.SearchRepository
import javax.inject.Inject

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var repository: SearchRepository

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory(repository))[SearchViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as ShowApplication).baseComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchObservables()
    }

    private fun initSearchObservables() {
        searchViewModel.observeSearchInput(binding.searchSearchView)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}