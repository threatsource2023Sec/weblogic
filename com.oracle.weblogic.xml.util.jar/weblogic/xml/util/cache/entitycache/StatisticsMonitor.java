package weblogic.xml.util.cache.entitycache;

import java.util.Enumeration;
import java.util.Vector;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;

class StatisticsMonitor implements TimerListener {
   EntityCache cache = null;
   Vector subjects = new Vector();
   long diskWriteInterval = 0L;
   Timer timer;

   StatisticsMonitor(EntityCache cache, long diskWriteInterval) {
      this.cache = cache;
      this.diskWriteInterval = diskWriteInterval;
   }

   void addSubject(Statistics stats, EntityCacheStats mBean) {
      StatisticsMonitorSubject subject = new StatisticsMonitorSubject(stats, mBean);
      this.subjects.addElement(subject);
   }

   void addMBean(Statistics stats, EntityCacheStats bean) {
      Enumeration e = this.subjects.elements();

      while(e.hasMoreElements()) {
         StatisticsMonitorSubject subject = (StatisticsMonitorSubject)e.nextElement();
         if (subject.stats == stats) {
            subject.mBean = bean;
            break;
         }
      }

   }

   void setDiskWriteInterval(long diskWriteInterval) {
      this.diskWriteInterval = diskWriteInterval;
   }

   public void timerExpired(Timer t) {
      try {
         Enumeration e = this.subjects.elements();

         while(e.hasMoreElements()) {
            StatisticsMonitorSubject subject = (StatisticsMonitorSubject)e.nextElement();
            subject.stats.save();
            if (subject.mBean != null) {
               subject.mBean.doNotifications();
            }
         }

         this.cache.statsCumulativeModification = false;
         this.cache.statsCurrentModification = false;
      } catch (Exception var4) {
      }

   }

   public void start() {
      this.timer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, this.diskWriteInterval, this.diskWriteInterval);
   }

   public void finish() {
      if (this.timer != null) {
         this.timer.cancel();
      }

   }
}
