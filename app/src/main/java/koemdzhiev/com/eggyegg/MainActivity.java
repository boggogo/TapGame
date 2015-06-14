package koemdzhiev.com.eggyegg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private int i;
    private Toolbar mToolbar;
    private ImageView tapImage;
    TextView mTitle;
    private TextView countTap;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // countTap = (TextView)findViewById(R.id.countTap);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        tapImage = (ImageView)findViewById(R.id.tapImage);
        tapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                mEditor.putInt(MyConstants.KEY_TAB_NUMBER,i).apply();
                Log.d(TAG, mSharedPreferences.getInt(MyConstants.KEY_TAB_NUMBER, 0) + "");
                mTitle.setText(i + "");
                //apply animation
                YoYo.with(Techniques.Pulse)
                        .duration(40)
                        .playOn(findViewById(R.id.tapImage));

                setEggImage();
            }
        });
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        i = mSharedPreferences.getInt(MyConstants.KEY_TAB_NUMBER, 1000000);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(i + "");
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 65);

        mTitle.setTextColor(getResources().getColor(R.color.text_white_color));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(null);
        }
        setEggImage();


    }

    private void setEggImage() {
        if(i < 840000){
            tapImage.setImageResource(R.drawable.egg2);
        }
        if(i < 680000){
            tapImage.setImageResource(R.drawable.egg3);
        }
        if(i < 520000){
            tapImage.setImageResource(R.drawable.egg4);
        }
        if(i < 360000){
            tapImage.setImageResource(R.drawable.egg5);
        }
        if(i < 200000){
            tapImage.setImageResource(R.drawable.egg6);
        }
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
