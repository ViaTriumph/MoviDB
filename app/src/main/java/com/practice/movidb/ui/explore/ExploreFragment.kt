package com.practice.movidb.ui.explore

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.practice.movidb.ShowApplication
import com.practice.movidb.activity.MainActivity
import com.practice.movidb.databinding.FragmentExploreBinding
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.ui.search.SearchFragment
import javax.inject.Inject

class ExploreFragment : Fragment() { //TODO searchview as in toolbar menu

    private lateinit var binding: FragmentExploreBinding

    @Inject
    lateinit var movieRepository: MovieRepository

    private val exploreViewModel: ExploreViewModel by lazy {
        ViewModelProvider(
            this,
            ExploreViewModelFactory(movieRepository)
        )[ExploreViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as ShowApplication).baseComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        binding.viewModel = exploreViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exploreViewModel.init()
        binding.explorePopularMoviesRv.adapter = exploreViewModel.getPopularMoviesAdapter()
        binding.exploreNowPlayingRv.adapter = exploreViewModel.getNowPlayingAdapter()
        onSearchClick() //TODO improve
    }

    private fun onSearchClick() {
        binding.exploreSearchView.setOnClickListener {
            (requireActivity() as MainActivity).addFragment(SearchFragment.TAG)
        }
    }

    companion object {
        const val TAG = "ExploreFragment"
        fun newInstance() = ExploreFragment()
    }
}