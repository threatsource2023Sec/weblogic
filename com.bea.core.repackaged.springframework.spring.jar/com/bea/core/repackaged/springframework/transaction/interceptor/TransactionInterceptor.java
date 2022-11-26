package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Properties;

public class TransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor, Serializable {
   public TransactionInterceptor() {
   }

   public TransactionInterceptor(PlatformTransactionManager ptm, Properties attributes) {
      this.setTransactionManager(ptm);
      this.setTransactionAttributes(attributes);
   }

   public TransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource tas) {
      this.setTransactionManager(ptm);
      this.setTransactionAttributeSource(tas);
   }

   @Nullable
   public Object invoke(MethodInvocation invocation) throws Throwable {
      Class targetClass = invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null;
      Method var10001 = invocation.getMethod();
      invocation.getClass();
      return this.invokeWithinTransaction(var10001, targetClass, invocation::proceed);
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      oos.writeObject(this.getTransactionManagerBeanName());
      oos.writeObject(this.getTransactionManager());
      oos.writeObject(this.getTransactionAttributeSource());
      oos.writeObject(this.getBeanFactory());
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.setTransactionManagerBeanName((String)ois.readObject());
      this.setTransactionManager((PlatformTransactionManager)ois.readObject());
      this.setTransactionAttributeSource((TransactionAttributeSource)ois.readObject());
      this.setBeanFactory((BeanFactory)ois.readObject());
   }
}
