package tw.org.iii.mypen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView =(MyView)findViewById(R.id.myView);
    }

    @Override
    public void finish() {
        myView.getTimer().cancel();
        super.finish();
    }
}
