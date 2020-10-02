package com.example.clientApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.clientApi.model.database.Authors;
import com.example.clientApi.ui.CustomAdapter;
import com.example.clientApi.model.Stories;
import com.example.clientApi.model.Users;
import com.example.clientApi.utils.RetrofitClientInstace;
import com.example.clientApi.utils.ClientDataService;
import com.example.clientApi.viewModel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MainActivity class
 * */
public class MainActivity extends AppCompatActivity {
    //Custom  instance
    private CustomAdapter adapter;
    //RecyclerView instance
    private RecyclerView recyclerView;
    //progress dialog
    ProgressDialog progressDoalog;
    //Swipe Layout to refresh the content
    SwipeRefreshLayout swipeRefreshLayout;
    //List to insert the data into Database
    List<Authors> authorsData = new ArrayList<>();
    //List for the fetched data
    List<Authors> authorsDataList = new ArrayList<>();
    // create instance of viewModel class
    private MainViewModel mMainViewModel;
    // TAG for debugger purpose
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        /*Refresh listener */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                initViewModel();
            }
        });

            initViewModel();
    }

    /*Method to initialize viewModel class*/
    private void initViewModel(){
        recyclerView = findViewById(R.id.customRecyclerView);


        mMainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
       // check for internet connection
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected != false) {
            dataLoader();
        }
        else{
            final Observer<List<Authors>> authorObserver = new Observer<List<Authors>>() {
                @Override
                public void onChanged(List<Authors> authorsData) {
                    authorsDataList.clear();
                    authorsDataList.addAll(authorsData);
                    List<Users> users = null;
                    if (adapter == null) {
                        adapter = new CustomAdapter(MainActivity.this, users,authorsData);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }else{
                        adapter.notifyDataSetChanged();
                    }
                }
            };
            mMainViewModel.mAuthors.observe(this,authorObserver);
        }

    }

    /*Method to load the data from url*/
    private void dataLoader(){

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        ClientDataService service = RetrofitClientInstace.getRetrofitInstance()
                                            .create(ClientDataService.class);
        Call<Stories> callSync = service.getStories();

        callSync.enqueue(new Callback<Stories>() {
            @Override
            public void onResponse(Call<Stories> call, Response<Stories> response) {
                progressDoalog.dismiss();

                generateDataList(response.body().getUserData());
                Log.i(TAG,"Response:  "+response.body().getUserData());
            }

            @Override
            public void onFailure(Call<Stories> call, Throwable t) {

                Log.e(TAG,"Failed Error From RETROFIT: ",t);
                Toast.makeText(MainActivity.this, "Something went Wrong!!!!",
                                        Toast.LENGTH_SHORT).show();
            }
        });



    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Users> users) {
        Log.i(TAG,"Response before adding file");
        //save the data into database before displaying
        try{
            authorsData = addAuthor(users);

            if (authorsData != null) {
                Log.i(TAG,"REady to inser the data: "+authorsData.get(0).getArticleAuthor());

                for(Authors eachAuthor: authorsData){
                    Log.i(TAG,"Inserting..."+eachAuthor.getArticleTitle());

                    if(mMainViewModel != null) {
                        mMainViewModel.addAuthor(eachAuthor);
                    }

                    //
                    View contextView = findViewById(R.id.swipeLayout);
                    Snackbar.make(contextView, "Stories saved for offline view",
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                }

                Log.i(TAG,"Response: to Custom Adapter..... "+ authorsData.toString());
                authorsData = null;
            }else{
                Log.i(TAG,"Problem: Empty data...:-(");
            }
        }catch (NullPointerException e){
            Log.i(TAG,"Problem: Empty data...:-(", e);
        }


        //Initialize the custom adapter to display the data
        adapter = new CustomAdapter(this, users,authorsData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /*Method to return author object to insert into the database*/
    private static List<Authors> addAuthor(List<Users> users){
        List<Authors> authors = new ArrayList<>();
        for (int i = 0; i< users.size(); i++){
            String title = users.get(i).getTitle();
            String authorName = users.get(i).getAuthor().getAutherName();
            Log.i(TAG,"Data: Title: "+ title+" Author: "+authorName);
            authors.add(new Authors(title,authorName));
        }
       // Log.i(TAG,"Data: "+ authors.get(0).getArticleTitle());
        return authors;
    }

}
