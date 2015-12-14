package kr.ac.ajou.media.sap6.activity;

/**
 * Created by mingeummaegbug on 15. 11. 15..
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import kr.ac.ajou.media.sap6.R;

/**
 * Created by mingeummaegbug on 15. 11. 15..
 */
public class WidjetFragment extends Fragment {

    private Button btnCapture;
    private Button btnShare;

    private View view;
    Calendar cal = Calendar.getInstance(); // 요일 함수
    int num = cal.get(Calendar.DAY_OF_WEEK)-1;
    String[] weekDay = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" }; // 요일 배열

    String today = weekDay[num]; // today 변수

    public WidjetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_widget,container,false);

        btnCapture = (Button) view.findViewById(R.id.captureBtn);
        btnShare = (Button) view.findViewById(R.id.shareBtn);

        GridView gridView = (GridView) view.findViewById(R.id.widget);
        gridView.setAdapter(new WidgetAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
        TextView day = (TextView) view.findViewById(R.id.day);



        day.setText("오늘은 "+today+" 입니다");

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.buildDrawingCache();
                Bitmap captureView = container.getDrawingCache();
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/capture.jpeg");
                    captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "Captured!", Toast.LENGTH_LONG).show();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                File files = new File(Environment.getExternalStorageDirectory().toString() + "/capture.jpeg");





                if(files.exists())  //파일유무확인

                {

                    Intent intentSend  = new Intent(Intent.ACTION_SEND);

                    intentSend.setType("image/*");


                    intentSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory().toString() + "/capture.jpeg"));




                    startActivity(Intent.createChooser(intentSend, "공유")); //공유하기 창 띄우기

                }else{

                    Toast.makeText(getActivity(), "저장을 먼저 해주세요", Toast.LENGTH_LONG).show();

                }



            }
        });

        return view;

    }

}
