package com.example.apptask.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.apptask.R
import com.example.apptask.databinding.FragmentRegisterBinding
import com.example.apptask.view.MainActivity
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if(email.isEmpty()){
                binding.emailInput.error = "Email tidak boleh kosong!"
                binding.emailInput.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailInput.error = "Email Tidak Valid!"
                binding.emailInput.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 8){
                binding.passwordInput.error = "Password harus lebih dari 8 Karakter"
                binding.passwordInput.requestFocus()
                return@setOnClickListener
            }
            registerUser(email, password)
        }

        binding.tvMasukk.setOnClickListener {
            findNavController().navigate(R.id.LoginFragment)
        }

        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.ForgotPasswordFragment)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()){ it ->
                if (it.isSuccessful){
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        val logFrag = LoginFragment()
                        transaction.replace(R.id.registerFragment, logFrag)
                        transaction.addToBackStack(null)
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        transaction.commit()
                }else{
                    Toast.makeText(context,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }

            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Intent(requireActivity(), MainActivity::class.java).also { it ->
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}