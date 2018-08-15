package com.digitaldna.supplier.ui.screens.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.EmptyBean;
import com.digitaldna.supplier.network.beans.GetOrderDetailsBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.requests.BaseWithLangIdRequest;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.ui.screens.MainActivity;
import com.digitaldna.supplier.ui.screens.MainMenuFragment;
import com.digitaldna.supplier.ui.screens.OrdersFragment;
import com.digitaldna.supplier.ui.screens.SettingsActivity;
import com.digitaldna.supplier.utils.PrefProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangeLanguageActivity extends Activity {
    public ListView listView;
    private String[] data = { "Turkce", "English" };
    private List<LanguageItem> languageItems = new ArrayList<LanguageItem>();
    public static int chosenLangId;
    public static String chosenLangShortName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        languageItems.add(new LanguageItem("Türkçe", 0, "TR"));
        languageItems.add(new LanguageItem("English", 1, "EN"));

        listView = (ListView) findViewById(R.id.listViewLanguageList);
        LanguageAdapter languageAdapter = new LanguageAdapter(getApplicationContext(), languageItems);
        listView.setAdapter(languageAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        Button saveLang = (Button) findViewById(R.id.buttonSaveLanguage);
        saveLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseWithLangIdRequest changeLanguageRequest = new BaseWithLangIdRequest(chosenLangId,
                        PrefProvider.getEmail(ChangeLanguageActivity.this),
                        PrefProvider.getTicket(ChangeLanguageActivity.this));

                RestClient.getInstance().create(NetworkAPIsInterface.class).changeLanguage(changeLanguageRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> Log.i("ChangeLanguageResult", result.toString()) , e -> Log.i("ChangeLanguageResult", "error" + e.toString()));;

                PrefProvider.saveLanguage(ChangeLanguageActivity.this, chosenLangShortName);
                PrefProvider.saveLanguageId(ChangeLanguageActivity.this, chosenLangId);
                setLanguage(chosenLangShortName);
                //Intent intent = new Intent(ChangeLanguageActivity.this, SettingsActivity.class);
               // startActivity(intent);
                MainMenuFragment.deleteInstance();
                OrdersFragment.deleteInstance();
                Intent intent = new Intent(ChangeLanguageActivity.this, MainActivity.class);
                startActivity(intent);
                //ChangeLanguageActivity.this.finish();

            }
        });

        ImageView imageView = (ImageView)findViewById(R.id.iv_toolbar_close);
        imageView.setOnClickListener(view -> onBackPressed());
    }



    private void handleError(Throwable t){
        Log.i("CCCC", "ERRRRRR "+ BaseJsonDataBean.mStatusText);
        Log.i("CCCC", "getCause "+ t.getCause());
        Log.i("CCCC", "getLocalizedMessage "+ t.getLocalizedMessage());
        Log.i("CCCC", "getStackTrace "+ t.getStackTrace());
    }

    public void setLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
