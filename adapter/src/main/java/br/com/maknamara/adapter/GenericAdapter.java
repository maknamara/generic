package br.com.maknamara.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public abstract class GenericAdapter<T> extends ArrayAdapter<T> {

    private int resourceId;

    public GenericAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.resourceId = resource;
    }

    public GenericAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    public GenericAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    public int getResourceId() {
        return resourceId;
    }

    public final void update(T[] data) {
        clear();
        addAll(data);
    }

    public final void update(List<T> data) {
        clear();
        addAll(data);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);
}
