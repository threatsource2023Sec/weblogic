package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import weblogic.diagnostics.descriptor.WLDFNotificationBean;

abstract class WatchNotificationListenerCommon implements WatchNotificationListener {
   private String notificationName;
   private boolean notificationEnabled;
   private int notificationTimeout = -1;
   private WatchManagerStatisticsImpl statistics = null;

   WatchNotificationListenerCommon(WLDFNotificationBean configBean, WatchManagerStatisticsImpl stats) throws InvalidNotificationException, NotificationCreateException {
      this.notificationName = configBean.getName();
      this.statistics = stats;
      this.notificationEnabled = configBean.isEnabled();
      this.notificationTimeout = configBean.getTimeout();
   }

   public String getNotificationName() {
      return this.notificationName;
   }

   public boolean isEnabled() {
      return this.notificationEnabled;
   }

   public boolean isDisabled() {
      return !this.notificationEnabled;
   }

   public void setEnabled() {
      this.notificationEnabled = true;
   }

   public void setDisabled() {
      this.notificationEnabled = false;
   }

   public int getNotificationTimeout() {
      return this.notificationTimeout;
   }

   public void cancel() {
   }

   protected WatchManagerStatisticsImpl getStatistics() {
      return this.statistics;
   }

   public void reset() {
   }
}
