package ke.co.stashare.hivenavigation.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ke.co.stashare.hivenavigation.R;
import ke.co.stashare.hivenavigation.helper.Config;
import ke.co.stashare.hivenavigation.helper.MyApplication;
import ke.co.stashare.hivenavigation.helper.PrefManager;
import ke.co.stashare.hivenavigation.helper.SharedPrefManager;
import ke.co.stashare.hivenavigation.services.HttpService;

/**
 * Created by Ken Wainaina on 23/04/2017.
 */

public class Register extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = Register.class.getSimpleName();
    Toolbar mToolbar;
    AppCompatEditText phoneNo;
    private EditText inputFirstName, inputNameLast, inputEmail,inputOtp;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    ImageView back;
    ProgressBar progressBar2;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        View card_view = findViewById(R.id.direction_card_view);
        View col_toolbar = findViewById(R.id.collapsing_toolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            card_view.setTransitionName("cardi");
            //col_toolbar.setTransitionName("collapse");
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);



        //viewPager = (ViewPager) findViewById(R.id.viewPagerVertical);
        inputFirstName = (EditText) findViewById(R.id.inputFirstName);
        inputNameLast = (EditText) findViewById(R.id.inputNameLast);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        phoneNo = (AppCompatEditText) findViewById(R.id.inputMobile);
        //inputOtp = (EditText) findViewById(R.id.inputOtp);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar);
        //back = (ImageView) findViewById(R.id.back);

        btnRequestSms = (Button) findViewById(R.id.btn_request_sms);
        //btnVerifyOtp = (Button) findViewById(R.id.btn_verify);



        // view click listeners
        //back.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        //btnVerifyOtp.setOnClickListener(this);

        // hiding the edit mobile number
       // back.setVisibility(View.GONE);

        pref = new PrefManager(this);

        /*// Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(Register.this, WipayHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }*/

      /*  adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        *//**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         *//*
        if (pref.isWaitingForSms()) {
            viewPager.setCurrentItem(1);
            //layoutEditMobile.setVisibility(View.VISIBLE);
        }
*/

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




    /**
     * Validating user details form
     */
    private void validateForm() {
        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputNameLast.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String mobile = phoneNo.getText().toString().trim();

        String num = pref.getMobileNumber();

        // validating empty name and email
        if (firstName.length() == 0 || email.length() == 0|| lastName.length() == 0|| mobile.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            progressBar2.setVisibility(View.VISIBLE);

            //final String token = SharedPrefManager.getInstance(this).getDeviceToken();

             SharedPrefManager.getInstance(getApplicationContext()).saveNumber(mobile);


            if(num != null && Objects.equals(num, mobile)) {

                View card = findViewById(R.id.direction_card_view);
                View companyIcon = findViewById(R.id.imageViewCompany);
                //View button = findViewById(R.id.button);

                Intent intent = new Intent(Register.this, Verify.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Pair<View, String> pair1 = Pair.create(card, card.getTransitionName());
                    Pair<View, String> pair2 = Pair.create(companyIcon, companyIcon.getTransitionName());
                    //Pair<View, String> pair3 = Pair.create(button, button.getTransitionName());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Register.this, pair1,pair2);
                    startActivity(intent, options.toBundle());

                }
                else {
                    startActivity(intent);
                }

            }else {

                pref.setMobileNumber(mobile);
                // requesting for sms
                requestForSMS(firstName, lastName, email, mobile);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume()
    {

        super.onResume();

        progressBar2.setVisibility(View.GONE);


    }


    /**
     * Method initiates the SMS request on the server
     *
     * @param firstname   user firstname
     * @param lastname   user lastname
     * @param email  user email address
     * @param mobile user valid mobile number
     */
    private void requestForSMS(final String firstname,final String lastname, final String email, final String mobile) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.URL_REQUEST_SMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                try {
                    JSONObject responseObj = new JSONObject(response);

                    // Parsing json object response
                    // response will be a json object
                    boolean error = responseObj.getBoolean("error");
                    String message = responseObj.getString("message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (!error) {

                        View card = findViewById(R.id.direction_card_view);
                        View companyIcon = findViewById(R.id.imageViewCompany);
                        //View button = findViewById(R.id.button);

                        Intent intent = new Intent(Register.this, Verify.class);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            Pair<View, String> pair1 = Pair.create(card, card.getTransitionName());
                            Pair<View, String> pair2 = Pair.create(companyIcon, companyIcon.getTransitionName());
                            //Pair<View, String> pair3 = Pair.create(button, button.getTransitionName());
                            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Register.this, pair1,pair2);
                            startActivity(intent, options.toBundle());

                        }
                        else {
                            startActivity(intent);
                        }


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error: " + message,
                                Toast.LENGTH_LONG).show();
                    }


                    // hiding the progress bar
                    progressBar2.setVisibility(View.GONE);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                    progressBar2.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar2.setVisibility(View.GONE);
            }
        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("email", email);
                params.put("mobile", mobile);

                Log.e(TAG, "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp() {
        String otp = inputOtp.getText().toString().trim();

        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
            grapprIntent.putExtra("otp", otp);
            startService(grapprIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_sms:
                validateForm();
                break;

            case R.id.btn_verify:
                verifyOtp();
                break;

          /*  case R.id.back:
                viewPager.setCurrentItem(0);
                back.setVisibility(View.GONE);
                pref.setIsWaitingForSms(false);
                break;*/


        }
    }

    public void validate(View view) {

        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputNameLast.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String mobile = phoneNo.getText().toString().trim();

        // validating empty name and email
        if (firstName.length() == 0 || email.length() == 0|| lastName.length() == 0|| mobile.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            progressBar2.setVisibility(View.VISIBLE);

            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);

            // requesting for sms
            requestForSMS(firstName,lastName, email, mobile);

        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }

    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }
            return findViewById(resId);
        }
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


}
