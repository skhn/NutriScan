package szk.nutriscan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void registerMethod(View view) {

        EditText email = (EditText) findViewById(R.id.email_text_reg) ;
        String eml = email.getText().toString();

       if(LoginSignupActivity.isValidEmail(eml)) {

       }

    }


    public void backMethod(View view) {

    }
}
