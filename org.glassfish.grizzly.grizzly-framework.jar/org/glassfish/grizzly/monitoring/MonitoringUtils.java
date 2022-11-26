package org.glassfish.grizzly.monitoring;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public class MonitoringUtils {
   private static final Logger LOGGER = Grizzly.logger(MonitoringUtils.class);

   public static Object loadJmxObject(String jmxObjectClassname, Object constructorParam) {
      return loadJmxObject(jmxObjectClassname, constructorParam, constructorParam.getClass());
   }

   public static Object loadJmxObject(String jmxObjectClassname, Object constructorParam, Class contructorParamType) {
      try {
         Class clazz = loadClass(jmxObjectClassname);
         Constructor c = clazz.getDeclaredConstructor(contructorParamType);
         return c.newInstance(constructorParam);
      } catch (Exception var5) {
         LOGGER.log(Level.FINE, "Can not load JMX Object: " + jmxObjectClassname, var5);
         return null;
      }
   }

   private static Class loadClass(String classname) throws ClassNotFoundException {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader == null) {
         classLoader = MonitoringUtils.class.getClassLoader();
      }

      return classLoader.loadClass(classname);
   }
}
