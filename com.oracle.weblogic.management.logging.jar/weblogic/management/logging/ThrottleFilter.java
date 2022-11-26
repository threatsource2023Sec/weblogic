package weblogic.management.logging;

import com.bea.logging.BaseLogRecord;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import weblogic.management.configuration.LogMBean;

class ThrottleFilter implements Filter {
   private static final boolean DEBUG = false;
   private static final String SEP = "/";
   private static final int THRESHOLD_PERCENT_PER_MESSAGE = Integer.getInteger("weblogic.log.LogMonitoringDefaultThresholdPercentPerMessage", 10);
   private static final double THRESHOLD_PERCENT_FRACTION;
   private AtomicInteger cycleCount = new AtomicInteger();
   private AtomicInteger successiveCyclesOverThreshold = new AtomicInteger();
   private Map throttleData = new ConcurrentHashMap();
   private Map currentCycleLoggedMessages = new ConcurrentHashMap();
   private LogMBean logMBean;

   ThrottleFilter(LogMBean logMBean) {
      this.logMBean = logMBean;
   }

   public boolean isLoggable(LogRecord record) {
      Level level = record.getLevel();
      if (level != null && level.intValue() <= Level.FINE.intValue()) {
         return true;
      } else {
         int count = this.cycleCount.incrementAndGet();
         if (this.logMBean.isLogMonitoringEnabled() && (count > this.logMBean.getLogMonitoringThrottleThreshold() || !this.throttleData.isEmpty())) {
            String id = "";
            if (record instanceof BaseLogRecord) {
               id = ((BaseLogRecord)record).getId();
            }

            if (id == null) {
               id = "";
            }

            String loggerName = record.getLoggerName();
            loggerName = loggerName == null ? "" : loggerName;
            StringBuilder sb = new StringBuilder();
            sb.append(id).append("/").append(loggerName);
            String msg = record.getMessage();
            msg = msg == null ? "" : msg;
            int msgTrimLen = this.logMBean.getLogMonitoringThrottleMessageLength();
            if (msg.length() > msgTrimLen) {
               msg = msg.substring(0, msgTrimLen);
            }

            sb.append("/").append(msg);
            String signature = sb.toString();
            AtomicInteger counter = null;
            int sizeLimit;
            synchronized(this) {
               counter = (AtomicInteger)this.throttleData.get(signature);
               if (counter == null) {
                  counter = new AtomicInteger(0);
                  sizeLimit = this.logMBean.getLogMonitoringMaxThrottleMessageSignatureCount();
                  if (this.throttleData.size() > sizeLimit) {
                     Iterator var13 = this.throttleData.keySet().iterator();

                     while(var13.hasNext()) {
                        String key = (String)var13.next();

                        while(true) {
                           this.throttleData.remove(key);
                           if (this.throttleData.size() <= sizeLimit) {
                              break;
                           }
                        }
                     }
                  }

                  this.throttleData.put(signature, counter);
               }
            }

            int value = counter.incrementAndGet();
            sizeLimit = this.logMBean.getLogMonitoringThrottleThreshold();
            int numOfCycles = this.successiveCyclesOverThreshold.get();
            if (numOfCycles == 0) {
               numOfCycles = 1;
            }

            double avgMessageRatePerCycle = (double)value / (double)numOfCycles;
            if (avgMessageRatePerCycle > (double)sizeLimit * THRESHOLD_PERCENT_FRACTION && this.currentCycleLoggedMessages.containsKey(signature)) {
               return false;
            } else {
               this.currentCycleLoggedMessages.put(signature, 1);
               return true;
            }
         } else {
            return true;
         }
      }
   }

   double getMessageThresholdPercentDecimal() {
      return THRESHOLD_PERCENT_FRACTION;
   }

   void clearThrottlingData() {
      this.throttleData.clear();
      this.currentCycleLoggedMessages.clear();
      this.successiveCyclesOverThreshold.getAndSet(0);
   }

   Map getThrottleData() {
      return this.throttleData;
   }

   int resetCycleCount() {
      this.currentCycleLoggedMessages.clear();
      return this.cycleCount.getAndSet(0);
   }

   int incrementSuccesiveCyclesOverThreshold() {
      return this.successiveCyclesOverThreshold.getAndIncrement();
   }

   static {
      THRESHOLD_PERCENT_FRACTION = (double)THRESHOLD_PERCENT_PER_MESSAGE / 100.0;
   }
}
