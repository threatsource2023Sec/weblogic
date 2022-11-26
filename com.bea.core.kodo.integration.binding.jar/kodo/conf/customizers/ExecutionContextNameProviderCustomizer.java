package kodo.conf.customizers;

import kodo.conf.descriptor.ExecutionContextNameProviderBean;
import kodo.conf.descriptor.StackExecutionContextNameProviderBean;
import kodo.conf.descriptor.TransactionNameExecutionContextNameProviderBean;
import kodo.conf.descriptor.UserObjectExecutionContextNameProviderBean;

public class ExecutionContextNameProviderCustomizer {
   private final ExecutionContextNameProviderBean customized;

   public ExecutionContextNameProviderCustomizer(ExecutionContextNameProviderBean custom) {
      this.customized = custom;
   }

   public Class[] getExecutionContextNameProviderTypes() {
      return new Class[]{StackExecutionContextNameProviderBean.class, TransactionNameExecutionContextNameProviderBean.class, UserObjectExecutionContextNameProviderBean.class};
   }

   public ExecutionContextNameProviderBean getExecutionContextNameProvider() {
      ExecutionContextNameProviderBean provider = null;
      provider = this.customized.getStackExecutionContextNameProvider();
      if (provider != null) {
         return provider;
      } else {
         ExecutionContextNameProviderBean provider = this.customized.getTransactionNameExecutionContextNameProvider();
         if (provider != null) {
            return provider;
         } else {
            ExecutionContextNameProviderBean provider = this.customized.getUserObjectExecutionContextNameProvider();
            return provider != null ? provider : provider;
         }
      }
   }

   public ExecutionContextNameProviderBean createExecutionContextNameProvider(Class type) {
      this.destroyExecutionContextNameProvider();
      ExecutionContextNameProviderBean provider = null;
      if (StackExecutionContextNameProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createStackExecutionContextNameProvider();
      } else if (TransactionNameExecutionContextNameProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createTransactionNameExecutionContextNameProvider();
      } else if (UserObjectExecutionContextNameProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createUserObjectExecutionContextNameProvider();
      }

      return (ExecutionContextNameProviderBean)provider;
   }

   public void destroyExecutionContextNameProvider() {
      ExecutionContextNameProviderBean provider = null;
      provider = this.customized.getStackExecutionContextNameProvider();
      if (provider != null) {
         this.customized.destroyStackExecutionContextNameProvider();
      } else {
         ExecutionContextNameProviderBean provider = this.customized.getTransactionNameExecutionContextNameProvider();
         if (provider != null) {
            this.customized.destroyTransactionNameExecutionContextNameProvider();
         } else {
            ExecutionContextNameProviderBean provider = this.customized.getUserObjectExecutionContextNameProvider();
            if (provider != null) {
               this.customized.destroyUserObjectExecutionContextNameProvider();
            }
         }
      }
   }
}
