package com.bea.core.repackaged.apache.commons.logging;

import com.bea.core.repackaged.apache.commons.logging.impl.NoOpLog;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

/** @deprecated */
public class LogSource {
   protected static Hashtable logs = new Hashtable();
   protected static boolean log4jIsAvailable = false;
   protected static boolean jdk14IsAvailable = false;
   protected static Constructor logImplctor = null;

   private LogSource() {
   }

   public static void setLogImplementation(String classname) throws LinkageError, NoSuchMethodException, SecurityException, ClassNotFoundException {
      try {
         Class logclass = Class.forName(classname);
         Class[] argtypes = new Class[]{"".getClass()};
         logImplctor = logclass.getConstructor(argtypes);
      } catch (Throwable var3) {
         logImplctor = null;
      }

   }

   public static void setLogImplementation(Class logclass) throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException {
      Class[] argtypes = new Class[]{"".getClass()};
      logImplctor = logclass.getConstructor(argtypes);
   }

   public static Log getInstance(String name) {
      Log log = (Log)logs.get(name);
      if (null == log) {
         log = makeNewLogInstance(name);
         logs.put(name, log);
      }

      return log;
   }

   public static Log getInstance(Class clazz) {
      return getInstance(clazz.getName());
   }

   public static Log makeNewLogInstance(String name) {
      Object log;
      try {
         Object[] args = new Object[]{name};
         log = (Log)logImplctor.newInstance(args);
      } catch (Throwable var3) {
         log = null;
      }

      if (null == log) {
         log = new NoOpLog(name);
      }

      return (Log)log;
   }

   public static String[] getLogNames() {
      return (String[])((String[])logs.keySet().toArray(new String[logs.size()]));
   }

   static {
      try {
         log4jIsAvailable = null != Class.forName("org.apache.log4j.Logger");
      } catch (Throwable var8) {
         log4jIsAvailable = false;
      }

      try {
         jdk14IsAvailable = null != Class.forName("java.util.logging.Logger") && null != Class.forName("com.bea.core.repackaged.apache.commons.logging.impl.Jdk14Logger");
      } catch (Throwable var9) {
         jdk14IsAvailable = false;
      }

      String name = null;

      try {
         name = System.getProperty("com.bea.core.repackaged.apache.commons.logging.log");
         if (name == null) {
            name = System.getProperty("com.bea.core.repackaged.apache.commons.logging.Log");
         }
      } catch (Throwable var7) {
      }

      if (name != null) {
         try {
            setLogImplementation(name);
         } catch (Throwable var6) {
            try {
               setLogImplementation("com.bea.core.repackaged.apache.commons.logging.impl.NoOpLog");
            } catch (Throwable var5) {
            }
         }
      } else {
         try {
            if (log4jIsAvailable) {
               setLogImplementation("com.bea.core.repackaged.apache.commons.logging.impl.Log4JLogger");
            } else if (jdk14IsAvailable) {
               setLogImplementation("com.bea.core.repackaged.apache.commons.logging.impl.Jdk14Logger");
            } else {
               setLogImplementation("com.bea.core.repackaged.apache.commons.logging.impl.NoOpLog");
            }
         } catch (Throwable var4) {
            try {
               setLogImplementation("com.bea.core.repackaged.apache.commons.logging.impl.NoOpLog");
            } catch (Throwable var3) {
            }
         }
      }

   }
}
