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
import java.util.*
import kotlin.math.abs
import androidx.lifecycle.Observer



class LoginPassword : Fragment() {

    private var initdone = 0

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

        // Locking drawer
        (activity as MainActivity).dissableDrawer()

        // Populating account selection spinner
        // TODO select same account as on first page.
        val work: AccountModel = AccountModel("Work@work.com", "ic_account_circle")
        val private: AccountModel = AccountModel("Private@home.com", "ic_account_circle")

        val modelList: List<AccountModel> = listOf(work, private)
        val customDropDownAdapter = context?.let { CustomDropDownAdapter(it, modelList) }
        spinner04.adapter = customDropDownAdapter

        // Pre-selecting correct item in spinner
        // Setting up location Location
        (activity as MainActivity).locationLoco.observe(viewLifecycleOwner, Observer {
            if (initdone > 0) {
                var diffLat = (activity as MainActivity).locationCurrent.latitude - 57.689167
                var difflong = (activity as MainActivity).locationCurrent.longitude - 11.973611

                if (abs(diffLat) < 0.01 && abs(difflong) < 0.01) {
                    spinner04.setSelection(0)
                } else {
                    if (spinner04.selectedItemPosition == 0) {
                        // Its working hours, perhaps working from home?
                    } else {
                        // Not working hours and not at work!
                        spinner04.setSelection(1)
                    }
                }
            }
            initdone++
        })

        // Requesting update of location
        (activity as MainActivity).getLocationWithPermissionCheck()

        // Selecting depending on Time
        val cal = Calendar.getInstance()
        val tz = TimeZone.getTimeZone("GMT+2")
        cal.timeZone = tz
        val day = cal.get(Calendar.DAY_OF_WEEK)
        if (day == 1 || day == 7) {
            // Its weekend, relax lol
            spinner04.setSelection(1)
        } else {
            val time = cal.get(Calendar.HOUR_OF_DAY)
            if (9 < time && time < 17) {
                spinner04.setSelection(0)
            } else {
                spinner04.setSelection(1)
            }
        }

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