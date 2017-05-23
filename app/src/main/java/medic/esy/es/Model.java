package medic.esy.es;

import java.util.ArrayList;


public class Model {
    String cName;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }


    public static String[] getName(){
        String names[] = {"Java","C++","C#","HTML","HTML5","CSS","CSS3","Javascript"};

        return names;
    }

    public static ArrayList<Model> getData(){

        ArrayList<Model> datalist = new ArrayList<>();
        String[] name = getName();
        int l = name.length;
        for (int i = 0; i < l ; i++){
            Model model = new Model();
            model.setcName(name[i]);
            datalist.add(model);
        }
        return datalist;
    }
}
