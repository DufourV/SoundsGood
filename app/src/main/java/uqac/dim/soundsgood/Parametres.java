package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Parametres extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public int nbOfTracks;
    public int bpmActuel;
    public TextView bpmTextView;
    public String text;
    public int instrument = 0;
    public ArrayList<Integer> instrumentArray;
    public int spinnerPosition =0;
    private int TagMemorizer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres);


        if(getIntent().hasExtra("NB_Tracks")) {
            Bundle bundleTracks = getIntent().getExtras();
            nbOfTracks = bundleTracks.getInt("NB_Tracks");

            for(int i = 0; i < nbOfTracks; i++){

                TableLayout tableLayout = findViewById(R.id.TableauParametres);
                TableRow tableRow = new TableRow(this);

                TextView textView = new TextView(this);
                textView.setText(String.valueOf(i));
                tableRow.addView(textView);

                Spinner spinner = new Spinner(this);

                spinner.setTag(TagMemorizer);
                TagMemorizer++;

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Instruments, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(this);



                tableRow.addView(spinner);
                tableLayout.addView(tableRow);

                if(getIntent().hasExtra("Array")){
                    Bundle bundle = getIntent().getExtras();
                    instrumentArray = bundle.getIntegerArrayList("Array");

                    instrument = instrumentArray.get(i);
                    spinner.setSelection(instrument);  //spinner.setSelection(2); //permet de choisir la selection du spinner
                }

            }

            if (getIntent().hasExtra("BPM_Actuel")) {

                Bundle bundle = getIntent().getExtras();
                bpmActuel = bundle.getInt("BPM_Actuel");

                bpmTextView = findViewById(R.id.bpmParametre);
                bpmTextView.setText(String.valueOf(bpmActuel));
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
                resultIntent1.putIntegerArrayListExtra("resultArray", instrumentArray);
                setResult(RESULT_OK, resultIntent1);
                finish();

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        text = adapterView.getItemAtPosition(position).toString();

        if(text.equals("Piano")){
            instrument = 0;
        }
        if(text.equals("Guitare")){
            instrument = 1;
        }
        if(text.equals("Claves")){
            instrument = 2;
        }

        instrumentArray.set((Integer) ((Spinner)view.getParent()).getTag(), instrument);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}