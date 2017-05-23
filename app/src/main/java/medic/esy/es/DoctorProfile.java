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


public class DoctorProfile extends AppCompatActivity {

    Button add;
    Button delete;
    Button update;
    Button mycourses;
    Button doctor_up;
    TextView doctor_name,doctor_email,doctor_number;
    DatabaseReference database;
    private FirebaseAuth mAuth;
    private Firebase myref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        doctor_name = (TextView) findViewById(R.id.doctor_name);
        doctor_email = (TextView) findViewById(R.id.doctor_email);
        doctor_number = (TextView) findViewById(R.id.doctor_number);
        mycourses=(Button) findViewById(R.id.mycourses);
        doctor_up=(Button) findViewById(R.id.update_doctor_profile);


        /// here code for retrieve data from database


//oncreate

//

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       try {
           if (user != null) {
               mAuth = FirebaseAuth.getInstance();
               String user_id = mAuth.getCurrentUser().getUid();
               database = FirebaseDatabase.getInstance().getReference().child("Teachers").child(user_id);
               doctor_email.setText(mAuth.getCurrentUser().getEmail());

                  myref = new Firebase("https://electronic-assignment-9112e.firebaseio.com/Teachers").child(user_id);

                  myref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       Map<String, String> map = dataSnapshot.getValue(Map.class);
                       String address = map.get("name");
                       String number = map.get("number");
                       doctor_name.setText(address);
                       doctor_number.setText(number);
                       Log.v("E_VALUE", "name:" + dataSnapshot.getValue());


                   }

                   @Override
                   public void onCancelled(FirebaseError firebaseError) {

                       finish();
                       Intent i=new Intent(DoctorProfile.this,StudentProfile.class);
                       startActivity(i);

                   }
               });
              }


       }
       catch (Exception ex){
           Intent i=new Intent(DoctorProfile.this,StudentProfile.class);
           startActivity(i);
       }




//         /// End for code for retriving data from database

        mycourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DoctorProfile.this,GetCourses.class);
                startActivity(i);
            }
        });
        doctor_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DoctorProfile.this,update_doctor_data.class);
                startActivity(i);
            }
        });
        add=(Button)findViewById(R.id.ADD);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DoctorProfile.this,AddCourses.class);
                startActivity(i);
            }
        });

        delete=(Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DoctorProfile.this,DeleteCourse.class);
                startActivity(i);
            }
        });


        update=(Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DoctorProfile.this,UpdateCourse.class);
                startActivity(i);
            }
        });



        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //  fab.setOnClickListener(new View.OnClickListener() {
        //      @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
            Intent r=new Intent(DoctorProfile.this,teacher.class);
            startActivity(r);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}