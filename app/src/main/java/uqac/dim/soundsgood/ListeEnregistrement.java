package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ListeEnregistrement extends AppCompatActivity {


    public ArrayList<Integer> instrumentArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_enregistrement);


        if (getIntent().hasExtra("ListeNotes")) {
            Bundle bundle = getIntent().getExtras();
            instrumentArray = bundle.getIntegerArrayList("ListeNotes");


            for (int i = 0; i < 3; i++) {

                TableLayout tableLayout = findViewById(R.id.TableauEnregistrements);
                TableRow tableRow = new TableRow(this);

                TextView textView = new TextView(this);
                textView.setText("Enregistrement" + String.valueOf(i));
                tableRow.addView(textView);

                Button button = new Button(this);
                button.setText(textView.getText());

                tableRow.addView(button);
                tableLayout.addView(tableRow);

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent resultIntent = new Intent();
                        resultIntent.putIntegerArrayListExtra("resultPathChargement", instrumentArray);
                        setResult(RESULT_FIRST_USER, resultIntent);
                        finish();

                    }
                });
            }
        }


    }
}
