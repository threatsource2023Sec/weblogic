package kodo.conf.descriptor;

public interface ExecutionContextNameProviderBean {
   StackExecutionContextNameProviderBean getStackExecutionContextNameProvider();

   StackExecutionContextNameProviderBean createStackExecutionContextNameProvider();

   void destroyStackExecutionContextNameProvider();

   TransactionNameExecutionContextNameProviderBean getTransactionNameExecutionContextNameProvider();

   TransactionNameExecutionContextNameProviderBean createTransactionNameExecutionContextNameProvider();

   void destroyTransactionNameExecutionContextNameProvider();

   UserObjectExecutionContextNameProviderBean getUserObjectExecutionContextNameProvider();

   UserObjectExecutionContextNameProviderBean createUserObjectExecutionContextNameProvider();

   void destroyUserObjectExecutionContextNameProvider();

   Class[] getExecutionContextNameProviderTypes();

   ExecutionContextNameProviderBean getExecutionContextNameProvider();

   ExecutionContextNameProviderBean createExecutionContextNameProvider(Class var1);

   void destroyExecutionContextNameProvider();
}
