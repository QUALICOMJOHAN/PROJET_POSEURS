package com.example.dev_qualicom.projet_poseurs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class planning extends AppCompatActivity {

    String id_equipe;
    String nom_equipe;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Pose> poses = new ArrayList<Pose>();
    RecyclerView calendrier;
    Calendar cal;




    LinearLayoutManager llm = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        //Recuperation variable

        Intent i = getIntent();
        id_equipe = i.getExtras().getString("id_equipe");
        nom_equipe = i.getExtras().getString("nom_equipe");

        cal = Calendar.getInstance();

        //vendredi = 6
        //lundi = 2

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date currentDay = cal.getTime();
        String currentDayString = df.format(currentDay);

        try {
            currentDay = df.parse(currentDayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Pose p = new Pose();
        p.setName((String) DateFormat.format("EEEE", currentDay));
        p.setX_date_pose(new Timestamp(currentDay));

        poses.add(p);

        int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        for (int j = currentDayOfWeek; j < 6; j++) {

            currentDay = new Date(currentDay.getTime() + (1000 * 60 * 60 * 24));
            currentDayString = df.format(currentDay);

            p = new Pose();
            p.setName((String) DateFormat.format("EEEE", currentDay));
            p.setX_date_pose(new Timestamp(currentDay));

            poses.add(p);

        }

        currentDay = new Date(currentDay.getTime() + (2000 * 60 * 60 * 24));

        for (int k = 2; k <= 6; k++) {

            currentDay = new Date(currentDay.getTime() + (1000 * 60 * 60 * 24));
            currentDayString = df.format(currentDay);

            p = new Pose();
            p.setName((String) DateFormat.format("EEEE", currentDay));
            p.setX_date_pose(new Timestamp(currentDay));

            poses.add(p);

        }

        db.collection("Poses").orderBy("x_date_pose").whereEqualTo("equipe", id_equipe).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Pose pose = document.toObject(Pose.class);

                                Log.e("TESTTT", pose.getX_date_pose().toString());
                                Log.e("TESTTT", poses.get(0).getX_date_pose().toString());

                                if(pose.getX_date_pose().getSeconds() > poses.get(0).getX_date_pose().getSeconds()) {
                                    poses.add(pose);
                                }
                            }

                            Collections.sort(poses, new Comparator<Pose>() {
                                @Override public int compare(Pose p1, Pose p2) {
                                    return (int) (p1.getX_date_pose().getSeconds() - p2.getX_date_pose().getSeconds()); // Ascending
                                }

                            });

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    calendrier = (RecyclerView) findViewById(R.id.calendrier);

                                    CalendrierAdapter docsAdapter = new CalendrierAdapter();
                                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                                    calendrier.setLayoutManager(llm);
                                    calendrier.setAdapter(docsAdapter);

                                }
                            });

                       } else {
                            Log.d("TEST", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    class CalendrierAdapter extends RecyclerView.Adapter<CalendrierHolder> {

        @Override
        public CalendrierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View vue = getLayoutInflater().inflate(R.layout.activity_list_pose, null);
            return new CalendrierHolder(vue);
        }

        @Override
        public void onBindViewHolder(CalendrierHolder holder, int position) {

            Pose pose = poses.get(position);
            holder.bind(pose);

        }

        @Override
        public int getItemCount() {
            return poses.size();
        }
    }

    class CalendrierHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView date;

        public CalendrierHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

        }

        private String getDate(long time, String format) {
            Calendar cal = Calendar.getInstance(Locale.FRANCE);
            cal.setTimeInMillis(time);
            String date = DateFormat.format(format, cal).toString();
            return date;
        }

        public void bind(Pose pose) {

            if(pose.getEquipe() == null){
                title.setText(getDate(pose.getX_date_pose().getSeconds() * 1000, "dd/MM/yyyy"));
                this.itemView.setBackgroundColor(Color.parseColor("#dddddd"));
                title.setTextColor(Color.parseColor("#ffffff"));
                date.setTextColor(Color.parseColor("#ffffff"));
            }else{
                title.setText(pose.getName());
            }

            if(pose.getEquipe() == null){
                date.setText(pose.getName().toUpperCase());
            }else{
                date.setText(getDate(pose.getX_date_pose().getSeconds() * 1000, "HH:mm"));
                this.itemView.setId(poses.indexOf(pose));
                this.itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {

            Intent i = new Intent(planning.this, demande_intervention.class);

            PoseSingleton.getInstance().init(poses.get(view.getId()));

            startActivity(i);

        }
    }
}
