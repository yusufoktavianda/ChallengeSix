package binar.academy.challengesix.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.challengesix.MyApplication
import binar.academy.challengesix.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    //    private val viewModel : UserAuthViewModel by activityViewModels()
    private val movieViewModel by viewModels<HomeViewModel> { HomeViewModelFactory((activity?.application as MyApplication).repository) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val factory = HomeViewModelFactory(view.context)
//        viewModel = ViewModelProvider(requireActivity(),factory)[]
        getAllMovie()
    }

    private fun getAllMovie() {
        movieViewModel.getMovie().observe(viewLifecycleOwner) {
            val adapter = HomeAdapter(it)
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.homeRecyclerView.layoutManager = layoutManager
            binding.homeRecyclerView.adapter = adapter
        }
//           movieViewModel.getCode().observe(viewLifecycleOwner) { codeResponse ->
//                if (codeResponse == 200) {
//                    binding.progressBar.visibility = View.GONE
//                }
//            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}