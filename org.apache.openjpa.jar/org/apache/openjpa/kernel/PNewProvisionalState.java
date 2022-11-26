package org.apache.openjpa.kernel;

class PNewProvisionalState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setLoaded(true);
      context.setDirty(true);
      context.saveFields(false);
   }

   PCState persist(StateManagerImpl context) {
      return PNEW;
   }

   PCState nonprovisional(StateManagerImpl context, boolean logical, OpCallbacks call) {
      context.preFlush(logical, call);
      return PNEW;
   }

   PCState commit(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState commitRetain(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState rollback(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      context.restoreFields();
      return TRANSIENT;
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return TRANSIENT;
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
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

   boolean isDirty() {
      return true;
   }

   boolean isProvisional() {
      return true;
   }
}
