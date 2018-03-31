package com.xiongxh.cryptocoin.coins;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xiongxh.cryptocoin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AckDialog extends DialogFragment{
//    public static void show(AppCompatActivity context) {
//        AckDialog dialog = new AckDialog();
//        dialog.show(context.getSupportFragmentManager(), "[ABOUT_DIALOG]");
//    }
//    @SuppressWarnings("WeakerAccess")
//    @BindView(R.id.tv_ack1)
//    TextView ackView1;
//    @BindView(R.id.tv_ack2)
//    TextView ackView2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final LayoutInflater inflater = LayoutInflater.from(getActivity());
//        @SuppressLint("InflateParams") final View ackDialogBody =
//                inflater.inflate(R.layout.dialog_acknowledgement, null);
//
//        ButterKnife.bind(this, ackDialogBody);

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
