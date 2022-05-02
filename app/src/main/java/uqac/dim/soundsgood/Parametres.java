package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class Parametres extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public int nbOfTracks;
    public int bpmActuel;
    public TextView bpmTextView;
    public String text;
    public int instrument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres);



        if(getIntent().hasExtra("NB_Tracks")) {
            //nbOfTracks = getIntent().getIntExtra("NB_Tracks", 0);
            Bundle bundleTracks = getIntent().getExtras();
            nbOfTracks = bundleTracks.getInt("NB_Tracks");

            for(int i = 1; i <= nbOfTracks; i++){

                TableLayout tableLayout = findViewById(R.id.TableauParametres);
                TableRow tableRow = new TableRow(this);

                TextView textView = new TextView(this);
                textView.setText(String.valueOf(i));
                tableRow.addView(textView);


                Spinner spinner = new Spinner(this);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Instruments, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(this);

                tableRow.addView(spinner);

                tableLayout.addView(tableRow);

            }

                if (getIntent().hasExtra("BPM_Actuel")) {

                    Bundle bundle = getIntent().getExtras();
                    bpmActuel = bundle.getInt("BPM_Actuel");

                    bpmTextView = findViewById(R.id.bpmParametre);
                    bpmTextView.setText(String.valueOf(bpmActuel));
/*
                    Spinner spinner = findViewById(R.id.Spinner1);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Instruments, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(this);



 */

                }
       }

        Button button = findViewById(R.id.BoutonRetour);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(text.equals("Piano")){
                    instrument = 0;
                }
                if(text.equals("Guitare")){
                    instrument = 1;
                }
                else{
                    instrument = 2;
                }
                Intent resultIntent1 = new Intent();
                resultIntent1.putExtra("result", instrument);
                setResult(RESULT_OK, resultIntent1);
                finish();

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}