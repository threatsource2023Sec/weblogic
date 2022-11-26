package weblogic.transaction;

public class ClientTxHelper {
   public static TransactionManager getTransactionManager() {
      try {
         return (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      } catch (ClassCastException var1) {
         throw new IllegalStateException("Transactions cannot be used in a client that uses both T3 and IIOP.");
      }
   }
}
