package apps.sandesh.stockmarketapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Sandesh on 1/25/2017.
 */

public class NoInternetDialog extends DialogFragment{
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("To continue, please turn on the Internet Connection.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
