package binar.academy.challengesix.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import binar.academy.challengesix.R
import binar.academy.challengesix.data.local.User
import binar.academy.challengesix.databinding.FragmentProfileBinding
import binar.academy.challengesix.ui.ViewModelFactory
import binar.academy.challengesix.ui.login.LoginViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
    lateinit var profileViewModel: ProfileViewModel
    private val REQUEST_CODE_PERMISSION = 100
    var imagePath: String? = null
    private var useernameValue = "default"
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
//            Log.d("HASILKAMERA",result.toString())
            imagePath = result.toString()
//            binding.imageProfile.setImageURI(imagePath!!.toUri())
            Glide.with(requireActivity())
                .load(result)
                .apply(RequestOptions.centerCropTransform())
                .error(R.drawable.ic_launcher_background)
                .into(binding.imageProfile)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        profileViewModel =
            ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]
        username()
        setData()

        binding.imageProfile.setOnClickListener {
            checkingPermissions()
        }
        binding.updateButton.setOnClickListener {
            val user = User(
                username = binding.usernameEditText.text.toString(),
                fullName = binding.fullnameEditText.text.toString(),
                birthDate = binding.birthdayEditText.text.toString(),
                address = binding.addressEditText.text.toString(),
                imgPath = imagePath.toString()
            )
            Log.d("GALERI", imagePath.toString())

            profileViewModel.getEmail().observe(viewLifecycleOwner) {
                profileViewModel.updateData(user, it)
                Log.d("profile", it.toString())
            }

            profileViewModel.getUpdateValidation().observe(viewLifecycleOwner){
                if(it==true){
                    profileViewModel.setUsername(user.username.toString())
                    profileViewModel.getUserData(user.username.toString())
                }
            }
            setData()

        }
        binding.logoutButton.setOnClickListener {
            logout()
        }


    }

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
//            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }
    private fun openGallery() {
        activity?.intent?.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun username(){
        profileViewModel.getUsername().observe(viewLifecycleOwner){result ->
            useernameValue = result
            profileViewModel.getUserData(result)
        }
    }

    private fun setData() {
        //Set Data
        profileViewModel.resultUser().observe(viewLifecycleOwner) {
            if (it.username !== "null") {
                binding.usernameEditText.setText(it.username.toString())
            }
            if (it.fullName != "null") {
                binding.fullnameEditText.setText(it.fullName.toString())
            }
            if (it.birthDate != "null") {
                binding.birthdayEditText.setText(it.birthDate.toString())
            }
            if (it.address != "null") {
                binding.addressEditText.setText(it.address.toString())
            }
            if (imagePath== null){
                if (it.imgPath != null) {
                    Log.d("GALERI", it.imgPath.toString())
                    imagePath = it.imgPath
                    Glide.with(requireActivity())
                        .load(it.imgPath)
                        .apply(RequestOptions.centerCropTransform())
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.imageProfile)
                }
            }
        }
    }

    fun logout() {
        profileViewModel.logout()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }

}