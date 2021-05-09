package com.example.simpletestexecution.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.simpletestexecution.FetchState
import com.example.simpletestexecution.NumberViewModel
import com.example.simpletestexecution.databinding.FragmentHomeBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment(), HasAndroidInjector {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModel: NumberViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is FetchState.Loading -> {
                    binding.loadedVisibilityGroup.visibility = View.GONE
                    binding.progressCircle.visibility = View.VISIBLE
                }
                is FetchState.Data -> {
                    binding.progressCircle.visibility = View.GONE
                    binding.loadedVisibilityGroup.visibility = View.VISIBLE
                    binding.txtRandomNum.text = it.number.toString()
                }
                is FetchState.Error -> {
                    Toast.makeText(requireContext(), "Some error occured", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnGetRandomNum.setOnClickListener {
            viewModel.getRandomNumber()
        }
    }

    override fun androidInjector() = androidInjector
}