package es.LBA97.PSourceInverted.widget;

import android.app.Service;
import android.graphics.Canvas;


public abstract class AnalogClockWidget implements ClockWidget {

    @Override
    public void init(Service service) {
        
    }

    public abstract void onDrawAnalog(Canvas canvas, float width, float height, float centerX, float centerY, float secRot, float minRot, float hrRot);
}
