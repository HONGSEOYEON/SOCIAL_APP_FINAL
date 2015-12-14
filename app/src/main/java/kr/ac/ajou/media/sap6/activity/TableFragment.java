package kr.ac.ajou.media.sap6.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import kr.ac.ajou.media.sap6.R;

/**
 * Created by mingeummaegbug on 15. 11. 15..
 */
public class TableFragment extends Fragment {
    private View view;
    GridView GridSchedule;
    ClassAdapter adapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_class,container,false);
        GridView gridView = (GridView) view.findViewById(R.id.schedule);
        gridView.setAdapter(new ClassAdapter(view.getContext())); // uses the view to get the context instead of getActivity().

        //gridView.setOnItemLongClickListener(onItemLongClickEvent);



        return view;

    }




}



