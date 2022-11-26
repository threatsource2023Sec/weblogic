package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.NoTransactionException;
import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.TransactionStatus;
import com.bea.core.repackaged.springframework.transaction.TransactionSystemException;
import com.bea.core.repackaged.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

public abstract class TransactionAspectSupport implements BeanFactoryAware, InitializingBean {
   private static final Object DEFAULT_TRANSACTION_MANAGER_KEY = new Object();
   private static final ThreadLocal transactionInfoHolder = new NamedThreadLocal("Current aspect-driven transaction");
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private String transactionManagerBeanName;
   @Nullable
   private PlatformTransactionManager transactionManager;
   @Nullable
   private TransactionAttributeSource transactionAttributeSource;
   @Nullable
   private BeanFactory beanFactory;
   private final ConcurrentMap transactionManagerCache = new ConcurrentReferenceHashMap(4);

   @Nullable
   protected static TransactionInfo currentTransactionInfo() throws NoTransactionException {
      return (TransactionInfo)transactionInfoHolder.get();
   }

   public static TransactionStatus currentTransactionStatus() throws NoTransactionException {
      TransactionInfo info = currentTransactionInfo();
      if (info != null && info.transactionStatus != null) {
         return info.transactionStatus;
      } else {
         throw new NoTransactionException("No transaction aspect-managed TransactionStatus in scope");
      }
   }

   public void setTransactionManagerBeanName(@Nullable String transactionManagerBeanName) {
      this.transactionManagerBeanName = transactionManagerBeanName;
   }

   @Nullable
   protected final String getTransactionManagerBeanName() {
      return this.transactionManagerBeanName;
   }

