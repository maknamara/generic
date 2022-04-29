package br.com.maknamara.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import br.com.maknamara.model.exceptions.RuleException;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void handleExceptions(Exception e) {

        Throwable t = e;

        while (t.getCause() != null) {
            t = t.getCause();
        }

        StringBuilder sb = new StringBuilder();

        if (RuleException.class.isAssignableFrom(t.getClass())) {
            String[] keyMessages = Objects.requireNonNull(t.getMessage()).split(",");
            for (String key : keyMessages) {
                sb.append(getResources().getString(Integer.parseInt(key)));
                sb.append("\r\n");
            }
        } else {
            sb.append(t.getMessage());
            sb.append(":\r\n");
            for (StackTraceElement st : t.getStackTrace()) {
                sb.append(st.toString());
                sb.append("\r\n");
            }
        }
        this.log(sb.toString(), e);
        showAlertOk(sb.toString());
    }

    protected void log(String message) {
        Log.v(getClass().getName() + "handleExceptions", message);
    }

    protected void log(Throwable e) {
        log(e.getMessage(), e);
    }

    protected void log(String message, Throwable e) {
        Log.v(getClass().getName() + "handleExceptions", message, e);
    }

    protected void showAlertOk(int messageId) {
        showAlertOk(getResources().getString(messageId));
    }

    protected void showAlertOk(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.message_alert);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setIcon(android.R.drawable.ic_dialog_info);

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void showAlertOkCancel(int messageId, DialogInterface.OnClickListener onClickListener) {
        showAlertOkCancel(getResources().getString(messageId), onClickListener);
    }

    protected void showAlertOkCancel(String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.message_alert);
        builder.setMessage(message);
        builder.setPositiveButton("YES", onClickListener);
        builder.setNegativeButton("NO", null);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected AlertDialog showAwait() {
        return showAwait(br.com.maknamara.activity.R.string.message_await);
    }

    protected AlertDialog showAwait(int resourceId) {
        return showAwait(getString(resourceId));
    }

    protected AlertDialog showAwait(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        LinearLayout ll = new LinearLayout(BaseActivity.this);
        ProgressBar pb = new ProgressBar(BaseActivity.this);
        TextView tv = new TextView(BaseActivity.this);

        ll.setGravity(Gravity.CENTER | Gravity.START);
        pb.setIndeterminate(true);
        tv.setText(message);

        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(pb);
        ll.addView(tv);

        builder.setView(ll);

        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    protected void chekPermission(String... permissions) {
        boolean hasPermission = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
                break;
            }
        }

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }
}