package weblogic.cache.tx;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionSynchronizationRegistry;

public class JNDISynchronizationRegistryJTAIntegration extends AbstractSynchronizationRegistryJTAIntegration {
   private final Context _ctx = new InitialContext();

   public JNDISynchronizationRegistryJTAIntegration() throws NamingException {
   }

   protected String getSynchronizationRegistryLocation() {
      return "java:com/TransactionSynchronizationRegistry";
   }

   protected TransactionSynchronizationRegistry getSynchronizationRegistry() throws Exception {
      return (TransactionSynchronizationRegistry)this._ctx.lookup(this.getSynchronizationRegistryLocation());
   }
}
