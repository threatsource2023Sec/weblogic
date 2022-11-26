package weblogic.ejb.container.interfaces;

import java.util.List;
import java.util.Set;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.utils.PartialOrderSet;

public interface EntityTxManager extends TxManager {
   PartialOrderSet getEnrolledKeys(Transaction var1);

   List getNotModifiedOtherTxKeys(Transaction var1);

   boolean isFlushPending(Transaction var1, Object var2);

   boolean needsToBeInserted(Transaction var1, Object var2) throws SystemException, RollbackException;

   List getFlushedKeys(Transaction var1);

   void executeUpdateOperations(Transaction var1, Set var2, boolean var3, boolean var4) throws InternalException;

   void executeDeleteOperations(Transaction var1, Set var2, boolean var3, boolean var4) throws InternalException;

   void executeInsertOperations(Transaction var1, Set var2, boolean var3, boolean var4) throws InternalException;

   void registerInsertBean(Object var1, Transaction var2) throws InternalException;

   boolean registerDeleteBean(Object var1, Transaction var2) throws InternalException;

   void registerInsertDeletedBean(Object var1, Transaction var2) throws InternalException;

   void registerM2NJoinTableInsert(Object var1, String var2, Transaction var3) throws InternalException;

   void registerModifiedBean(Object var1, Transaction var2) throws InternalException;

   void registerInvalidatedBean(Object var1, Transaction var2) throws InternalException;

   void unregisterModifiedBean(Object var1, Transaction var2) throws InternalException;

   void flushModifiedBeans(Transaction var1) throws InternalException;

   void flushModifiedBeans(Transaction var1, boolean var2) throws InternalException;
}
