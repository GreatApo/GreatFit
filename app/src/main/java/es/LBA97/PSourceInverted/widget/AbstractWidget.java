package es.LBA97.PSourceInverted.widget;

import android.app.Service;
import android.graphics.drawable.Drawable;

import java.util.Collections;
import java.util.List;

import es.LBA97.PSourceInverted.data.DataType;
import es.LBA97.PSourceInverted.data.MultipleWatchDataListener;


public abstract class AbstractWidget implements Widget, MultipleWatchDataListener {

    private int x = 0;
    private int y = 0;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public void init(Service service) {
        
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.emptyList();
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {

    }

    protected void setDrawableBounds(Drawable drawable, float x, float y) {
        drawable.setBounds((int) x, (int) y, (int) x + drawable.getMinimumWidth(), (int) y + drawable.getMinimumHeight());
    }
}
