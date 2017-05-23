package medic.esy.es;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;


public class StudentProfile extends AppCompatActivity {


    TextView student_name, student_emaill, student_number;
    Button update;
    Button assign;
    DatabaseReference database;
    private FirebaseAuth mAuth;
    private Firebase myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        student_name = (TextView) findViewById(R.id.student_name_profile);
        student_emaill = (TextView) findViewById(R.id.student_email_profile);
        student_number = (TextView) findViewById(R.id.student_number_profile);
        update=(Button) findViewById(R.id.update_data_now);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentProfile.this,student_update_data.class);
                startActivity(i);

            }
        });

        assign=(Button) findViewById(R.id.assignment);
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentProfile.this,GetCoursesForStudent.class);
                startActivity(i);
            }
        });

           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
           if (user != null) {
               mAuth = FirebaseAuth.getInstance();
               String user_id = mAuth.getCurrentUser().getUid();
               database = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
               student_emaill.setText(mAuth.getCurrentUser().getEmail());
               myref = new Firebase("https://electronic-assignment-9112e.firebaseio.com/users").child(user_id);
               myref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       Map<String, String> map = dataSnapshot.getValue(Map.class);
                       String address = map.get("name");
                       String number = map.get("number");
                       student_name.setText(address);
                       student_number.setText(number);
                       Log.v("E_VALUE", "name:" + dataSnapshot.getValue());

                   }

                   @Override
                   public void onCancelled(FirebaseError firebaseError) {



                   }
               });
           }




        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctor_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent r=new Intent(StudentProfile.this,student.class);
            startActivity(r);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
