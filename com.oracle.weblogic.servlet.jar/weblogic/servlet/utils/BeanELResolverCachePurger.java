package weblogic.servlet.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import javax.el.BeanELResolver;
import weblogic.servlet.HTTPLogger;

public class BeanELResolverCachePurger {
   private static Map cache = null;

   private BeanELResolverCachePurger() {
   }

   public static void purgeCache(ClassLoader cl) {
      if (cl != null && cache != null && !cache.isEmpty()) {
         Iterator it = cache.keySet().iterator();

         while(it.hasNext()) {
            Class beanClass = (Class)it.next();
            if (isSameOrChild(cl, beanClass.getClassLoader())) {
               it.remove();
            }
         }

      }
   }

   private static boolean isSameOrChild(ClassLoader appLoader, ClassLoader beanLoader) {
      for(ClassLoader cl = appLoader; cl != ClassLoader.getSystemClassLoader(); cl = cl.getParent()) {
         if (cl == beanLoader) {
            return true;
         }
      }

      return false;
   }

   static {
      try {
         Field propertiesField = BeanELResolver.class.getDeclaredField("properties");
         propertiesField.setAccessible(true);
         Object properties = propertiesField.get((Object)null);
         Field propertiesMapField = properties.getClass().getDeclaredField("map");
         propertiesMapField.setAccessible(true);
         cache = (Map)propertiesMapField.get(properties);
      } catch (NoSuchFieldException var3) {
         HTTPLogger.logBeanELResolverPurgerException(var3);
      } catch (IllegalAccessException var4) {
         HTTPLogger.logBeanELResolverPurgerException(var4);
      } catch (Exception var5) {
         HTTPLogger.logBeanELResolverPurgerException(var5);
      }

   }
}
