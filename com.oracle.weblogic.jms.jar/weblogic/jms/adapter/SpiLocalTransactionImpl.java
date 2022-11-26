package weblogic.jms.adapter;

import javax.resource.ResourceException;
import javax.resource.spi.LocalTransaction;

public class SpiLocalTransactionImpl implements LocalTransaction {
   private JMSManagedConnection mc;
   private LocalTransactionImpl localTran;

   public SpiLocalTransactionImpl(JMSManagedConnection mc) {
      this.mc = mc;
      this.localTran = new LocalTransactionImpl(mc);
   }

   public void begin() throws ResourceException {
      this.localTran.begin();
   }

   public void commit() throws ResourceException {
      this.localTran.commit();
   }

   public void rollback() throws ResourceException {
      this.localTran.rollback();
   }
}
