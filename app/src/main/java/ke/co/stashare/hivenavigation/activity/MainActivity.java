package ke.co.stashare.hivenavigation.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import ke.co.stashare.hivenavigation.R;

public class MainActivity extends AppCompatActivity {

    ImageView companyIcon;
    CardView cardi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.induce_smile_collapse);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

       Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

       companyIcon = (ImageView)findViewById(R.id.imageViewCompany);
        cardi= (CardView)findViewById(R.id.direction_card_view);
    }

    public void register(View view) {

        View card = findViewById(R.id.direction_card_view);
        //View col_toolbar = findViewById(R.id.collapsing_toolbar);
        //View companyIcon = findViewById(R.id.imageViewCompany);
        //View button = findViewById(R.id.button);

        Intent intent = new Intent(this,  Register.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Pair<View, String> pair1 = Pair.create(card, card.getTransitionName());
                //Pair<View, String> pair2 = Pair.create(col_toolbar, col_toolbar.getTransitionName());
                //Pair<View, String> pair3 = Pair.create(button, button.getTransitionName());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1);
                startActivity(intent, options.toBundle());

            }
            else {
                startActivity(intent);
            }

    }

    public void signIn(View view) {
        View card = findViewById(R.id.direction_card_view);
        //View companyIcon = findViewById(R.id.imageViewCompany);
        //View button = findViewById(R.id.button);

        Intent intent = new Intent(this,  SignIn.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Pair<View, String> pair1 = Pair.create(card, card.getTransitionName());
            //Pair<View, String> pair2 = Pair.create(companyIcon, companyIcon.getTransitionName());
            //Pair<View, String> pair3 = Pair.create(button, button.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1);
            startActivity(intent, options.toBundle());

        }
        else {
            startActivity(intent);
        }

    }
}
