package medic.esy.es;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView student,teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student = (TextView)findViewById(R.id.student);
        teacher = (TextView)findViewById(R.id.teacher);

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this, medic.esy.es.teacher.class);
                startActivity(it);
//                finish();

            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this, medic.esy.es.student.class);
                startActivity(it);
//                finish();


            }
        });

    }
}
