package com.PisangHitam.InstaFashion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ShareCompat
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
        var user = db.daoAccount().getAccById(singletonData.currentAccId)[0]
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

        var logout = v.findViewById<Button>(R.id.logout)
        logout.setOnClickListener{
            singletonData.mAlarmManager?.cancel(singletonData.mPendingIntent)
            singletonData.mPendingIntent?.cancel()
            var intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        var settings = v.findViewById<Button>(R.id.setting)
        settings.setOnClickListener{
            var intent = Intent(requireContext(), profile_settings::class.java)
            startActivity(intent)
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