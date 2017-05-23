package medic.esy.es;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class student extends AppCompatActivity {


    Typeface fonts1;

    TextView signup_ya_student;
    EditText email_ya_stuent;
    EditText pass_ya_student;
    TextView signin_ya_student;
    private FirebaseAuth mmAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent it = new Intent(student.this, medic.esy.es.DoctorProfile.class);
            startActivity(it);
        }

        mmAuth = FirebaseAuth.getInstance();

        fonts1 = Typeface.createFromAsset(student.this.getAssets(),
                "fonts/Lato-Light.ttf");

        email_ya_stuent=(EditText) findViewById(R.id.student_email_login);
        pass_ya_student=(EditText)  findViewById(R.id.student_pass_login);
        signup_ya_student = (TextView) findViewById(R.id.signup_studentt);



        signin_ya_student=(TextView)findViewById(R.id.signin_student_login);
        signin_ya_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(email_ya_stuent.getText().toString().length() == 0 ||
                pass_ya_student.getText().toString().length()==0)
            {
                email_ya_stuent.setError("Email  is required!");
                pass_ya_student.setError("Pass is required!");
            }
                else {
                con();
            }
            }
        });

        signup_ya_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(student.this, medic.esy.es.signup_student.class);
                startActivity(it);
                finish();

            }
        });

    }
    public void con(){
        mmAuth.signInWithEmailAndPassword(email_ya_stuent.getText().toString(), pass_ya_student.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(student.this, "Login success.",
                                    Toast.LENGTH_SHORT).show();
         Intent i = new Intent(student.this, StudentProfile.class);
         startActivity(i);
           } else {
          // If sign in fails, display a message to the user.
           Toast.makeText(student.this, "Login failed.",
             Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
