package com.solarmetric.profile;

import com.solarmetric.manage.jmx.BaseDynamicMBean;
import com.solarmetric.manage.jmx.NotificationBroadcasterImpl;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class ProfilingAgentMBean extends BaseDynamicMBean implements NotificationBroadcaster, ProfilingAgent, Configurable {
   private static final Localizer s_loc = Localizer.forPackage(ProfilingAgentMBean.class);
   protected NotificationBroadcasterImpl bcastSupport;
   protected Log log;
   protected boolean _hasListeners = false;
   protected long _seqNum = 0L;
   protected String _notifType;

   public ProfilingAgentMBean() {
   }

   public ProfilingAgentMBean(Configuration conf) {
      this.setConfiguration(conf);
   }

   public void setConfiguration(Configuration conf) {
      this._notifType = s_loc.get("prof-agent-notif-type").getMessage();
      this.log = ProfilingLog.get(conf);
      this.bcastSupport = new NotificationBroadcasterImpl(conf, true);
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   protected String getMBeanDescription() {
      return s_loc.get("prof-agent-desc").getMessage();
   }

   protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("CustomMBeanViewerName", String.class.getName(), "Class name of custom MBean viewer", true, false, false)};
   }

   public String getCustomMBeanViewerName() {
      return "com.solarmetric.profile.gui.ProfilingMBeanViewer";
   }

   protected MBeanNotificationInfo[] createMBeanNotificationInfo() {
      return new MBeanNotificationInfo[]{new MBeanNotificationInfo(new String[]{this._notifType}, "javax.management.Notification", s_loc.get("prof-agent-notif-desc").getMessage())};
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      this.bcastSupport.addNotificationListener(listener, filter, handback);
      this._hasListeners = true;
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return this.createMBeanNotificationInfo();
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      this.bcastSupport.removeNotificationListener(listener);
      if (!this.bcastSupport.hasListeners()) {
         this._hasListeners = false;
      }

   }

   public void handleEvent(ProfilingEvent ev) {
      if (this._hasListeners) {
         Notification notif = new Notification(this._notifType, this, this._seqNum, ev.getTime());
         notif.setUserData(ev);
         this.bcastSupport.sendNotification(notif);
         ++this._seqNum;
      }

   }
}
