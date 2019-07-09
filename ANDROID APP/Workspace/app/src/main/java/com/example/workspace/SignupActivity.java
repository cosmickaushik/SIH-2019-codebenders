package com.example.workspace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] employeePost = {"Select Post", "Manager", "Printing Worker", "Frame Maker", "Pasting Worker","Painting Worker","Customer"};
    private EditText nameField;
    private Spinner postField;
    private EditText emailField;
    private EditText passField;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameField = (EditText) findViewById(R.id.employeeName);
        postField = (Spinner) findViewById(R.id.employeePost);
        postField.setOnItemSelectedListener(this);
        emailField = (EditText) findViewById(R.id.signupEmail);
        passField = (EditText) findViewById(R.id.signupPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,employeePost);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postField.setAdapter(aa);

    }

    public void signupButtonClicked(View view){
        final String name = nameField.getText().toString().trim();
        final Integer post = postField.getSelectedItemPosition();
        final String email = emailField.getText().toString().trim();
        final String pass = passField.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful() && post!=0){
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("Name").setValue(name);
                        current_user_db.child("Post").setValue(post);

                        if (post == 1) {
                            Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        }

                        else if (post == 2){
                            Intent mainIntent = new Intent(SignupActivity.this, PrintingActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        }

                        else if (post == 3){
                            Intent mainIntent = new Intent(SignupActivity.this, FrameActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        }

                        else if (post == 4){
                            Intent mainIntent = new Intent(SignupActivity.this, PastingActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        }
                        else if (post == 5){
                            Intent mainIntent = new Intent(SignupActivity.this, PaintingActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        }
                        else if (post == 6){
                            Intent mainIntent = new Intent(SignupActivity.this, CustomerActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        }

                        else{
                            Toast.makeText(SignupActivity.this,"Select Appropriate Post", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

    }




    public void signinButtonClicked(View view){
        Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
