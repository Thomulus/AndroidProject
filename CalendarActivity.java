package com.bigfoot.bigfoot;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.sundeepk.compactcalendarview.domain.Event;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

public class CalendarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        SharedPreferences prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        int nextRecylingDate = prefs.getInt("first_recycling_date", Calendar.DAY_OF_MONTH);  //the day of the month of the next recycling day, defaults to today's date if not entered by user
        final String nextRecyclingType = prefs.getString("first_recycle_type", "Blue Bin"); //will be either "Blue Bin" or "Grey Bin", defaults to "Blue Bin" if not entered by user
        final String recylingDay = prefs.getString("recycle_day", "Tuesday"); // will be day of week (e.g. "Monday", "Tuesday", "Wednesday", ... ).
        ArrayList<String> daysOfWeek = new ArrayList<>();
        daysOfWeek.add("Thursday");
        daysOfWeek.add("Friday");
        daysOfWeek.add("Saturday");
        daysOfWeek.add("Sunday");
        daysOfWeek.add("Monday");
        daysOfWeek.add("Tuesday");
        daysOfWeek.add("Wednesday");


        //back button, worst case we can go
        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalendarActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });
        createNotificationChannel();
        int day = 0;
        for (int i = 0; i < daysOfWeek.size(); i++) {
            if (daysOfWeek.get(i).equals(recylingDay)) {
                day = i + 1;
                break;
            }
        }
        TextView text = findViewById(R.id.text_view_2);
        text.setText("On " + daysOfWeek.get((day+5)%7) + " after 6PM put out:");
        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        Event evGarbage;
        evGarbage = new Event(Color.rgb(255, 237, 209), 1541739600000L);
        compactCalendarView.addEvent(evGarbage);
        int c0;
        int c1;
        if (nextRecyclingType.equals("Blue Bin")) {
            c0 = Color.GRAY;
            c1 = Color.BLUE;
        } else {
            c0 = Color.BLUE;
            c1 = Color.GRAY;
        }
        //Toast.makeText(this, "DAY: " + day + " c0: " + c0, Toast.LENGTH_LONG).show();
        for (long i = 0; i < 17; i++) {

            evGarbage = new Event(Color.GREEN, 1541044800000L + (i * 604800000L) + (day * 86400000L));
            compactCalendarView.addEvent(evGarbage);
            if (i % 2 == 0) {
                evGarbage = new Event(c0, 1541044800000L + (i * 604800000L) + (day * 86400000L));
                compactCalendarView.addEvent(evGarbage);
            } else {
                evGarbage = new Event(c1, 1541044800000L + (i * 604800000L) + (day * 86400000L));
                compactCalendarView.addEvent(evGarbage);
            }
        }
        final int c00 = c0;
        final int days = day;
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            ImageView imgViewGrey = (ImageView) findViewById(R.id.imageView10);
            ImageView imgViewBlue = (ImageView) findViewById(R.id.imageView8);

            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                if (!events.isEmpty()) {
                    long timeString = events.get(0).getTimeInMillis() - 1541044800000L - (days * 86400000);
                    if (c00 == Color.GRAY) {
                        if (timeString % 1209600000 == 0) {
                            imgViewGrey.setVisibility(View.VISIBLE);
                            imgViewBlue.setVisibility(View.INVISIBLE);
                        } else if (timeString % 1209600000 == 604800000) {
                            imgViewGrey.setVisibility(View.INVISIBLE);
                            imgViewBlue.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (timeString % 1209600000 == 0) {
                            imgViewGrey.setVisibility(View.INVISIBLE);
                            imgViewBlue.setVisibility(View.VISIBLE);
                        } else if (timeString % 1209600000 == 604800000) {
                            imgViewGrey.setVisibility(View.VISIBLE);
                            imgViewBlue.setVisibility(View.INVISIBLE);
                        }
                    }
                        if (events.get(0).getTimeInMillis() == 1541739600000L) {
                            callNotification("Garbage Pick " + recylingDay, nextRecyclingType + " , Green & Black Bin");
                        }
                    }
                    else
                    {
                        imgViewGrey.setVisibility(View.INVISIBLE);
                        imgViewBlue.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void onMonthScroll (Date firstDayOfNewMonth){
                    Log.d("MainActivity", "Month was scrolled to: " + firstDayOfNewMonth);
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            switch (item.getItemId()) {
                case (R.id.action_schedule):
                    Intent i = new Intent(this, CalendarActivity.class);
                    startActivity(i);
                    return true;
                case R.id.action_facts:
                    return true;
                case R.id.action_previous:
                    Intent j = new Intent(this, ResultsActivity.class);
                    startActivity(j);
                    break;
                case R.id.action_settings:
                    Intent k = new Intent(this,FirstOpenActivity.class);
                    startActivity(k);
                    return true;
            }

            //noinspection SimplifiableIfStatement
            return super.onOptionsItemSelected(item);
        }



        public void createNotificationChannel () {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        public void callNotification (String title, String text){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1") //CHANNEL_ID
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setDefaults(Notification.DEFAULT_ALL);
            //.setPriority(Notification.PRIORITY_HIGH);

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

    }
