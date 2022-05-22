package br.com.maknamara.component;

import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"unchecked", "unused"})
public class Async<P, T> {

    private final Executable<P, T> executable;
    private final Manipulable<T> manipulable;

    public Async(Executable<P, T> executable, Manipulable<T> manipulable) {
        super();
        this.executable = executable;
        this.manipulable = manipulable;
    }

    @SafeVarargs
    public final void execute(P... params) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            try {
                List<T> result = executable.execute(params);
                handler.post(() -> {
                    try {
                        manipulable.manipulate(result);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FunctionalInterface
    public interface Executable<P, T> {
        List<T> execute(P... params) throws Exception;
    }

    @FunctionalInterface
    public interface Manipulable<T> {
        void manipulate(List<T> result) throws Exception;
    }
}