   public void setTransactionManager(@Nullable PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   @Nullable
   public PlatformTransactionManager getTransactionManager() {
      return this.transactionManager;
   }

   public void setTransactionAttributes(Properties transactionAttributes) {
      NameMatchTransactionAttributeSource tas = new NameMatchTransactionAttributeSource();
      tas.setProperties(transactionAttributes);
      this.transactionAttributeSource = tas;
   }

   public void setTransactionAttributeSources(TransactionAttributeSource... transactionAttributeSources) {
      this.transactionAttributeSource = new CompositeTransactionAttributeSource(transactionAttributeSources);
   }

   public void setTransactionAttributeSource(@Nullable TransactionAttributeSource transactionAttributeSource) {
      this.transactionAttributeSource = transactionAttributeSource;
   }

   @Nullable
   public TransactionAttributeSource getTransactionAttributeSource() {
      return this.transactionAttributeSource;
   }

   public void setBeanFactory(@Nullable BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   @Nullable
   protected final BeanFactory getBeanFactory() {
      return this.beanFactory;
   }

   public void afterPropertiesSet() {
      if (this.getTransactionManager() == null && this.beanFactory == null) {
         throw new IllegalStateException("Set the 'transactionManager' property or make sure to run within a BeanFactory containing a PlatformTransactionManager bean!");
      } else if (this.getTransactionAttributeSource() == null) {
         throw new IllegalStateException("Either 'transactionAttributeSource' or 'transactionAttributes' is required: If there are no transactional methods, then don't use a transaction aspect.");
      }
   }

   @Nullable
   protected Object invokeWithinTransaction(Method method, @Nullable Class targetClass, InvocationCallback invocation) throws Throwable {
      TransactionAttributeSource tas = this.getTransactionAttributeSource();
      TransactionAttribute txAttr = tas != null ? tas.getTransactionAttribute(method, targetClass) : null;
      PlatformTransactionManager tm = this.determineTransactionManager(txAttr);
      String joinpointIdentification = this.methodIdentification(method, targetClass, txAttr);
      Object result;
      if (txAttr != null && tm instanceof CallbackPreferringPlatformTransactionManager) {
         ThrowableHolder throwableHolder = new ThrowableHolder();

         try {
            result = ((CallbackPreferringPlatformTransactionManager)tm).execute(txAttr, (status) -> {
               TransactionInfo txInfo = this.prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);

               Object var9;
               try {
                  Object var8 = invocation.proceedWithInvocation();
                  return var8;
               } catch (Throwable var13) {
                  if (txAttr.rollbackOn(var13)) {
                     if (var13 instanceof RuntimeException) {
                        throw (RuntimeException)var13;
                     }

                     throw new ThrowableHolderException(var13);
                  }

                  throwableHolder.throwable = var13;
                  var9 = null;
               } finally {
                  this.cleanupTransactionInfo(txInfo);
               }

               return var9;
            });
            if (throwableHolder.throwable != null) {
               throw throwableHolder.throwable;
            } else {
               return result;
            }
         } catch (ThrowableHolderException var19) {
            throw var19.getCause();
         } catch (TransactionSystemException var20) {
            if (throwableHolder.throwable != null) {
               this.logger.error("Application exception overridden by commit exception", throwableHolder.throwable);
               var20.initApplicationException(throwableHolder.throwable);
            }

            throw var20;
         } catch (Throwable var21) {
            if (throwableHolder.throwable != null) {
               this.logger.error("Application exception overridden by commit exception", throwableHolder.throwable);
            }

            throw var21;
         }
      } else {
         TransactionInfo txInfo = this.createTransactionIfNecessary(tm, txAttr, joinpointIdentification);

         try {
            result = invocation.proceedWithInvocation();
         } catch (Throwable var17) {
            this.completeTransactionAfterThrowing(txInfo, var17);
            throw var17;
         } finally {
            this.cleanupTransactionInfo(txInfo);
         }

         this.commitTransactionAfterReturning(txInfo);
         return result;
      }
   }

   protected void clearTransactionManagerCache() {
      this.transactionManagerCache.clear();
      this.beanFactory = null;
   }

   @Nullable
   protected PlatformTransactionManager determineTransactionManager(@Nullable TransactionAttribute txAttr) {
      if (txAttr != null && this.beanFactory != null) {
         String qualifier = txAttr.getQualifier();
         if (StringUtils.hasText(qualifier)) {
            return this.determineQualifiedTransactionManager(this.beanFactory, qualifier);
         } else if (StringUtils.hasText(this.transactionManagerBeanName)) {
            return this.determineQualifiedTransactionManager(this.beanFactory, this.transactionManagerBeanName);
         } else {
            PlatformTransactionManager defaultTransactionManager = this.getTransactionManager();
            if (defaultTransactionManager == null) {
               defaultTransactionManager = (PlatformTransactionManager)this.transactionManagerCache.get(DEFAULT_TRANSACTION_MANAGER_KEY);
               if (defaultTransactionManager == null) {
                  defaultTransactionManager = (PlatformTransactionManager)this.beanFactory.getBean(PlatformTransactionManager.class);
                  this.transactionManagerCache.putIfAbsent(DEFAULT_TRANSACTION_MANAGER_KEY, defaultTransactionManager);
               }
            }

            return defaultTransactionManager;
         }
      } else {
         return this.getTransactionManager();
      }
   }

   private PlatformTransactionManager determineQualifiedTransactionManager(BeanFactory beanFactory, String qualifier) {
      PlatformTransactionManager txManager = (PlatformTransactionManager)this.transactionManagerCache.get(qualifier);
      if (txManager == null) {
         txManager = (PlatformTransactionManager)BeanFactoryAnnotationUtils.qualifiedBeanOfType(beanFactory, PlatformTransactionManager.class, qualifier);
         this.transactionManagerCache.putIfAbsent(qualifier, txManager);
      }

      return txManager;
   }

   private String methodIdentification(Method method, @Nullable Class targetClass, @Nullable TransactionAttribute txAttr) {
      String methodIdentification = this.methodIdentification(method, targetClass);
      if (methodIdentification == null) {
         if (txAttr instanceof DefaultTransactionAttribute) {
            methodIdentification = ((DefaultTransactionAttribute)txAttr).getDescriptor();
         }

         if (methodIdentification == null) {
            methodIdentification = ClassUtils.getQualifiedMethodName(method, targetClass);
         }
      }

      return methodIdentification;
   }

   @Nullable
   protected String methodIdentification(Method method, @Nullable Class targetClass) {
      return null;
   }

   protected TransactionInfo createTransactionIfNecessary(@Nullable PlatformTransactionManager tm, @Nullable TransactionAttribute txAttr, final String joinpointIdentification) {
      if (txAttr != null && ((TransactionAttribute)txAttr).getName() == null) {
         txAttr = new DelegatingTransactionAttribute((TransactionAttribute)txAttr) {
            public String getName() {
               return joinpointIdentification;
            }
         };
      }

      TransactionStatus status = null;
      if (txAttr != null) {
         if (tm != null) {
            status = tm.getTransaction((TransactionDefinition)txAttr);
         } else if (this.logger.isDebugEnabled()) {
            this.logger.debug("Skipping transactional joinpoint [" + joinpointIdentification + "] because no transaction manager has been configured");
         }
      }

      return this.prepareTransactionInfo(tm, (TransactionAttribute)txAttr, joinpointIdentification, status);
   }

   protected TransactionInfo prepareTransactionInfo(@Nullable PlatformTransactionManager tm, @Nullable TransactionAttribute txAttr, String joinpointIdentification, @Nullable TransactionStatus status) {
      TransactionInfo txInfo = new TransactionInfo(tm, txAttr, joinpointIdentification);
      if (txAttr != null) {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Getting transaction for [" + txInfo.getJoinpointIdentification() + "]");
         }

         txInfo.newTransactionStatus(status);
      } else if (this.logger.isTraceEnabled()) {
         this.logger.trace("No need to create transaction for [" + joinpointIdentification + "]: This method is not transactional.");
      }

      txInfo.bindToThread();
      return txInfo;
   }

