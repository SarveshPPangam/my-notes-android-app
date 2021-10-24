package com.famt.mynotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Random;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Note> notesList;
    DBHelper db;

    int[] colors = {
            Color.parseColor("#FFADAD"),
            Color.parseColor("#FFD6A5"),
            Color.parseColor("#FDFFB6"),
            Color.parseColor("#CAFFBF"),
            Color.parseColor("#9BF6FF"),
            Color.parseColor("#A0C4FF"),
            Color.parseColor("#BDB2FF"),
            Color.parseColor("#FFC6FF")
    };
    Random generator = new Random();

    public MyAdapter(Context context, ArrayList<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        db = new DBHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleOutput.setText(wrapTitle((note.getTitle())));
        holder.descriptionOutput.setText(wrapDescription(note.getDescription()));

        String formatedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formatedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("DELETE")) {
                            //delete the note
                            System.out.println(note.getId());
                            db.deletedata(note.getId());
                            Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(context, MainActivity.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(myIntent);
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });


        holder.itemView.setBackgroundColor(colors[(generator).nextInt(colors.length)]);

        holder.itemView.setOnClickListener(v -> {
                    Note note1 = notesList.get(position);
                    Intent i = new Intent(context, ViewNoteActivity.class);
                    i.putExtra("id", note1.getId());
                    i.putExtra("title", note1.getTitle());
                    i.putExtra("description", note1.getDescription());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
        );
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public String wrapTitle(String title) {
        int limit = 20;
        int length = title.length();
        if (length > limit)
            return (title.substring(0, limit) + "......").replaceAll("[\\t\\n\\r]+", " ");
        return title;
    }

    public String wrapDescription(String description) {
        int limit = 40;
        int length = description.length();
        if (length > limit)
            return (description.substring(0, limit) + "......").replaceAll("[\\t\\n\\r]+", " ");
        return description;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.timeitem);
            descriptionOutput = itemView.findViewById(R.id.descriptionitem);
            timeOutput = itemView.findViewById(R.id.timeitem);
        }
    }


}
