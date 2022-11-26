package weblogic.cache.tx;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

public class JNDITransactionManagerJTAIntegration extends AbstractTransactionManagerJTAIntegration {
   private final Context _ctx = new InitialContext();

   public JNDITransactionManagerJTAIntegration() throws NamingException {
   }

   protected String getTransactionManagerLocation() {
      return "java:com/TransactionManager";
   }

   protected TransactionManager getTransactionManager() throws Exception {
      return (TransactionManager)this._ctx.lookup(this.getTransactionManagerLocation());
   }
}
