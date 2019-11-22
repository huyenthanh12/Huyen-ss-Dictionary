package com.example.dictionary.ui.flashcard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.Random;

import customAdapter.pageAdapter;
import model.Word;

public class FlashCardFragment extends Fragment {

    ViewPager vpPractice;
    pageAdapter mAdapter;
    ArrayList<Word> mList;
    int mCurrentPage = 0;

        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flashcard, container, false);
        final TextView textView = root.findViewById(R.id.paper);

        return root;
    }

}