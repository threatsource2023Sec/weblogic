package com.solarmetric.manage.jmx.gui;

import java.util.ArrayList;
import java.util.List;

public class DashboardMBeanMetaData {
   private String _name;
   private List _notifs = new ArrayList();

   public DashboardMBeanMetaData(String name) {
      this._name = name;
   }

   public String getName() {
      return this._name;
   }

   public void add(DashboardNotificationMetaData notif) {
      this._notifs.add(notif);
   }

   public List getNotifs() {
      return this._notifs;
   }
}
