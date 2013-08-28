package ar.com.hgdeoro.tdam.holamundo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.wasteMoreMemoryButton);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText text = (EditText) findViewById(R.id.status);
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < 1000; i++)
					sb.append("Jo jo jo! Feliz festivus! ");
				text.setText(text.getText() + sb.toString());
				Log.i(this.getClass().getName(), "Add: " + sb.length()
						+ " bytes?");
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
