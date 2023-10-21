package com.example.myapplication.adapter;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AddAndUpdateHikeActivity;
import com.example.myapplication.HikeActivity;
import com.example.myapplication.ObservationActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.MyDatabaseHelper;
import com.example.myapplication.database.entity.Hike;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HikeRecycleViewAdapter extends RecyclerView.Adapter<HikeRecycleViewAdapter.MyViewHolder> {

    Context context;
    private ArrayList<Hike> adapterData;

    public HikeRecycleViewAdapter(Context context, ArrayList<Hike> adapterData) {
        this.context = context;
        this.adapterData = adapterData;
    }

    @NonNull
    @Override
    public HikeRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hike_recycle_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeRecycleViewAdapter.MyViewHolder holder, int position) {
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);

        holder.txt_hike_id.setText(String.valueOf(adapterData.get(position).getHike_id()));
        holder.txt_hike_name.setText(String.valueOf(adapterData.get(position).getHike_name()));
        holder.txt_location.setText(String.valueOf(adapterData.get(position).getHike_location()));
        holder.txt_hiker_number.setText(String.valueOf(adapterData.get(position).getHiker_number()));
        holder.txt_date_of_hike.setText(dateFormat.format(adapterData.get(position).getDate_of_hike()));
        switch (String.valueOf(adapterData.get(position).getHike_level())){
            case "Very easy":
                holder.txt_level_of_difficulty.setTextColor(ContextCompat.getColor(context, R.color.levelVeryEasy));
                break;
            case "Easy":
                holder.txt_level_of_difficulty.setTextColor(ContextCompat.getColor(context, R.color.levelEasy));
                break;
            case "Medium":
                holder.txt_level_of_difficulty.setTextColor(ContextCompat.getColor(context, R.color.levelMedium));
                break;
            case "Hard":
                holder.txt_level_of_difficulty.setTextColor(ContextCompat.getColor(context, R.color.levelHard));
                break;
            case "Very hard":
                holder.txt_level_of_difficulty.setTextColor(ContextCompat.getColor(context, R.color.levelVeryHard));
                break;
        }
        holder.txt_level_of_difficulty.setText(String.valueOf(adapterData.get(position).getHike_level()));
        holder.txt_length_of_hiker.setText(String.valueOf(adapterData.get(position).getLength_of_hike())+"Km");
        holder.img_observation_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ObservationActivity.class);
                intent.putExtra("hikeId", adapterData.get(position).getHike_id());
                intent.putExtra("hikeName",  adapterData.get(position).getHike_name());
                context.startActivity(intent);
            }
        });


        //handle onclick event
        holder.img_hike_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAndUpdateHikeActivity.class);
                intent.putExtra("function", "UPDATE");
                intent.putExtra("hikeId", adapterData.get(position).getHike_id());
                intent.putExtra("hikeName", adapterData.get(position).getHike_name());
                intent.putExtra("hikeLocation", adapterData.get(position).getHike_location());
                String myFormat="MM/dd/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
                intent.putExtra("dateOfHike", dateFormat.format(adapterData.get(position).getDate_of_hike()));
                intent.putExtra("parkingAvailable", adapterData.get(position).isParking_available()+"");
                intent.putExtra("hikeLength", adapterData.get(position).getLength_of_hike()+"");
                intent.putExtra("levelOfDifficulty", adapterData.get(position).getHike_level());
                intent.putExtra("hikeDescription", adapterData.get(position).getHike_description());
                intent.putExtra("hikerNumber", adapterData.get(position).getHiker_number()+"");
                intent.putExtra("hikeType", adapterData.get(position).getType_of_hike());
                context.startActivity(intent);
            }
        });

        // handle on long click event
        holder.hikeItemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String hikeName = adapterData.get(position).getHike_name();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove "+hikeName+"?");
                builder.setMessage("Are you sure to remove "+hikeName+"?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
                        long result = myDatabaseHelper.deleteOneHike(adapterData.get(position).getHike_id()+"");
                        if (result == -1){
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(context, HikeActivity.class);
                            Toast.makeText(context, "Remove successfully", Toast.LENGTH_SHORT).show();
                            context.startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return adapterData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_hike_id, txt_hike_name, txt_location, txt_date_of_hike, txt_hiker_number, txt_level_of_difficulty, txt_length_of_hiker;
        ImageView img_observation_direction, img_hike_detail;
        LinearLayout hikeItemLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_hike_id = itemView.findViewById(R.id.textview_hike_id);
            txt_hike_name = itemView.findViewById(R.id.textview_hike_name);
            txt_location = itemView.findViewById(R.id.textview_location);
            txt_date_of_hike = itemView.findViewById(R.id.textview_date_of_hike);
            txt_hiker_number = itemView.findViewById(R.id.textview_hiker_number);
            txt_level_of_difficulty = itemView.findViewById(R.id.textview_level_of_difficulty);
            txt_length_of_hiker = itemView.findViewById(R.id.textview_length_of_hiker);
            img_observation_direction = itemView.findViewById(R.id.image_observation_direction);
            img_hike_detail = itemView.findViewById(R.id.image_hike_detail);
            hikeItemLayout = itemView.findViewById(R.id.hikeItemLayout);
        }
    }


}
