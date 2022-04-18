package johan.santos.reservesisha.ui.access.models

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment (val listener : (String) -> Unit) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour : Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minute : Int = calendar.get(Calendar.MINUTE)
        // sin Style
        val dialog = TimePickerDialog(activity as Context, this, hour, minute, true)
        // con Style
        //val dialog = TimePickerDialog(activity as Context, R.style.TimePicker, this, hour, minute, false)

        return dialog
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener("$hourOfDay:$minute")
    }
}