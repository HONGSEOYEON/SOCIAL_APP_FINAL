package kr.ac.ajou.media.sap6.activity;

/**
 * Created by mingeummaegbug on 15. 12. 9..
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kr.ac.ajou.media.sap6.R;

public class ListDataAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<UserData> userdata;


    public ListDataAdapter(Activity activity, List<UserData> userdata){
        this.activity = activity;
        this.userdata = userdata;
    }
    @Override
    public int getCount() {
        return userdata.size();
    }

    @Override
    public Object getItem(int position) {
        return userdata.get(position);
    }

    @Override
    public long getItemId(int position) { return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_item,null);

        TextView name = (TextView) convertView.findViewById(R.id.time_name);

        TextView context = (TextView) convertView.findViewById(R.id.time_context);

        UserData d = userdata.get(position);

        name.setText(d.getName());
        context.setText(d.getContext());

        return convertView;
    }
}