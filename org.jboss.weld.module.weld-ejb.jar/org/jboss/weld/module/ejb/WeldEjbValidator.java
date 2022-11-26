package org.jboss.weld.module.ejb;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Default.Literal;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.transaction.UserTransaction;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.PlugableValidator;
import org.jboss.weld.util.reflection.Formats;

class WeldEjbValidator implements PlugableValidator {
   public void validateInjectionPointForDefinitionErrors(InjectionPoint ij, Bean bean, BeanManagerImpl beanManager) {
      if (bean instanceof SessionBean) {
         SessionBean sessionBean = (SessionBean)bean;
         if (UserTransaction.class.equals(ij.getType()) && (ij.getQualifiers().isEmpty() || ij.getQualifiers().contains(Literal.INSTANCE)) && this.hasContainerManagedTransactions(sessionBean)) {
            throw ValidatorLogger.LOG.userTransactionInjectionIntoBeanWithContainerManagedTransactions(ij, Formats.formatAsStackTraceElement(ij));
         }
      }

   }

   private boolean hasContainerManagedTransactions(SessionBean bean) {
      TransactionManagement transactionManagementAnnotation = (TransactionManagement)bean.getAnnotated().getAnnotation(TransactionManagement.class);
      if (transactionManagementAnnotation == null) {
         return true;
      } else {
         return transactionManagementAnnotation.value() == TransactionManagementType.CONTAINER;
      }
   }
}
