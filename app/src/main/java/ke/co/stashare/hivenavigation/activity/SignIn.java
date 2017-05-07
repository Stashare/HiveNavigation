package ke.co.stashare.hivenavigation.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import ke.co.stashare.hivenavigation.R;

/**
 * Created by Ken Wainaina on 24/04/2017.
 */

public class SignIn extends AppCompatActivity {

    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Sign In");
        actionBar.setDisplayShowTitleEnabled(true);

        View card_view = findViewById(R.id.direction_card_view);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //imageViewCompany.setTransitionName("company_icon");
            card_view.setTransitionName("cardi");
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

    public void verify(View view) {

        View card = findViewById(R.id.direction_card_view);
        //View collapse = findViewById(R.id.collapsing_toolbar);
        View companyIcon = findViewById(R.id.imageViewCompany);
        //View button = findViewById(R.id.btn_request_sms);

        Intent intent = new Intent(this,  Verify.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Pair<View, String> pair1 = Pair.create(card, card.getTransitionName());
           //Pair<View, String> pair2 = Pair.create(button, button.getTransitionName());
            Pair<View, String> pair3 = Pair.create(companyIcon, companyIcon.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1,pair3);
            startActivity(intent, options.toBundle());

        }
        else {
            startActivity(intent);
        }

    }
}
