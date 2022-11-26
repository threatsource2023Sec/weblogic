package org.apache.openjpa.kernel;

class EDeletedState extends PCState {
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
      return ENONTRANS;
   }

   PCState nontransactional(StateManagerImpl context) {
      return this.error("dirty", context);
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
   }

   boolean isTransactional() {
      return true;
   }

   public boolean isPersistent() {
      return true;
   }

   boolean isDeleted() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
