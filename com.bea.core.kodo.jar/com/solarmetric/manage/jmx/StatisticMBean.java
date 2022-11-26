package com.solarmetric.manage.jmx;

import com.solarmetric.manage.Statistic;
import com.solarmetric.manage.StatisticEvent;
import com.solarmetric.manage.StatisticListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import org.apache.openjpa.lib.conf.Configuration;
import serp.util.Numbers;

public class StatisticMBean extends BaseDynamicMBean implements NotificationBroadcaster, Serializable {
   private NotificationBroadcasterImpl _bcastSupport;
   private Statistic _stat;
   private String _typeName;
   private StatisticListener _statListener;

   public StatisticMBean(Statistic stat, Configuration conf) {
      this(stat, "", conf);
   }

   public StatisticMBean(Statistic stat, String prefix, Configuration conf) {
      this._bcastSupport = new NotificationBroadcasterImpl(conf, false);
      this._stat = stat;
      if (prefix.length() == 0) {
         this._typeName = "Statistic";
      } else {
         this._typeName = prefix + ".Statistic";
      }

      this.setResource(this._stat);
      this._statListener = new BatchingStatisticListener();
   }

   public void setTypeName(String typeName) {
      this._typeName = typeName;
   }

   public String getTypeName() {
      return this._typeName;
   }

   public Object getStatistic() {
      return this._stat;
   }

   protected String getMBeanDescription() {
      return ((Statistic)this.getResource()).getDescription();
   }

   protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("Value", "double", "Current Value of Statistic", true, false, false)};
   }

   protected MBeanNotificationInfo[] createMBeanNotificationInfo() {
      return new MBeanNotificationInfo[]{new MBeanNotificationInfo(new String[]{this._typeName}, "javax.management.Notification", "Statistic:Description=" + this._stat.getDescription() + ",OrdinateDescription=" + this._stat.getOrdinateDescription() + ",DrawStyle=" + this._stat.getDrawStyle())};
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      boolean needsAdd = false;
      if (!this._bcastSupport.hasListeners()) {
         needsAdd = true;
      }

      this._bcastSupport.addNotificationListener(listener, filter, handback);
      if (needsAdd) {
         this._stat.addListener(this._statListener);
      }

   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return this.createMBeanNotificationInfo();
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      this._bcastSupport.removeNotificationListener(listener);
      if (!this._bcastSupport.hasListeners()) {
         this._stat.removeListener(this._statListener);
      }

   }

   public class BatchingStatisticListener implements StatisticListener, Serializable {
      private long _seqNum = 0L;
      private List _data = new ArrayList();
      private int _batchSize = 100;
      private long _batchTimeout = 1000L;
      private boolean _started = false;

      public void statisticChanged(StatisticEvent e) {
         if (StatisticMBean.this._bcastSupport.hasListeners()) {
            Notification notif = null;
            synchronized(this) {
               this._data.add(new Number[]{Numbers.valueOf(e.getTime()), new Double(e.getValue())});
               if (this._data.size() > this._batchSize) {
                  notif = this.createNotification();
               }

               if (!this._started && StatisticMBean.this._bcastSupport.hasListeners()) {
                  this._started = true;
                  TimerThread timer = new TimerThread();
                  timer.setDaemon(true);
                  timer.start();
               }
            }

            if (notif != null) {
               StatisticMBean.this._bcastSupport.sendNotification(notif);
            }

         }
      }

      public Notification createNotification() {
         Notification notif = new Notification(StatisticMBean.this._typeName, StatisticMBean.this, this._seqNum);
         notif.setUserData(this._data);
         this._data = new ArrayList();
         ++this._seqNum;
         return notif;
      }

      public class TimerThread extends Thread {
         private boolean _active = true;

         public void deactivate() {
            this._active = false;
         }

         public void run() {
            while(this._active) {
               try {
                  Notification notif = null;
                  synchronized(BatchingStatisticListener.this) {
                     if (!StatisticMBean.this._bcastSupport.hasListeners()) {
                        this.deactivate();
                        BatchingStatisticListener.this._started = false;
                        return;
                     }

                     if (BatchingStatisticListener.this._data.size() > 0) {
                        notif = BatchingStatisticListener.this.createNotification();
                     } else {
                        BatchingStatisticListener.this._data.add(new Number[]{Numbers.valueOf(System.currentTimeMillis()), new Double(StatisticMBean.this._stat.getValue())});
                        notif = BatchingStatisticListener.this.createNotification();
                     }
                  }

                  if (notif != null) {
                     StatisticMBean.this._bcastSupport.sendNotification(notif);
                  }

                  Thread.sleep(BatchingStatisticListener.this._batchTimeout);
               } catch (InterruptedException var5) {
               }
            }

         }
      }
   }
}
