package encinas.valeria.digimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import encinas.valeria.digimind.R
import encinas.valeria.digimind.databinding.FragmentDashboardBinding
import encinas.valeria.digimind.ui.Task
import encinas.valeria.digimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import encinas.valeria.digimind.ui.dashboard.DashboardViewModel

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private var initialTimeButtonText: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val btn_time = root.findViewById(R.id.btn_time) as Button
        val btn_save = root.findViewById(R.id.btn_save) as Button
        val et_titulo = root.findViewById(R.id.et_task) as EditText
        val checkBoxMonday = root.findViewById(R.id.checkMonday) as CheckBox
        val checkBoxTuesday = root.findViewById(R.id.checkTuesday) as CheckBox
        val checkBoxWednesday = root.findViewById(R.id.checkWednesday) as CheckBox
        val checkBoxThursday = root.findViewById(R.id.checkThursday) as CheckBox
        val checkBoxFriday = root.findViewById(R.id.checkFriday) as CheckBox
        val checkBoxSaturday = root.findViewById(R.id.checkSaturday) as CheckBox
        val checkBoxSunday = root.findViewById(R.id.checkSunday) as CheckBox

        if (initialTimeButtonText == null) {
            initialTimeButtonText = btn_time.text.toString()
        }

        btn_time.setOnClickListener {
                val cal = Calendar.getInstance()
                val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)

                        btn_time.text = SimpleDateFormat("HH:mm").format(cal.time)
                    }
                TimePickerDialog(root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE), true).show()
            }


        btn_save.setOnClickListener {
            var title = et_titulo.text.toString()
            var time = btn_time.text.toString()
            var days = ArrayList<String>()

            if (checkBoxMonday.isChecked)
                days.add("Monday")
            if (checkBoxTuesday.isChecked)
                days.add("Tuesday")
            if (checkBoxWednesday.isChecked)
                days.add("Wednesday")
            if (checkBoxThursday.isChecked)
                days.add("Thursday")
            if (checkBoxFriday.isChecked)
                days.add("Friday")
            if (checkBoxSaturday.isChecked)
                days.add("Saturday")
            if (checkBoxSunday.isChecked)
                days.add("Sunday")


        // VALIDACIONES

            var allValid = true

            if (title.isEmpty()) {
                et_titulo.error = "Activity title is required"
                Toast.makeText(requireContext(), "Please enter an activity title.", Toast.LENGTH_SHORT).show()
                allValid = false
            } else {
                et_titulo.error = null
            }

            if (time == initialTimeButtonText || time.isEmpty()) {
                Toast.makeText(requireContext(), "Please select a time.", Toast.LENGTH_SHORT).show()
                allValid = false
            }

            if (days.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one day.", Toast.LENGTH_SHORT).show()
                allValid = false
            }

            if (allValid) {
                val task = Task(title, days, time)
                HomeFragment.tasks.add(task)

                Toast.makeText(requireContext(), "New task added successfully!", Toast.LENGTH_SHORT).show()

                et_titulo.text.clear()
                btn_time.text = initialTimeButtonText ?: "SET TIME"

                checkBoxMonday.isChecked = false
                checkBoxTuesday.isChecked = false
                checkBoxWednesday.isChecked = false
                checkBoxThursday.isChecked = false
                checkBoxFriday.isChecked = false
                checkBoxSaturday.isChecked = false
                checkBoxSunday.isChecked = false
            }
        }

            return root
        }

}