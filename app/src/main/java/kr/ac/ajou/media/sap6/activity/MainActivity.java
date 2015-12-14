package kr.ac.ajou.media.sap6.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import kr.ac.ajou.media.sap6.R;
import kr.ac.ajou.media.sap6.activity.handler.SQLiteHandler;
import kr.ac.ajou.media.sap6.activity.handler.SessionManager;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout; //로그아웃할시에 사용할 버튼
    private Button btnGoMain;
    private static MainActivity instance = null;
    private SQLiteHandler db;
    private SessionManager session;

    public static synchronized MainActivity getInstance(){
        if(null == instance){
            instance = new MainActivity();
        }
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DBname = "";
        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnGoMain = (Button) findViewById(R.id.btnGoMain);
        btnLogout = (Button) findViewById(R.id.btnLogout); //로그아웃시에 쓰일 버튼


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        Intent intent = new Intent(getApplicationContext(),TimeLineWriteActivity.class);
        intent.putExtra("user",user);





        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        //Toast.makeText(MainActivity.this,"name"+name,Toast.LENGTH_LONG).show();//ex

    }
    public void OnClick(View v){
        Intent intent = new Intent(MainActivity.this, TotalActivity.class);
        startActivity(intent);
        finish();
    }
        // Logout button click event


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public Button getBtnGoMain() {
        return btnGoMain;
    }

    public void setBtnGoMain(Button btnGoMain) {
        this.btnGoMain = btnGoMain;
    }
}