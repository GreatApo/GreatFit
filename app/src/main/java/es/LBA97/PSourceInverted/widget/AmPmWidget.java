package es.LBA97.PSourceInverted.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.Settings;
import android.text.TextPaint;

import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodayFloorNumView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import es.LBA97.PSourceInverted.data.AmPm;
import es.LBA97.PSourceInverted.data.DataType;
import es.LBA97.PSourceInverted.data.TodayFloor;
import es.LBA97.PSourceInverted.data.WeatherData;
import es.LBA97.PSourceInverted.resource.ResourceManager;
import pt.neno.prototype.R;


public class AmPmWidget extends AbstractWidget {
    private TextPaint textPaint;
    private AmPm timePeriod;
    private String text;
    private int textint;

    private float textTop;
    private float textLeft;

    @Override
    public void init(Service service){
        this.textLeft = service.getResources().getDimension(R.dimen.ampm_left);
        this.textTop = service.getResources().getDimension(R.dimen.ampm_top);

        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(service.getResources().getColor(R.color.ampm_colour));
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.ampm_font_size));
        this.textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.AMPM);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        this.timePeriod = (AmPm) value;
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        Calendar now = Calendar.getInstance();
        String periode = (now.get(Calendar.AM_PM) == Calendar.AM)?"am":"pm";
        this.text = String.format("%S", periode);//Capitalize
        canvas.drawText(text, textLeft, textTop, textPaint);
    }

    public AmPm getSlptAmPm() {
        return new AmPm();
    }


    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {

        this.timePeriod = getSlptAmPm();

        SlptLinearLayout ampm = new SlptLinearLayout();
        SlptPictureView ampmStr = new SlptPictureView();
        ampmStr.setStringPicture( this.timePeriod.getAmPmStr() );
        ampm.add(ampmStr);
        ampm.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.ampm_font_size),
                service.getResources().getColor(R.color.ampm_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        ampm.alignX = 2;
        ampm.alignY=0;
        ampm.setRect(
                (int) (2*service.getResources().getDimension(R.dimen.ampm_left)+640),
                (int) (service.getResources().getDimension(R.dimen.ampm_font_size))
        );
        ampm.setStart(
                -320,
                (int) (service.getResources().getDimension(R.dimen.ampm_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.ampm_font_size))
        );

        return Arrays.asList(new SlptViewComponent[]{ampm});
    }
}
