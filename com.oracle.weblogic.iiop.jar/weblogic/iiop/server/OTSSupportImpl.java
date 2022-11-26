package weblogic.iiop.server;

import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import weblogic.corba.cos.transactions.OTSHelper;
import weblogic.iiop.contexts.PropagationContextImpl;
import weblogic.transaction.internal.PropagationContext;

public class OTSSupportImpl implements OTSSupport {
   public Transaction importOTSTransaction(PropagationContextImpl propagationContext) throws XAException {
      return OTSHelper.importTransaction(propagationContext, 0);
   }

   public PropagationContextImpl exportOTSTransaction(PropagationContext propagationContext) throws Throwable {
      return OTSHelper.exportTransaction((PropagationContext)propagationContext, 1);
   }
}
