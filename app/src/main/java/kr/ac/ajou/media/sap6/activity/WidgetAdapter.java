package kr.ac.ajou.media.sap6.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import kr.ac.ajou.media.sap6.activity.handler.MyClient;
import kr.ac.ajou.media.sap6.activity.handler.SQLiteHandler;

/**
 * Created by mingeummaegbug on 15. 12. 14..
 */
public class WidgetAdapter extends BaseAdapter {

    private TextView day;
    boolean fflag=true;

    String[] weekDay = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" }; // 요일 배열

    // String[] weekClass = {"a", "b", "c", "d", "e", "f", "g"};
    int number;
    // String tableTitle = weekClass[number];
    ClassData tempClassData = new ClassData();


    SQLiteHandler db;



    EditText edittableTime,edittableTitle,edittableClass;
    TextView tvtableTime  ,tvtableTitle,tvtableClass;


    int rcount =0;
    Context mContext;
    int count = 14;
    String[] mWeekTitleIds ={
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G"


    };
    WidgetAdapter(Context context){
        mContext = context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return count;
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View oldView, ViewGroup parent) {
        // TODO Auto-generated method stub


       // day = new TextView(mContext,getView(.)findViewById(R.id.day));

        db = new SQLiteHandler(mContext);

        HashMap<String, String> user = db.getUserDetails();
        final String user_id = user.get("name");

        final View w;


        if(oldView == null &&fflag==true  )
        {
            w = new ImageView(mContext);



            //view size(40 / 60)
            //v.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
            w.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 320));


        }
        else if(position>6 &&fflag==true  && position < count) {

            w = new TextView(mContext);




            ((TextView) w).setTextColor(Color.BLACK);
            ((TextView) w).setGravity(Gravity.CENTER);
            w.setBackgroundColor(Color.rgb(255, 255, 255));
            ((TextView) w).setHeight(200);


            MyClient.get("/classtables", null, new JsonHttpResponseHandler() {

                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                    //Toast.makeText(finalV.getContext(), user_id, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getActivity(), "Created", Toast.LENGTH_SHORT).show();
                    // If the response is JSONObject instead of expected JSONArray


                    //JSONArray state = new JSONArray(); //JsonArray 생성
                    try {
                        for (int i = 0; i < timeline.length(); i++) {
                            JSONObject firstEvent = (JSONObject) timeline.get(i);
                            int db_id = firstEvent.getInt("id");
                            String name = firstEvent.getString("user_id");
                            String class_name = firstEvent.getString("class_name");
                            int class_position = firstEvent.getInt("position");

                            ClassData tempclassdata = new ClassData();
                            tempclassdata.setDb_id(db_id);
                            tempclassdata.setUser_name(name);
                            tempclassdata.setClass_name(class_name);
                            tempclassdata.setClass_position(class_position);
                            // Toast.makeText(finalV.getContext(), tempclassdata.getUser_name(), Toast.LENGTH_SHORT).show();

                            if (Objects.equals(user_id, tempclassdata.getUser_name()) && position == tempclassdata.getClass_position()) {

                                // finalV = new TextView(mContext); // View v = new TextView(mContext);
                                w.setBackgroundColor(Color.rgb(114, 173, 58));
                                ((TextView) w).setText(tempclassdata.getClass_name());

                                //Toast.makeText(finalV.getContext(), user_id + "s" + class_name + class_position, Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void onFailure(int statusCode, Header[] headers, String
                        responseString, Throwable throwable) {
                    // Toast.makeText(getActivity(), "onFailure:" + statusCode, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(w.getContext(), "Fail", Toast.LENGTH_SHORT).show();

                }

            });


        }
        else if (position < 7 &&fflag==true ) {
            w = new TextView(mContext);
            w.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ((TextView)w).setGravity(Gravity.CENTER);
            ((TextView)w).setText(mWeekTitleIds[position]);
            //((ImageView)v).setImageResource(R.drawable.ic_launcher);

            w.setBackgroundColor(Color.rgb(255, 211, 155));

        }
        // 교시

        else {
            w = oldView;
        }

        return w;
    }






}

