package paul.labat.com.traveldiary.Timeline;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import paul.labat.com.traveldiary.R;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private List<TimelineItem> entries;


    public TimelineAdapter(){
        super();
        entries = new ArrayList<>();
        TimelineItem item = new TimelineItem();
        item.setLocation("12 rue du vercor");
        item.setDayHour("11:20");
        item.setDayNumber("22");
        item.setDayString("mer");
        item.setSummary("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

        entries.add(item);



        item = new TimelineItem();
        item.setLocation("22 avenue des jeux olympiques");
        item.setDayHour("14:53");
        item.setDayNumber("11");
        item.setDayString("jeu");
        item.setSummary("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");

        entries.add(item);

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
