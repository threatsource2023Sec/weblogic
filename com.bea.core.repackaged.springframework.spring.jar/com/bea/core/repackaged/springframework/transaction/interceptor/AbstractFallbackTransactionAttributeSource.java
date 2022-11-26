package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.core.MethodClassKey;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractFallbackTransactionAttributeSource implements TransactionAttributeSource {
   private static final TransactionAttribute NULL_TRANSACTION_ATTRIBUTE = new DefaultTransactionAttribute() {
      public String toString() {
         return "null";
      }
   };
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final Map attributeCache = new ConcurrentHashMap(1024);

   @Nullable
   public TransactionAttribute getTransactionAttribute(Method method, @Nullable Class targetClass) {
      if (method.getDeclaringClass() == Object.class) {
         return null;
      } else {
         Object cacheKey = this.getCacheKey(method, targetClass);
         TransactionAttribute cached = (TransactionAttribute)this.attributeCache.get(cacheKey);
         if (cached != null) {
            return cached == NULL_TRANSACTION_ATTRIBUTE ? null : cached;
         } else {
            TransactionAttribute txAttr = this.computeTransactionAttribute(method, targetClass);
            if (txAttr == null) {
               this.attributeCache.put(cacheKey, NULL_TRANSACTION_ATTRIBUTE);
            } else {
               String methodIdentification = ClassUtils.getQualifiedMethodName(method, targetClass);
               if (txAttr instanceof DefaultTransactionAttribute) {
                  ((DefaultTransactionAttribute)txAttr).setDescriptor(methodIdentification);
               }

               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Adding transactional method '" + methodIdentification + "' with attribute: " + txAttr);
               }

               this.attributeCache.put(cacheKey, txAttr);
            }

            return txAttr;
         }
      }
   }

   protected Object getCacheKey(Method method, @Nullable Class targetClass) {
      return new MethodClassKey(method, targetClass);
   }

   @Nullable
   protected TransactionAttribute computeTransactionAttribute(Method method, @Nullable Class targetClass) {
      if (this.allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
         return null;
      } else {
         Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
         TransactionAttribute txAttr = this.findTransactionAttribute(specificMethod);
         if (txAttr != null) {
            return txAttr;
         } else {
            txAttr = this.findTransactionAttribute(specificMethod.getDeclaringClass());
            if (txAttr != null && ClassUtils.isUserLevelMethod(method)) {
               return txAttr;
            } else {
               if (specificMethod != method) {
                  txAttr = this.findTransactionAttribute(method);
                  if (txAttr != null) {
                     return txAttr;
                  }

                  txAttr = this.findTransactionAttribute(method.getDeclaringClass());
                  if (txAttr != null && ClassUtils.isUserLevelMethod(method)) {
                     return txAttr;
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   protected abstract TransactionAttribute findTransactionAttribute(Class var1);

   @Nullable
   protected abstract TransactionAttribute findTransactionAttribute(Method var1);

   protected boolean allowPublicMethodsOnly() {
      return false;
   }
}
