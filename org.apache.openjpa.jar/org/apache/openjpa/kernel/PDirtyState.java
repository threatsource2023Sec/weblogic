package org.apache.openjpa.kernel;

class PDirtyState extends PCState {
   void initialize(StateManagerImpl context) {
      context.saveFields(false);
   }

   void beforeFlush(StateManagerImpl context, boolean logical, OpCallbacks call) {
      context.preFlush(logical, call);
   }

   PCState commit(StateManagerImpl context) {
      return HOLLOW;
   }

   PCState commitRetain(StateManagerImpl context) {
      return PNONTRANS;
   }

   PCState rollback(StateManagerImpl context) {
      return HOLLOW;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      context.restoreFields();
      return PNONTRANS;
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return PDELETED;
   }

   PCState nontransactional(StateManagerImpl context) {
      return this.error("dirty", context);
   }

   PCState release(StateManagerImpl context) {
      return this.error("dirty", context);
   }

   boolean isVersionCheckRequired(StateManagerImpl context) {
      return !context.isFlushed() || context.isFlushedDirty();
   }

   PCState afterRefresh() {
      return PCLEAN;
   }

   PCState afterOptimisticRefresh() {
      return PNONTRANS;
   }

   boolean isTransactional() {
      return true;
   }

   boolean isPersistent() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
