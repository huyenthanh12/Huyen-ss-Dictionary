package com.example.dictionary.ui.your_words;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dictionary.R;

public class YourWordsFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_your_words, container, false);
        TextView textView = root.findViewById(R.id.tv_yourWords);
        textView.setText("Favorites");
        return root;
    }
}
