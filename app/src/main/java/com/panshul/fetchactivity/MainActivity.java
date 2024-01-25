package com.panshul.fetchactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewFetchData;
    ProgressBar progressbar;
    List<DisplayObject> data;

    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewFetchData = findViewById(R.id.recyclerView);
        progressbar = findViewById(R.id.progressBar);
        data = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                .client(getClientInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<List<ListIdObject>> call = api.getAllListIds();

        call.enqueue(new Callback<List<ListIdObject>>() {
            @Override
            public void onResponse(Call<List<ListIdObject>> call, Response<List<ListIdObject>> response) {
                try{
                    List<ListIdObject> tempObjects = response.body();

                    tempObjects.sort(Comparator.comparingInt(ListIdObject::getListId)
                            .thenComparing(item -> item.getName() == null ? "" : item.getName()));
                    HashMap<Integer, List<ListIdObject>> groupedItems = new HashMap<>();
                    for (ListIdObject item : tempObjects) {
                        groupedItems.computeIfAbsent(item.listId, k -> new ArrayList<>()).add(item);
                    }

                    for(Map.Entry<Integer, List<ListIdObject>> entry: groupedItems.entrySet()){
                        String names = "[";
                        String ids = "[";

                        for(ListIdObject tempListObject: entry.getValue()){
                            ids+=tempListObject.getId()+", ";
                            if(tempListObject.getName() == null || tempListObject.getName().isEmpty()){
                                // Not adding objects with empty name
                            } else {
                                names+=tempListObject.getName()+", ";
                            }
                        }
                        names +="]";
                        ids +="]";
                        data.add(new DisplayObject(ids, Integer.valueOf(entry.getKey()), names ));
                    }
                    //Sorting objects first by listId, then by name
                    tempObjects.sort(Comparator.comparingInt((ListIdObject i) -> i.listId).thenComparing(i -> i.name, Comparator.nullsLast(Comparator.naturalOrder())));
                    setData();

                } catch(Exception e){
                    Log.i("exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ListIdObject>> call, Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });
    }

    public static OkHttpClient getClientInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return client;
    }

    public void setData(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewFetchData.setAdapter(adapter);
        recyclerViewFetchData.setLayoutManager(manager);

        progressbar.setVisibility(View.INVISIBLE);
    }
}