package com.example.clientApi.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.clientApi.model.database.AuthorRepository;
import com.example.clientApi.model.database.Authors;
import com.example.clientApi.ui.CustomAdapter;

import java.util.List;

/**ViewModel class to implement business logic
 * to wrape data as LiveData objects
 * */
public class MainViewModel extends AndroidViewModel {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    public LiveData<List<Authors>> mAuthors;
    private AuthorRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AuthorRepository.getInstance(application.getApplicationContext());
        mAuthors = mRepository.mAuthors;
    }


    public void addAuthor(Authors authors) {
        mRepository.insertAuthor(authors);
    }


}
