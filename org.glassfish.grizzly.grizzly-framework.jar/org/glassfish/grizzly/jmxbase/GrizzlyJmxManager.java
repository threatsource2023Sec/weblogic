package org.glassfish.grizzly.jmxbase;

import java.util.Iterator;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.ServiceFinder;

public abstract class GrizzlyJmxManager {
   private static final GrizzlyJmxManager manager;

   private static Class loadClass(String classname) throws ClassNotFoundException {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader == null) {
         classLoader = MonitoringUtils.class.getClassLoader();
      }

      return classLoader.loadClass(classname);
   }

   public static GrizzlyJmxManager instance() {
      return manager;
   }

   public abstract Object registerAtRoot(Object var1);

   public abstract Object registerAtRoot(Object var1, String var2);

   public abstract Object register(Object var1, Object var2);

   public abstract Object register(Object var1, Object var2, String var3);

   public abstract void deregister(Object var1);

   static {
      ServiceFinder serviceFinder = ServiceFinder.find(GrizzlyJmxManager.class);
      Iterator it = serviceFinder.iterator();
      Object jmxManager;
      if (it.hasNext()) {
         jmxManager = (GrizzlyJmxManager)it.next();
      } else {
         try {
            jmxManager = (GrizzlyJmxManager)loadClass("org.glassfish.grizzly.monitoring.jmx.DefaultJmxManager").newInstance();
         } catch (Exception var4) {
            jmxManager = new NullJmxManager();
         }
      }

      manager = (GrizzlyJmxManager)jmxManager;
   }

   private static final class NullJmxManager extends GrizzlyJmxManager {
      private NullJmxManager() {
      }

      public Object registerAtRoot(Object object) {
         return null;
      }

      public Object registerAtRoot(Object object, String name) {
         return null;
      }

      public Object register(Object parent, Object object) {
         return null;
      }

      public Object register(Object parent, Object object, String name) {
         return null;
      }

      public void deregister(Object object) {
      }

      // $FF: synthetic method
      NullJmxManager(Object x0) {
         this();
      }
   }
}
