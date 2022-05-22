package com.PisangHitam.InstaFashion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ShareCompat
import com.PisangHitam.InstaFashion.SharedPref.loginSharedPref
import com.PisangHitam.InstaFashion.locChecker.js_getGeo
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile__main_.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile_Main_Frag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.fragment_profile__main_, container, false)
        // Inflate the layout for this fragment
        return main(v)
    }

    fun main(v : View) : View{
        var db = singletonData.getRoomHelper(requireContext())
//        var user = singletonData.accList[singletonData.currentAccId]
        var user : classAccount?
        var testArgs = arguments
        if(testArgs?.getParcelable<classAccount>(dummy_for_test) != null){
            user = db.daoAccount().getAccUserCheck("TEST")[0]
        }
        else{
            user = db.daoAccount().getAccById(singletonData.currentAccId)[0]
        }

        var fullName = v.findViewById<TextView>(R.id.fullName)
        fullName.text = user.fullName

        var email = v.findViewById<TextView>(R.id.email)
        email.text = user.email


        var personaldata = v.findViewById<Button>(R.id.personalData)
        personaldata.setOnClickListener{
            var intent = Intent(requireContext(),Personaldata::class.java)
            startActivity(intent)
        }

        var purchaseHistory = v.findViewById<Button>(R.id.purchaseHistory)
        purchaseHistory.setOnClickListener{
            var intent = Intent(requireContext(),shop_tracker::class.java)
            startActivity(intent)
        }

        var share = v.findViewById<Button>(R.id.share)
        share.setOnClickListener{
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this.requireActivity())
                .setType(mimeType)
                .setChooserTitle("Share this app to your friends!")
                .setText("Find your style with InstaFashion!")
                .startChooser()
        }

        var shareBySms = v.findViewById<Button>(R.id.shareBySms)
        shareBySms.setOnClickListener{
            var intent = Intent(requireContext(), profile_sms::class.java)
            startActivity(intent)
        }

        var logout = v.findViewById<Button>(R.id.logout)
        logout.setOnClickListener{
            singletonData.mAlarmManager?.cancel(singletonData.mPendingIntent)
            singletonData.mPendingIntent?.cancel()
            var sharedpref = loginSharedPref(requireContext())
            sharedpref.resetKept()
            singletonData.currentAccId = 0
            var intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        var settings = v.findViewById<Button>(R.id.setting)
        settings.setOnClickListener{
            var intent = Intent(requireContext(), profile_settings::class.java)
            startActivity(intent)
        }

        var ltrans = v.findViewById<Button>(R.id.lastTransaction)
        ltrans.setOnClickListener {
            if(user.transactionHistory.isNotEmpty()){
                var intent = Intent(requireContext(), profile_lastTransactionToText::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(), "You haven't made any transactions.", Toast.LENGTH_SHORT).show()
            }
        }


        var country = v.findViewById<TextView>(R.id.country)
        var countryCode = v.findViewById<TextView>(R.id.countryCode)
        var timezone = v.findViewById<TextView>(R.id.timezone)

//        country.text = "Country : ${singletonData.geoRes!![0]}"
//        countryCode.text = "Country Code : ${singletonData.geoRes!![1]}"
//        timezone.text = "Timezone: ${singletonData.geoRes!![3]} (${singletonData.geoRes!![2]})"

        if(testArgs?.getParcelable<classAccount>(dummy_for_test) != null) {
            country.text = "Country : TEST"
            countryCode.text = "Country Code : TEST"
            timezone.text = "Timezone: TEST"
        }
        else{
            var hasil = js_getGeo.hasil
            if (hasil.isEmpty()){
                country.text = "Country : -"
                countryCode.text = "Country Code : -"
                timezone.text = "Timezone: -"
            }
            else{
                country.text = "Country : ${hasil[0]}"
                countryCode.text = "Country Code : ${hasil[1]}"
                timezone.text = "Timezone: ${hasil[3]} (${hasil[2]})"
            }
        }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile_Main_Frag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1,param1)
                    putString(ARG_PARAM2,param2)
                }
            }
    }
}