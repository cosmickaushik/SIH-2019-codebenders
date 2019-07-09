package com.example.workspace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewCustomerProject extends AppCompatActivity {
    private RecyclerView mProjectView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_project);


        mProjectView = (RecyclerView) findViewById(R.id.viewprojectslist);
        mProjectView.setHasFixedSize(true);
        mProjectView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("CustomerProjects");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(ViewCustomerProject.this, SignupActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };
    }

    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Projects, ViewCustomerProject.ProjectViewHolder> FBRA = new FirebaseRecyclerAdapter<Projects, ViewCustomerProject.ProjectViewHolder>(
                Projects.class,
                R.layout.viewprojects_row,
                ViewCustomerProject.ProjectViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ViewCustomerProject.ProjectViewHolder viewHolder, Projects model, int position) {
                final String post_key = getRef(position).getKey().toString();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleInstaActivity = new Intent(ViewCustomerProject.this, SingleCustomerView.class);
                        singleInstaActivity.putExtra("PostId", post_key);
                        startActivity(singleInstaActivity);

                    }
                });

            }
        };
        mProjectView.setAdapter(FBRA);
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.textTitle);
            post_title.setText(title);
        }

        public void setDescription(String desc) {
            TextView post_desc = (TextView) mView.findViewById(R.id.textDescription);
            post_desc.setText(desc);
        }

    }
}
