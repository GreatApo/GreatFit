package com.dinodevs.greatfitwatchface.resource;

import com.ingenic.iwds.slpt.view.digital.SlptTimeView;

public class SlptAnalogAmPmView extends SlptTimeView {
    public SlptAnalogAmPmView() {
    }

    protected short initType() {
        return SVIEW_ALTITUDE;
    }
}