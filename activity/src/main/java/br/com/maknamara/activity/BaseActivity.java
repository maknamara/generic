package br.com.maknamara.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
        }
        showAlertOk(sb.toString());
        Log.v(getClass().getName() + "handleExceptions", e.getMessage() + ": " + sb, e);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout ll = new LinearLayout(this);

        ll.setGravity(Gravity.CENTER | Gravity.START);

        ProgressBar pb = new ProgressBar(this);
        TextView tv = new TextView(this);
        tv.setText(message);

        pb.setIndeterminate(true);

        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(pb);
        ll.addView(tv);

        builder.setView(ll);

        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }
}