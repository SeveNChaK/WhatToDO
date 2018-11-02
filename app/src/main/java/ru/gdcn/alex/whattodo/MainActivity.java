package ru.gdcn.alex.whattodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ToDO_Logger";
    public static final String className = "MainActivity";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, TextFormer.getStartText(className) + "Создание MainActivity...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        NotesFragment notes = new NotesFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.main_space, notes)
                .commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //TODO создавать фрагменты один раз!!! Убрать создание из кейсов!!!
                        switch (item.getItemId()){
                            case R.id.nav_notes:
                                NotesFragment notes = new NotesFragment();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, notes)
                                        .commit();
                                item.setChecked(true);
                                break;
                            case R.id.nav_tasks:
                                TasksFragment tasks = new TasksFragment();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, tasks)
                                        .commit();
                                item.setChecked(true);
                                DBConnector.clearTable(getApplicationContext());
                                break;
                            case R.id.nav_calendar:
                                CalendarFragment calendarFragment = new CalendarFragment();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, calendarFragment)
                                        .commit();
                                item.setChecked(true);
                                break;
                        }
                        return false;
                    }
                });
        Log.d(TAG, TextFormer.getStartText(className) + "Создание MainActivity завершено!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обработка нажатия на элемент выпадающего меню...");
        int id = item.getItemId();
        switch (id) {
            case R.id.top_right_menu_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Настройки\"!");
                return true;
            case R.id.top_right_menu_about:
                Toast.makeText(getApplicationContext(), "Нажали О программе.", Toast.LENGTH_LONG).show();
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"О программе\"!");
                return true;
            default:
                Log.e(TAG, TextFormer.getStartText(className) + "Не найдено такой кнопки!");
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickCreate(View v){
        Log.d(TAG, TextFormer.getStartText(className) + "Обработка нажатия кнопки \"Создать\"...");
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
//        FrameLayout frameLayout = findViewById(R.id.main_space);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                100,
//                100);
//        layoutParams.setMargins(0, 0, 0, (int) (140 / getApplicationContext().getResources().getDisplayMetrics().density));
//        layoutParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
        Log.d(TAG, TextFormer.getStartText(className) + "Обработка нажатия кнопки \"Создать\" завершено!");
    }
}
