package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.ExpressionExtensionsManager;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.JMRuntimeException;
import javax.management.MBeanException;
import javax.management.Notification;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.LogSupport;
import weblogic.management.ManagementException;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFWatchJMXNotificationRuntimeMBean;
import weblogic.management.runtime.WLDFWatchNotificationRuntimeMBean;
import weblogic.management.runtime.WLDFWatchNotificationSourceRuntimeMBean;

public class WatchNotificationRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFWatchNotificationRuntimeMBean {
   private WLDFWatchJMXNotificationRuntimeMBean notificationProducer;
   private WLDFWatchNotificationSourceRuntimeMBean jmxNotificationSource;
   private NotificationGenerator notificationGenerator;
   private AtomicInteger notificationSequence = new AtomicInteger();
   private WatchManagerFactory factoryInstance;

   public WatchNotificationRuntimeMBeanImpl(RuntimeMBean parent, WatchManagerFactory wmFactory) throws ManagementException {
      super("WatchNotification", parent);
      this.factoryInstance = wmFactory;
   }

   public void resetWatchAlarm(String watchName) throws ManagementException {
      try {
         WatchManager[] managers = this.factoryInstance.listActiveWatchManagers();
         WatchManager[] var3 = managers;
         int var4 = managers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            WatchManager wm = var3[var5];
            if (wm.hasWatch(watchName)) {
               wm.resetWatchAlarm(watchName);
            }
         }

      } catch (WatchException var7) {
         throw new ManagementException(var7);
      }
   }

   public synchronized void setWatchJMXNotificationRuntime(WLDFWatchJMXNotificationRuntimeMBean notificationProducer) {
      this.notificationProducer = notificationProducer;
   }

   void setNotificationGenerator(NotificationGenerator notifGen) {
      this.notificationGenerator = notifGen;
   }

   public void sendNotification(String source) {
      if (this.notificationGenerator != null && this.notificationGenerator.isSubscribed()) {
         try {
            this.notificationGenerator.sendNotification(new Notification("weblogic.diagnostics.watch.cycleCompleted", this.notificationGenerator.getObjectName(), (long)this.notificationSequence.incrementAndGet(), System.currentTimeMillis(), source));
         } catch (MBeanException var3) {
            LogSupport.logUnexpectedException("Harvest cycle notification failed with exception.", var3);
         }
      }

   }

   public synchronized void setWatchJMXNotificationSource(WLDFWatchNotificationSourceRuntimeMBean source) {
      this.jmxNotificationSource = source;
   }

   public String[] getActiveAlarmWatches() throws ManagementException {
      ArrayList results = new ArrayList();
      WatchManager[] managers = this.factoryInstance.listActiveWatchManagers();
      WatchManager[] var3 = managers;
      int var4 = managers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WatchManager wm = var3[var5];
         Watch[] watches = wm.getActiveAlarmWatches();
         Watch[] var8 = watches;
         int var9 = watches.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Watch w = var8[var10];
            results.add(w.getWatchName());
         }
      }

      return (String[])results.toArray(new String[results.size()]);
   }

   public long getAverageEventDataWatchEvaluationTime() {
      long totaltime = 0L;
      long count = 0L;
      WatchManager[] managers = this.factoryInstance.listActiveWatchManagers();
      WatchManager[] var6 = managers;
      int var7 = managers.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         WatchManager wm = var6[var8];
         totaltime += wm.getStatistics().getTotalEventDataWatchEvaluationTime();
         count += wm.getStatistics().getTotalEventDataEvaluationCycles();
      }

      return count > 0L ? totaltime / count : 0L;
   }

   public long getAverageHarvesterWatchEvaluationTime() {
      long totaltime = 0L;
      long count = 0L;
      WatchManager[] managers = this.factoryInstance.listActiveWatchManagers();
      WatchManager[] var6 = managers;
      int var7 = managers.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         WatchManager wm = var6[var8];
         totaltime += wm.getStatistics().getTotalHarvesterWatchEvaluationTime();
         count += wm.getStatistics().getTotalHarvesterEvaluationCycles();
      }

      return count > 0L ? totaltime / count : 0L;
   }

   public long getAverageLogWatchEvaluationTime() {
      long totaltime = 0L;
      long count = 0L;
      WatchManager[] managers = this.factoryInstance.listActiveWatchManagers();
      WatchManager[] var6 = managers;
      int var7 = managers.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         WatchManager wm = var6[var8];
         totaltime += wm.getStatistics().getTotalLogWatchEvaluationTime();
         count += wm.getStatistics().getTotalLogEvaluationCycles();
      }

      return count > 0L ? totaltime / count : 0L;
   }

   public int getCurrentActiveAlarmsCount() {
      return (int)this.aggregateValue("getCurrentActiveAlarmsCount");
   }

   public int getMaximumActiveAlarmsCount() {
      return this.maxValue("getMaximumActiveAlarmsCount").intValue();
   }

   public long getMaximumEventDataWatchEvaluationTime() {
      return this.maxValue("getMaximumEventDataWatchEvaluationTime");
   }

   public long getMaximumHarvesterWatchEvaluationTime() {
      return this.maxValue("getMaximumHarvesterWatchEvaluationTime");
   }

   public long getMaximumLogWatchEvaluationTime() {
      return this.maxValue("getMaximumLogWatchEvaluationTime");
   }

   public long getMinimumEventDataWatchEvaluationTime() {
      return this.minValue("getMinimumEventDataWatchEvaluationTime");
   }

   public long getMinimumHarvesterWatchEvaluationTime() {
      return this.minValue("getMinimumHarvesterWatchEvaluationTime");
   }

   public long getMinimumLogWatchEvaluationTime() {
      return this.minValue("getMinimumLogWatchEvaluationTime");
   }

   public long getTotalActiveAutomaticResetAlarms() {
      return this.aggregateValue("getTotalActiveAutomaticResetAlarms");
   }

   public long getTotalActiveManualResetAlarms() {
      return this.aggregateValue("getTotalActiveManualResetAlarms");
   }

   public long getTotalDIMGNotificationsPerformed() {
      return this.aggregateValue("getTotalDIMGNotificationsPerformed");
   }

   public long getTotalEventDataEvaluationCycles() {
      return this.aggregateValue("getTotalEventDataEvaluationCycles");
   }

   public long getTotalEventDataWatchEvaluations() {
      return this.aggregateValue("getTotalEventDataWatchEvaluations");
   }

   public long getTotalEventDataWatchesTriggered() {
      return this.aggregateValue("getTotalEventDataWatchesTriggered");
   }

   public long getTotalFailedDIMGNotifications() {
      return this.aggregateValue("getTotalFailedDIMGNotifications");
   }

   public long getTotalFailedJMSNotifications() {
      return this.aggregateValue("getTotalFailedJMSNotifications");
   }

   public long getTotalFailedJMXNotifications() {
      return this.aggregateValue("getTotalFailedJMXNotifications");
   }

   public long getTotalFailedNotifications() {
      return this.aggregateValue("getTotalFailedNotifications");
   }

   public long getTotalFailedSMTPNotifications() {
      return this.aggregateValue("getTotalFailedSMTPNotifications");
   }

   public long getTotalFailedSNMPNotifications() {
      return this.aggregateValue("getTotalFailedSNMPNotifications");
   }

   public long getTotalHarvesterEvaluationCycles() {
      return this.aggregateValue("getTotalHarvesterEvaluationCycles");
   }

   public long getTotalHarvesterWatchEvaluations() {
      return this.aggregateValue("getTotalHarvesterWatchEvaluations");
   }

   public long getTotalHarvesterWatchesTriggered() {
      return this.aggregateValue("getTotalHarvesterWatchesTriggered");
   }

   public long getTotalJMSNotificationsPerformed() {
      return this.aggregateValue("getTotalJMSNotificationsPerformed");
   }

   public long getTotalJMXNotificationsPerformed() {
      return this.aggregateValue("getTotalJMXNotificationsPerformed");
   }

   public long getTotalLogEvaluationCycles() {
      return this.aggregateValue("getTotalLogEvaluationCycles");
   }

   public long getTotalLogWatchEvaluations() {
      return this.aggregateValue("getTotalLogWatchEvaluations");
   }

   public long getTotalLogWatchesTriggered() {
      return this.aggregateValue("getTotalLogWatchesTriggered");
   }

   public long getTotalNotificationsPerformed() {
      return this.aggregateValue("getTotalNotificationsPerformed");
   }

   public long getTotalSMTPNotificationsPerformed() {
      return this.aggregateValue("getTotalSMTPNotificationsPerformed");
   }

   public long getTotalSNMPNotificationsPerformed() {
      return this.aggregateValue("getTotalSNMPNotificationsPerformed");
   }

   public WLDFWatchNotificationSourceRuntimeMBean getWLDFWatchJMXNotificationSource() {
      return this.jmxNotificationSource;
   }

   public WLDFWatchJMXNotificationRuntimeMBean getWLDFWatchJMXNotificationRuntime() {
      return this.notificationProducer;
   }

   private long aggregateValue(String method) {
      Long cumulativeValue = 0L;

      try {
         Method m = WatchManagerStatistics.class.getMethod(method, (Class[])null);
         WatchManager[] managers = this.factoryInstance.listActiveWatchManagers();
         WatchManager[] var5 = managers;
         int var6 = managers.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            WatchManager wm = var5[var7];
            Number value = (Number)m.invoke(wm.getStatistics(), (Object[])null);
            cumulativeValue = cumulativeValue + value.longValue();
         }
      } catch (Exception var10) {
         JMRuntimeException jmex = new JMRuntimeException();
         jmex.initCause(var10);
         throw jmex;
      }

      return cumulativeValue;
   }

   private Long minValue(String method) {
      return this.minmaxValue(method, false);
   }

   private Long maxValue(String method) {
      return this.minmaxValue(method, true);
   }

   private Long minmaxValue(String method, boolean max) {
      Long result = 0L;

      try {
         Method m = WatchManagerStatistics.class.getMethod(method, (Class[])null);
         WatchManager[] managers = this.factoryInstance.listActiveWatchManagers();
         WatchManager[] var6 = managers;
         int var7 = managers.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            WatchManager wm = var6[var8];
            Number value = (Number)m.invoke(wm.getStatistics(), (Object[])null);
            if (max) {
               result = Math.max(value.longValue(), result);
            } else if (result > 0L) {
               result = Math.min(value.longValue(), result);
            } else {
               result = value.longValue();
            }
         }

         return result;
      } catch (Exception var11) {
         JMRuntimeException jmex = new JMRuntimeException();
         jmex.initCause(var11);
         throw jmex;
      }
   }

   public String execute(String expression, String... qualifiers) {
      DebugLogger.println("Execute expression ${" + expression + "}");
      Object result = WatchUtils.evalSingleExpression(expression);
      return result != null ? result.toString() : "";
   }

   public String getBeanInfo(String expression, String... qualifiers) {
      String info = null;
      ServiceLocator locator = WatchUtils.getServiceLocator();
      if (locator != null) {
         WatchExtensionsManager watchExtensionsManager = (WatchExtensionsManager)locator.getService(WatchExtensionsManager.class, new Annotation[0]);
         ExpressionExtensionsManager eem = watchExtensionsManager.getExpressionExtensions();
         BeanInfo beanInfo = eem.getBeanInfo(expression, new Annotation[0]);
         if (beanInfo != null) {
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            BeanDescriptor bd = beanInfo.getBeanDescriptor();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
            pw.append("BeanInfo: " + bd.getDisplayName()).append("(").append(bd.getName()).append(")\n").append("  Short description: " + bd.getShortDescription()).append('\n').append("  Bean class:        ").append(bd.getBeanClass().getName()).append('\n').append("  Description:       ").append((String)bd.getValue("description")).append('\n').append("  Properties:\n");
            int var14;
            int var15;
            if (propertyDescriptors != null) {
               PropertyDescriptor[] var13 = propertyDescriptors;
               var14 = propertyDescriptors.length;

               for(var15 = 0; var15 < var14; ++var15) {
                  PropertyDescriptor pd = var13[var15];
                  pw.append("      ").append(pd.getName()).append(": ").append(pd.toString()).append('\n');
               }
            }

            pw.append("  Methods:\n");
            if (methodDescriptors != null) {
               MethodDescriptor[] var17 = methodDescriptors;
               var14 = methodDescriptors.length;

               for(var15 = 0; var15 < var14; ++var15) {
                  MethodDescriptor md = var17[var15];
                  pw.append("      ").append(md.getName()).append(": ").append(md.toString()).append('\n');
               }
            }

            pw.flush();
            pw.close();
            info = writer.toString();
         }
      }

      return info;
   }
}
