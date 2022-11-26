package org.apache.openjpa.kernel;

class PCleanState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setDirty(false);
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
      return PNONTRANS;
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return PDELETED;
   }

   PCState nontransactional(StateManagerImpl context) {
      return PNONTRANS;
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState evict(StateManagerImpl context) {
      return HOLLOW;
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return PDIRTY;
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return PDIRTY;
   }

   boolean isTransactional() {
      return true;
   }

   boolean isPersistent() {
      return true;
   }
}
