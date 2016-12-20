package inf4041.inf4041_jais_demolon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Commander extends AppCompatActivity {

    public static final String COMMANDE_UPDATE = "inf4041.inf4041_jais_demolon.COMMANDE_UPDATE";

    // Déclaration de notre RV
    RecyclerView rv_commande = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander);

        IntentFilter intentFilter = new IntentFilter(COMMANDE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new CommandeUpdate(), intentFilter);
        GetCommandeService.startActionCommande(getApplicationContext());

        rv_commande = (RecyclerView) findViewById(R.id.rv_commande);
        rv_commande.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_commande.setAdapter(new CommandeAdapter());
    }

    public class CommandeUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("GetCommandeService", " " + getIntent().getAction());
            ((CommandeAdapter) rv_commande.getAdapter()).setNewCommande(getCommandeFromFile());
        }
    }

    public JSONArray getCommandeFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"commande.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer,"UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private class CommandeAdapter extends RecyclerView.Adapter<CommandeAdapter.CommandeHolder>{

        JSONArray commande = new JSONArray();

        @Override
        public CommandeAdapter.CommandeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_commande_element,parent,false);

            return new CommandeHolder(v);
        }

        @Override
        public void onBindViewHolder(CommandeHolder holder, int position) {
            try {
                holder.name.setText((commande.getJSONObject(position).getString("name")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return commande.length();
        }

        public void setNewCommande(JSONArray json){
            commande = json;
            notifyDataSetChanged();
        }

        private class CommandeHolder extends RecyclerView.ViewHolder{
            public TextView name;

            public CommandeHolder(View itemView){
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.rv_commande_element_name);
            }
        }
    }
}
