package weblogic.diagnostics.flightrecorder;

import java.lang.reflect.Method;

public class JFRDebug {
   private static boolean initialized = false;
   private static boolean isValid = false;
   private static Object flightRecorderMgr = null;
   private static Method beginDebugTimedEventMtd;
   private static Method commitDebugTimedEventMtd;
   private static Method generateDebugEventMtd;

   private static synchronized void ensureInitialized() {
      if (!initialized) {
         try {
            Class managerClazz = Class.forName("weblogic.diagnostics.flightrecorder.FlightRecorderManager");
            beginDebugTimedEventMtd = managerClazz.getDeclaredMethod("beginDebugTimedEvent", String.class, String.class, Object.class);
            commitDebugTimedEventMtd = managerClazz.getDeclaredMethod("commitDebugTimedEvent", Object.class, Throwable.class, Object.class);
            generateDebugEventMtd = managerClazz.getDeclaredMethod("generateDebugEvent", String.class, String.class, Throwable.class, Object.class);
            Class managerFactoryClazz = Class.forName("weblogic.diagnostics.flightrecorder.FlightRecorderManager$Factory");
            Method getInstanceMtd = managerFactoryClazz.getDeclaredMethod("getInstance");
            flightRecorderMgr = getInstanceMtd.invoke((Object)null);
            isValid = true;
         } catch (Throwable var3) {
            isValid = false;
         }

         initialized = true;
      }
   }

   public static Object beginDebugTimedEvent(String component, String message, Object contributor) {
      if (!initialized) {
         ensureInitialized();
      }

      if (!isValid) {
         return null;
      } else {
         try {
            return beginDebugTimedEventMtd.invoke(flightRecorderMgr, component, message, contributor);
         } catch (Throwable var4) {
            return null;
         }
      }
   }

   public static Object beginDebugTimedEvent(String message) {
      return beginDebugTimedEvent((String)null, message, (Object)null);
   }

   public static Object beginDebugTimedEvent(String message, Object contributor) {
      return beginDebugTimedEvent((String)null, message, contributor);
   }

   public static void commitDebugTimedEvent(Object event, Throwable debugTh, Object contributor) {
      ensureInitialized();
      if (isValid && event != null) {
         try {
            commitDebugTimedEventMtd.invoke(flightRecorderMgr, event, debugTh, contributor);
         } catch (Throwable var4) {
         }

      }
   }

   public static void commitDebugTimedEvent(Object event, Throwable debugTh) {
      commitDebugTimedEvent(event, debugTh, (Object)null);
   }

   public static void commitDebugTimedEvent(Object event) {
      commitDebugTimedEvent(event, (Throwable)null, (Object)null);
   }

   public static void generateDebugEvent(String component, String message, Throwable debugTh, Object contributor) {
      ensureInitialized();
      if (isValid) {
         try {
            generateDebugEventMtd.invoke(flightRecorderMgr, component, message, debugTh, contributor);
         } catch (Throwable var5) {
         }

      }
   }

   public static void generateDebugEvent(String component, String message, Throwable debugTh) {
      generateDebugEvent(component, message, debugTh, (Object)null);
   }

   public static void generateDebugEvent(String message) {
      generateDebugEvent((String)null, message, (Throwable)null, (Object)null);
   }

   public static void generateDebugEvent(String message, Object contributor) {
      generateDebugEvent((String)null, message, (Throwable)null, contributor);
   }
}
