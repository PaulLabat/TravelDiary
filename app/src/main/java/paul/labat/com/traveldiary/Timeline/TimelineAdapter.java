package paul.labat.com.traveldiary.Timeline;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import paul.labat.com.traveldiary.R;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private List<TimelineItem> entries;


    public TimelineAdapter(Context context){
        super();
        entries = new ArrayList<>();
        TimelineItem item;
        for(String name : context.fileList()){
            Log.d("TextEditor", "open file : "+name);
            item = new TimelineItem();
            FileInputStream inputStream;
            String tmp="";

            try {
                inputStream = context.openFileInput(name);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf8"));
                String str;
                while ((str = br.readLine()) != null) {
                    tmp += str;
                }
                inputStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(tmp);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(getClass().getName(),"Could not parse json file: "+ name);
                jsonObject = null;
            }

            if(jsonObject != null){

                try {
                    JSONObject dataObject = jsonObject.getJSONObject("Data");
                    item.setSummary(dataObject.getString("Text"));

                    dataObject = jsonObject.getJSONObject("System");

                    Timestamp timeStamp = new Timestamp(dataObject.getLong("Date"));
                    Date date = new Date(timeStamp.getTime());
                    SimpleDateFormat sdf;

                    if(TimeZone.getTimeZone(dataObject.getString("TimeZone")).inDaylightTime(date)){
                        sdf = new SimpleDateFormat("kk:mm");
                        sdf.setTimeZone(TimeZone.getTimeZone(dataObject.getString("TimeZone")));
                    }else{
                        sdf = new SimpleDateFormat("kk:mm", Locale.getDefault());
                    }

                    item.setDayHour(sdf.format(date));
                    sdf = new SimpleDateFormat("ccc");
                    item.setDayString(sdf.format(date));
                    sdf = new SimpleDateFormat("d");
                    item.setDayNumber(sdf.format(date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                entries.add(item);


            }


        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimelineItem item = entries.get(position);
        holder.summary.setText(item.getSummary());
        holder.address.setText(item.getLocation());
        holder.dayString.setText(item.getDayString());
        holder.dayNumber.setText(item.getDayNumber());
        holder.dayHour.setText(item.getDayHour());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dayNumber;
        public TextView dayString;
        public TextView dayHour;
        public TextView summary;
        public TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            dayHour =(TextView)itemView.findViewById(R.id.item_hour);
            dayNumber =(TextView)itemView.findViewById(R.id.item_day_number);
            dayString =(TextView)itemView.findViewById(R.id.item_day_string);

            summary = (TextView)itemView.findViewById(R.id.item_summary);
            address = (TextView)itemView.findViewById(R.id.item_address);

        }
    }
}
