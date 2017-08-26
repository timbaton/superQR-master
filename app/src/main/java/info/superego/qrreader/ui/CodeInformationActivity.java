package info.superego.qrreader.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timur.myqrreader.R;

import info.superego.qrreader.NetworkService;
import info.superego.qrreader.models.Data;
import info.superego.qrreader.models.Model;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeInformationActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private TextView mEmailText;
    private TextView mPhoneText;
    private TextView mNameText;
    private TextView mDateText;
    public NetworkService mNetworkService;
    private String hash;
    public String[] text;
    private final String KEY = "5gu9d7njm7ciwyjwcfnzb9s9cpisg13e6u6w8cto";

    public CodeInformationActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_information);

        mEmailText = (TextView) findViewById(R.id.email_info);
        mPhoneText = (TextView) findViewById(R.id.phone_info);
        mNameText = (TextView) findViewById(R.id.name_info);
        mDateText = (TextView) findViewById(R.id.date_info);

        mProgressDialog = new ProgressDialog(this);

        Intent intentFromMain = getIntent();
        if (intentFromMain.hasExtra(Intent.EXTRA_TEXT)) {
            setTextForViews(intentFromMain.getStringExtra(Intent.EXTRA_TEXT));
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        mNetworkService = new NetworkService();
    }

    private void setTextForViews(String stringExtra) {
        text = stringExtra.split(";");
        if (text.length != 5) {
            // incorrect qr code
            final Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("code", "3");
            startActivity(intent);
            finish();
            return;
        }
        mEmailText.setText(text[0]);
        mNameText.setText(text[1]);
        mPhoneText.setText(text[2]);
        mDateText.setText(text[3]);
        hash = text[4];
    }


    public void onClickSend(View view) {
        mProgressDialog = ProgressDialog.show(this, "Проверяем билет", "Пожалуйста, подождите");
        final Intent intent = new Intent(this, ResultActivity.class);
        mNetworkService.getAPI().getData(KEY, hash).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                mProgressDialog.hide();
                if (response.body().getCode() == 1) {
                    intent.putExtra("code", "1");
                } else if (response.body().getCode() == 2) {
                    intent.putExtra("code", "2");
                } else if (response.body().getCode() == 3) {
                    intent.putExtra("code", "3");
                } else {
                    Data data = response.body().getData();
                    if (response.body().getCode() == 0
                            && data.getEmail().equals(text[0])
                            && data.getName().equals(text[1])
                            && data.getPhone().equals(text[2])
                            && data.getBuyTime().equals(text[3])) {
                        intent.putExtra("code", "0");
                    } else {
                        intent.putExtra("code", "4");
                    }
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(CodeInformationActivity.this, R.string.error_conneection,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onBack(View view) {
        finish();
    }
}
