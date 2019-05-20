package com.dinodevs.greatfitwatchface.resource;

import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.utils.KeyWriter;

public class SlptOrientedLayout extends SlptLayout {
    public byte orientation = 0;

    public SlptOrientedLayout() {
    }

    protected short initType() {
        return SVIEW_LINEAR_LAYOUT;
    }

    public void setOrientation(byte or) {
        this.orientation = or;
    }

    public void writeConfigure(KeyWriter var1) {
        super.writeConfigure(var1);
        var1.writeByte(this.orientation);
    }
}
