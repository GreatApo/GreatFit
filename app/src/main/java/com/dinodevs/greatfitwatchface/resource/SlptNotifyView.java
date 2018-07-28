package com.dinodevs.greatfitwatchface.resource;

import com.ingenic.iwds.slpt.view.digital.SlptTimeView;

public class SlptNotifyView extends SlptTimeView {
    public SlptNotifyView() {
    }

    protected short initType() {
        return SVIEW_NOTIFY;
    }
}