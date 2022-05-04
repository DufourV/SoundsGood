package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ListeEnregistrement extends AppCompatActivity {


    public ArrayList<Integer> instrumentArray;
    AppBD database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_enregistrement);

        database = AppBD.getDatabase(getApplicationContext());

        ArrayList<SongEntity> songsList =  (ArrayList<SongEntity>) database.dao().getSongs();

        for (int i = 0; i < songsList.size(); i++) {

            TableLayout tableLayout = findViewById(R.id.TableauEnregistrements);
            TableRow tableRow = new TableRow(this);

            TextView textView = new TextView(this);
            textView.setText(songsList.get(i).tracknom);
            tableRow.addView(textView);

            Button button = new Button(this);
            button.setTag(songsList.get(i).trackpath);
            button.setText(songsList.get(i).trackpath);

            tableRow.addView(button);
            tableLayout.addView(tableRow);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("resultPathChargement", (String) view.getTag());
                    setResult(RESULT_FIRST_USER, resultIntent);
                    finish();
                }
            });
        }
    }
}
