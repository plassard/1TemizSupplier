package com.digitaldna.supplier.ui.screens.authorization;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.beans.GetVerifCodeBean;
import com.digitaldna.supplier.network.beans.LoginSupplierBean;
import com.digitaldna.supplier.network.beans.VerifPhoneNumberBean;
import com.digitaldna.supplier.network.beans.VerificationCodeBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.GetVerificationCodeRequest;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.network.requests.VerifyPhoneRequest;
import com.digitaldna.supplier.ui.screens.MainActivity;
import com.digitaldna.supplier.ui.screens.MainMenuFragment;
import com.digitaldna.supplier.ui.screens.OrdersFragment;
import com.digitaldna.supplier.utils.PrefProvider;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/*
import static com.dijitaldna.customer.StaticMethods.deletePref;
import static com.dijitaldna.customer.StaticMethods.getPref;
import static com.dijitaldna.customer.StaticMethods.savePref;*/

/**
 * Allows users to enter sms verification code just after sign up operation.
 */
public class SmsVerificationActivity extends AppCompatActivity {
    public static String ticket;
    public static String mail, userID;
    public static String phoneNumber;
    public static String verificationCode;
    public static String selectedCode = null;
    public static boolean doGetSmsCode;
    private long startTime;
    private final long smsValidationInterval = 1 * 1000;//decrease seconds by 1 //saniyeyi 1er olarak azaltıyor
    Typeface tf;
    Button restartBtn;
    private CountDownTimer countDownTimer;
    private boolean hasTimerStarted = false;
    static boolean hideSkipButton = false;
    Button btnSkip;
    ImageView ivCircle1, ivCircle2, ivCircle3, ivCircle4;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_sms_varification);


