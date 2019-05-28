package agh.dfbazan.measurements_3_0.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import agh.dfbazan.measurements_3_0.R;

public class InfoAppActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_home_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemBack:
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_app);

        SpannableStringBuilder str = new SpannableStringBuilder("System \"MeDataReader 1.0\"\nAutor: Dominik Bazan\nZrealizowany w ramach pracy inżynierskiej, pt.\n\"Archiwizacja danych medycznych uzyskanych za pomocą urządzenia mobilnego\"\nPromotor: dr inż. J. Piwowarczyk\nWydział Elektrotechniki, Automatyki, Informatyki i Inżynierii Biomedycznej\nAGH w Krakowie");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 7, 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 33, 46, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView tv=(TextView) findViewById(R.id.info);
        tv.setText(str);
    }
}
