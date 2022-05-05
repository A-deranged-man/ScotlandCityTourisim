package uk.ac.abertay.cmp309_app;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme2);
        setContentView(R.layout.activity_main);

        CardView dundeeCard = findViewById(R.id.dundeeCard);
        CardView edinburghCard = findViewById(R.id.edinburghCard);
        CardView notesCard = findViewById(R.id.notesCard);

        dundeeCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent dundeeIntent = new Intent(getApplicationContext(), MapsActivity.class);
                dundeeIntent.putExtra("cityID", 0);
                startActivity(dundeeIntent);
            }
        });

        edinburghCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent edinburghIntent = new Intent(getApplicationContext(), MapsActivity.class);
                edinburghIntent.putExtra("cityID", 1);
                startActivity(edinburghIntent);
            }
        });

        notesCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent notesIntent = new Intent(getApplicationContext(), notesActivity.class);
                startActivity(notesIntent);
            }
        });
    }
}
