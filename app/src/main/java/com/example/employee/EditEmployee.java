package com.example.employee;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditEmployee extends AppCompatActivity {
    TextView textViewID;
    EditText editTextName, editTextEmail;
    Button btnEdit, btnDelete;
    int employeeId;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        // Tambah back button + title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Employee");
        }

        textViewID = findViewById(R.id.textViewID);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);
        employeeId = getIntent().getIntExtra("employee_id", -1);

        if (employeeId != -1) {
            EmployeeModel employee = dbHelper.getEmployeeById(employeeId);
            if (employee != null) {
                textViewID.setText("ID: " + employee.getId());
                editTextName.setText(employee.getName());
                editTextEmail.setText(employee.getEmail());
            }
        }

        btnEdit.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            if (!name.isEmpty() && !email.isEmpty() && email.endsWith("@gmail.com")) {
                EmployeeModel updated = new EmployeeModel(employeeId, name, email);
                dbHelper.updateEmployee(updated);
                Toast.makeText(this, "Employee diupdate", Toast.LENGTH_SHORT).show();
                finish();  // Back ke list, onResume refresh
            } else if (!email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Email harus Gmail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Masukkan data lengkap", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteEmployee(employeeId);
            Toast.makeText(this, "Employee dihapus", Toast.LENGTH_SHORT).show();
            finish();  // Langsung back ke list, onResume refresh (langsung ilang tanpa relog)
        });
    }

    // Handle back arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // Back ke list
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}