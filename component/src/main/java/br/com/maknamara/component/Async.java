package br.com.maknamara.component;

import java.util.List;

@SuppressWarnings({"unchecked", "unused"})
public class Async<P, T> extends Thread {

    private final Executable<P, T> executable;
    private final Manipulable<T> manipulable;
    private final P[] params;

    @SafeVarargs
    public Async(Executable<P, T> executable, Manipulable<T> manipulable, P... params) {
        super();

        this.executable = executable;
        this.manipulable = manipulable;
        this.params = params;
    }

    @Override
    public void run() {
        try {
            List<T> result = executable.execute(params);
            manipulable.manipulate(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface Executable<P, T> {
        List<T> execute(P... params) throws Exception;
    }

    public interface Manipulable<T> {
        void manipulate(List<T> result) throws Exception;
    }
}