package com.example.parkings.coreActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.parkings.fragment.AccountFragment;
import com.example.parkings.fragment.ActivityFragment;
import com.example.parkings.fragment.ChatFragment;
import com.example.parkings.R;
import com.example.parkings.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    ViewPager view_pager;
    private int[] selectitem = {
            R.drawable.ic_baseline_explore_24,
            R.drawable.ic_baseline_receipt_long_24,
            R.drawable.ic_baseline_chat_bubble_24,
            R.drawable.ic_baseline_account_circle_24
    };
    private int[] unselectitem={
            R.drawable.ic_outline_explore_24,
            R.drawable.ic_outline_receipt_long_24,
            R.drawable.ic_baseline_chat_bubble_outline_24,
            R.drawable.ic_outline_account_circle_24
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int flag = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility();
        flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flag);
        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.white));
        window.setNavigationBarColor(MainActivity.this.getResources().getColor(android.R.color.white));

        navigation = findViewById(R.id.navigation);
        view_pager = findViewById(R.id.view_pager);
        setupViewPager(view_pager);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        view_pager.addOnPageChangeListener(onPageChangeListener);
    }
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    navigation.getMenu().findItem(R.id.home).setChecked(true);
                    navigation.getMenu().findItem(R.id.home).setIcon(selectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(unselectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(unselectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(unselectitem[3]);
                    break;
                case 1:
                    navigation.getMenu().findItem(R.id.activity).setChecked(true);
                    navigation.getMenu().findItem(R.id.home).setIcon(unselectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(selectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(unselectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(unselectitem[3]);
                    break;
                case 2:
                    navigation.getMenu().findItem(R.id.chat).setChecked(true);
                    navigation.getMenu().findItem(R.id.home).setIcon(unselectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(unselectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(selectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(unselectitem[3]);
                    break;
                case 3:
                    navigation.getMenu().findItem(R.id.account).setChecked(true);
                    navigation.getMenu().findItem(R.id.home).setIcon(unselectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(unselectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(unselectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(selectitem[3]);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home:
                    view_pager.setCurrentItem(0);
                    navigation.getMenu().findItem(R.id.home).setIcon(selectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(unselectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(unselectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(unselectitem[3]);
                    break;
                case R.id.activity:
                    view_pager.setCurrentItem(1);
                    navigation.getMenu().findItem(R.id.home).setIcon(unselectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(selectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(unselectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(unselectitem[3]);
                    break;
                case R.id.chat:
                    view_pager.setCurrentItem(2);
                    navigation.getMenu().findItem(R.id.home).setIcon(unselectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(unselectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(selectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(unselectitem[3]);
                    break;
                case R.id.account:
                    view_pager.setCurrentItem(3);
                    navigation.getMenu().findItem(R.id.home).setIcon(unselectitem[0]);
                    navigation.getMenu().findItem(R.id.activity).setIcon(unselectitem[1]);
                    navigation.getMenu().findItem(R.id.chat).setIcon(unselectitem[2]);
                    navigation.getMenu().findItem(R.id.account).setIcon(selectitem[3]);
                    break;
            }
            return true;
        }
    };
    private void setupViewPager(ViewPager view_pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Trang chủ");
        adapter.addFragment(new ActivityFragment(), "Hoạt động");
        adapter.addFragment(new ChatFragment(),"Nhắn tin");
        adapter.addFragment(new AccountFragment(),"Tài khoản");
        view_pager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;
        private ArrayList<Drawable> drawables;

        ViewPagerAdapter(FragmentManager fm){
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }
        @NonNull
        @Override
        public CharSequence getPageTitle(int position){
            return null;
        }
    }
}