package com.example.firebcrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {

    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;
    int lastPos = -1;


    public CourseRVAdapter(ArrayList<CourseRVModal> courseRVModalArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.courseRVModalArrayList = courseRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {

        CourseRVModal courseRVModal = courseRVModalArrayList.get(position);
        holder.courseTV.setText(courseRVModal.getCourseName());
        holder.coursePriceTV.setText(courseRVModal.getCoursePrice());

        setAnimation(holder.itemView, position);
        holder.courseTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseClickInterface.onCourseClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return courseRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTV, coursePriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTV = itemView.findViewById(R.id.idTVCOurseName);
            coursePriceTV = itemView.findViewById(R.id.idTVCousePrice);
        }
    }

    public interface CourseClickInterface {
        void onCourseClick(int position);
    }
}
