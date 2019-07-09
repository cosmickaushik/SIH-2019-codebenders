package com.example.workspace;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class SingleCustomerView extends AppCompatActivity {
    private String post_key=null;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView Title;
    private TextView Description;
    private TextView Category;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;

    String post_Title;
    String post_Desc;
    String post_Category;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer_view);

        post_key=getIntent().getExtras().getString("PostId");

        mDatabase= FirebaseDatabase.getInstance().getReference().child("CustomerProjects");

        Title=(TextView) findViewById(R.id.singleCustomerTitle);
        Description=(TextView) findViewById(R.id.singleCustomerDescription);
        Category=(TextView) findViewById(R.id.singleCustomerCategory);

        databaseReference = database.getInstance().getReference().child("Projects");
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post_Title=(String) dataSnapshot.child("title").getValue();
                post_Desc=(String) dataSnapshot.child("description").getValue();
                post_Category=(String) dataSnapshot.child("category").getValue();
                Title.setText(post_Title);
                Description.setText(post_Desc);
                Category.setText(post_Category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addProjectCustomer(View view){
        final String titleValue=post_Title.toString().trim();
        final String descValue=post_Desc.toString().trim();
        final String category=post_Category.toString().trim();

        if (!TextUtils.isEmpty(titleValue) && !TextUtils.isEmpty(descValue)){

            final DatabaseReference newPost = databaseReference.push();
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    newPost.child("title").setValue(titleValue);
                    newPost.child("description").setValue(descValue);
                    newPost.child("category").setValue(category);
                    if (category.equals("Flex Board")) {
                        newPost.child("printing").setValue("Job Not Done");
                        newPost.child("frame").setValue("Job Not Done");
                        newPost.child("pasting").setValue("Job Not Done").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SingleCustomerView.this, "Project Added", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SingleCustomerView.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                    else if (category.equals("Sign Board")){
                        newPost.child("frame").setValue("Job Not Done");
                        newPost.child("painting").setValue("Job Not Done").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SingleCustomerView.this, "Project Added", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SingleCustomerView.this, MainActivity.class);
                                startActivity(intent);

                            }
                        });

                    }

                    else if (category.equals("Banners")){
                        newPost.child("printing").setValue("Job Not Done").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SingleCustomerView.this, "Project Added", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SingleCustomerView.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }





}

