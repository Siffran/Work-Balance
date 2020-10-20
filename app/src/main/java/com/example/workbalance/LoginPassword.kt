package com.example.workbalance

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_first.spinner04
import kotlinx.android.synthetic.main.fragment_login_password.*


class LoginPassword : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populating account selection spinner
        // TODO select same account as on first page.
        val work: AccountModel = AccountModel("Work@work.com", "ic_account_circle")
        val private: AccountModel = AccountModel("Private@home.com", "ic_account_circle")

        val modelList: List<AccountModel> = listOf(work, private)
        val customDropDownAdapter = context?.let { CustomDropDownAdapter(it, modelList) }
        spinner04.adapter = customDropDownAdapter

        // Requesting focus to password field and opening keyboard
        editTextTextPassword.requestFocus()
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.showSoftInput(editTextTextPassword, InputMethodManager.SHOW_IMPLICIT)

        // Adding listener to check pass/change view on enter click
        editTextTextPassword.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                // Hiding keyboard
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                // Navigating to next view
                findNavController().navigate(R.id.action_loginPassword_to_SecondFragment)
                return@OnKeyListener true
            }
            false
        })
    }
}