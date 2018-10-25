package com.example.robin.demoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment {

    private FragmentListener fragmentCallback;
    private EditText etMessageTyped;

    public interface FragmentListener {
        void answerFromFragment(String message);
    }

    public MyFragment() {
        // Required empty public constructor
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MyFragment newInstance(String title) {
        MyFragment frag = new MyFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener) {
            fragmentCallback = (FragmentListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        etMessageTyped = view.findViewById(R.id.et_message_typed);

        String title = "";

        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey("title"))
                title = arguments.getString("title");
        }

        if(title != null && title.length() > 0)
            tvTitle.setText(title);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.answerFromFragment(String.valueOf(etMessageTyped.getText()));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
