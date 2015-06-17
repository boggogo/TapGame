package koemdzhiev.com.eggyegg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;


public class ContributorsActivity extends AppCompatActivity {
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);

        mLayout = (RelativeLayout)findViewById(R.id.contributorsRelativeLayout);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
