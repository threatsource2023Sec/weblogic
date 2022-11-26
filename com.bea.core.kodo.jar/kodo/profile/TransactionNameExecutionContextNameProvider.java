package kodo.profile;

import javax.transaction.Transaction;
import org.apache.openjpa.kernel.Broker;

public class TransactionNameExecutionContextNameProvider implements KodoExecutionContextNameProvider {
   private String nameMethod = "getName";

   public String getCreationPoint(Object creationPtType, Broker broker) {
      try {
         Transaction transaction = broker.getManagedRuntime().getTransactionManager().getTransaction();
         Object name = transaction.getClass().getMethod(this.nameMethod).invoke(transaction);
         return name == null ? "null" : name.toString();
      } catch (Exception var5) {
         return var5.toString();
      }
   }

   public void setNameMethod(String nameMethod) {
      this.nameMethod = nameMethod;
   }

   public String getNameMethod() {
      return this.nameMethod;
   }
}
