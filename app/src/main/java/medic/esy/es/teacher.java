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

public class teacher extends AppCompatActivity {


    Typeface fonts1;

    TextView signup;
    EditText email;
    EditText pass;
    TextView signin_teacher;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent it = new Intent(teacher.this, medic.esy.es.DoctorProfile.class);
            startActivity(it);
        }

        mAuth = FirebaseAuth.getInstance();

        fonts1 = Typeface.createFromAsset(teacher.this.getAssets(),
                "fonts/Lato-Light.ttf");

        email=(EditText) findViewById(R.id.email_teacher);
        pass=(EditText)  findViewById(R.id.pass_teacher);
        signup = (TextView) findViewById(R.id.signup_teacher);



        signin_teacher=(TextView)findViewById(R.id.signin_teacher);
        signin_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    if(email.getText().toString().trim().length() == 0 || pass.getText().length()==0)
    {
        email.setError("Email  is required !");
        pass.setError("pass is Rquired !")    ;

    }
        else{
        con();
    }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(teacher.this, medic.esy.es.signup_teacher.class);
                startActivity(it);

            }
        });

    }
    public void con(){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(teacher.this, "Login success.",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(teacher.this, DoctorProfile.class);
                    startActivity(i);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(teacher.this, "Login failed.",
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
