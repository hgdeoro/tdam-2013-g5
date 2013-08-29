package ar.com.hgdeoro.tdam.holamundo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class ProgressBarActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        ((Button) findViewById(R.id.btMas)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                pb.setProgress(pb.getProgress() + 10);
            }
        });
        ((Button) findViewById(R.id.btMenos)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                pb.setProgress(pb.getProgress() - 10);
            }
        });
    }

}
