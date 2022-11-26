package weblogic.deployment.jms;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.wrapper.WrapperFactory;

public class JMSWrapperFactory extends WrapperFactory {
   private Map classCache = new HashMap();

   public int needPreInvocationHandler(Method vendorMethod) {
      return 2;
   }

   public int needPostInvocationHandler(Method vendorMethod) {
      return 2;
   }

   public Class getCachedWrapperClass(String superClassName, Object vendorObj, ClassLoader classLoader) {
      WrappedClassKey key = new WrappedClassKey(superClassName, vendorObj.getClass());
      Class ret = (Class)this.classCache.get(key);
      if (ret == null && JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Generating new JMS wrapper class for wrapper class " + superClassName + " and vendor class " + vendorObj.getClass().getName());
      }

      return ret;
   }

   public void putCachedWrapperClass(String superClassName, Object vendorObj, Class wrapperClass, ClassLoader classLoader) {
      WrappedClassKey key = new WrappedClassKey(superClassName, vendorObj.getClass());
      this.classCache.put(key, wrapperClass);
   }

   private static final class WrappedClassKey {
      private String wrapperClass;
      private Class vendorClass;

      WrappedClassKey(String wrapperClass, Class vendorClass) {
         this.wrapperClass = wrapperClass;
         this.vendorClass = vendorClass;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else {
            try {
               WrappedClassKey key = (WrappedClassKey)o;
               return this.wrapperClass.equals(key.wrapperClass) && this.vendorClass.equals(key.vendorClass);
            } catch (ClassCastException var3) {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.wrapperClass.hashCode() + this.vendorClass.hashCode();
      }
   }
}
