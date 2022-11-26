package com.bea.core.jatmi.intf;

import java.io.Serializable;
import javax.transaction.Transaction;
import javax.transaction.xa.Xid;

public interface TCResourceService {
   int getWLSFormatID();

   Xid createWLSXid(int var1, byte[] var2, byte[] var3);

   boolean isTightlyCoupledTransactionsEnabled();

   boolean getParallelXAEnabled();

   Serializable getTransactionProperty(Transaction var1, String var2);
}