//        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "opensans.ttf");
        selectedCode = null;
        TextView t11 = (TextView) findViewById(R.id.textView11);
        TextView t46 = (TextView) findViewById(R.id.textView46);
      //  t11.setTypeface(LoginActivity.typeface);
       // t46.setTypeface(LoginActivity.typeface);
        b1 = (Button) findViewById(R.id.buttonSMSVer1);
        b2 = (Button) findViewById(R.id.buttonSMSVer2);
        b3 = (Button) findViewById(R.id.button57);
        b4 = (Button) findViewById(R.id.button58);
        b5 = (Button) findViewById(R.id.button59);
        b6 = (Button) findViewById(R.id.button60);
        b7 = (Button) findViewById(R.id.button61);
        b8 = (Button) findViewById(R.id.button62);
        b9 = (Button) findViewById(R.id.button63);
        btnSubmit = (Button) findViewById(R.id.button65);
        b0 = (Button) findViewById(R.id.button64);
        ivCircle1 = (ImageView)findViewById(R.id.imageSMSCircle1);
        ivCircle2 = (ImageView)findViewById(R.id.imageSMSCircle2);
        ivCircle3 = (ImageView)findViewById(R.id.imageSMSCircle3);
        ivCircle4 = (ImageView)findViewById(R.id.imageSMSCircle4);

        final TextView t7 = (TextView) findViewById(R.id.editText7);
        btnSkip = (Button) findViewById(R.id.button74);
        //btnSubmit.setTypeface(LoginActivity.typeface);
        t7.setText(null);
        restartBtn = (Button) findViewById(R.id.button2);
        //restartBtn.setTypeface(LoginActivity.typeface);
        restartBtn.setClickable(true);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartActivity();
            }
        });
        /*if (ChangePhoneNumberActivity.countryCodeUrl == true) {
            HttpPost.url = Urls.UPDATE_USER_PHONE_NUMBER;
        } else {
            HttpPost.url = Urls.GET_USER_VERIFICATION_CODE;
            doGetSmsCode = true;
        }*/

     /*   HttpPost gp = new HttpPost(SmsVerificationActivity.this);

        try {
            String serverResponse = gp.execute().get();
            if (serverResponse != null) {
                JSONObject jo = new JSONObject(serverResponse);
                String StatusCode = jo.getString("StatusCode");
                String Data = jo.getString("Data");
                JSONObject jo1 = new JSONObject(Data);
                mail = jo1.getString("UserID");
                ticket = jo1.getString("Ticket");
                startTime = jo1.getLong("Countdown");
                startTime = startTime * 1000;
                Log.i("RRRR", "startTime " + startTime);
                if (StatusCode.equals("100")) {
                    //  Toast.makeText(getApplicationContext(), "sms kodunu yazınız", Toast.LENGTH_SHORT).show();
                }
                Log.i("info1", jo.toString());
                doGetSmsCode = false;
                ChangePhoneNumberActivity.countryCodeUrl = false;


                continueOnCreateMethod();

            } else {
                StaticMethods.showWarning(this);
                return;
            }
        } catch (Exception ex) {
            StaticMethods.writeExceptionLog(SmsVerificationActivity.this, ex);
            ex.printStackTrace();
        }
        //countDownTimer = new MyCountDownTimer(startTime, smsValidationInterval);

*/


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GGG", "clicked 1");
                if (selectedCode != null) {
                    selectedCode = selectedCode + "1";
                } else {
                    selectedCode = "1";
                }
                //t7.setText(selectedCode);
                //t7.setTypeface(LoginActivity.typeface);
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        Log.i("GGG", "setting ");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GGG", "clicked 2");
                if (selectedCode != null) {
                    selectedCode = selectedCode + "2";
                } else {
                    selectedCode = "2";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GGG", "clicked 3");
                if (selectedCode != null) {
                    selectedCode = selectedCode + "3";
                } else {
                    selectedCode = "3";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GGG", "clicked 4");
                if (selectedCode != null) {
                    selectedCode = selectedCode + "4";
                } else {
                    selectedCode = "4";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCode != null) {
                    selectedCode = selectedCode + "5";
                } else {
                    selectedCode = "5";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCode != null) {
                    selectedCode = selectedCode + "6";
                } else {
                    selectedCode = "6";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCode != null) {
                    selectedCode = selectedCode + "7";
                } else {
                    selectedCode = "7";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("OOO", "click");
                if (selectedCode != null) {
                    Log.i("OOO", "not null" + selectedCode);
                    selectedCode = selectedCode + "8";
                } else {
                    Log.i("OOO", "null");
                    selectedCode = "8";
                }
                Log.i("OOO", "paint");
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCode != null) {
                    selectedCode = selectedCode + "9";
                } else {
                    selectedCode = "9";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCode != null) {
                    selectedCode = selectedCode + "0";
                } else {
                    selectedCode = "0";
                }
                paintCircles(selectedCode);
                autoGetCode(selectedCode);
            }
        });
        btnSubmit.setVisibility(View.GONE);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCode != null && selectedCode.length() > 0) {
                    //t7.setText(t7.getText().toString().substring(0, t7.length() - 1));
                    //selectedCode = t7.getText().toString();
                    selectedCode = selectedCode.substring(0, (selectedCode.length() - 1));
                    paintCircles(selectedCode);
                }else {
                    selectedCode = "";
                    paintCircles(selectedCode);
                }
            }
        });
        getVerifCode();
    }


    public void continueOnCreateMethod(){
      /*  if (getPref(SmsVerificationActivity.this, "change_phone").equals("okey")) {
            //btnSkip.setVisibility(View.INVISIBLE);
        }*/
        if(hideSkipButton){
            btnSkip.setVisibility(View.INVISIBLE);
            hideSkipButton = false;
        } else {
            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (getPref(getApplicationContext(), "return_to_new_order").equals("1")) {
                        SmsVerificationActivity.this.finish();
                    } else {
                        if(MainActivity.mail.equals(null)){
                            MainActivity.mail = String.valueOf(getPref(SmsVerificationActivity.this, "mail"));}
                        if(ticket != null){
                            LoginActivity.activeTicket = ticket;
                        }
                        Log.i("jsonobje", " ticket ");
                        Log.i("jsonobje", " ticket "+ ticket);
                        Intent i = new Intent(SmsVerificationActivity.this, MainActivity.class);
                        startActivity(i);
                    }*/
                }
            });
        }

        Button submitBtn = (Button) findViewById(R.id.button66);
        submitBtn.setTypeface(tf);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (selectedCode.length() < 4) {
                        new AlertDialog.Builder(SmsVerificationActivity.this)
                                .setMessage(getResources().getString(R.string.sms_verification_correctly))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        doGetSmsCode = false;
                        new VerifyUserPhoneNumberTask().execute((Void) null);
                    }
                }catch (Exception e) {}
            }
        });
        countDownTimer = new MyCountDownTimer(startTime, smsValidationInterval);

        if (!hasTimerStarted) {
            countDownTimer.start();
            hasTimerStarted = true;
        } else {
            countDownTimer.cancel();
            hasTimerStarted = false;
        }

       /* howConnected = String.valueOf(getPref(this, "withcon"));
        savePref(this, "dologout", "true");*/
    }
    String howConnected = "";

    @Override
    protected void onStop() {
        super.onStop();
        hideSkipButton = false;
        btnSkip.setVisibility(View.VISIBLE);
    }

    private void paintCircles(String code){
        if(code.length() == 1){
            ivCircle1.setBackgroundResource(R.drawable.circle_black);
            ivCircle2.setBackgroundResource(R.drawable.circle_white);
            ivCircle3.setBackgroundResource(R.drawable.circle_white);
            ivCircle4.setBackgroundResource(R.drawable.circle_white);
        } else if(code.length() == 2){
            ivCircle1.setBackgroundResource(R.drawable.circle_black);
            ivCircle2.setBackgroundResource(R.drawable.circle_black);
            ivCircle3.setBackgroundResource(R.drawable.circle_white);
            ivCircle4.setBackgroundResource(R.drawable.circle_white);
        } else if(code.length() == 3){
            ivCircle1.setBackgroundResource(R.drawable.circle_black);
            ivCircle2.setBackgroundResource(R.drawable.circle_black);
            ivCircle3.setBackgroundResource(R.drawable.circle_black);
            ivCircle4.setBackgroundResource(R.drawable.circle_white);
        } else if(code.length() == 4){
            ivCircle1.setBackgroundResource(R.drawable.circle_black);
            ivCircle2.setBackgroundResource(R.drawable.circle_black);
            ivCircle3.setBackgroundResource(R.drawable.circle_black);
            ivCircle4.setBackgroundResource(R.drawable.circle_black);
        } else {
            ivCircle1.setBackgroundResource(R.drawable.circle_white);
            ivCircle2.setBackgroundResource(R.drawable.circle_white);
            ivCircle3.setBackgroundResource(R.drawable.circle_white);
            ivCircle4.setBackgroundResource(R.drawable.circle_white);
        }
    }

    //gelen sms kodunu girerken 4 haneyi tamamladı mı tamamlamadı mı ona bakıyor
    //checks if user has completed typing 4 digit sms verification code asynchronously.
    private void autoGetCode(String codesCustom) {
        if (codesCustom.length() == 4) {
            verificationCode = codesCustom;
            Log.i("GGG", "autoGetCode 2 " + verificationCode);
            checkVerifCode();
            //new VerifyUserPhoneNumberTask().execute();
        }
    }

    private void restartActivity() {
      /*  if (ChangePhoneNumberActivity.countryCodeUrl == true) {
            HttpPost.url = Urls.UPDATE_USER_PHONE_NUMBER;
        } else {
            HttpPost.url = Urls.GET_USER_VERIFICATION_CODE;
            doGetSmsCode = true;
        }
        HttpPost gp = new HttpPost(SmsVerificationActivity.this);
        if (LoginActivity.isGuestLogin == true) {
        }
        try {
            String serverResponse = gp.execute().get();
            if (serverResponse != null) {
                JSONObject jo = new JSONObject(serverResponse);
                String StatusCode = jo.getString("StatusCode");
                String Data = jo.getString("Data");
                JSONObject jo1 = new JSONObject(Data);
                mail = jo1.getString("UserID");
                ticket = jo1.getString("Ticket");
                if (StatusCode.equals("100")) {
                    //  Toast.makeText(getApplicationContext(), "sms kodunu yazınız", Toast.LENGTH_SHORT).show();
                }
                Log.i("info", jo.toString());
                doGetSmsCode = false;
                ChangePhoneNumberActivity.countryCodeUrl = false;
            } else {
                StaticMethods.showWarning(this);
                return;
            }
        } catch (Exception ex) {
            StaticMethods.writeExceptionLog(SmsVerificationActivity.this, ex);
            ex.printStackTrace();
        }
       // countDownTimer = new MyCountDownTimer(startTime, smsValidationInterval);
        if (!hasTimerStarted) {
            countDownTimer.start();
            hasTimerStarted = true;
            // startB.setText("STOP");
        } else {
            countDownTimer.cancel();
            hasTimerStarted = false;
            // startB.setText("RESTART");
        }*/
    }
    public void onBackPressed() {
    }


    private void getVerifCode(){
        GetVerificationCodeRequest getVerCodeRequest = new GetVerificationCodeRequest(PrefProvider.getEmail(this), PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getVerifCode(getVerCodeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResultGetVerCode(result) , e -> handleGetVerCodeError(e));
    }

    private void handleResultGetVerCode(GetVerifCodeBean getVerifCodeBean){
        VerificationCodeBean verificationCodeBean = getVerifCodeBean.getData();
        Log.i("LLL", "handleResult" + verificationCodeBean.getCountdown());
        startTime = verificationCodeBean.getCountdown();
        startTime = startTime * 1000;
        continueOnCreateMethod();
    }

    private void handleGetVerCodeError(Throwable t){

    }

    private void checkVerifCode(){
        VerifyPhoneRequest verCodeRequest = new VerifyPhoneRequest(
                PrefProvider.getCountryID(this),
                PrefProvider.getPhoneNumber(this),
                verificationCode,
                PrefProvider.getEmail(this),
                PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).verifyPhoneNumber(verCodeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResultCheckVerCode(result) , e -> handleCheckVerCodeError(e));
    }

    private void handleResultCheckVerCode(VerifPhoneNumberBean verifCodeBean){
        //VerificationCodeBean verificationCodeBean = verifCodeBean.getData();
        Log.i("LLL", "verifCodeBean ok");
        Log.i("LLL", "verifCodeBean " + verifCodeBean.getStatusText());
        Log.i("LLL", "verifCodeBean " + verifCodeBean.getStatusCode());

        if(verifCodeBean.getStatusCode() == 100) {
            MainMenuFragment.deleteInstance();
            OrdersFragment.deleteInstance();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.transparency_in_screen, R.anim.transparency_out);
        } else {
            //dialog
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_info);
            TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
            text.setText(BaseJsonBean.mStatusText);
            Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private void handleCheckVerCodeError(Throwable t){
        Log.i("LLL", "verifCodeBean err");
        Log.i("LLL", "handleResult" + t);
    }


    private class VerifyUserPhoneNumberTask extends AsyncTask<Void, Void, Void> {

        public String serverResponse;
        @Override
        protected Void doInBackground(Void... params) {
            Log.i("jsonobjeler ", "verofocatopm");
            //  Log.e("response", "responsesverify -----" + ticket);
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("Ticket", ticket);
                jsonObj.put("UserID", mail);
                jsonObj.put("VerificationCode", verificationCode);
                jsonObj.put("PhoneNumber", phoneNumber);
                String countryIDTemp = "1"; //getPref(getApplicationContext(), "countryID").toString();
                Log.i("jsonobjeler ", "countryID" + countryIDTemp);
                //I already commented it to make Facebook login work
                //if(countryIDTemp.equals("1")){countryIDTemp = "5";}
                if(countryIDTemp.equals("90")){countryIDTemp = "1";}
                if(countryIDTemp.equals("380")){countryIDTemp = "3";}
                jsonObj.put("CountryID", Integer.valueOf(countryIDTemp));
               // serverResponse = (new PostHelper(jsonObj, Urls.VERIFY_USER_PHONE_NUMBER)).getResponseData();
                Log.i("jsonobjeler ", jsonObj.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                if (serverResponse != null) {
                    JSONObject jsonObj1 = new JSONObject(serverResponse);
                    System.out.println(serverResponse);
                    String statuscode = jsonObj1.getString("StatusCode");
                    String statustext = jsonObj1.getString("StatusText");
                    if (statuscode.equals("100")) {

                       /* savePref(getApplicationContext(), "dologout", "false");
                        savePref(getApplicationContext(), "withcon", howConnected);
                        //
                        mail = MainActivity.mail;
                        StaticMethods.showToastMessage(SmsVerificationActivity.this, statustext);
                        if (getPref(SmsVerificationActivity.this, "change_phone").equals("okey")) {
                            deletePref(SmsVerificationActivity.this, "change_phone");
                            Intent i = new Intent(SmsVerificationActivity.this, ProfileSettingsActivity.class);
                            startActivity(i);
                        } else if (getPref(SmsVerificationActivity.this, "return_to_new_order").equals("1")) {
                            NewOrderFragment.isReturnedFromSuccessfulSmsVerification = true;
                            SmsVerificationActivity.this.finish();
                        } else{
                            Intent i = new Intent(SmsVerificationActivity.this, MainActivity.class);
                            startActivity(i);
                        }*/
                    } else {
                        Log.i("jsonobjeler", "verification sms answer " + jsonObj1.toString());
                        new AlertDialog.Builder(SmsVerificationActivity.this)
                                .setMessage(statustext)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        selectedCode = "";
                    }
                    String data = jsonObj1.getString("Data");
                    //JSONObject dta = new JSONObject(data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onFinish() {
            //deletePref(SmsVerificationActivity.this, "return_to_new_order");
            restartBtn.setText(getResources().getString(R.string.resend));
            restartBtn.setClickable(true);
            restartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    restartActivity();
                }
            });
        }
        @Override
        public void onTick(long millisUntilFinished) {
            restartBtn.setText("" + millisUntilFinished / 1000);
            restartBtn.setClickable(false);
        }
    }
}
