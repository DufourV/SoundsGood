package uqac.dim.soundsgood;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class BPMDialogue extends AppCompatDialogFragment {

    private EditText editBPM;
    private dialogueListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bpm_dialog, null);
        builder.setView(view);
        builder.setTitle("Choisissez un nouveau BPM")
                .setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int nouveauBPM = Integer.parseInt(editBPM.getText().toString());
                        listener.applyBPM(nouveauBPM);
                        editBPM.setHint(String.valueOf(nouveauBPM));
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        editBPM = view.findViewById(R.id.bpm_input);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (dialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "doit implementer le listener");
        }
    }

    public interface dialogueListener {
        void applyBPM(int nouveauBPM);
    }


}
