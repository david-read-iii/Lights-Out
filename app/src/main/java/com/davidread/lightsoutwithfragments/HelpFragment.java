package com.davidread.lightsoutwithfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * {@link HelpFragment} represents a user interface with instructions that tell the user how to play
 * the Lights Out game.
 */
public class HelpFragment extends Fragment {

    /**
     * Callback method invoked when the fragment is created. It simply inflates the layout of the
     * fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }
}