package medic.esy.es;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateCourse extends AppCompatActivity {
     EditText name;
     EditText grade;
    private FirebaseAuth mAuth;

    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name=(EditText) findViewById(R.id.test1);
        grade=(EditText) findViewById(R.id.test2);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Courses");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.update_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value_name=  name.getText().toString();
                String value_grade= grade.getText().toString();

                DatabaseReference courses_id = database.child(value_name);
                courses_id.child("graid").setValue(value_grade);

                if( name.getText().toString().trim().length() == 0 ||
                        grade.getText().length() == 0  )

                {
                    name.setError("Course Name is Required");
                    grade.setError("Course Text is Required  ");
                    //  courseGradeText.setError("Course Grade is required");

                }
                else {
                    Intent i = new Intent(UpdateCourse.this, GetCourses.class);
                    startActivity(i);
                }


            }
        });


    }

}
