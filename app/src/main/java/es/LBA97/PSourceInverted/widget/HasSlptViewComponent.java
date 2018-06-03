package es.LBA97.PSourceInverted.widget;

import android.app.Service;

import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.util.List;


public interface HasSlptViewComponent {

    List<SlptViewComponent> buildSlptViewComponent(Service service);
}
