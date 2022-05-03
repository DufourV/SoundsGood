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

public class AddNotesDialogue extends AppCompatDialogFragment {

    private EditText editNote;
    private AddNotesDialogue.dialogueListener2 listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addnotes_dialog, null);
        builder.setView(view);
        builder.setTitle("Choisissez le nombres de notes")
                .setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int nouveauNbNotes = Integer.parseInt(editNote.getText().toString());
                        listener.applyNewNotes(nouveauNbNotes);
                        editNote.setHint(String.valueOf(nouveauNbNotes));
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        editNote = view.findViewById(R.id.addNotes_input);
        return builder.create();
    }



    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (AddNotesDialogue.dialogueListener2) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "doit implementer le listener");
        }
    }

    public interface dialogueListener2 {
        void applyNewNotes(int nouveauNbNotes);
    }
}
