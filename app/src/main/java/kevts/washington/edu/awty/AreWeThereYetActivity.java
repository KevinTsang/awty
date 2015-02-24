package kevts.washington.edu.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AreWeThereYetActivity extends Activity {

    private static final int ALARM_ID = 3333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_we_there_yet);
        final Button button = (Button)findViewById(R.id.button);
        Intent activeIntent = new Intent(AreWeThereYetActivity.this, AreWeThereYetReceiver.class);
        boolean activeAlarm = (PendingIntent.getBroadcast(AreWeThereYetActivity.this, ALARM_ID,
                activeIntent, PendingIntent.FLAG_NO_CREATE) != null);
        if (activeAlarm) {
            button.setText("Stop");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(AreWeThereYetActivity.this, AreWeThereYetReceiver.class);
                EditText messageEntry = (EditText)findViewById(R.id.message);
                EditText phoneNumberEntry = (EditText)findViewById(R.id.phoneNumber);
                intent.putExtra("message", messageEntry.getText().toString());
                intent.putExtra("phoneNumber", phoneNumberEntry.getText().toString());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AreWeThereYetActivity.this,
                        ALARM_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (button.getText().equals("Start")) {
                    if (validate()) {
                        long currentTime = System.currentTimeMillis();
                        EditText intervalEntry = (EditText)findViewById(R.id.interval);
                        int interval = Integer.parseInt(intervalEntry.getText().toString()) * 60000;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentTime, interval, pendingIntent);
                        button.setText("Stop");
                    }
                }
                else {
                    button.setText("Start");
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }
            }
        });
    }

    public boolean validate() {
        EditText message = (EditText)findViewById(R.id.message);
        EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        EditText interval = (EditText)findViewById(R.id.interval);
        if (message.getText().length() == 0) {
            return false;
        } else if (phoneNumber.length() == 0) {
            return false;
        } else {
            try {
                if (Integer.parseInt(interval.getText().toString()) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    }
}
