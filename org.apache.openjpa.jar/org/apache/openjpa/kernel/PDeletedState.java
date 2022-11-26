package org.apache.openjpa.kernel;

class PDeletedState extends PCState {
   void initialize(StateManagerImpl context) {
      context.saveFields(false);
   }

   PCState flush(StateManagerImpl context) {
      return PDELETEDFLUSHED;
   }

   PCState commit(StateManagerImpl context) {
      context.clearFields();
      return TRANSIENT;
   }

   PCState commitRetain(StateManagerImpl context) {
      context.clearFields();
      return TRANSIENT;
   }

   PCState rollback(StateManagerImpl context) {
      return HOLLOW;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      context.restoreFields();
      return PNONTRANS;
   }

   PCState nontransactional(StateManagerImpl context) {
      return this.error("deleted", context);
   }

   PCState persist(StateManagerImpl context) {
      return context.getDirty().length() > 0 ? PDIRTY : PCLEAN;
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

   boolean isDeleted() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
