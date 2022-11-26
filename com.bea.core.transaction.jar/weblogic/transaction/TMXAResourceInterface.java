package weblogic.transaction;

import javax.transaction.xa.Xid;
import weblogic.transaction.internal.TransactionImpl;

public interface TMXAResourceInterface extends javax.transaction.xa.XAResource {
   void add(Xid var1, TransactionImpl var2);

   TransactionImpl get(Xid var1);

   Xid[] getIndoubtXids();

   void remove(Xid var1);
}
