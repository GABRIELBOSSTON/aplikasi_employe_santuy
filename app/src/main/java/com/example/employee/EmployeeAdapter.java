package com.example.employee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    List<EmployeeModel> employeeList;
    Context context;
    DBHelper dbHelper;

    public EmployeeAdapter(List<EmployeeModel> employeeList, Context context) {
        this.employeeList = employeeList;
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.employee_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmployeeModel employee = employeeList.get(position);
        holder.textViewName.setText(employee.getName());  // Nama judul
        holder.textViewID.setText("EMP" + String.format("%03d", employee.getId()));  // ID seperti EMP001
        holder.textViewFullName.setText(employee.getName());  // Nama di row
        holder.textViewEmail.setText(employee.getEmail());

        // Button Edit: Intent ke EditEmployee
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditEmployee.class);
            intent.putExtra("employee_id", employee.getId());
            context.startActivity(intent);
        });

        // Button Delete: Hapus dari DB + toast + refresh list
        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteEmployee(employee.getId());
            Toast.makeText(context, "Employee dihapus", Toast.LENGTH_SHORT).show();
            employeeList.remove(position);  // Hapus dari list lokal
            notifyItemRemoved(position);  // Refresh tampilan langsung (gak perlu relog)
            notifyItemRangeChanged(position, employeeList.size());  // Update posisi item lain
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewID, textViewFullName, textViewEmail;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}