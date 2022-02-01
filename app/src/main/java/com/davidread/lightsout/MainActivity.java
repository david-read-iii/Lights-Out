package com.davidread.lightsout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * {@link MainActivity} represents a user interface with a bottom navigation bar. The bar allows
 * the user to swap between {@link GameFragment}, {@link ColorFragment}, and {@link HelpFragment}.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Callback method invoked when the activity is created. It simply inflates the activity's
     * layout and initializes the bottom navigation bar.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the bottom navigation bar.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.navigation_game, R.id.navigation_color, R.id.navigation_help)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }
}