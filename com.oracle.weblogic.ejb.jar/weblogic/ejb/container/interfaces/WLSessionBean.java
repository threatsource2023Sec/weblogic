package weblogic.ejb.container.interfaces;

import java.util.Set;
import javax.transaction.Transaction;

public interface WLSessionBean extends WLEnterpriseBean {
   boolean __WL_isBusy();

   void __WL_setBusy(boolean var1);

   Transaction __WL_getBeanManagedTransaction();

   void __WL_setBeanManagedTransaction(Transaction var1);

   boolean __WL_needsRemove();

   void __WL_setNeedsRemove(boolean var1);

   boolean __WL_needsSessionSynchronization();

   void __WL_setNeedsSessionSynchronization(boolean var1);

   Set __WL_getExtendedPersistenceContexts();
}
