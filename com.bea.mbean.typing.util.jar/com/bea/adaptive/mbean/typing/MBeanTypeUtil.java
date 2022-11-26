package com.bea.adaptive.mbean.typing;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.Executor;
import javax.management.JMException;
import javax.management.MBeanServerConnection;

public class MBeanTypeUtil {
   private MBeanServerConnection mbs;
   private MBeanCategorizer categorizer;
   private String name;
   private Executor workManager;
   private String[] patterns;
   private Hashtable handlers;
   private static String[] defaultPatterns = new String[]{"com.bea:Name=(.*),Type=ServerRuntime(.*)", "com.bea:Name=(.*),ServerRuntime=(.*),Type=JRockitRuntime(.*)", "com.bea:Name=(.*),ServerRuntime=(.*),Type=JVMRuntime(.*)"};
   private static final String debugStr = "MBeanTypingUtility";
   private static DebugHelper logDbg = new DebugHelper("MBeanTypingUtility");

   static final boolean dbg() {
      return logDbg.isDebugEnabled();
   }

   static void dbg(Object o) {
      logDbg.debug(o);
   }

   /** @deprecated */
   @Deprecated
   public MBeanTypeUtil(MBeanServerConnection mbs, MBeanCategorizer categorizer) throws JMException {
      this(mbs, categorizer, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public MBeanTypeUtil(MBeanServerConnection mbs, MBeanCategorizer categorizer, String name) throws JMException {
      this(mbs, categorizer, name, (String[])null, (Executor)null);
   }

   public MBeanTypeUtil(MBeanServerConnection mbs, String name, String[] objectNamePatterns, Executor executor) throws JMException {
      this(mbs, (MBeanCategorizer)null, name, objectNamePatterns, executor);
   }

   public MBeanTypeUtil(MBeanServerConnection mbs, MBeanCategorizer categorizer, String name, String[] objectNamePatterns, Executor executor) throws JMException {
      this.handlers = new Hashtable();
      this.mbs = mbs;
      this.categorizer = categorizer;
      this.name = name;
      this.patterns = objectNamePatterns == null ? defaultPatterns : objectNamePatterns;
      this.workManager = executor;
      if (this.workManager == null) {
         throw new RuntimeException(MBeanTypingUtilTextTextFormatter.getInstance().getNoExecutorProvidedText());
      }
   }

   public void shutdown(Object id, boolean forceShutdown) throws JMException, IOException {
      if (id != null && id instanceof JMXNotificationListener) {
         JMXNotificationListener handler = (JMXNotificationListener)id;
         if (!forceShutdown) {
            handler.removeRegistrationHandler(id);
         }

         handler.halt();
      }

   }

   public Object addRegistrationHandler(RegHandler regHandler) throws JMException, IOException {
      if (dbg()) {
         dbg("MBean Typing Utility (" + this.name + "): Adding registration handler");
      }

      JMXNotificationListener listener = new JMXNotificationListener(this.name, regHandler, this.patterns, this.workManager, this.mbs, this.categorizer);
      listener.initialize();
      this.handlers.put(listener, regHandler);
      return listener;
   }

   MBeanServerConnection getMBeanServerConnection() {
      return this.mbs;
   }

   String getName() {
      return this.name;
   }

   Executor getWorkManager() {
      return this.workManager;
   }

   String[] getPatterns() {
      return this.patterns;
   }

   public static void setDefaultPatterns(String[] namePatterns) {
      defaultPatterns = (String[])Arrays.copyOf(namePatterns, namePatterns.length);
   }

   public static String[] getDefaultPatterns() {
      return (String[])Arrays.copyOf(defaultPatterns, defaultPatterns.length);
   }

   public interface RegHandler {
      void newInstance(String var1, String var2, String var3) throws Exception;

      void instanceDeleted(String var1) throws Exception;
   }
}
