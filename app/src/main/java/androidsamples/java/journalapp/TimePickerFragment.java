package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment {
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    @NonNull
    public static TimePickerFragment newInstance(Date time) {
        // TODO implement the method
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO implement the method
        // TODO initialize using parseTime and bundle
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new TimePickerDialog(requireContext(), (dp, hm, m) -> {
            EntryDetailsViewModel viewModelObj = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);
            String time;
            if(m<10){
                time = hm + ":" + "0" +m;
            }
            else{
                time = hm + ":" + m;
            }
            if(viewModelObj.getIsStart()) {
                viewModelObj.setStartTime(time);
            }
            else{
                viewModelObj.setEndTime(time);
            }
        }, hour, minute,false);
    }



}
