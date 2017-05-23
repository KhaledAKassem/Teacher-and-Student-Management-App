package medic.esy.es;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteCourse extends AppCompatActivity {

    TextView delete_course_doctor_j;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);
        delete_course_doctor_j=(TextView)findViewById(R.id.delete_course_doctor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.delete_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=delete_course_doctor_j.getText().toString();
                database = FirebaseDatabase.getInstance().getReference().child("Courses");
                database.child(data).setValue(null);
                Intent i=new Intent(DeleteCourse.this,DoctorProfile.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Deleted successfully course: "+ data, Toast.LENGTH_LONG).show();

            }
        });
    }

}
