package androidsamples.java.journalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  private EntryListViewModel entryListViewModel;

  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    entryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
    view.findViewById(R.id.btn_add_entry).setOnClickListener(v -> goToEntryDetails());

    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(getActivity());
    entriesList.setAdapter(adapter);
    entryListViewModel.getAllEntries().observe(requireActivity(), adapter::setEntries);

    return view;
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    inflater.inflate(R.menu.list_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.info:  {
        goToInfoFragment();
        return true;
      }
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void goToEntryDetails() {
    Fragment fragment = new EntryDetailsFragment();
    requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit();
  }

  private void goToInfoFragment() {
    Fragment fragment = new InfoFragment();
    requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit();
  }

}