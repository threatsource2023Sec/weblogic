package weblogic.iiop.server;

import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import weblogic.iiop.contexts.PropagationContextImpl;
import weblogic.transaction.internal.PropagationContext;

public interface OTSSupport {
   Transaction importOTSTransaction(PropagationContextImpl var1) throws XAException;

   PropagationContextImpl exportOTSTransaction(PropagationContext var1) throws Throwable;
}
