package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class EntryDetailsViewModel extends ViewModel {
    private final MutableLiveData<String> startTime;
    private final MutableLiveData<String> endTime;
    private final MutableLiveData<String> entryDate;
    private static boolean isStart = false;
    private final JournalRepository mRepository;
    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();


    public EntryDetailsViewModel(SavedStateHandle state) {
        startTime = state.getLiveData("Start Time");
        endTime = state.getLiveData("End Time");
        entryDate = state.getLiveData("Entry Date");
        mRepository = JournalRepository.getInstance();
    }

//    public EntryDetailsViewModel() {
//        //default constructor to test the database related functions
//        startTime = null;
//        endTime = null;
//        entryDate = null;
//        mRepository = JournalRepository.getInstance();
//    }

    public void setIsStart(boolean ans){
        this.isStart = ans;
    }
    public boolean getIsStart(){
        return isStart;
    }
    public void setStartTime(String msg) {
        startTime.setValue(msg);

    }
    public MutableLiveData<String> getStartTime() {
        return startTime;
    }

    public void setEndTime(String msg) {
        endTime.setValue(msg);

    }
    public MutableLiveData<String> getEndTime() {
        return endTime;
    }

    public void setEntryDate(String msg) {
        entryDate.setValue(msg);
    }
    public MutableLiveData<String> getEntryDate() {
        return entryDate;
    }
    public void loadEntry(UUID entryId) {
        entryIdLiveData.setValue(entryId);
    }

    public LiveData<JournalEntry> getEntry(UUID id) {
        entryIdLiveData.setValue(id);
        return Transformations.switchMap(entryIdLiveData, mRepository::getEntry);
    }

    public void insert(JournalEntry entry) {
        mRepository.insert(entry);
    }

    public void update(JournalEntry entry) {
        mRepository.update(entry);
    }

    public void delete(JournalEntry entry) {
        mRepository.delete(entry);
    }
}