package medic.esy.es;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_student extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;
    EditText emailForStudent_j;
    EditText number_for_student_j;
    EditText pass_for_student_j;
    EditText address_for_student_j;
    TextView signUpForStudent_j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String userID = user.getUid();


        setContentView(R.layout.activity_signup_student);
        emailForStudent_j=(EditText)findViewById(R.id.emailForStudent);
        number_for_student_j=(EditText)findViewById(R.id.number_t_signup);
        pass_for_student_j=(EditText)findViewById(R.id.pass_t_sign);
        address_for_student_j=(EditText)findViewById(R.id.address_for_student);
        signUpForStudent_j=(TextView)findViewById(R.id.signUpForStudent);
        mAuth = FirebaseAuth.getInstance();

        signUpForStudent_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailForStudent_j.getText().toString().length() == 0 || pass_for_student_j.getText().length() == 0
                        ||number_for_student_j.getText().toString().length() == 0 || address_for_student_j.getText().toString().length() == 0) {

                    emailForStudent_j.setError("Email  is required !");
                    pass_for_student_j.setError("pass is required");
                    address_for_student_j.setError("Address is required");
                    number_for_student_j.setError("Number is Required");
                }
                else {
                    con();
                }
            }
        });

    }

    public void con(){
        mAuth.createUserWithEmailAndPassword(emailForStudent_j.getText().toString(),
                pass_for_student_j.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(signup_student.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            String user_id = mAuth.getCurrentUser().getUid();
                            mDatabase.child(user_id).child("number").setValue(number_for_student_j.getText()+"");
                            mDatabase.child(user_id).child("name").setValue(address_for_student_j.getText()+"");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(signup_student.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
