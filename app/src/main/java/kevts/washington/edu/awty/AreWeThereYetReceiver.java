package kevts.washington.edu.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AreWeThereYetReceiver extends BroadcastReceiver {
    public AreWeThereYetReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        Toast.makeText(context, phoneNumber + ": " + message, Toast.LENGTH_SHORT).show();
    }
}
