package com.example.magic.taskgithubrepos.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.magic.taskgithubrepos.R;
import com.example.magic.taskgithubrepos.data.Repository;

/**
 * Created by Magician on 12/26/2017.
 * Show Html For more Information
 */

public class ChoiceDialog extends DialogFragment {
   // private Repository mRepos;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //TODO: Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //get data from argument
        final String repos_page_url = getArguments().getString(getString(R.string.repos_page_key));
        final String owner_page_url = getArguments().getString(getString(R.string.owner_page_key));
        getArguments().getString("owner_page_url");
        //Create the custom layout using the LayoutInflater class
        // View layout = inflater.inflate(R.layout.custom_dialog_layout, null);
        //builder.setView(layout);
        //TODO: Set builder properties
        builder.setTitle("Choose Html Page:")
                .setItems(R.array.list_of_html_page, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url = null;
                        switch (which) {
                            case 0:
                                url = repos_page_url;

                                break;
                            case 1:
                                url = owner_page_url;
                                break;
                        }
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
        return builder.create();
    }


}