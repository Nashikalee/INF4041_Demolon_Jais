package inf4041.inf4041_jais_demolon;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btCommander, btToaster, btNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération des boutons de la première activité
        btCommander = (Button) findViewById(R.id.btCommander);
        btToaster = (Button) findViewById(R.id.btToaster);
        btNotif = (Button) findViewById(R.id.btNotif);

    }

    public void Commander(View btCommander){
        Intent myIntent = new Intent(this, Commander.class);

        startActivity(myIntent);
    }

    public void Horaire(View btToaster){
    Toast.makeText(getApplicationContext(), getString(R.string.bienvenue), Toast.LENGTH_SHORT).show();
    }

    public void Notification(View btNotif){
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Titre Notif")
                .setContentText("Text Notif");

        NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(13, builder.build());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
