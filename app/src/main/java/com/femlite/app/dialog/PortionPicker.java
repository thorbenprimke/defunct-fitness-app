package com.femlite.app.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.femlite.app.model.Portion;

import java.util.List;

/**
 * Created by thorben2 on 12/6/15.
 */
public class PortionPicker extends DialogFragment {

    public interface PortionSelectedListener {
        void onPortionSelected(Portion portion);
    }

    private List<Portion> portions;
    private PortionSelectedListener clickListener;

    public PortionPicker() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence[] items = new CharSequence[portions.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = portions.get(i).getTitle() + " " + portions.get(i).getServingSize() + " " + portions.get(i).getCalories();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a portion size")
                .setItems(
                        items,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(
                                        getContext(),
                                        portions.get(which).getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                if (clickListener != null) {
                                    clickListener.onPortionSelected(portions.get(which));
                                }
                            }
                        });
        return builder.create();
    }

    public void setPortions(List<Portion> portions) {
        this.portions = portions;
    }

    public void setClickListener(PortionSelectedListener clickListener) {
        this.clickListener = clickListener;
    }
}
