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

public class signup_teacher extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;
    EditText email_up;
    EditText number_up;
    EditText pass_up;
    EditText address;
    TextView signup_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Teachers");
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String userID = user.getUid();


        setContentView(R.layout.activity_signup_teacher);
        email_up = (EditText) findViewById(R.id.email_t_signup);
        number_up = (EditText) findViewById(R.id.number_t_signup);
        pass_up = (EditText) findViewById(R.id.pass_t_sign);
        address = (EditText) findViewById(R.id.address_t_signup);
        signup_t = (TextView) findViewById(R.id.signup_t_signup);
        mAuth = FirebaseAuth.getInstance();

        signup_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email_up.getText().toString().length() == 0 || pass_up.getText().length() == 0
                        || number_up.getText().toString().length() == 0 || address.getText().toString().length() == 0) {

                   email_up.setError("Email  is required !");
                   pass_up.setError("pass is required");
                   address.setError("Address is required");
                   number_up.setError("Number is Required");


                } else {
                    con();
                }
            }
        });
    }


  public void con(){
      mAuth.createUserWithEmailAndPassword(email_up.getText().toString(),
  pass_up.getText().toString())
  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
  @Override
  public void onComplete(@NonNull Task<AuthResult> task) {
      if (task.isSuccessful()) {
          // Sign in success, update UI with the signed-in user's information
  Toast.makeText(signup_teacher.this, "Authentication success.",
                  Toast.LENGTH_SHORT).show();
          String user_id = mAuth.getCurrentUser().getUid();
          mDatabase.child(user_id).child("number").setValue(number_up.getText()+"");
          mDatabase.child(user_id).child("name").setValue(address.getText()+"");
      } else {
          // If sign in fails, display a message to the user.
          Toast.makeText(signup_teacher.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
      }

                      // ...
                  }
              });
  }
}
