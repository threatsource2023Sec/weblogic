package com.oracle.jrockit.jfr;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import oracle.jrockit.jfr.JFR;

public class FlightRecorder {
   private FlightRecorder() {
   }

   public static boolean isActive() {
      return JFR.get().active();
   }

   public static boolean isNativeImplementation() {
      return JFR.get().isNativeImplementation();
   }

   public static synchronized void registerWithMBeanServer(MBeanServer server) {
      JFR.get().bind(server);
   }

   public static synchronized void unregisterWithMBeanServer(MBeanServer server) {
      JFR.get().unbind(server);
   }

   public static synchronized void registerWithPlatformMBeanServer() {
      registerWithMBeanServer(ManagementFactory.getPlatformMBeanServer());
   }

   public static synchronized void unregisterWithPlatformMBeanServer() {
      unregisterWithMBeanServer(ManagementFactory.getPlatformMBeanServer());
   }
}
