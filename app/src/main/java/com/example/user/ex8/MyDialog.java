package com.example.user.ex8;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by User on 12/16/2016.
 */

public class MyDialog extends DialogFragment {
    private int requestCode = -1;
    final public static int EXIT_DIALOG = 1;
    final public static int PRECISION = 2;
    private ResultsListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.requestCode = getArguments().getInt("requestCode");
        if(requestCode==EXIT_DIALOG) {
            return buildExitDialog().create();
        }
        else
        {
            return buildPreciionDialog().create();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        try
        {
            this.listener = (ResultsListener) activity;
        }catch(ClassCastException e)
        {
            String str = getResources().getString(R.string.host);
            throw new ClassCastException(str);
        }
        super.onAttach(activity);
    }
    /*
    @Override
    public void onAttach(Context context)
    {

        super.onAttach(context);
    }
*/
    @Override
    public void onDetach() {

        this.listener = null;
        super.onDetach();
    }
    public static MyDialog newInstance(int requestCode) {
        Bundle args = new Bundle();
        MyDialog fragment = new MyDialog();
        args.putInt("requestCode",requestCode);
        fragment.setArguments(args);
        return fragment;
    }
    private AlertDialog.Builder buildExitDialog()
    {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.closing)
                .setMessage(R.string.sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnfinishDialog(requestCode, "ok");

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }
    private AlertDialog.Builder buildPreciionDialog(){
        final Double d = 123.0;
        View view = getActivity().getLayoutInflater().inflate(R.layout.precision, null);
        final TextView tv = (TextView)view.findViewById(R.id.TVAfterPoint);
        final SeekBar sk = (SeekBar)view.findViewById(R.id.seekBar);
        sk.setProgress(((MainActivity)getActivity()).getCurrentPercision());
        tv.setText(String.format("%."+((MainActivity)getActivity()).getCurrentPercision()+"f", d));
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(String.format("%."+progress+"f", d));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.precision)
                .setView(view)
                .setPositiveButton(R.string.Ok ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnfinishDialog(requestCode, sk.getProgress());

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }
    public interface ResultsListener
    {
        public void OnfinishDialog(int requestCode, Object result);
    }
}
