package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_ListeMusique:
                Log.i("DIM", "VOICI VOTRE LISTE DE MUSIQUE!");


                return true;

            case R.id.menu_Parametre:
                Log.i("DIM", "VOICI LES PARAMETRES!");


                return true;

            case R.id.menu_Interface_Principal:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}