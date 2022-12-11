package com.example.firebcrud;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AgregarEmpleado extends AppCompatActivity {

    private Button addCourseBtn;
    private TextInputEditText courseNameEdt, courseDescEdt, coursePriceEdt, bestSuitedEdt, courseImgEdt, courseLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_empleado);

        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Empleados");

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);

                String courseName = courseNameEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String bestSuited = bestSuitedEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                courseID = courseName;

                CourseRVModal courseRVModal = new CourseRVModal(courseID, courseName, courseDesc, coursePrice, bestSuited, courseImg, courseLink);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child(courseID).setValue(courseRVModal);

                        Toast.makeText(AgregarEmpleado.this, "Empleado Agregado..", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AgregarEmpleado.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(AgregarEmpleado.this, "Fallo al agregar..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
