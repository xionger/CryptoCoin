package com.xiongxh.cryptocoin.coins;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiongxh.cryptocoin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCoinDialog extends DialogFragment{

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.dialog_add_coin_symbol)
    EditText coin;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") final View addCoinDialogBody =
                inflater.inflate(R.layout.dialog_add_coin, null);

        ButterKnife.bind(this, addCoinDialogBody);

        coin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                addCoin();
                return true;
            }
        });

        final AlertDialog dialog = new AlertDialog.Builder( getActivity() )
                .setView(addCoinDialogBody)
                .setMessage(getString(R.string.search_symbol))
                .setPositiveButton(getString(R.string.dialog_add), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addCoin();
                    }
                })
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .create();

        final Window dialogWindow = dialog.getWindow();
        if (null != dialogWindow) {
            dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

        final Button addButton = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        addButton.setEnabled(false);

        coin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // We do not need to do anything here. It is required by the interface.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // We do not need to do anything here. It is required by the interface.
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    addButton.setEnabled(false);
                } else {
                    addButton.setEnabled(true);
                }
            }
        });
    }

    private void addCoin() {
        final Activity parent = getActivity();
        if (parent instanceof CoinsActivity) {
            ((CoinsActivity) parent).addCoin(coin.getText().toString());
        }
        dismissAllowingStateLoss();
    }
}
