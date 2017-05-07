package ke.co.stashare.hivenavigation.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import ke.co.stashare.hivenavigation.R;
import ke.co.stashare.hivenavigation.helper.PrefManager;
import ke.co.stashare.hivenavigation.helper.SharedPrefManager;

/**
 * Created by Ken Wainaina on 24/04/2017.
 */

public class Verify  extends AppCompatActivity {

    Toolbar mToolbar;
    AppCompatTextView resend_timer;
    RelativeLayout resend;
    RelativeLayout send;
    EditText editMobi;
    LinearLayout plain;
    LinearLayout editMobile;
    LinearLayout container;
    TextView mobilex;
    ProgressBar progressBar2;
    PrefManager pref;
    int secondsLeft;
    static CountDownTimer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify);

        pref = new PrefManager(getApplicationContext());



        resend_timer =(AppCompatTextView)findViewById(R.id.timer);
        resend= (RelativeLayout)findViewById(R.id.resend_layout);
        send = (RelativeLayout)findViewById(R.id.send_layout);
        plain = (LinearLayout)findViewById(R.id.plainMobile);
        editMobile = (LinearLayout)findViewById(R.id.editNumber);
        container = (LinearLayout)findViewById(R.id.container);
        mobilex = (TextView)findViewById(R.id.mobile);
        editMobi = (EditText)findViewById(R.id.editMobi);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar);
        mobilex.setText(pref.getMobileNumber());
        editMobi.setText(pref.getMobileNumber());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Verify Phone No.");
        actionBar.setDisplayShowTitleEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        View card_view = findViewById(R.id.direction_card_view);
        //View btnVerify = findViewById(R.id.btn_verify);
        View companyIcon = findViewById(R.id.imageViewCompany);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            companyIcon.setTransitionName("icon");
            card_view.setTransitionName("cardi");
            //btnVerify.setTransitionName("buttonx");
        }

        secondsLeft = 0;
        startTimer();
    }

    private void startTimer() {
        timer =  new CountDownTimer(60000, 1000) {

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    String tym= String.valueOf(secondsLeft);
                    String finaltym = tym + " secs";
                    resend_timer.setText(finaltym);
                }
            }

            public void onFinish() {
                // hiding the edit mobile number
                send.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void stopCounting() {
        timer.cancel();
    }

    public void resendCode(View view) {

    }

     /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void confirmEditNumber(View view) {


        String mobile = editMobi.getText().toString().trim();


        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            //progressBar2.setVisibility(View.VISIBLE);

            //final String token = SharedPrefManager.getInstance(this).getDeviceToken();


            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);

        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }

        mobilex.setText(pref.getMobileNumber());

        plain.setVisibility(View.VISIBLE);
        editMobile.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);


    }




    public void editNumber(View view) {
        plain.setVisibility(View.GONE);
        container.setVisibility(View.GONE);
        editMobile.setVisibility(View.VISIBLE);

    }

    /**
     * Regex to validate the mobile number
     * mobile number should be of 10 digits length
     *
     * @param mobile
     * @return
     */
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    public void exitEditNumber(View view) {
        plain.setVisibility(View.VISIBLE);
        editMobile.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        stopCounting();
        secondsLeft = 0;
        startTimer();
    }
}

