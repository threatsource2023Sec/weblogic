package com.bea.core.repackaged.springframework.transaction.config;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.jta.JtaTransactionManager;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class JtaTransactionManagerFactoryBean implements FactoryBean {
   private static final String WEBLOGIC_JTA_TRANSACTION_MANAGER_CLASS_NAME = "com.bea.core.repackaged.springframework.transaction.jta.WebLogicJtaTransactionManager";
   private static final String WEBSPHERE_TRANSACTION_MANAGER_CLASS_NAME = "com.bea.core.repackaged.springframework.transaction.jta.WebSphereUowTransactionManager";
   private static final String JTA_TRANSACTION_MANAGER_CLASS_NAME = "com.bea.core.repackaged.springframework.transaction.jta.JtaTransactionManager";
   private static final boolean weblogicPresent;
   private static final boolean webspherePresent;
   @Nullable
   private final JtaTransactionManager transactionManager;

   public JtaTransactionManagerFactoryBean() {
      String className = resolveJtaTransactionManagerClassName();

      try {
         Class clazz = ClassUtils.forName(className, JtaTransactionManagerFactoryBean.class.getClassLoader());
         this.transactionManager = (JtaTransactionManager)BeanUtils.instantiateClass(clazz);
      } catch (ClassNotFoundException var3) {
         throw new IllegalStateException("Failed to load JtaTransactionManager class: " + className, var3);
      }
   }

   @Nullable
   public JtaTransactionManager getObject() {
      return this.transactionManager;
   }

   public Class getObjectType() {
      return this.transactionManager != null ? this.transactionManager.getClass() : JtaTransactionManager.class;
   }

   public boolean isSingleton() {
      return true;
   }

   static String resolveJtaTransactionManagerClassName() {
      if (weblogicPresent) {
         return "com.bea.core.repackaged.springframework.transaction.jta.WebLogicJtaTransactionManager";
      } else {
         return webspherePresent ? "com.bea.core.repackaged.springframework.transaction.jta.WebSphereUowTransactionManager" : "com.bea.core.repackaged.springframework.transaction.jta.JtaTransactionManager";
      }
   }

   static {
      ClassLoader classLoader = JtaTransactionManagerFactoryBean.class.getClassLoader();
      weblogicPresent = ClassUtils.isPresent("weblogic.transaction.UserTransaction", classLoader);
      webspherePresent = ClassUtils.isPresent("com.ibm.wsspi.uow.UOWManager", classLoader);
   }
}
