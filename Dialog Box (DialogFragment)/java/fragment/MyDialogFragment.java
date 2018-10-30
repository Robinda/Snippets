package com.example.robin.demoapp;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robin.demoapp.R;

public class DialogYesNoFragment extends DialogFragment {

    private final String TAG = DialogYesNoFragment.class.getSimpleName();
    private static final String ICON_RESOURCE_ID = "icone_resource_id";
    private static final String TITLE = "dialog_title";
    private static final String MESSAGE = "dialog_message";
    private static final String POSITIVE_BUTTON_VALUE = "dialog_positive_button";
    private static final String NEGATIVE_BUTTON_VALUE = "dialog_negative_button";
    private static final String DIALOG_IS_CANCELABLE = "dialog_is_cancelable";

    public static final int DIALOG_RESULT_POSITIVE = 1;
    public static final int DIALOG_RESULT_NEGATIVE = 0;

    // Par défaut le résultat de la DialogBox est "négatif"
    private int mDialogResult = DIALOG_RESULT_NEGATIVE;

    private DialogListener mDialogListener;
    private TextView mTvDialogTitle, mTvDialogMessage, mTvDialogActionPositive, mTvDialogActionNegative;
    private ImageView mIvDialogTitleIcon;

    /**
     * Interface de communication entre la DialogBox et l'activité parente
     */
    public interface DialogListener {
        void onConfirm(int result);
    }

    /**
     * Initialisation du listener
     * @param listener Listener
     */
    public void setDialogListener(DialogListener listener) {
        this.mDialogListener = listener;
    }

    public DialogYesNoFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DialogYesNoFragment newInstance(int iconResourceId, String title, String message,
                                                  String positiveButton, String negativeButton, Boolean isCancelable) {
        DialogYesNoFragment frag = new DialogYesNoFragment();

        Bundle args = new Bundle();
        args.putInt(ICON_RESOURCE_ID, iconResourceId);
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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_yes_no, container, true);

        // Remove title bar
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Make dialog cancelable or not
        setCancelable(getArguments().getBoolean(DIALOG_IS_CANCELABLE, true));

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIvDialogTitleIcon = view.findViewById(R.id.iv_dialog_title_icon);
        mTvDialogTitle = view.findViewById(R.id.tv_dialog_title_value);
        mTvDialogMessage = view.findViewById(R.id.tv_dialog_message_value);
        mTvDialogActionPositive = view.findViewById(R.id.tv_button_positive_action);
        mTvDialogActionNegative = view.findViewById(R.id.tv_button_negative_action);

        int iconResourceId = getArguments().getInt(ICON_RESOURCE_ID, -1);
        String title = getArguments().getString(TITLE, "");
        String message = getArguments().getString(MESSAGE, "");
        String positiveButton = getArguments().getString(POSITIVE_BUTTON_VALUE, "");
        String negativeButton = getArguments().getString(NEGATIVE_BUTTON_VALUE, "");

        // Pas d'icone
        if(iconResourceId == 0) {
            mIvDialogTitleIcon.setVisibility(View.GONE);
        }
        // Icone spécifique
        else if(iconResourceId > 0) {
            mIvDialogTitleIcon.setImageResource(iconResourceId);
        }
        // Icone par défaut
        else {
            mIvDialogTitleIcon.setImageResource(R.drawable.ic_menu_share);
        }

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
                // On set le résultat à "negatif", qui sera transmis à l'activité au moment du onDetach()
                mDialogResult = DIALOG_RESULT_NEGATIVE;
                dismiss();
            }
        });

        mTvDialogActionPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On set le résultat à "positif", qui sera transmis à l'activité au moment du onDetach()
                mDialogResult = DIALOG_RESULT_POSITIVE;
                dismiss();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        /*
         * On transmet le résultat à l'activité au moment où onDetach est appelé : de cette manière même si on clique à
         * l'extérieur de la DialogBox (si isCancelable = true), on récupère toujours le résultat (dans ce cas "négatif")
         */
        if (mDialogListener != null) {
            mDialogListener.onConfirm(mDialogResult);
        }
        else {
            Log.e(TAG, "Cannot make dialog callback because listener is null (onDetach() method).");
        }
    }
}
