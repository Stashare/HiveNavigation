package ke.co.stashare.hivenavigation.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ke.co.stashare.hivenavigation.activity.WipayHome;
import ke.co.stashare.hivenavigation.helper.Config;
import ke.co.stashare.hivenavigation.helper.MyApplication;
import ke.co.stashare.hivenavigation.helper.PrefManager;
import ke.co.stashare.hivenavigation.helper.SharedPrefManager;

/**
 * Created by Ken Wainaina on 25/04/2017.
 */

public class HttpService extends IntentService {


    private static String TAG = HttpService.class.getSimpleName();

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            verifyOtp(otp);
        }
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String otp) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.URL_VERIFY_OTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {

                    JSONObject responseObj = new JSONObject(response);

                    // Parsing json object response
                    // response will be a json object
                    boolean error = responseObj.getBoolean("error");
                    String message = responseObj.getString("message");

                    if (!error) {


                        // parsing the user profile information
                        JSONObject profileObj = responseObj.getJSONObject("profile");


                        String firstname = profileObj.getString("firstname");
                        String lastname = profileObj.getString("lastname");
                        String name = firstname + " " + lastname;
                        String email = profileObj.getString("email");
                        String mobile = profileObj.getString("mobile");

                        PrefManager pref = new PrefManager(getApplicationContext());
                        pref.createLogin(name, email, mobile);

                        Intent intent = new Intent(HttpService.this, WipayHome.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        //SharedPrefManager.getInstance(getApplicationContext()).saveError(message);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", otp);

                Log.e(TAG, "Posting params: " + params.toString());
                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }


}