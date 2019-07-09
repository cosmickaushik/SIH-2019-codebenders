package com.example.workspace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText) findViewById(R.id.loginEmail);
        password =(EditText) findViewById(R.id.loginPass);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

    }

    public void loginButtonClicked(View view){
        String emailfinal=email.getText().toString().trim();
        String passfinal=password.getText().toString().trim();

        if(!TextUtils.isEmpty(emailfinal) && !TextUtils.isEmpty(passfinal)){
            mAuth.signInWithEmailAndPassword(emailfinal,passfinal).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkUserExists();
                    }
                }
            });
        }
    }

    public void checkUserExists(){
        final String user_id=mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)) {
                    if (dataSnapshot.child(user_id).child("Post").getValue(Long.class) == 1) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else if (dataSnapshot.child(user_id).child("Post").getValue(Long.class) == 2){
                        startActivity(new Intent(LoginActivity.this,PrintingActivity.class));
                    }

                    else if (dataSnapshot.child(user_id).child("Post").getValue(Long.class) == 3){
                        startActivity(new Intent(LoginActivity.this,FrameActivity.class));
                    }
                    else if (dataSnapshot.child(user_id).child("Post").getValue(Long.class) == 4){
                        startActivity(new Intent(LoginActivity.this, PastingActivity.class));
                    }
                    else if (dataSnapshot.child(user_id).child("Post").getValue(Long.class) == 5){
                        startActivity(new Intent(LoginActivity.this, PaintingActivity.class));
                    }
                    else if (dataSnapshot.child(user_id).child("Post").getValue(Long.class) == 6){
                        startActivity(new Intent(LoginActivity.this, CustomerActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Email Id not registered",Toast.LENGTH_LONG).show();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }

    public void signupfromloginClicked(View view){
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }
}
