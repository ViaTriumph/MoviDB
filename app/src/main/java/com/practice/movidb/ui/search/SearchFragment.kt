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
        binding.viewModel = searchViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBack()
        initSearchObservables()

        binding.searchRecyclerView.adapter = searchViewModel.getAdapter()
    }

    private fun initSearchObservables() {
        searchViewModel.observeSearchInput(binding.searchSearchView)
    }

    private fun handleBack() { //TODO move to menu
        binding.searchBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }
}