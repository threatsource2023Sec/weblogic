package com.solarmetric.manage.jmx.gui;

import java.util.ArrayList;
import java.util.List;

public class DashboardMetaData {
   private String _name;
   private String _description;
   private List _chartPanels = new ArrayList();

   public DashboardMetaData(String name) {
      this._name = name;
      this._description = "";
   }

   public String getName() {
      return this._name;
   }

   public String getDescription() {
      return this._description;
   }

   public String getViewerName() {
      return null;
   }

   public void add(DashboardChartPanelMetaData chartPanel) {
      this._chartPanels.add(chartPanel);
   }

   public List getChartPanels() {
      return this._chartPanels;
   }

   public String toString() {
      return this._name;
   }
}
