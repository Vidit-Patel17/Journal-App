package androidsamples.java.journalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.UUID;

public class EntryDetailsFragment extends Fragment {
    UUID entryId = null;
    boolean isNew = true;
    static boolean isRotated = false;
    EntryDetailsViewModel entryDetailsViewModel;
    EditText edit_title;
    Button btn_entry_date, btn_start_time, btn_end_time, btn_save;
    String startTimeString = "", endTimeString = "", entryDateString = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);
        if(getArguments() != null) {
            isNew = false;
            entryId = (UUID) getArguments().getSerializable(MainActivity.KEY_ENTRY_ID);
            entryDetailsViewModel.loadEntry(entryId);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:  {
                String msg = "Look what I have been up to:" + edit_title.getText().toString() + " on " + entryDateString + " "  + startTimeString + " to " + endTimeString;
                shareText(msg);
                return true;
            }
            case R.id.delete: {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                if(!isNew) {
                                    entryDetailsViewModel.getEntry(entryId).observe(requireActivity(), entry -> {
                                        if(entry != null)
                                            entryDetailsViewModel.delete(entry);
                                    });
                                    requireActivity().onBackPressed();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }

                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareText(String msg){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = msg;
        String shareSubject = "Sharing my Task";
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entry_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_title = view.findViewById(R.id.edit_title);
        btn_entry_date = view.findViewById(R.id.btn_entry_date);
        btn_start_time = view.findViewById(R.id.btn_start_time);
        btn_end_time = view.findViewById(R.id.btn_end_time);
        btn_save = view.findViewById(R.id.btn_save);

        if (isNew && !isRotated) {
            entryDetailsViewModel.setEntryDate("");
            entryDetailsViewModel.setStartTime("");
            entryDetailsViewModel.setEndTime("");
        }

        if (!isNew && !isRotated) {
            entryDetailsViewModel.getEntry(entryId).observe(requireActivity(), entry -> {
                if (entry != null) {
                    Init(entry);
                }
            });
        }

        if(savedInstanceState != null && isRotated) {
            entryDateString = savedInstanceState.getString("DATE_KEY", "");
            startTimeString = savedInstanceState.getString("START_KEY", "");
            endTimeString = savedInstanceState.getString("END_KEY", "");
            setBtnText();
            isRotated = false;
        }

        btn_entry_date.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(requireActivity().getSupportFragmentManager(), null);
            entryDetailsViewModel.getEntryDate().observe(getViewLifecycleOwner(), data -> {
                entryDateString = data;
                if(!data.equals(""))
                    btn_entry_date.setText(entryDateString);
            });
        });

        btn_start_time.setOnClickListener(v-> {
            entryDetailsViewModel.setIsStart(true);
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(requireActivity().getSupportFragmentManager(), null);
            entryDetailsViewModel.getStartTime().observe(getViewLifecycleOwner(), data -> {
                startTimeString = data;
                if(!data.equals(""))
                    btn_start_time.setText(startTimeString);
            });
        });

        btn_end_time.setOnClickListener(v -> {
            entryDetailsViewModel.setIsStart(false);
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(requireActivity().getSupportFragmentManager(), null);
            entryDetailsViewModel.getEndTime().observe(getViewLifecycleOwner(), data -> {
                endTimeString = data;
                if(!data.equals(""))
                    btn_end_time.setText(endTimeString);
            });
        });

        btn_save.setOnClickListener(v -> {
            String title = edit_title.getText().toString();
            JournalEntry journalEntry = new JournalEntry(title, entryDateString, startTimeString, endTimeString);
            if(isNew)
                entryDetailsViewModel.insert(journalEntry);
            else {
                journalEntry.setUid(entryId);
                entryDetailsViewModel.update(journalEntry);
            }
            requireActivity().onBackPressed();
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isRotated = true;
        outState.putString("DATE_KEY", entryDateString);
        outState.putString("START_KEY", startTimeString);
        outState.putString("END_KEY", endTimeString);
    }

    public void Init(JournalEntry journalEntry){
        String title = journalEntry.getTitle();
        entryDateString = journalEntry.getDate();
        startTimeString = journalEntry.getStartTime();
        endTimeString = journalEntry.getEndTime();

        setBtnText();
        edit_title.setText(title);
        entryDetailsViewModel.setEntryDate(entryDateString);
        entryDetailsViewModel.setStartTime(startTimeString);
        entryDetailsViewModel.setEndTime(endTimeString);
    }

    private void setBtnText() {
        if(entryDateString.equals(""))
            btn_entry_date.setText(R.string.date);
        else
            btn_entry_date.setText(entryDateString);

        if(startTimeString.equals(""))
            btn_start_time.setText(R.string.start_time);
        else
            btn_start_time.setText(startTimeString);

        if(endTimeString.equals(""))
            btn_end_time.setText(R.string.end_time);
        else
            btn_end_time.setText(endTimeString);
    }
}