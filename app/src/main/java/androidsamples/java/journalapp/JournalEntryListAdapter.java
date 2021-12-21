package androidsamples.java.journalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

public class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {

  private final Context context;
  private final LayoutInflater mInflater;
  private List<JournalEntry> mEntries;

  public JournalEntryListAdapter(Context context) {
    this.context = context;
    mInflater = LayoutInflater.from(context);
  }

  @NonNull @Override
  public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
    return new EntryViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
    if (mEntries != null) {
      JournalEntry current = mEntries.get(position);
      holder.mTxtTitle.setText(current.getTitle());
      holder.mTxtDate.setText(current.getDate());
      holder.mTxtStartTime.setText(current.getStartTime());
      holder.mTxtEndTime.setText(current.getEndTime());
    }
  }

  @Override
  public int getItemCount() {
    return (mEntries == null) ? 0 : mEntries.size();
  }

  public void setEntries(List<JournalEntry> entries) {
    mEntries = entries;
    notifyDataSetChanged();
  }

  class EntryViewHolder extends RecyclerView.ViewHolder{
    private final TextView mTxtTitle;
    private final TextView mTxtDate;
    private final TextView mTxtStartTime;
    private final TextView mTxtEndTime;

    public EntryViewHolder(@NonNull View itemView) {
      super(itemView);
      mTxtTitle = itemView.findViewById(R.id.txt_item_title);
      mTxtDate = itemView.findViewById(R.id.txt_item_date);
      mTxtStartTime = itemView.findViewById(R.id.txt_item_start_time);
      mTxtEndTime = itemView.findViewById(R.id.txt_item_end_time);
      itemView.setOnClickListener(this::launchJournalEntryFragment);
    }

    private void launchJournalEntryFragment(View v) {
      Callbacks callbacks = (Callbacks) context;
      callbacks.onEntrySelected(mEntries.get(getLayoutPosition()).getUid());
    }
  }

  interface Callbacks {
    void onEntrySelected(UUID id);
  }
}