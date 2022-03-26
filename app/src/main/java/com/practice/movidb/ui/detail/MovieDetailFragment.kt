package com.practice.movidb.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.practice.movidb.ShowApplication
import com.practice.movidb.databinding.FragmentDetailsBinding
import com.practice.movidb.shared.domain.details.MovieDetailsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    @Inject
    lateinit var repository: MovieDetailsRepository

    private val detailVM: MovieDetailViewModel by lazy {
        ViewModelProvider(this, MovieDetailVMFactory(repository))[MovieDetailViewModel::class.java]
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
        binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
        initData()
        initComponentObserver()
    }

    private fun initBindings() {
        binding.viewModel = detailVM

        binding.detailsSimilarMovieRv.adapter = detailVM.similarMovieModel.adapter
    }

    private fun initComponentObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailVM.componentFlow.collect { component ->
                when (component.name) {
                    TAG -> {
                        if (component.data is Int) {
                            detailVM.init(component.data)
                        }
                    }
                }
            }
        }
    }

    private fun initData() {
        val id = arguments?.getInt("id") ?: 0
        detailVM.init(id)
    }

    companion object { //todo move to navigator
        const val TAG = "MovieDetailFragment"

        fun newInstance(id: Int): MovieDetailFragment { //todo custom args
            val args = Bundle()
            args.putInt("id", id)
            val frag = MovieDetailFragment()
            frag.arguments = args
            return frag
        }
    }

}