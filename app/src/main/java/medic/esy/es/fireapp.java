package medic.esy.es;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by khaled on 21/05/17.
 */



public class fireapp extends Application{

     @Override

    public void onCreate(){
        super.onCreate();
         Firebase.setAndroidContext(this);


    }

}
