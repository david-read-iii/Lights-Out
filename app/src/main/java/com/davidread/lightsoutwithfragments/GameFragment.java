package com.davidread.lightsoutwithfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * {@link GameFragment} represents the game user interface for the Lights Out game. It maintains the
 * logic of the game in {@link #game} and displays the logic of the game using {@link #lightGrid}
 * and {@link #countTextView}.
 */
public class GameFragment extends Fragment {

    /**
     * {@link String} constant for identifying the game state passed in a {@link Bundle} during
     * configuration changes.
     */
    private static final String GAME_STATE_EXTRA = "game_state";

    /**
     * {@link LightsOutGame} representing the logic of the current Lights Out game.
     */
    private LightsOutGame game;

    /**
     * {@link GridLayout} used for displaying the board of the Lights Out game.
     */
    private GridLayout lightGrid;

    /**
     * {@link TextView} used for displaying the count of clicks made so far.
     */
    private TextView countTextView;

    /**
     * Int representing the color used for a lit cell.
     */
    private int lightOnColor;

    /**
     * Int representing the color used for a dark cell.
     */
    private int lightOffColor;

    /**
     * Callback method invoked when the fragment view is initially created. It initializes the
     * member variables of this class, sets up click listeners, and updates the user interface
     * to match these initializations.
     *
     * @param inflater           Used to inflate any views in the fragment.
     * @param container          If non-null, is the parent view that the fragment's UI should be
     *                           attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return The {@link View} for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        // Initialize game with either new game state or saved game state.
        game = new LightsOutGame();
        if (savedInstanceState == null) {
            game.newGame();
        } else {
            game.setState(savedInstanceState.getString(GAME_STATE_EXTRA));
        }

        // Initialize lightGrid and link each of its Button children with a click listener.
        lightGrid = rootView.findViewById(R.id.light_grid);
        for (int i = 0; i < lightGrid.getChildCount(); i++) {
            Button gridButton = (Button) lightGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onLightButtonClick);
        }

        // Initialize lightOnColor with the saved color value stored in SharedPreferences.
        SharedPreferences sharedPreferences = this.requireActivity().getPreferences(Context.MODE_PRIVATE);
        int onColorId = sharedPreferences.getInt(ColorFragment.COLOR_EXTRA, R.color.yellow);
        lightOnColor = ContextCompat.getColor(this.requireActivity(), onColorId);

        // Initialize other member variables.
        countTextView = rootView.findViewById(R.id.count_text_view);
        lightOffColor = ContextCompat.getColor(this.requireActivity(), R.color.black);

        // Link newGameButton with a click listener.
        Button newGameButton = rootView.findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(this::onNewGameClick);

        // Update the user interface.
        setButtonColors();
        setCountTextView();

        return rootView;
    }

    /**
     * Callback method invoked directly before a configuration change occurs. It saves the state of
     * {@link #game}.
     *
     * @param outState {@link Bundle} for preserving state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE_EXTRA, game.getState());
    }

    /**
     * Method invoked when a light button is clicked. It registers the click with {@link #game} and
     * updates {@link #lightGrid} to match it. Then, it pops a {@link Snackbar} and resets
     * {@link #game} if {@link #game} is in a terminal state.
     *
     * @param view {@link View} clicked that invoked this method.
     */
    public void onLightButtonClick(View view) {

        // Find the Button view's row and col.
        int buttonIndex = lightGrid.indexOfChild(view);
        int row = buttonIndex / LightsOutGame.GRID_SIZE;
        int col = buttonIndex % LightsOutGame.GRID_SIZE;

        // Select the light in the game object.
        game.selectLight(row, col);

        // Update the UI.
        setButtonColors();
        setCountTextView();

        // Congratulate the user and start a new game if the game is over.
        if (game.isGameOver()) {
            Snackbar.make(view, getString(R.string.game_win_message, game.getCountClicks()), BaseTransientBottomBar.LENGTH_SHORT).show();
            game.newGame();
            setButtonColors();
            setCountTextView();
        }
    }

    /**
     * Method invoked when the new game button is clicked. It initializes {@link #game} with new
     * state and updates the UI.
     *
     * @param view {@link View} clicked that invoked this method.
     */
    public void onNewGameClick(View view) {
        game.newGame();
        setButtonColors();
        setCountTextView();
    }

    /**
     * Updates {@link #lightGrid} to match the state given by {@link #game}.
     */
    private void setButtonColors() {

        // Iterate through all Button view's in the GridLayout.
        for (int row = 0; row < LightsOutGame.GRID_SIZE; row++) {
            for (int col = 0; col < LightsOutGame.GRID_SIZE; col++) {

                // Get the Button in the GridLayout at the selected row and col.
                int buttonIndex = row * LightsOutGame.GRID_SIZE + col;
                Button gridButton = (Button) lightGrid.getChildAt(buttonIndex);

                // Set the Button view's color.
                if (game.isLightOn(row, col)) {
                    gridButton.setBackgroundColor(lightOnColor);
                } else {
                    gridButton.setBackgroundColor(lightOffColor);
                }
            }
        }
    }

    /**
     * Updates {@link #countTextView} with the latest count from {@link #game}.
     */
    public void setCountTextView() {
        countTextView.setText(getString(R.string.count_label, game.getCountClicks()));
    }
}