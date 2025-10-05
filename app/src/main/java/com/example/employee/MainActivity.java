package com.example.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editTextName, editTextEmail;
    Button btnAdd, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tambah back button (arrow) di atas kiri
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Tambah Employee");
        }

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Masukkan data lengkap", Toast.LENGTH_SHORT).show();
            } else if (!email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Email harus Gmail (akhiri dengan @gmail.com)", Toast.LENGTH_SHORT).show();
                editTextEmail.setText("");  // Reset email salah
            } else {
                DBHelper dbHelper = new DBHelper(this);
                EmployeeModel employee = new EmployeeModel(name, email);
                dbHelper.addEmployee(employee);
                Toast.makeText(this, "Telah di tambahkan employee", Toast.LENGTH_SHORT).show();
                editTextName.setText("");
                editTextEmail.setText("");
            }
        });

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewEmployee.class);
            startActivity(intent);
        });
    }

    // Handle back arrow click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // Back ke Splash
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}