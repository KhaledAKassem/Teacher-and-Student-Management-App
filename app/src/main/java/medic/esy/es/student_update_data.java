package medic.esy.es;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Map;

public class student_update_data extends AppCompatActivity {

    EditText update_email;
    EditText update_pass;
    EditText update_number;
    EditText update_address;
    TextView update_data;
    DatabaseReference database;
    private FirebaseAuth mAuth;
    private Firebase myref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatenow);


        update_number=(EditText)findViewById(R.id.update_number);
        update_address=(EditText)findViewById(R.id.update_address);
        update_data=(TextView) findViewById(R.id.update_data);

        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mAuth = FirebaseAuth.getInstance();
            String user_id = mAuth.getCurrentUser().getUid();
            myref = new Firebase("https://electronic-assignment-9112e.firebaseio.com/users").child(user_id);
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if( update_address.getText().toString().trim().length() == 0 ||
                     update_number.getText().toString().trim().length() == 0 )

                    {
                       update_address.setError("Address is required!");
                       update_number.setError("Number is required");
                    }
                    else {
                        Map<String, String> map = dataSnapshot.getValue(Map.class);
                        dataSnapshot.getRef().child("name").setValue(update_address.getText().toString());
                        dataSnapshot.getRef().child("number").setValue(update_number.getText().toString());
                        Intent i = new Intent(student_update_data.this, StudentProfile.class);
                        startActivity(i);
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
            }
        });






    }
}
