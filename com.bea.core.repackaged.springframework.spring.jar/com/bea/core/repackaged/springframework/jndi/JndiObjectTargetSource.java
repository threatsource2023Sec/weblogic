package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.naming.NamingException;

public class JndiObjectTargetSource extends JndiObjectLocator implements TargetSource {
   private boolean lookupOnStartup = true;
   private boolean cache = true;
   @Nullable
   private Object cachedObject;
   @Nullable
   private Class targetClass;

   public void setLookupOnStartup(boolean lookupOnStartup) {
      this.lookupOnStartup = lookupOnStartup;
   }

   public void setCache(boolean cache) {
      this.cache = cache;
   }

   public void afterPropertiesSet() throws NamingException {
      super.afterPropertiesSet();
      if (this.lookupOnStartup) {
         Object object = this.lookup();
         if (this.cache) {
            this.cachedObject = object;
         } else {
            this.targetClass = object.getClass();
         }
      }

   }

   @Nullable
   public Class getTargetClass() {
      if (this.cachedObject != null) {
         return this.cachedObject.getClass();
      } else {
         return this.targetClass != null ? this.targetClass : this.getExpectedType();
      }
   }

   public boolean isStatic() {
      return this.cachedObject != null;
   }

   @Nullable
   public Object getTarget() {
      try {
         if (!this.lookupOnStartup && this.cache) {
            synchronized(this) {
               if (this.cachedObject == null) {
                  this.cachedObject = this.lookup();
               }

               return this.cachedObject;
            }
         } else {
            return this.cachedObject != null ? this.cachedObject : this.lookup();
         }
      } catch (NamingException var4) {
         throw new JndiLookupFailureException("JndiObjectTargetSource failed to obtain new target object", var4);
      }
   }

   public void releaseTarget(Object target) {
   }
}
