package br.com.maknamara.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Objects;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import br.com.maknamara.component.CustomHandlerThread;
import br.com.maknamara.di.DI;
import br.com.maknamara.model.exceptions.RuleException;

@SuppressWarnings({"unused", "unchecked"})
public class BaseActivity extends AppCompatActivity {

    protected BaseActivity baseActivity;
    protected CustomHandlerThread customHandlerThread;
    private long pressedTime;

    public BaseActivity() {
        baseActivity = this;
        customHandlerThread = new CustomHandlerThread();
    }

    @Override
    public void onBackPressed() {
        if (getClass().getSimpleName().equals("MainActivity")) {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
            } else {
                showToastShort(br.com.maknamara.activity.R.string.press_back_again_to_exit);
            }
            pressedTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DI.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customHandlerThread.quit();
    }

    protected void handleExceptions(Exception e) {

        Throwable throwable = e;

        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        StringBuilder sb = new StringBuilder();

        if (RuleException.class.isAssignableFrom(throwable.getClass())) {
            String[] keyMessages = Objects.requireNonNull(throwable.getMessage()).split(",");
            for (String key : keyMessages) {
                sb.append(getResources().getString(Integer.parseInt(key)));
                sb.append("\r\n");
            }
        } else {
            sb.append(throwable.getMessage());
            sb.append(":\r\n");
            for (StackTraceElement st : throwable.getStackTrace()) {
                sb.append(st.toString());
                sb.append("\r\n");
            }
        }
        this.log(sb, e);
        showAlertOk(sb.toString());
    }

    protected void showToastLong(@StringRes int resourceId) {
        showToastLong(getString(resourceId));
    }

    protected void showToastLong(@NonNull CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showToastShort(@StringRes int resourceId) {
        showToastShort(getString(resourceId));
    }

    protected void showToastShort(@NonNull CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void log(@NonNull CharSequence message) {
        Log.v(getClass().getName() + "handleExceptions", message.toString());
    }

    protected void log(Throwable e) {
        log(e.getMessage(), e);
    }

    protected void log(@NonNull CharSequence message, Throwable e) {
        Log.v(getClass().getName() + "handleExceptions", message.toString(), e);
    }

    protected void showAlertOk(int messageId) {
        showAlertOk(getResources().getString(messageId));
    }

    protected void showAlertOk(CharSequence message) {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.message_alert);
            builder.setMessage(message);
            builder.setPositiveButton("OK", null);
            builder.setIcon(android.R.drawable.ic_dialog_info);

            builder.create().show();
        });
    }

    protected void showAlertOkCancel(int messageId, DialogInterface.OnClickListener onClickListener) {
        showAlertOkCancel(getResources().getString(messageId), onClickListener);
    }

    protected void showAlertOkCancel(CharSequence message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.message_alert);
        builder.setMessage(message);
        builder.setPositiveButton("YES", onClickListener);
        builder.setNegativeButton("NO", null);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.create().show();
    }

    protected AlertDialog showAwait() {
        return showAwait(br.com.maknamara.activity.R.string.message_await);
    }

    protected AlertDialog showAwait(int resourceId) {
        return showAwait(getString(resourceId));
    }

    protected AlertDialog showAwait(CharSequence message) {

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

        return builder.create();
    }

    protected boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected void chekPermissions(String... permissions) {
        boolean hasPermission = true;
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                hasPermission = false;
                break;
            }
        }

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    protected SSLSocketFactory getSSLSocketFactory(@NonNull String crtFileName) throws Exception {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        BufferedInputStream bis = new BufferedInputStream(getAssets().open(crtFileName));
        Certificate ca;
        try {
            ca = cf.generateCertificate(bis);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            bis.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        // Tell the URLConnection to use a SocketFactory from our SSLContext
        return context.getSocketFactory();
    }

    protected <RESULT> RESULT invoke(@NonNull String methodName, @NonNull Object... values) {
        Class<?>[] classes = new Class[values.length];
        int i = 0;
        for (Object o : values) {
            classes[i++] = o.getClass();
        }
        Method method;
        try {
            method = getClass().getDeclaredMethod(methodName, classes);
            method.setAccessible(true);
            return (RESULT) method.invoke(this, values);
        } catch (Exception e) {
            try {
                method = getClass().getMethod(methodName, classes);
                method.setAccessible(true);
                return (RESULT) method.invoke(this, values);
            } catch (Exception e_) {
                throw new RuntimeException(e_);
            }
        }
    }
}