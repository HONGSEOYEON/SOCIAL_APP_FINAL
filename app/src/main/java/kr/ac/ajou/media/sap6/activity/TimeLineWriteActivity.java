package kr.ac.ajou.media.sap6.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import kr.ac.ajou.media.sap6.R;
import kr.ac.ajou.media.sap6.activity.handler.MyClient;
import kr.ac.ajou.media.sap6.activity.handler.SQLiteHandler;

/**
 * Created by mingeummaegbug on 15. 11. 29..
 */
public class TimeLineWriteActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView mResultTextView;
    private EditText mInputEditText;
    private Button mSave;
    private Button mExit;
    //SharedPreferences DBname;

     SQLiteHandler db;
//    public EditText getmInputEditText() {
//        return mInputEditText;
//    }
//
//    public void setmInputEditText(EditText mInputEditText) {
//        this.mInputEditText = mInputEditText;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writetimeline);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mSave = (Button) findViewById(R.id.save);
        mExit = (Button) findViewById(R.id.write_exit);


        mInputEditText = (EditText) findViewById(R.id.editText);

//        DBname = getSharedPreferences("ID",MODE_PRIVATE);
//        user_id = DBname.getString("ID", "");
//
//        Bundle user = getIntent().getExtras();
//        user_id = user.getString(Intent.EXTRA_TEXT);
//
        db = new SQLiteHandler(getApplicationContext());

mExit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        finish();


    }
});


        mSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                HashMap<String, String> user = db.getUserDetails();
                String user_id = user.get("name");


                // String user_id = "hi";
                Toast.makeText(TimeLineWriteActivity.this,"userid? : "+user_id,Toast.LENGTH_SHORT).show();
                String context = mInputEditText.getText().toString();



                RequestParams params = new RequestParams();
                params.add("user_id", user_id);
                params.add("context", context);

                MyClient.post("/timelines", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(TimeLineWriteActivity.this, "Created" + statusCode, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(TimeLineWriteActivity.this, "Failed!" + statusCode, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



    }

}