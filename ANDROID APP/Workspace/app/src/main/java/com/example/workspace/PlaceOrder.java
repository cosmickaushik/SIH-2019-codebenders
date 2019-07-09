package com.example.workspace;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlaceOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] typeProject= {"Select Category", "Flex Board", "Sign Board", "Banners"};
    private Spinner projectCategory;
    private EditText Title;
    private EditText Desc;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Title = (EditText) findViewById(R.id.addTitlec);
        Desc = (EditText) findViewById(R.id.addDescc);
        projectCategory=(Spinner) findViewById(R.id.typeProjectc);
        projectCategory.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeProject);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectCategory.setAdapter(aa);


        databaseReference = database.getInstance().getReference().child("CustomerProjects");
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

    }

    public void orderButtonClicked(View view){

        final String titleValue=Title.getText().toString().trim();
        final String descValue=Desc.getText().toString().trim();
        final String category=projectCategory.getSelectedItem().toString();

        if (!TextUtils.isEmpty(titleValue) && !TextUtils.isEmpty(descValue)){

            final DatabaseReference newPost = databaseReference.push();
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    newPost.child("title").setValue(titleValue);
                    newPost.child("description").setValue(descValue);
                    newPost.child("category").setValue(category);
                    newPost.child("Uid").setValue(mCurrentUser.getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(PlaceOrder.this, "Order Placed", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PlaceOrder.this, CustomerActivity.class);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public void addpayment(View view){
        Uri uri = Uri.parse("https://imjo.in/QJfDrv"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
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
}
