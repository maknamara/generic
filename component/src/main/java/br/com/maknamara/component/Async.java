package br.com.maknamara.component;

import android.os.AsyncTask;

import java.util.List;

public class Async<T> extends AsyncTask<Void, Void, List<T>> {

    private final Executable executable;
    private final Manipulable manipulable;

    public Async(Executable<T> executable, Manipulable<T> manipulable) {
        this.executable = executable;
        this.manipulable = manipulable;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<T> doInBackground(Void... params) {
        try {
            return executable.execute(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(List<T> result) {
        try {
            manipulable.manipulate(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.onPostExecute(result);
    }

    public static interface Executable<T> {
        List<T> execute(Void... params) throws Exception;
    }

    public static interface Manipulable<T> {
        void manipulate(List<T> result) throws Exception;
    }
}