package weblogic.connector.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class IntrospectorManager {
   private static ThreadLocal introspectors = new ThreadLocal();

   public static BeanInfo getBeanInfo(Class beanClass) throws IntrospectionException {
      BeanIntrospector introst = (BeanIntrospector)introspectors.get();
      return introst != null ? introst.getBeanInfo(beanClass) : Introspector.getBeanInfo(beanClass);
   }

   public static void startToolAction() {
      BeanIntrospector introst = (BeanIntrospector)introspectors.get();
      if (introst != null) {
         introspectors.set(new BeanIntrospector());
      }

   }

   public static void endToolAction() {
      BeanIntrospector introst = (BeanIntrospector)introspectors.get();
      if (introst != null) {
         introspectors.remove();
         introst.clear();
      }

   }

   private static class BeanIntrospector {
      private Set cachedClasses;

      private BeanIntrospector() {
         this.cachedClasses = new HashSet();
      }

      public void clear() {
         Iterator var1 = this.cachedClasses.iterator();

         while(var1.hasNext()) {
            Class cls = (Class)var1.next();
            Introspector.flushFromCaches(cls);
         }

         this.cachedClasses.clear();
      }

      public BeanInfo getBeanInfo(Class beanClass) throws IntrospectionException {
         this.cachedClasses.add(beanClass);
         return Introspector.getBeanInfo(beanClass);
      }

      // $FF: synthetic method
      BeanIntrospector(Object x0) {
         this();
      }
   }
}