   protected void commitTransactionAfterReturning(@Nullable TransactionInfo txInfo) {
      if (txInfo != null && txInfo.getTransactionStatus() != null) {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Completing transaction for [" + txInfo.getJoinpointIdentification() + "]");
         }

         txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
      }

   }

   protected void completeTransactionAfterThrowing(@Nullable TransactionInfo txInfo, Throwable ex) {
      if (txInfo != null && txInfo.getTransactionStatus() != null) {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Completing transaction for [" + txInfo.getJoinpointIdentification() + "] after exception: " + ex);
         }

         if (txInfo.transactionAttribute != null && txInfo.transactionAttribute.rollbackOn(ex)) {
            try {
               txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
            } catch (TransactionSystemException var6) {
               this.logger.error("Application exception overridden by rollback exception", ex);
               var6.initApplicationException(ex);
               throw var6;
            } catch (Error | RuntimeException var7) {
               this.logger.error("Application exception overridden by rollback exception", ex);
               throw var7;
            }
         } else {
            try {
               txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
            } catch (TransactionSystemException var4) {
               this.logger.error("Application exception overridden by commit exception", ex);
               var4.initApplicationException(ex);
               throw var4;
            } catch (Error | RuntimeException var5) {
               this.logger.error("Application exception overridden by commit exception", ex);
               throw var5;
            }
         }
      }

   }

   protected void cleanupTransactionInfo(@Nullable TransactionInfo txInfo) {
      if (txInfo != null) {
         txInfo.restoreThreadLocalStatus();
      }

   }

   private static class ThrowableHolderException extends RuntimeException {
      public ThrowableHolderException(Throwable throwable) {
         super(throwable);
      }

      public String toString() {
         return this.getCause().toString();
      }
   }

   private static class ThrowableHolder {
      @Nullable
      public Throwable throwable;

      private ThrowableHolder() {
      }

      // $FF: synthetic method
      ThrowableHolder(Object x0) {
         this();
      }
   }

   @FunctionalInterface
   protected interface InvocationCallback {
      Object proceedWithInvocation() throws Throwable;
   }

   protected final class TransactionInfo {
      @Nullable
      private final PlatformTransactionManager transactionManager;
      @Nullable
      private final TransactionAttribute transactionAttribute;
      private final String joinpointIdentification;
      @Nullable
      private TransactionStatus transactionStatus;
      @Nullable
      private TransactionInfo oldTransactionInfo;

      public TransactionInfo(@Nullable PlatformTransactionManager transactionManager, @Nullable TransactionAttribute transactionAttribute, String joinpointIdentification) {
         this.transactionManager = transactionManager;
         this.transactionAttribute = transactionAttribute;
         this.joinpointIdentification = joinpointIdentification;
      }

      public PlatformTransactionManager getTransactionManager() {
         Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");
         return this.transactionManager;
      }

      @Nullable
      public TransactionAttribute getTransactionAttribute() {
         return this.transactionAttribute;
      }

      public String getJoinpointIdentification() {
         return this.joinpointIdentification;
      }

      public void newTransactionStatus(@Nullable TransactionStatus status) {
         this.transactionStatus = status;
      }

      @Nullable
      public TransactionStatus getTransactionStatus() {
         return this.transactionStatus;
      }

      public boolean hasTransaction() {
         return this.transactionStatus != null;
      }

      private void bindToThread() {
         this.oldTransactionInfo = (TransactionInfo)TransactionAspectSupport.transactionInfoHolder.get();
         TransactionAspectSupport.transactionInfoHolder.set(this);
      }

      private void restoreThreadLocalStatus() {
         TransactionAspectSupport.transactionInfoHolder.set(this.oldTransactionInfo);
      }

      public String toString() {
         return this.transactionAttribute != null ? this.transactionAttribute.toString() : "No transaction";
      }
   }
}
