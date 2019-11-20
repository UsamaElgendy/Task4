package com.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task4.R;

import java.util.ArrayList;
import java.util.List;

import Ui.RecyclerViewAdapter;
import data.DatabaseFaculty;
import model.Register;

public class DetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Register> registerList;
    private List<Register> listItem;
    private DatabaseFaculty db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = new DatabaseFaculty(this);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerList = new ArrayList<>();
        listItem = new ArrayList<>();

        registerList = db.getAllUsers();

        for (Register c : registerList) {
            Register register = new Register();
            register.setStudentName(c.getStudentName());
            register.setPassword(c.getPassword());
            register.setId(c.getId());


            listItem.add(register);

        }
        recyclerViewAdapter = new RecyclerViewAdapter(this, listItem);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();


    }
}
