package koemdzhiev.com.eggyegg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private int i;
    private Toolbar mToolbar;
    private Button mRestartButton;
    private TextView mSecretMessage;
    private TextView mSecretMessageSubtitle;
    private ImageView tapImage;
    private TextView nextEggLabel;
    private int nextEggCounter;
    private boolean soundsState;
    TextView mTitle;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Vibrator mVibrator;
    View.OnClickListener mTapListener;
    //here enter start value to count down
    private final int startNextEggValue = 20;
    private final int startCountDownValue = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Google ads
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        if(mAdView != null) {
            mAdView.loadAd(adRequest);
        }

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        nextEggLabel = (TextView)findViewById(R.id.nextEggCount);
        mTapListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                nextEggCounter--;
                mEditor.putInt(MyConstants.KEY_TAB_NUMBER,i).apply();
                mEditor.putInt(MyConstants.KEY_TAB_NEXT_EGG,nextEggCounter).apply();
                Log.d(TAG, mSharedPreferences.getInt(MyConstants.KEY_TAB_NUMBER, 0) + "");
                mTitle.setText(i + "");
                nextEggLabel.setText("Next egg in:"+nextEggCounter);
                //apply animation
                YoYo.with(Techniques.Pulse)
                        .duration(40)
                        .playOn(findViewById(R.id.tapImage));

                setEggImage();
            }

        };
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSecretMessage = (TextView) findViewById(R.id.secretMessage);
        mSecretMessage.setVisibility(View.INVISIBLE);
        mSecretMessageSubtitle = (TextView) findViewById(R.id.secretMessage_subtitle);
        mSecretMessageSubtitle.setVisibility(View.INVISIBLE);
        mRestartButton = (Button) findViewById(R.id.restartButton);
        mRestartButton.setVisibility(View.INVISIBLE);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putInt(MyConstants.KEY_TAB_NUMBER, startCountDownValue).apply();
                i = startCountDownValue;
                tapImage.setImageResource(R.drawable.egg1);
                mTitle.setText(i + "");
                YoYo.with(Techniques.FadeOut).duration(500).playOn(findViewById(R.id.restartButton));
                YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.tapImage));
                mRestartButton.setVisibility(View.INVISIBLE);
                tapImage.setOnClickListener(mTapListener);
                mSecretMessage.setVisibility(View.INVISIBLE);
                mSecretMessageSubtitle.setVisibility(View.INVISIBLE);
                restartNextEggCounter();
            }
        });
        tapImage = (ImageView)findViewById(R.id.tapImage);
        tapImage.setOnClickListener(mTapListener);
        tapImage.setSoundEffectsEnabled(true);
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        nextEggCounter = mSharedPreferences.getInt(MyConstants.KEY_TAB_NEXT_EGG, 20);
        i = mSharedPreferences.getInt(MyConstants.KEY_TAB_NUMBER, startCountDownValue);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(i + "");
        //set the sound off/on
        soundsState = mSharedPreferences.getBoolean(MyConstants.KEY_SOUNDS_STATE,true);
        tapImage.setSoundEffectsEnabled(soundsState);

        nextEggLabel.setText("Next egg in:"+nextEggCounter);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 65);
        mTitle.setTextColor(getResources().getColor(R.color.text_white_color));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(null);
        }
        setEggImage();

    }

    private void setEggImage() {
        //840000
        if(i <= 100){
            tapImage.setImageResource(R.drawable.egg2);
            if(i == 100){
                mVibrator.vibrate(200);
                achievementCeremony();
            }
        }
        //680000
        if(i <= 80){
            tapImage.setImageResource(R.drawable.egg3);
            if(i == 80){
                mVibrator.vibrate(200);
                achievementCeremony();
            }
        }
        //520000
        if(i <= 60){
            tapImage.setImageResource(R.drawable.egg4);
            if(i == 60) {
                mVibrator.vibrate(200);
                achievementCeremony();
            }
        }
        //360000
        if(i <= 40){
            tapImage.setImageResource(R.drawable.egg5);
            if(i == 40){
                mVibrator.vibrate(200);
                achievementCeremony();
            }
        }
        //200000
        if(i <= 20){
            tapImage.setImageResource(R.drawable.egg6);
            if(i == 20){
                mVibrator.vibrate(200);
                achievementCeremony();
            }
            if(i == 0){
                mSecretMessage.setVisibility(View.VISIBLE);
                mSecretMessageSubtitle.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn).duration(1900).playOn(mSecretMessage);
                YoYo.with(Techniques.FadeOut).duration(1500).playOn(tapImage);
                mRestartButton.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn).duration(1500).playOn(mRestartButton);
                tapImage.setOnClickListener(null);
            }
        }
        //check nextEggCounter
        if(nextEggCounter == 0 && i !=0){
            restartNextEggCounter();
        }
    }

    private void restartNextEggCounter() {
        nextEggCounter = startNextEggValue;
        mEditor.putInt(MyConstants.KEY_TAB_NEXT_EGG,startNextEggValue).apply();
        nextEggLabel.setText("Next egg in:"+nextEggCounter);
    }

    private void achievementCeremony() {
        YoYo.with(Techniques.FadeOut).duration(300).playOn(mTitle);
        YoYo.with(Techniques.FadeIn).duration(300).playOn(mTitle);
        YoYo.with(Techniques.Swing).duration(800).playOn(tapImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem soundEffectsItem = menu.findItem(R.id.action_mute);
        soundEffectsItem.setChecked(!soundsState);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mute) {
            //trigger check
            if(item.isChecked()){
                item.setChecked(false);
                tapImage.setSoundEffectsEnabled(true);
                mEditor.putBoolean(MyConstants.KEY_SOUNDS_STATE,true).apply();
            }else{
                item.setChecked(true);
                tapImage.setSoundEffectsEnabled(false);
                mEditor.putBoolean(MyConstants.KEY_SOUNDS_STATE,false).apply();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
