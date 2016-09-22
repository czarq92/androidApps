package firstapp.com.firstapp;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    FloatingActionButton fab;
    MediaPlayer mediaPlayer;
    TextView sensorTextView;
    TextView fapPointsResult;

    int counter = 0;
    int fapPoints = 0;

    int beginSesnorValue = 0;
    boolean wasDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.fapp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.RED);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();

                Snackbar.make(view, "Czego tu szukasz, Pajacu ?!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), 0, null);

        sensorTextView = (TextView) findViewById(R.id.sensorTextView);

        final View include = (View) findViewById(R.id.include);
        include.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;

                fab.setRotation(fab.getRotation() + 30);

                Toast.makeText(getApplicationContext(), "BANG! [" + counter + "]", Toast.LENGTH_SHORT).show();
            }
        });

        fapPointsResult = (TextView) findViewById(R.id.fapPoints);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(SettingsIntent.class);
            return true;
        }
        if(id == R.id.calculate){
            startActivity(CalculateIntent.class);
        }

        return super.onOptionsItemSelected(item);
    }

    public void startActivity(Class intentClass){
        Intent intent = new Intent(this, intentClass);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorTextView.setText("Azymut = " + (int) event.values[0] + "\nWychylenie góra/dół = " + (int) event.values[1]
                                + "\nWychylenie lewa/prawa = " + (int) event.values[2]);

        if(beginSesnorValue == 0)
            beginSesnorValue = (int) event.values[0];

        if((int) event.values[0] < beginSesnorValue){
            wasDown = true;
        } else if(wasDown && (int) event.values[0] > beginSesnorValue){
            fapPointsResult.setText(fapPoints++ + "");
            wasDown = false;
            mediaPlayer.start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
