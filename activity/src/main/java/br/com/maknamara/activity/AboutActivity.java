package br.com.maknamara.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);

        try {
            ImageView imageView = findViewById(R.id.logoImageView);
            imageView.setImageResource(android.R.drawable.ic_menu_myplaces);
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);

            TextView applicationName = findViewById(R.id.applicationName);
            TextView applicationVersion = findViewById(R.id.applicationVersion);
            TextView lastUpdate = findViewById(R.id.lastUpdate);

            imageView.setImageResource(info.applicationInfo.icon);
            applicationName.setText(info.applicationInfo.labelRes);

            applicationVersion.setText(String.format("V%s", info.versionName));
            lastUpdate.setText(String.format("%s%s", getString(R.string.last_update_time), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.US).format(new Date(info.lastUpdateTime))));

        } catch (Exception e) {
            handleExceptions(e);
        }
    }
}