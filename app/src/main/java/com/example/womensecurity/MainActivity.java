package com.example.womensecurity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.womensecurity.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Integer> arrayList=new ArrayList<Integer>();
    private static final String APP_ID="ca-app-pub-9764053019342743~7587197975";
    private static final String UNIT_ID="ca-app-pub-9764053019342743/6122073065";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addFrag(1,new home_fragment(MainActivity.this));
        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home : {
                       addFrag(1,new home_fragment(MainActivity.this));
                       arrayList.add(0);
                       break;
                    }
                    case R.id.gps: {
                        binding.bottomNav.getMenu().getItem(1).setChecked(true);
                        addFrag(0,new MapsFragment(MainActivity.this));
                        arrayList.add(1);
                        break;
                    }
                    case R.id.news: {
                        addFrag(0,new news_fragment(MainActivity.this));
                        arrayList.add(2);
                        break;
                    }
                    case R.id.search: {
                        addFrag(0,new google_search_fragment());
                        arrayList.add(3);
                        break;
                    }
                }
                return true;
            }
        });
    }
    public void addFrag(int flag, Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 1) {
            if(arrayList.size()!=0)arrayList.removeAll(arrayList);
            arrayList.add(0);
            ft.add(R.id.container, fragment);
            fm.popBackStack("ROOT_FRAGMENT_TAG",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack("ROOT_FRAGMENT_TAG");
        } else {
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(null);
        }
            ft.commit();
        }
    @Override
    public void onBackPressed() {
        arrayList.remove(arrayList.size()-1);
        if(arrayList.size()!=0){
            int num=arrayList.get(arrayList.size()-1);
            binding.bottomNav.getMenu().getItem(num).setChecked(true);
        }
            super.onBackPressed();

    }
}