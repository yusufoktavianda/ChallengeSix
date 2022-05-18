package binar.academy.challengesix.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import binar.academy.challengesix.R
import binar.academy.challengesix.databinding.FragmentProfileBinding
import binar.academy.challengesix.ui.ViewModelFactory
import binar.academy.challengesix.ui.login.LoginViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
//    lateinit var profileViewModel: ProfileViewModel
    lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val factory = ViewModelFactory(view.context)
        profileViewModel = ViewModelProvider(requireActivity(),factory)[ProfileViewModel::class.java]

        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    fun logout() {
//        profileViewModel.logout()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }

}