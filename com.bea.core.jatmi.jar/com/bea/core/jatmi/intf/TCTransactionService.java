package com.bea.core.jatmi.intf;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public interface TCTransactionService {
   void shutdown(int var1);

   int getRealTransactionTimeout();

   void registerResource(String var1, XAResource var2) throws SystemException;

   void unregisterResource(String var1);

   TuxedoLoggable createTuxedoLoggable();

   TuxedoLoggable createTuxedoLoggable(Xid var1, int var2);

   XAResource getXAResource();

   Transaction getTransaction();

   Transaction getTransaction(Xid var1);

   Xid getXidFromTransaction(Transaction var1);

   Xid getXidFromThread();

   int getXidFormatId();

   void resumeTransaction(Transaction var1) throws Exception;
}
