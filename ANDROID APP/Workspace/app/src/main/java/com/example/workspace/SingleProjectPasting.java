package com.example.workspace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class SingleProjectPasting extends AppCompatActivity {
    private String post_key=null;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView Title;
    private TextView Description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_project_pasting);

        post_key=getIntent().getExtras().getString("PostId");

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Projects");

        Title=(TextView) findViewById(R.id.singlePastingTitle);
        Description=(TextView) findViewById(R.id.singlePastingDescription);


        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String post_Title=(String) dataSnapshot.child("title").getValue();
                String post_Desc=(String) dataSnapshot.child("description").getValue();

                Title.setText(post_Title);
                Description.setText(post_Desc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void pastingComplete(View view){
        long date=System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        final String datestring=sdf.format(date);
        final DatabaseReference newPost = mDatabase.child(post_key);
        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("pasting")) {
                    newPost.child("timepasting").setValue(datestring);
                    newPost.child("pasting").setValue("Job Done").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SingleProjectPasting.this, " PASTING JOB COMPLETE", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                else {
                    Toast.makeText(SingleProjectPasting.this,"This Job is not meant for you",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SingleProjectPasting.this,PaintingActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
