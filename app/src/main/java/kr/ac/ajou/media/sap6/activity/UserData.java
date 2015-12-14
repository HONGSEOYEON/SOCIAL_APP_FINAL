package kr.ac.ajou.media.sap6.activity;

/**
 * Created by mingeummaegbug on 15. 12. 10..
 */
public class UserData {

public UserData(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private String name, context;

    public UserData(String name, String context){
                this.name = name;
                this.context = context;

    }



}
