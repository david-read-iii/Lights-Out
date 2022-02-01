package com.davidread.lightsoutwithfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

/**
 * {@link ColorFragment} represents a user interface with {@link MaterialButton} views that allow
 * the user to change the color of the squares of the Lights Out game.
 */
public class ColorFragment extends Fragment {

    /**
     * {@link String} constant used to identify the color ID in {@link SharedPreferences} passed
     * between the {@link GameFragment} and {@link ColorFragment}.
     */
    public static final String COLOR_EXTRA = "color";

    /**
     * {@link MaterialButton} for the red color option.
     */
    private MaterialButton redMaterialButton;

    /**
     * {@link MaterialButton} for the orange color option.
     */
    private MaterialButton orangeMaterialButton;

    /**
     * {@link MaterialButton} for the yellow color option.
     */
    private MaterialButton yellowMaterialButton;

    /**
     * {@link MaterialButton} for the green color option.
     */
    private MaterialButton greenMaterialButton;

    /**
     * Callback method invoked when the fragment view is created. First, it inflates the layout of
     * this fragment. Then, it receives the color ID used to light squares up from
     * {@link GameFragment}. Finally, it puts the {@link MaterialButton} in the layout that
     * represents the color ID in a selected state.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_color, container, false);

        // Create references to all MaterialButton views.
        redMaterialButton = rootView.findViewById(R.id.red_button);
        orangeMaterialButton = rootView.findViewById(R.id.orange_button);
        yellowMaterialButton = rootView.findViewById(R.id.yellow_button);
        greenMaterialButton = rootView.findViewById(R.id.green_button);

        // Add click callbacks to all MaterialButton views.
        redMaterialButton.setOnClickListener(this::onColorSelected);
        orangeMaterialButton.setOnClickListener(this::onColorSelected);
        yellowMaterialButton.setOnClickListener(this::onColorSelected);
        greenMaterialButton.setOnClickListener(this::onColorSelected);

        // Get the color ID from GameFragment.
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        int colorId = sharedPreferences.getInt(COLOR_EXTRA, R.color.yellow);

        // Put the appropriate MaterialButton in a selected state given the color ID.
        if (colorId == R.color.red) {
            redMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_checked));
        } else if (colorId == R.color.orange) {
            orangeMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_checked));
        } else if (colorId == R.color.yellow) {
            yellowMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_checked));
        } else if (colorId == R.color.green) {
            greenMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_checked));
        }

        return rootView;
    }

    /**
     * Method invoked when a {@link MaterialButton} is selected in this fragment. It gets the color
     * ID of the selected {@link MaterialButton} and saves it in {@link SharedPreferences}.
     *
     * @param view {@link View} selected.
     */
    public void onColorSelected(View view) {

        // Uncheck all MaterialButton views.
        redMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_unchecked));
        orangeMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_unchecked));
        yellowMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_unchecked));
        greenMaterialButton.setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_unchecked));

        // Check the selected MaterialButton.
        ((MaterialButton) view).setIcon(AppCompatResources.getDrawable(this.requireActivity(), R.drawable.ic_radio_button_checked));

        // Get a color ID based on which MaterialButton was selected.
        int colorId = R.color.yellow;
        if (view.getId() == R.id.red_button) {
            colorId = R.color.red;
        } else if (view.getId() == R.id.orange_button) {
            colorId = R.color.orange;
        } else if (view.getId() == R.id.green_button) {
            colorId = R.color.green;
        }

        // Save the color ID in SharedPreferences.
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(COLOR_EXTRA, colorId);
        editor.apply();
    }
}