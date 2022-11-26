package weblogic.diagnostics.instrumentation.gathering;

import com.bea.logging.LogLevel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.event.WLLogRecordEvent;

public class WLLog4jLogEventClassHelper {
   private static WLLog4jLogEventClassHelper SINGLETON;
   private Class wlLog4jLogEventClass = null;
   private boolean available = false;
   private boolean availabilityCheckDone = false;
   private Method isGatherable = null;
   private Method getDiagnosticVolume = null;
   private Method getLogMessage = null;
   private Method getId = null;
   private Method getLoggerName = null;
   private Method getSeverity = null;
   private Method getUserId = null;
   private Method getTransactionId = null;
   private Method getServerName = null;
   private Method getDiagnosticContextId = null;
   private Method getMachineName = null;
   private Method getSupplementalAttributes = null;

   public static WLLog4jLogEventClassHelper getInstance() {
      if (SINGLETON != null) {
         return SINGLETON;
      } else {
         Class var0 = WLLog4jLogEventClassHelper.class;
         synchronized(WLLog4jLogEventClassHelper.class) {
            if (SINGLETON == null) {
               SINGLETON = new WLLog4jLogEventClassHelper();
            }
         }

         return SINGLETON;
      }
   }

   private WLLog4jLogEventClassHelper() {
   }

   public boolean isAvailable(Object event) {
      if (this.availabilityCheckDone) {
         return this.available;
      } else {
         synchronized(this) {
            if (this.availabilityCheckDone) {
               return this.available;
            }

            if (event != null) {
               try {
                  this.wlLog4jLogEventClass = Class.forName("weblogic.logging.log4j.WLLog4jLogEvent", true, event.getClass().getClassLoader());
                  this.isGatherable = this.wlLog4jLogEventClass.getDeclaredMethod("isGatherable", (Class[])null);
                  this.getDiagnosticVolume = this.wlLog4jLogEventClass.getDeclaredMethod("getDiagnosticVolume", (Class[])null);
                  this.getLogMessage = this.wlLog4jLogEventClass.getDeclaredMethod("getLogMessage", (Class[])null);
                  this.getId = this.wlLog4jLogEventClass.getDeclaredMethod("getId", (Class[])null);
                  this.getSeverity = this.wlLog4jLogEventClass.getDeclaredMethod("getSeverity", (Class[])null);
                  this.getUserId = this.wlLog4jLogEventClass.getDeclaredMethod("getUserId", (Class[])null);
                  this.getTransactionId = this.wlLog4jLogEventClass.getDeclaredMethod("getTransactionId", (Class[])null);
                  this.getServerName = this.wlLog4jLogEventClass.getDeclaredMethod("getServerName", (Class[])null);
                  this.getDiagnosticContextId = this.wlLog4jLogEventClass.getDeclaredMethod("getDiagnosticContextId", (Class[])null);
                  this.getMachineName = this.wlLog4jLogEventClass.getDeclaredMethod("getMachineName", (Class[])null);
                  this.getLoggerName = this.wlLog4jLogEventClass.getMethod("getLoggerName", (Class[])null);
                  this.getSupplementalAttributes = this.wlLog4jLogEventClass.getMethod("getSupplementalAttributes", (Class[])null);
                  this.available = true;
               } catch (Exception var5) {
                  this.available = false;
               } catch (NoSuchMethodError var6) {
                  this.available = false;
               }
            }

            this.availabilityCheckDone = true;
         }

         return this.available;
      }
   }

   public boolean isInstance(Class testClass) {
      if (testClass != null && this.wlLog4jLogEventClass != null) {
         return testClass == this.wlLog4jLogEventClass ? true : testClass.isInstance(this.wlLog4jLogEventClass);
      } else {
         return false;
      }
   }

   public boolean isGatherable(Object instance) {
      if (this.available && instance != null) {
         try {
            return (Boolean)this.isGatherable.invoke(instance, (Object[])null);
         } catch (Exception var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   public String getDiagnosticVolume(Object instance) {
      if (this.available && instance != null) {
         try {
            return (String)this.getDiagnosticVolume.invoke(instance, (Object[])null);
         } catch (Exception var3) {
            return "Off";
         }
      } else {
         return "Off";
      }
   }

   public FlightRecorderEvent populateWLLogRecordEvent(Object instance) {
      WLLogRecordEvent wlLogEvent = new WLLogRecordEvent();

      try {
         wlLogEvent.setMessage((String)this.getLogMessage.invoke(instance, (Object[])null));
         wlLogEvent.setLevel(LogLevel.getLevel((Integer)((Integer)this.getSeverity.invoke(instance, (Object[])null))).toString());
         wlLogEvent.setId((String)this.getId.invoke(instance, (Object[])null));
         wlLogEvent.setLoggerName((String)this.getLoggerName.invoke(instance, (Object[])null));
         wlLogEvent.setUserID((String)this.getUserId.invoke(instance, (Object[])null));
         wlLogEvent.setTransactionID((String)this.getTransactionId.invoke(instance, (Object[])null));
         wlLogEvent.setECID((String)this.getDiagnosticContextId.invoke(instance, (Object[])null));
         Properties attrs = (Properties)this.getSupplementalAttributes.invoke(instance, (Object[])null);
         if (attrs != null && attrs.size() > 0) {
            wlLogEvent.setRID(attrs.getProperty("RID", ""));
            wlLogEvent.setPartitionName(attrs.getProperty("partition-name", ""));
            wlLogEvent.setPartitionId(attrs.getProperty("partition-id", ""));
         }
      } catch (IllegalArgumentException var4) {
      } catch (IllegalAccessException var5) {
      } catch (InvocationTargetException var6) {
      }

      return wlLogEvent;
   }
}
