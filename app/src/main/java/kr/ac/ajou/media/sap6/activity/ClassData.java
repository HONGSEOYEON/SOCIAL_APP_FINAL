package kr.ac.ajou.media.sap6.activity;

/**
 * Created by mingeummaegbug on 15. 12. 12..
 */
class ClassData {

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    private int db_id;
    private String user_name;

    public ClassData() {

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getClass_position() {
        return class_position;
    }

    public void setClass_position(int class_position) {
        this.class_position = class_position;
    }

    private String class_name;
    private int class_position;

    public ClassData(String user_name, String class_name, int class_position, int db_id){
        this.user_name = user_name;
        this.class_name = class_name;
        this.class_position = class_position;
        this.db_id = db_id;
    }




}
