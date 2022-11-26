package org.apache.openjpa.kernel;

class ECopyState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setLoaded(true);
      context.setDirty(true);
   }

   void beforeFlush(StateManagerImpl context, boolean logical, OpCallbacks call) {
      context.preFlush(logical, call);
   }

   PCState commit(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState commitRetain(StateManagerImpl context) {
      return ENONTRANS;
   }

   PCState rollback(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      return ENONTRANS;
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return EDELETED;
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

   boolean isPersistent() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
