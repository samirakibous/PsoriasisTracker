package com.example.samira;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.samira.adapter.MessageAdapter;
import com.example.samira.model.GeminiPro;
import com.example.samira.model.Message;
import com.example.samira.model.ResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    private MessageAdapter mA;
    private RecyclerView Rv;
    private ImageButton send;
    private EditText EdtTxt;
    private ImageView logo;
    private LottieAnimationView Av;
    private List<Message> messageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        send = view.findViewById(R.id.Sendbtn);
        EdtTxt = view.findViewById(R.id.editText);
        Rv = view.findViewById(R.id.Rview);
        logo = view.findViewById(R.id.lg);
        // Av = view.findViewById(R.id.animationView); // Uncomment if using Lottie Animation

        messageList = new ArrayList<>();
        mA = new MessageAdapter(messageList);
        Rv.setAdapter(mA);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setStackFromEnd(true);
        Rv.setLayoutManager(llm);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setVisibility(View.GONE);
                String question = EdtTxt.getText().toString().trim();
                addToChat(question, Message.SENT_BY_MY);
                EdtTxt.setText("");
                // Av.setVisibility(View.VISIBLE); // Uncomment if using Lottie Animation

                GeminiPro model = new GeminiPro();
                model.getResponse(question, new ResponseCallback() {
                    @Override
                    public void onResponse(String response) {
                        addToChat(response, Message.SENT_BY_BOT);
                        // Av.setVisibility(View.GONE); // Uncomment if using Lottie Animation
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        return view;
    }

    private void addToChat(String message, String sentBy) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageList.add(new Message(message, sentBy));
                    mA.notifyDataSetChanged();
                    Rv.smoothScrollToPosition(mA.getItemCount());
                }
            });
        }
    }


}
