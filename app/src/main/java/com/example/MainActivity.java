package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task4.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseFaculty;
import model.Register;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerFacultyName, spinnerDepartmentName;
    DatabaseFaculty db;
    EditText editTextUserName, editTextEmail, editTextPassword, editTextPhone;
    Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseFaculty(this);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO validation here !!!!!!

                if (!editTextUserName.getText().toString().isEmpty()
                        && !editTextPassword.getText().toString().isEmpty()
                        && !editTextEmail.getText().toString().isEmpty()) {
                    addUser(v);
                }
            }

            private void addUser(View v) {

                Register register = new Register();
                String userName = editTextUserName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();

                String s1 = spinnerFacultyName.getSelectedItem().toString();
                String s2 = spinnerDepartmentName.getSelectedItem().toString();

                register.setStudentName(userName);
                register.setEmail(email);
                register.setPassword(password);
                register.setPhone(phone);
                register.setFacultyName(s1);
                register.setDepartmentName(s2);

                Log.d("TAG", s2 + "  " + s1);

                db.addUser(register);
                Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();
                Log.d("Item Added ID : ", String.valueOf(db.getUsersCount()));

                startActivity(new Intent(MainActivity.this,DetailsActivity.class));

            }
        });


        spinnerFacultyName = findViewById(R.id.spinnerFacultyName);

        String[] items = new String[]{"computer and information", "engineering"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerFacultyName.setAdapter(adapter);

        spinnerDepartmentName =

                findViewById(R.id.spinnerDepartmentName);
        spinnerFacultyName.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sp1 = String.valueOf(spinnerFacultyName.getSelectedItem());
        String sp2 = String.valueOf(spinnerDepartmentName.getSelectedItem());

        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();

        Log.d("TAG", "" + sp2);

        if (sp1.contentEquals("computer and information")) {
            List<String> list = new ArrayList<>();
            list.add("se");
            list.add("cs");
            list.add("it");
            list.add("is");
            Log.d("TAG", "" + list.indexOf("se"));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinnerDepartmentName.setAdapter(dataAdapter);
        }
        if (sp1.contentEquals("engineering")) {
            List<String> list = new ArrayList<>();
            list.add("Biomedical ");
            list.add("Chemical ");
            list.add("Civil and Environmental");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            spinnerDepartmentName.setAdapter(dataAdapter2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
