package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.PatternMatchUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MethodMapTransactionAttributeSource implements TransactionAttributeSource, BeanClassLoaderAware, InitializingBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private Map methodMap;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   private boolean eagerlyInitialized = false;
   private boolean initialized = false;
   private final Map transactionAttributeMap = new HashMap();
   private final Map methodNameMap = new HashMap();

   public void setMethodMap(Map methodMap) {
      this.methodMap = methodMap;
   }

   public void setBeanClassLoader(ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   public void afterPropertiesSet() {
      this.initMethodMap(this.methodMap);
      this.eagerlyInitialized = true;
      this.initialized = true;
   }

   protected void initMethodMap(@Nullable Map methodMap) {
      if (methodMap != null) {
         methodMap.forEach(this::addTransactionalMethod);
      }

   }

   public void addTransactionalMethod(String name, TransactionAttribute attr) {
      Assert.notNull(name, (String)"Name must not be null");
      int lastDotIndex = name.lastIndexOf(46);
      if (lastDotIndex == -1) {
         throw new IllegalArgumentException("'" + name + "' is not a valid method name: format is FQN.methodName");
      } else {
         String className = name.substring(0, lastDotIndex);
         String methodName = name.substring(lastDotIndex + 1);
         Class clazz = ClassUtils.resolveClassName(className, this.beanClassLoader);
         this.addTransactionalMethod(clazz, methodName, attr);
      }
   }

   public void addTransactionalMethod(Class clazz, String mappedName, TransactionAttribute attr) {
      Assert.notNull(clazz, (String)"Class must not be null");
      Assert.notNull(mappedName, (String)"Mapped name must not be null");
      String name = clazz.getName() + '.' + mappedName;
      Method[] methods = clazz.getDeclaredMethods();
      List matchingMethods = new ArrayList();
      Method[] var7 = methods;
      int var8 = methods.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Method method = var7[var9];
         if (this.isMatch(method.getName(), mappedName)) {
            matchingMethods.add(method);
         }
      }

      if (matchingMethods.isEmpty()) {
         throw new IllegalArgumentException("Could not find method '" + mappedName + "' on class [" + clazz.getName() + "]");
      } else {
         Iterator var11 = matchingMethods.iterator();

         while(true) {
            while(var11.hasNext()) {
               Method method = (Method)var11.next();
               String regMethodName = (String)this.methodNameMap.get(method);
               if (regMethodName != null && (regMethodName.equals(name) || regMethodName.length() > name.length())) {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("Keeping attribute for transactional method [" + method + "]: current name '" + name + "' is not more specific than '" + regMethodName + "'");
                  }
               } else {
                  if (this.logger.isDebugEnabled() && regMethodName != null) {
                     this.logger.debug("Replacing attribute for transactional method [" + method + "]: current name '" + name + "' is more specific than '" + regMethodName + "'");
                  }

                  this.methodNameMap.put(method, name);
                  this.addTransactionalMethod(method, attr);
               }
            }

            return;
         }
      }
   }

   public void addTransactionalMethod(Method method, TransactionAttribute attr) {
      Assert.notNull(method, (String)"Method must not be null");
      Assert.notNull(attr, (String)"TransactionAttribute must not be null");
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Adding transactional method [" + method + "] with attribute [" + attr + "]");
      }

      this.transactionAttributeMap.put(method, attr);
   }

   protected boolean isMatch(String methodName, String mappedName) {
      return PatternMatchUtils.simpleMatch(mappedName, methodName);
   }

   @Nullable
   public TransactionAttribute getTransactionAttribute(Method method, @Nullable Class targetClass) {
      if (this.eagerlyInitialized) {
         return (TransactionAttribute)this.transactionAttributeMap.get(method);
      } else {
         synchronized(this.transactionAttributeMap) {
            if (!this.initialized) {
               this.initMethodMap(this.methodMap);
               this.initialized = true;
            }

            return (TransactionAttribute)this.transactionAttributeMap.get(method);
         }
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MethodMapTransactionAttributeSource)) {
         return false;
      } else {
         MethodMapTransactionAttributeSource otherTas = (MethodMapTransactionAttributeSource)other;
         return ObjectUtils.nullSafeEquals(this.methodMap, otherTas.methodMap);
      }
   }

   public int hashCode() {
      return MethodMapTransactionAttributeSource.class.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.methodMap;
   }
}
