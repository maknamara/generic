package br.com.maknamara.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.maknamara.activity.BaseActivity;
import br.com.maknamara.activity.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);

        ImageView imageView = findViewById(R.id.logoImageView);
        imageView.setImageResource(android.R.drawable.ic_menu_myplaces);

        try{
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);

            TextView applicationName = findViewById(R.id.applicationName);
            ImageView logoImageView = findViewById(R.id.logoImageView);

            logoImageView.setImageResource(info.applicationInfo.icon);
            applicationName.setText(info.applicationInfo.labelRes);


        }catch (Exception e){
            handleExceptions(e);
        }
    }
}