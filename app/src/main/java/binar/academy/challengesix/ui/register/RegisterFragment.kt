package binar.academy.challengesix.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import binar.academy.challengesix.data.local.User
import binar.academy.challengesix.databinding.FragmentRegisterBinding
import binar.academy.challengesix.ui.ViewModelFactory

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!
    lateinit var registerViewModel : RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        registerViewModel = ViewModelProvider(requireActivity(),factory)[RegisterViewModel::class.java]
        binding.registerButton.setOnClickListener {
            val confirmPassword = binding.confirmpasswordEditTextRegister.text.toString()
            val objectuser= User(
                username = binding.usernameEditText.text.toString(),
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditTextRegister.text.toString()
            )
            registerViewModel.addUser(objectuser,confirmPassword)
        }
        navigateUi()
    }

    private fun navigateUi() {
        registerViewModel.result().observe(viewLifecycleOwner){
            if (it == true){
                Toast.makeText(requireActivity(), "Register Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                registerViewModel.reset()
                Log.d("register", "Success $it")
            }else{
                Toast.makeText(requireActivity(), "Register Success", Toast.LENGTH_SHORT).show()
                Log.d("register", "fail $it")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}