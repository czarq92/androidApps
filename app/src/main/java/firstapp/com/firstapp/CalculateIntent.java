package firstapp.com.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculateIntent extends AppCompatActivity {

    Button calculateButton;
    EditText wallLength;
    TextView resultLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_intent);


        calculateButton = (Button) findViewById(R.id.calculateButton);
        wallLength = (EditText) findViewById(R.id.wallLength);
        resultLabel = (TextView) findViewById(R.id.resultLabel);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wallLength.getText().toString().equals(""))
                    resultLabel.setText("Podaj wartość!");
                else
                    resultLabel.setText((Double.parseDouble(wallLength.getText().toString()) *
                        Double.parseDouble(wallLength.getText().toString())) +"");
            }
        });
    }
}
