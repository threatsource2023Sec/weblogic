package weblogic.jaxrs.monitoring.util;

import javax.ejb.Singleton;
import javax.ejb.Stateless;

public class MonitoringUtil {
   public static boolean isEJB(Class c) {
      if (c.isAnnotationPresent(Stateless.class)) {
         return true;
      } else {
         return c.isAnnotationPresent(Singleton.class);
      }
   }

   public static boolean convertBooleanProperty(Object property, boolean defaultValue) {
      if (property == null) {
         return defaultValue;
      } else {
         return property instanceof Boolean ? (Boolean)property : Boolean.valueOf(property.toString());
      }
   }
}
