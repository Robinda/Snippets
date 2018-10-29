package com.example.robin.demoapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class MyDialogFragment extends DialogFragment {

    private static final String TITLE = "dialog_title";
    private static final String MESSAGE = "dialog_message";
    private static final String POSITIVE_BUTTON_VALUE = "dialog_positive_button";
    private static final String NEGATIVE_BUTTON_VALUE = "dialog_negative_button";
    private static final String DIALOG_IS_CANCELABLE = "dialog_is_cancelable";

    public static final int DIALOG_RESULT_POSITIVE = 1;
    public static final int DIALOG_RESULT_NEGATIVE = 0;

    private DialogListener mDialogListener;
    private TextView mTvDialogTitle, mTvDialogMessage, mTvDialogActionPositive, mTvDialogActionNegative;

	/**
	* Interface de communication entre la DialogBox et l'activité parente
	*/
    public interface DialogListener {
        void onConfirm(int result);
    }

    public MyDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MyDialogFragment newInstance(String title, String message, String positiveButton,
                                                  String negativeButton, Boolean isCancelable) {
        MyDialogFragment frag = new MyDialogFragment();

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        args.putString(POSITIVE_BUTTON_VALUE, positiveButton);
        args.putString(NEGATIVE_BUTTON_VALUE, negativeButton);
        args.putBoolean(DIALOG_IS_CANCELABLE, isCancelable);
        frag.setArguments(args);

        return frag;
    }
	
	@Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof DialogListener){
            mDialogListener = (DialogListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_dialog, container, true);
		
		// Remove title bar
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Make dialog cancelable or not
        setCancelable(getArguments().getBoolean(DIALOG_IS_CANCELABLE, true));

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
		mTvDialogTitle = view.findViewById(R.id.tv_dialog_title_value);
        mTvDialogMessage = view.findViewById(R.id.tv_dialog_message_value);
        mTvDialogActionPositive = view.findViewById(R.id.tv_button_positive_action);
        mTvDialogActionNegative = view.findViewById(R.id.tv_button_negative_action);

        String title = getArguments().getString(TITLE, "");
        String message = getArguments().getString(MESSAGE, "");
        String positiveButton = getArguments().getString(POSITIVE_BUTTON_VALUE, "");
        String negativeButton = getArguments().getString(NEGATIVE_BUTTON_VALUE, "");

        if (title.isEmpty()) {
            mTvDialogTitle.setVisibility(View.GONE);
        }
        else {
            mTvDialogTitle.setText(title);
        }

        if (message.isEmpty() || positiveButton.isEmpty() || negativeButton.isEmpty()) {
            dismiss();
            return;
        }

        mTvDialogMessage.setText(message);
        mTvDialogActionPositive.setText(positiveButton);
        mTvDialogActionNegative.setText(negativeButton);

        mTvDialogActionNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mDialogListener != null) {
                    mDialogListener.onConfirm(DIALOG_RESULT_NEGATIVE);
                }
            }
        });

        mTvDialogActionPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mDialogListener != null) {
                    mDialogListener.onConfirm(DIALOG_RESULT_POSITIVE);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }    
}
