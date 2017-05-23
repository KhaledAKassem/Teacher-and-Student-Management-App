package medic.esy.es;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GetCourses extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<String> values = new ArrayList<String>();

    private Firebase myref;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_courses);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_get_courses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new GetDataFromFirebase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Courses");

        myref=new Firebase("https://electronic-assignment-9112e.firebaseio.com/Courses");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {


                  String name = dataSnapshot.getKey();
//                  String grade=dataSnapshot.child("graid").toString();

                values.add(name);
//                values.add(grade);
                recyclerView.setAdapter(new RecyclerViewAdapter(values,GetCourses.this));





            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private class GetDataFromFirebase extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
