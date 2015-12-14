package kr.ac.ajou.media.sap6.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import kr.ac.ajou.media.sap6.R;
import kr.ac.ajou.media.sap6.activity.handler.MyClient;
import kr.ac.ajou.media.sap6.activity.handler.SQLiteHandler;

/**
 * Created by mingeummaegbug on 15. 11. 22..
 */
public class ClassAdapter extends BaseAdapter {
    String tabletime="";
    String tableTitle="";
    String tableClass="";
    String testtest = "";
    boolean fflag=true;

    EditText edittableTime,edittableTitle,edittableClass;
    TextView tvtableTime  ,tvtableTitle,tvtableClass;

    ClassData tempClassData = new ClassData();
    List<ClassData> old_class = new ArrayList<>();

    SQLiteHandler db;

    int rcount =0;
    Context mContext;
    int count = 48;
    String[] mWeekTitleIds ={
            "시간",
            "월",
            "화",
            "수",
            "목",
            "금",


    };


    public ClassAdapter(Context context) {
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

    public View getView(final int position, final View oldView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v=null;

        db = new SQLiteHandler(mContext);

        HashMap<String, String> user = db.getUserDetails();
        final String user_id = user.get("name");

        //    update((TextView)v,tableTitle,position);


        //TextView context = (TextView) convertView.findViewById(R.id.time_context);




        if(oldView == null &&fflag==true  )
        {
            v = new ImageView(mContext);
           v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 320));





        }

        else if(position%6==0 && position>5 &&fflag==true ){
            v = new TextView(mContext);
            //if(position==0) ((TextView)v).setText("");
            ((TextView)v).setGravity(Gravity.CENTER);
            ((TextView)v).setPadding(0,70,0,0);
            if(position==6) ((TextView)v).setText("A");
            else  if(position==12) ((TextView)v).setText("B");
            else  if(position==18) ((TextView)v).setText("C");
            else  if(position==24) ((TextView)v).setText("D");
            else  if(position==30) ((TextView)v).setText("E");
            else  if(position==36) ((TextView)v).setText("F");
            else  if(position==42) ((TextView)v).setText("G");

            ((TextView)v).setTextColor(Color.BLACK);


        }

        else if (position < 6 &&fflag==true ) {
            v = new TextView(mContext);
            v.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
            ((TextView)v).setGravity(Gravity.CENTER);
            ((TextView)v).setText(mWeekTitleIds[position]);
            ((TextView)v).setPadding(0, 10, 0, 10);
            //((ImageView)v).setImageResource(R.drawable.ic_launcher);
            ((TextView)v).setTextColor(Color.WHITE);
            v.setBackgroundColor(Color.rgb(0,0,0));

        }

        else if (position%6!=0 && position >= 6 && position < count && fflag==true  ) {
            v = new TextView(mContext);
            //((TextView)v).setText("**");
            ((TextView) v).setHeight(200);
            v.setBackgroundColor(Color.WHITE);

            final View finalV = v;


            MyClient.get("/classtables", null, new JsonHttpResponseHandler() {

                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

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
                                finalV.setBackgroundColor(Color.rgb(114, 173, 58));
                                ((TextView) finalV).setText(tempclassdata.getClass_name());

                                //Toast.makeText(finalV.getContext(), user_id + "s" + class_name + class_position, Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(finalV.getContext(), tempclassdata.getClass_name() + "s", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(finalV.getContext(), "Fail", Toast.LENGTH_SHORT).show();

                }

            });

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    final View dialogView = (View) View.inflate((Activity) mContext, R.layout.activity_addclass, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder((Activity) mContext);
                    dlg.setTitle("시간표선택");
                    dlg.setView(dialogView);
                    dlg.setPositiveButton("확인",

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fflag = true;

                                    edittableTitle = (EditText) dialogView.findViewById(R.id.editTitle);
                                    tableTitle = edittableTitle.getText().toString();
                                    //    update((TextView)v,tableTitle,position);

                                    v.setBackgroundColor(Color.rgb(114, 173, 58));
                                    ((TextView) v).setText(tableTitle);
                                    rcount = 0;
                                    fflag = false;


                                    // String user_id = "hi";
                                    //Toast.makeText(TimeLineWriteActivity.this, "userid? : " + user_id, Toast.LENGTH_SHORT).show();
                                    String class_name = tableTitle;


                                    RequestParams params = new RequestParams();
                                    params.add("user_id", user_id);
                                    params.add("class_name", class_name);
                                    params.add("position", String.valueOf(position)); //int 아니어도되나?

                                   // Toast.makeText(v.getContext(),ㅅ, Toast.LENGTH_SHORT).show();


                                    MyClient.post("/classtables", params, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            // Toast.makeText(TimeLineWriteActivity.this, "Created" + statusCode, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            //Toast.makeText(v.getContext(), "Failed!" + statusCode, Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                    findTablaTime(position);

                                }
                            });


                        dlg.setNegativeButton("취소",null);
                        dlg.show();


                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    final View dialogView = View.inflate(mContext, R.layout.activity_addclass, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

                    // android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);
                    alert.setTitle("삭제메뉴");
                    alert.setMessage("수업을 삭제하시겠습니까?");
                    alert.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setText("");

                        }
                    });
                    alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();

                    return true;
                }

            });


            //  Log.e("******************count", "" + rcount);
            rcount++;
            //  Log.e("???????????????????? ㅡㅡ", "" + tableTitle);
            // ((TextView)v).setText(tableTitle);

        }
        else {
            v = oldView;




        }





        return v;
    }


    private void findTablaTime(int position){

        int s_hour=0;
        int s_min=0;
        int e_hour=0;
        int e_min=0;

        int day=0;

        int whatTime=position/5;
        int when=position%6; // 1월 2화 3수 4목 5금

        if(whatTime==1){ //A교시
            Log.e("********************  A교시", "" + whatTime);

        }

        else if(whatTime==2){ //B교시
            Log.e("********************  B교시", "" + whatTime);

        }




    }





}