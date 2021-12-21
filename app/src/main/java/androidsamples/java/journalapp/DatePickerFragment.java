package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    static Calendar c = Calendar.getInstance();
    static int year = c.get(Calendar.YEAR);
    static int month = c.get(Calendar.MONTH);
    static int day = c.get(Calendar.DAY_OF_MONTH);

    @NonNull
    public static DatePickerFragment newInstance(Calendar date) {
        // TODO implement the method
        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO implement the method
        return new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
            EntryDetailsViewModel viewModelObj = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);
            viewModelObj.setEntryDate(btnTxt(d,m,y));
        }, year, month, day);
    }
    private String btnTxt(int d, int m, int y) {
        Calendar entryDate = Calendar.getInstance();
        entryDate.set(y,m,d);
        int w = entryDate.get(Calendar.DAY_OF_WEEK);
        String month, day;
        switch (m) {
            case 0: { month = "JAN"; break;}
            case 1: { month = "FEB"; break;}
            case 2: { month = "MAR"; break;}
            case 3: { month = "APR"; break;}
            case 4: { month = "MAY"; break;}
            case 5: { month = "JUN"; break;}
            case 6: { month = "JUL"; break;}
            case 7: { month = "AUG"; break;}
            case 8: { month = "SEP"; break;}
            case 9: { month = "OCT"; break;}
            case 10: { month = "NOV"; break;}
            default: month = "DEC";
        }
        switch (w) {
            case 1: { day = "SUN"; break;}
            case 2: { day = "MON"; break;}
            case 3: { day = "TUE"; break;}
            case 4: { day = "WED"; break;}
            case 5: { day = "THU"; break;}
            case 6: { day = "FRI"; break;}
            default: day = "SAT";
        }
        return day + ", " + month + " " + d + ", " + y;
    }
}
