package medic.esy.es;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCourses extends AppCompatActivity {

    TextInputLayout courseNameInput,courseIdInput,courseGradeInput;
    EditText courseNameText,courseIdText,courseGradeText;

    private FirebaseAuth mAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        courseNameInput = (TextInputLayout) findViewById(R.id.courseNameInput);
        courseIdInput = (TextInputLayout) findViewById(R.id.courseIdInput);
        courseGradeInput = (TextInputLayout) findViewById(R.id.courseGradeInput);

        courseNameText = (EditText) findViewById(R.id.courseNameText);

        courseGradeText = (EditText) findViewById(R.id.courseGradeText);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Courses");




    }

    public void addCourse(View view) {
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference courses_id = database.child(courseNameText.getText().toString());
        courses_id.child("graid").setValue(courseGradeText.getText().toString());

        if( courseNameText.getText().toString().trim().length() == 0 ||
         courseGradeText.getText().length() == 0  )

        {
            courseNameText.setError("Course Name is Required");
            courseGradeText.setError("Course Text is Required  ");
          //  courseGradeText.setError("Course Grade is required");

        }
        else {
            Intent i = new Intent(AddCourses.this, GetCourses.class);
            startActivity(i);
        }
    }
}
