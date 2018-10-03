package com.sealday.fitness.ui

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sealday.fitness.R
import com.sealday.fitness.app
import kotlinx.android.synthetic.main.fragment_blank.*


class BlankFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitness_text_view.setOnClickListener {
            val mBuilder = NotificationCompat.Builder(activity)
                    .setSmallIcon(R.drawable.notification_template_icon_bg)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!")

            val mNotificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(100, mBuilder.build())
            Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_planFragment)
        }

        person_info.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_infoFragment)
        }

        login.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_loginFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        app.runOnWorkingThread {
            val isLogin = app.appState.login
            activity?.runOnUiThread {
                if (isLogin) {
                    login.visibility = View.GONE
                    fitness_text_view.visibility = View.VISIBLE
                    person_info.visibility = View.VISIBLE
                }  else {
                    login.visibility = View.VISIBLE
                    fitness_text_view.visibility = View.GONE
                    person_info.visibility = View.GONE
                }
            }
        }
    }

}
