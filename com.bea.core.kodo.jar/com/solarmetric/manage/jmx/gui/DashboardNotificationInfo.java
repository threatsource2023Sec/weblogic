package com.solarmetric.manage.jmx.gui;

import javax.management.MBeanNotificationInfo;
import javax.management.ObjectName;

public class DashboardNotificationInfo {
   private ObjectName _name;
   private MBeanNotificationInfo _notif;

   public DashboardNotificationInfo(ObjectName name, MBeanNotificationInfo notif) {
      this._name = name;
      this._notif = notif;
   }

   public ObjectName getMBeanObjectName() {
      return this._name;
   }

   public MBeanNotificationInfo getMBeanNotificationInfo() {
      return this._notif;
   }
}
