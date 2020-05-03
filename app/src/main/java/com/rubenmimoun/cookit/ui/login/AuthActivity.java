package com.rubenmimoun.cookit.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rubenmimoun.cookit.MainActivity;
import com.rubenmimoun.cookit.R;

import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private List<Fragment> pages = Arrays.asList(new LoginFragment(), new RegisterFragment());

    @Override
    protected void onStart() {
        super.onStart();
            super.onStart();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user  = mAuth .getCurrentUser() ;

            if(user != null){
                Snackbar.make(getWindow().getDecorView(), "Welcome back !", Snackbar.LENGTH_SHORT).show();

                startActivity(new Intent(AuthActivity.this, MainActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }

        }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {

                return new LoginFragment();
            }

            return new RegisterFragment();

        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }

}
