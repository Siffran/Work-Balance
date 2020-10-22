package com.example.workbalance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*
import java.util.concurrent.Executor
import androidx.lifecycle.Observer
import kotlin.math.abs

private lateinit var executor: Executor
private lateinit var biometricPrompt: BiometricPrompt
private lateinit var promptInfo: BiometricPrompt.PromptInfo
private var initdone = 0

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Disabling drawer
        (activity as MainActivity).dissableDrawer()

        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        context,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                    findNavController().navigate(R.id.action_FirstFragment_to_loginPassword)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(context, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint login")
            .setSubtitle("Log in using your fingerprint")
            .setNegativeButtonText("Use account password")
            .build()

        val biometricLoginButton = view.findViewById<Button>(R.id.sign_in)
        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo);
        }


        // Populating the account spinner
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

    }
}