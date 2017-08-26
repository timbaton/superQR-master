package info.superego.qrreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timur.myqrreader.R;

public class ResultActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textMessage = (TextView) findViewById(R.id.pict_description);
        resultImage = (ImageView) findViewById(R.id.result_image);
        toolbar = (Toolbar) findViewById(R.id.toolbar_result);

        setSupportActionBar(toolbar);

        Intent intentFromInformation = getIntent();
        if (intentFromInformation.hasExtra("code")) {
            switch (intentFromInformation.getStringExtra("code")) {
                case "1":
                    showError();
                    textMessage.setText(R.string.error_key);
                    break;
                case "2":
                    showError();
                    textMessage.setText(R.string.error_not_found);
                    break;
                case "3":
                    showError();
                    textMessage.setText(R.string.error_unexpected);
                    break;
                case "4":
                    showQuestion();
                    textMessage.setText(R.string.false_ticket);
                    break;
                case "0":
                    showSuccess();
                    textMessage.setText(R.string.good);
                    break;
            }
        }
    }

    private void showSuccess() {
        getSupportActionBar().setTitle(R.string.ticket_success);
        resultImage.setImageResource(R.drawable.check);
    }

    private void showError() {
        getSupportActionBar().setTitle(R.string.ticket_error);
        resultImage.setImageResource(R.drawable.close);
    }

    private void showQuestion() {
        getSupportActionBar().setTitle(R.string.ticket_question);
        resultImage.setImageResource(R.drawable.help);
    }

    public void onBack(View view) {
        finish();
    }
}
