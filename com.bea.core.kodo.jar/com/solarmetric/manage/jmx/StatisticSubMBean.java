package com.solarmetric.manage.jmx;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

public class StatisticSubMBean implements SubMBeanNotifier {
   private transient StatisticMBean _statMBean;
   private String _prefix;

   public StatisticSubMBean(StatisticMBean statMBean, String prefix) {
      this._statMBean = statMBean;
      this._prefix = prefix;
      this._statMBean.setTypeName(prefix + "." + this._statMBean.getTypeName());
   }

   public String getPrefix() {
      return this._prefix;
   }

   public Object getSub() {
      return this._statMBean.getStatistic();
   }

   public MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return this._statMBean.createMBeanAttributeInfo();
   }

   public MBeanNotificationInfo[] createMBeanNotificationInfo() {
      return this._statMBean.createMBeanNotificationInfo();
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      this._statMBean.addNotificationListener(listener, filter, handback);
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return this._statMBean.getNotificationInfo();
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      this._statMBean.removeNotificationListener(listener);
   }
}
