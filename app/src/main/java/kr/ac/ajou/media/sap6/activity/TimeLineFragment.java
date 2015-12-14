package kr.ac.ajou.media.sap6.activity;

/**
 * Created by mingeummaegbug on 15. 11. 15..
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import kr.ac.ajou.media.sap6.R;
import kr.ac.ajou.media.sap6.activity.handler.MyClient;
import kr.ac.ajou.media.sap6.activity.handler.SQLiteHandler;

/**
 * Created by mingeummaegbug on 15. 11. 15..
 */
public class TimeLineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    public TimeLineFragment() {
        // Required empty public constructor
    }
    private ListView mListView;
    private List<UserData> userdataList = new ArrayList<UserData>();
    //private Lis sortlist = new ArrayList<>();
    private ListDataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    Context mContext;

    SQLiteHandler db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        ImageButton FAB = (ImageButton) view.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), TimeLineWriteActivity.class);
                startActivity(i);
            }
        });



        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);


        mListView = (ListView) view.findViewById(R.id.list);

        adapter = new ListDataAdapter(getActivity(), userdataList);

        mListView.setAdapter(adapter);

        mContext = getActivity().getApplicationContext();


        //float action button
        return view;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetctTimeline();
                                    }
                                }
        );

    }

    @Override
    public void onRefresh() {
        fetctTimeline();
    }

    private void fetctTimeline() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);



        MyClient.get("/timelines", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {


                try {
                    for (int i = 0; i < timeline.length(); i++) {
                        JSONObject firstEvent = (JSONObject) timeline.get(i);
                        String name = firstEvent.getString("user_id");
                        String context = firstEvent.getString("context");


                        UserData tempuserData = new UserData();
                        tempuserData.setName(name);
                        tempuserData.setContext(context);
                        userdataList.add(tempuserData);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                swipeRefreshLayout.setRefreshing(false);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String
                    responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "onFailure:" + statusCode, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        });




    }

    private void toggleButtonImage(){


    }



}