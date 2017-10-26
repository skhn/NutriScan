package szk.nutriscan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class LoginSignupActivity extends Activity {

    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        sharedPreferences = this.getSharedPreferences("package szk.nutriscan", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("DBV", LoginSignupActivity.sharedPreferences.getInt("DBV", 1)).apply();

    }

    // to check for email pattern
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void loginMethod(View view) {

        TextView err = (TextView) findViewById(R.id.err);
        EditText eml = (EditText) findViewById(R.id.email_text);
        EditText pwd = (EditText) findViewById(R.id.pw_text);
        String email = eml.getText().toString();
        String password = pwd.getText().toString();


        if (isValidEmail(email)) {
            if (email.equalsIgnoreCase(DataClass.getEm()) &&
                    password.equalsIgnoreCase(DataClass.getPw())) {

                err.setText("");
                err.setAlpha(0f);

                Intent intentLSA_DBA = new Intent(LoginSignupActivity.this,
                        DashBoardActivity.class);
                startActivity(intentLSA_DBA);

                eml.setText("");
                pwd.setText("");

            } else {
                err.setText("email and/or password is invalid \n" +
                        "please try again");
                err.setAlpha(1f);
            }

        } else {
            err.setText("Please enter a proper email id");
            err.setAlpha(1f);
        }

    }

    public void registerMethod(View view) {


        Intent intentLSA_DBA = new Intent(LoginSignupActivity.this,
                RegisterActivity.class);
        startActivity(intentLSA_DBA);
    }


    //TODO this is not working
    public void forgotMethod(View view) {

        TextView err = (TextView) findViewById(R.id.err);
        EditText eml = (EditText) findViewById(R.id.email_text);
        EditText pwd = (EditText) findViewById(R.id.pw_text);
        String email = eml.getText().toString();
        String password = pwd.getText().toString();

        if (isValidEmail(email) && email.equalsIgnoreCase(DataClass.getEm())) {
            try {
                new ForgotEmail().execute(email);
                err.setText("");
                err.setAlpha(0f);
            } catch (Exception e) {
                Toast toast = Toast.makeText(LoginSignupActivity.this, "Unable to send email", Toast.LENGTH_LONG);
                toast.show();
            }

        } else {
            err.setText("Enter your email id above.\n\n If it is registered on this device," +
                    " a password recovery email will be sent");
            err.setAlpha(1f);
        }

    }

    private class ForgotEmail extends AsyncTask<String, Void, Boolean> {

        GMailSender sender;

        @Override
        protected void onPreExecute() {
            sender = new GMailSender("app.recovery.service@gmail.com", "10094339434692344833");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast toast = Toast.makeText(LoginSignupActivity.this, "Successfully sent email with instructions", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        protected Boolean doInBackground(String... toSend) {
            String email = toSend[0];
            String pwdVar = DataClass.getPw();
            try {
                sender.sendMail(email, pwdVar);
                return true;
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
                return false;
            }
        }
    }

}
