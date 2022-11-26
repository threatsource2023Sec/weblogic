package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.Notification;
import com.bea.diagnostics.notifications.NotificationSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WatchNotificationInternal extends WatchNotification implements Notification {
   static final long serialVersionUID = -2415851253529341071L;
   public static final String WATCH_MODULE = "WatchModule";
   private static transient ArrayList watchKeySet;
   private transient WatchNotificationSourceImpl source;
   private transient long watchTimeMillis;
   private String moduleName;

   WatchNotificationInternal(Watch watch, long watchTimeMillis, Map watchData) throws NotificationCreateException {
      if (watch == null) {
         throw new NotificationCreateException("Watch can not be null");
      } else {
         this.moduleName = watch.getModuleName();
         this.watchTimeMillis = watchTimeMillis;
         this.source = new WatchNotificationSourceImpl(watch.getWatchName());
         if (watchData != null) {
            this.setWatchData(new ConcurrentHashMap(watchData));
         }

         this.setWatchDataString(WatchUtils.getWatchDataString(this.getWatchData()));
         WatchUtils.setWatchTimeInDateFormat(this, watchTimeMillis);
         WatchUtils.populateFromWatch(this, watch);
      }
   }

   WatchNotification createWatchNotificationExternal() {
      WatchNotification wn = new WatchNotification();
      wn.setMessage(this.getMessage());
      wn.setWatchAlarmResetPeriod(this.getWatchAlarmResetPeriod());
      wn.setWatchAlarmType(this.getWatchAlarmType());
      wn.setWatchDataString(this.getWatchDataToString());
      wn.setWatchDomainName(this.getWatchDomainName());
      wn.setWatchName(this.getWatchName());
      wn.setWatchRule(this.getWatchRule());
      wn.setWatchRuleType(this.getWatchRuleType());
      wn.setWatchServerName(this.getWatchServerName());
      wn.setWatchSeverityLevel(this.getWatchSeverityLevel());
      wn.setWatchTime(this.getWatchTime());
      wn.setWatchData(this.getWatchData());
      return wn;
   }

   public NotificationSource getSource() {
      return this.source;
   }

   public void setSource(NotificationSource source) {
      throw new UnsupportedOperationException();
   }

   public Object getValue(Object key) {
      if ("WatchName".equals(key)) {
         return this.getWatchName();
      } else if ("WatchModule".equals(key)) {
         return this.moduleName;
      } else if ("WatchDomainName".equals(key)) {
         return this.getWatchDomainName();
      } else if ("WatchServerName".equals(key)) {
         return this.getWatchServerName();
      } else if ("WatchRuleType".equals(key)) {
         return this.getWatchRuleType();
      } else if ("WatchRule".equals(key)) {
         return this.getWatchRule();
      } else if ("WatchTime".equals(key)) {
         return this.getWatchTime();
      } else if ("WatchSeverityLevel".equals(key)) {
         return this.getWatchSeverityLevel();
      } else if ("WatchData".equals(key)) {
         return this.getWatchDataToString();
      } else if ("WatchAlarmType".equals(key)) {
         return this.getWatchAlarmType();
      } else {
         return "WatchAlarmResetPeriod".equals(key) ? this.getWatchAlarmResetPeriod() : null;
      }
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public void setValue(Object key, Object value) {
   }

   public List keyList() {
      synchronized(this) {
         if (watchKeySet == null) {
            watchKeySet = new ArrayList(10);
            watchKeySet.add("WatchName");
            watchKeySet.add("WatchModule");
            watchKeySet.add("WatchDomainName");
            watchKeySet.add("WatchServerName");
            watchKeySet.add("WatchRuleType");
            watchKeySet.add("WatchRule");
            watchKeySet.add("WatchTime");
            watchKeySet.add("WatchSeverityLevel");
            watchKeySet.add("WatchData");
            watchKeySet.add("WatchAlarmType");
            watchKeySet.add("WatchAlarmResetPeriod");
         }
      }

      return (List)watchKeySet.clone();
   }

   protected long getWatchTimeMillis() {
      return this.watchTimeMillis;
   }

   private static class WatchNotificationSourceImpl implements NotificationSource {
      static final long serialVersionUID = 8733358832295734061L;
      private String watchName;

      public WatchNotificationSourceImpl(String watch) {
         this.watchName = watch;
      }

      public String getName() {
         return this.watchName;
      }

      public void setName(String name) {
         throw new UnsupportedOperationException();
      }
   }
}
