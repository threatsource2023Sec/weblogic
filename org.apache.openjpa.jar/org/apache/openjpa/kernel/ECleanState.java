package org.apache.openjpa.kernel;

class ECleanState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setDirty(false);
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
      return ENONTRANS;
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState evict(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return EDIRTY;
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return EDIRTY;
   }

   boolean isTransactional() {
      return true;
   }

   boolean isPersistent() {
      return true;
   }
}
