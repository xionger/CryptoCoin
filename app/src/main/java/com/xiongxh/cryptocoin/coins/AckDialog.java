package com.xiongxh.cryptocoin.coins;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.xiongxh.cryptocoin.R;

public class AckDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(R.string.acknowledgements)
                .setNegativeButton(R.string.close, null)
                .create();

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
