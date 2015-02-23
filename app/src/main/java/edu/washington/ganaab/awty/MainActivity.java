package edu.washington.ganaab.awty;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.AlarmManager.RTC_WAKEUP;

public class MainActivity extends Activity {
    private PendingIntent pendingIntent;
    int interval = 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        final EditText editText3 = (EditText) findViewById(R.id.editText3);

        /* Retrieve a PendingIntent that will perform a broadcast */
        final Intent alarmIntent = new Intent(this, AlarmReceiver.class);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("START")) {
                    if(!editText.getText().toString().matches("")&& editText2.getText().toString().length() > 9 && !editText2.getText().toString().matches("") && !editText3.getText().toString().matches("") && editText3.getText().toString().charAt(0) != '-' && !editText3.getText().toString().equals("0")){
                        button.setText("CANCEL");
                        interval = 1000 * Integer.parseInt(editText3.getText().toString());
                        alarmIntent.putExtra("value", editText.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                        start();
                    }
                } else {
                    button.setText("START");
                    cancel();
                }
            }
        });
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        manager.setInexactRepeating(RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

}
