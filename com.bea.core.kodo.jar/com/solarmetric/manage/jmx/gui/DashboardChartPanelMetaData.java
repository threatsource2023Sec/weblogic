package com.solarmetric.manage.jmx.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;

public class DashboardChartPanelMetaData {
   private String _name;
   private List _mbeans = new ArrayList();

   public DashboardChartPanelMetaData(String name) {
      this._name = name;
   }

   public String getName() {
      return this._name;
   }

   public void add(DashboardMBeanMetaData mbean) {
      this._mbeans.add(mbean);
   }

   public DashboardNotificationInfo[] getNotificationInfo(MBeanServer server) {
      List dashNotifsList = new ArrayList();
      Set nameSet = server.queryNames((ObjectName)null, (QueryExp)null);
      Iterator i = this._mbeans.iterator();

      while(i.hasNext()) {
         DashboardMBeanMetaData mbean = (DashboardMBeanMetaData)i.next();

         try {
            ObjectName objName = new ObjectName(mbean.getName());
            Iterator j = nameSet.iterator();

            while(j.hasNext()) {
               ObjectName name = (ObjectName)j.next();
               if (this.matches(objName, name)) {
                  try {
                     MBeanInfo info = server.getMBeanInfo(name);
                     MBeanNotificationInfo[] notifs = info.getNotifications();
                     this.addNotificationInfos(dashNotifsList, mbean, notifs, name);
                  } catch (Exception var11) {
                  }
               }
            }
         } catch (MalformedObjectNameException var12) {
         }
      }

      return (DashboardNotificationInfo[])((DashboardNotificationInfo[])dashNotifsList.toArray(new DashboardNotificationInfo[dashNotifsList.size()]));
   }

   private boolean matches(ObjectName pattern, ObjectName name) {
      if (!pattern.getDomain().equals(name.getDomain())) {
         return false;
      } else {
         Hashtable keyprops = pattern.getKeyPropertyList();
         Iterator i = keyprops.keySet().iterator();

         String patternValue;
         String nameValue;
         do {
            if (!i.hasNext()) {
               return true;
            }

            String key = (String)i.next();
            patternValue = pattern.getKeyProperty(key);
            nameValue = name.getKeyProperty(key);
         } while(patternValue.equals(nameValue));

         return false;
      }
   }

   private void addNotificationInfos(List dashNotifsList, DashboardMBeanMetaData mbean, MBeanNotificationInfo[] notifs, ObjectName name) {
      Iterator i = mbean.getNotifs().iterator();

      while(i.hasNext()) {
         DashboardNotificationMetaData notifMetaData = (DashboardNotificationMetaData)i.next();
         MBeanNotificationInfo notif = this.getNotificationInfo(notifs, notifMetaData);
         if (notif != null) {
            dashNotifsList.add(new DashboardNotificationInfo(name, notif));
         }
      }

   }

   private MBeanNotificationInfo getNotificationInfo(MBeanNotificationInfo[] notifs, DashboardNotificationMetaData notifMetaData) {
      for(int i = 0; i < notifs.length; ++i) {
         if (notifs[i].getNotifTypes()[0].equals(notifMetaData.getName())) {
            return notifs[i];
         }
      }

      return null;
   }
}
