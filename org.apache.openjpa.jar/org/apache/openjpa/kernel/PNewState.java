package org.apache.openjpa.kernel;

class PNewState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setLoaded(true);
      context.setDirty(true);
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
      return TRANSIENT;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      context.restoreFields();
      return TRANSIENT;
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return context.isFlushed() ? PNEWFLUSHEDDELETED : PNEWDELETED;
   }

   PCState nontransactional(StateManagerImpl context) {
      return this.error("new", context);
   }

   PCState release(StateManagerImpl context) {
      return this.error("new", context);
   }

   boolean isVersionCheckRequired(StateManagerImpl context) {
      return context.isFlushedDirty();
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
}
