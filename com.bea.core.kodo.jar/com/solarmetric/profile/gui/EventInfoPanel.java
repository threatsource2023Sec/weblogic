package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.profile.EventInfo;

public abstract class EventInfoPanel extends XPanel {
   public abstract void setInfo(EventInfo var1);

   public abstract void update();
}
