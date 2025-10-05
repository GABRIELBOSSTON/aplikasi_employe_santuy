package com.example.employee;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ViewEmployee extends AppCompatActivity {
    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    List<EmployeeModel> employeeModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        // Tambah back button + title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daftar Employee");
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEmployeeList();  // Load awal (view data SQLite seperti slide)
    }

    // Refresh list saat balik dari Edit (setelah delete/edit) – langsung ilang tanpa relog
    @Override
    protected void onResume() {
        super.onResume();
        loadEmployeeList();
    }

    private void loadEmployeeList() {
        DBHelper dbHelper = new DBHelper(this);
        employeeModels = dbHelper.getEmployeeList();  // Ambil data dari SQLite (rawQuery + Cursor)

        if (employeeModels.size() > 0) {
            adapter = new EmployeeAdapter(employeeModels, this);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No Employee Found", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle back arrow click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // Back ke Main
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}