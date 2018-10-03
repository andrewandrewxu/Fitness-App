package com.sealday.fitness.ui


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import com.sealday.fitness.R
import com.sealday.fitness.app
import com.sealday.fitness.db.Plan
import com.sealday.fitness.service.PlanService
import kotlinx.android.synthetic.main.fragment_plan.*
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.util.*


class PlanFragment : Fragment() {

    private var animating = false
    private var date = LocalDate.now()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        event_list.layoutManager = layoutManager

        app.runOnWorkingThread {
            val plans =  app.appDatabase.planDao().all
            activity?.runOnUiThread {
                event_list.adapter = EventListAdapter(plans)
            }
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = LocalDate(year, month, dayOfMonth)
        }

        addBtn.setOnClickListener {
            val view = LayoutInflater.from(activity!!).inflate(R.layout.dialog_time_text, null)
            val time = view.findViewById<TimePicker>(R.id.time)
            val text = view.findViewById<EditText>(R.id.text)
            AlertDialog
                    .Builder(activity!!)
                    .setTitle("Add Plan")
                    .setView(view)
                    .setPositiveButton("Confirm") {
                        dialog, which ->
                        val datetime =  LocalDateTime(date.year, date.monthOfYear, date.dayOfMonth, time.currentHour, time.currentMinute)
                        app.runOnWorkingThread {
                            val plan = Plan(0, datetime.toDate(), text.text.toString())
                            app.appDatabase.planDao().insertAll(plan)
                            val plans =  app.appDatabase.planDao().all
                            activity?.runOnUiThread {
                                event_list.adapter = EventListAdapter(plans)
                            }
                            val jobScheduler = activity?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                            val jobInfo =  JobInfo
                                    .Builder(plans.last().pid, ComponentName(activity?.packageName!!, PlanService::class.java.name))
                                    .setMinimumLatency(datetime.toDate().time - Date().time)
                                    .build()
                            jobScheduler.schedule(jobInfo)
                            null
                        }
                    }
                    .setNegativeButton("Cancel") {
                        dialog, which ->
                    }
                    .show()
        }
        showHideBtn.setOnClickListener {
            val shortAnimationDuration = resources.getInteger(
                    android.R.integer.config_shortAnimTime);
            if (!animating) {
                if (calendarView.visibility == View.GONE) {
                    calendarView
                            .animate()
                            .alpha(1f)
                            .setDuration(shortAnimationDuration.toLong())
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationStart(animation: Animator?) {
                                    calendarView.visibility = View.VISIBLE
                                }

                                override fun onAnimationEnd(animation: Animator?) {
                                    animating = false
                                }
                            })
                } else {
                    calendarView
                            .animate()
                            .alpha(0f)
                            .setDuration(shortAnimationDuration.toLong())
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    calendarView.visibility = View.GONE
                                    animating = false
                                }
                            })
                }
            }
        }

    }


}

class EventListItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView.findViewById<TextView>(R.id.text)!!

}


class EventListAdapter(val plans: List<Plan>) : RecyclerView.Adapter<EventListItem>() {
    override fun onBindViewHolder(item: EventListItem, pos: Int) {
        item.textView.text = "${plans[pos].date.toString()} ${plans[pos].name}"
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListItem {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_item, parent, false)
        return EventListItem(view)
    }

}