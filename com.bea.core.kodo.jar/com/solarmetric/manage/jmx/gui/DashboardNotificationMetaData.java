package com.solarmetric.manage.jmx.gui;

import java.util.ArrayList;
import java.util.List;

public class DashboardNotificationMetaData {
   private String _name;
   private List _chartPanels = new ArrayList();

   public DashboardNotificationMetaData(String name) {
      this._name = name;
   }

   public String getName() {
      return this._name;
   }

   public void add(DashboardChartPanelMetaData chartPanel) {
      this._chartPanels.add(chartPanel);
   }
}
