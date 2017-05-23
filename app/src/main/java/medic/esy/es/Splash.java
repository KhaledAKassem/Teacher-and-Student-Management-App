package medic.esy.es;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    ImageView splash_img;
    TextView splash_txt;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
        R.anim.clockwise);

        splash_img = (ImageView) findViewById(R.id.welcome_img);

        splash_txt = (TextView) findViewById(R.id.welcome_text);

        splash_img.startAnimation(animation);
        splash_txt.startAnimation(animation1);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        Thread splash = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);

                    sleep(3050);
                } catch (Exception e){

                } finally {
                    mediaPlayer.pause();
                    Intent i = new Intent(Splash.this,MainActivity.class);
                    startActivity(i);
                    finish();

                }
            }
        };
        splash.start();
    }

}