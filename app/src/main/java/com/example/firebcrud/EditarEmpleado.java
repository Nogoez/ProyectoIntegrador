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

import java.util.HashMap;
import java.util.Map;

public class EditarEmpleado extends AppCompatActivity {


    private TextInputEditText courseNameEdt, courseDescEdt, coursePriceEdt, bestSuitedEdt, courseImgEdt, courseLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CourseRVModal courseRVModal;
    private ProgressBar loadingPB;

    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        Button addCourseBtn = findViewById(R.id.idBtnAddCourse);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();

        courseRVModal = getIntent().getParcelableExtra("course");
        Button deleteCourseBtn = findViewById(R.id.idBorrarBtn);

        if (courseRVModal != null) {

            courseNameEdt.setText(courseRVModal.getCourseName());
            coursePriceEdt.setText(courseRVModal.getCoursePrice());
            bestSuitedEdt.setText(courseRVModal.getBestSuitedFor());
           // courseImgEdt.setText(courseRVModal.getCourseImg());
            courseLinkEdt.setText(courseRVModal.getCourseLink());
            courseDescEdt.setText(courseRVModal.getCourseDescription());
            courseID = courseRVModal.getCourseId();
        }


        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);

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

                Map<String, Object> map = new HashMap<>();
                map.put("courseName", courseName);
                map.put("courseDescription", courseDesc);
                map.put("coursePrice", coursePrice);
                map.put("bestSuitedFor", bestSuited);
                map.put("courseImg", courseImg);
                map.put("courseLink", courseLink);
                map.put("courseId", courseID);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        loadingPB.setVisibility(View.GONE);

                        databaseReference.updateChildren(map);

                        Toast.makeText(EditarEmpleado.this, "Course Updated..", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(EditarEmpleado.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(EditarEmpleado.this, "Fail to update course..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteCourse();
            }
        });

    }

    private void deleteCourse() {

        databaseReference.removeValue();

        Toast.makeText(this, "Course Deleted..", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(EditarEmpleado.this, MainActivity.class));
    }
}
