package medic.esy.es;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by khaled on 21/05/17.
 */
@IgnoreExtraProperties
public class DoctorData {

    public String username;
    public String email;


    public DoctorData(String username, String email) {
        this.username = username;
        this.email = email;
    }

}