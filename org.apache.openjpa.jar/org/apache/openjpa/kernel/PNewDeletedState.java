package org.apache.openjpa.kernel;

class PNewDeletedState extends PCState {
   PCState commit(StateManagerImpl context) {
      context.clearFields();
      return TRANSIENT;
   }

   PCState commitRetain(StateManagerImpl context) {
      context.clearFields();
      return TRANSIENT;
   }

   PCState rollback(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      context.restoreFields();
      return TRANSIENT;
   }

   PCState persist(StateManagerImpl context) {
      context.eraseFlush();
      return PNEW;
   }

   PCState nontransactional(StateManagerImpl context) {
      return this.error("deleted", context);
   }

   PCState release(StateManagerImpl context) {
      return this.error("deleted", context);
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return this.error("deleted", context);
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return this.error("deleted", context);
   }

   boolean isTransactional() {
      return true;
   }

   boolean isPersistent() {
      return true;
   }

   boolean isNew() {
      return true;
   }

   boolean isDeleted() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
