package org.apache.openjpa.kernel;

class PNonTransDirtyState extends PCState {
   void initialize(StateManagerImpl context) {
      context.saveFields(false);
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return PNONTRANSDELETED;
   }

   PCState transactional(StateManagerImpl context) {
      return PDIRTY;
   }

   PCState release(StateManagerImpl context) {
      return this.error("dirty", context);
   }

   PCState evict(StateManagerImpl context) {
      return HOLLOW;
   }

   PCState afterNontransactionalRefresh() {
      return PNONTRANS;
   }

   boolean isPendingTransactional() {
      return true;
   }

   boolean isPersistent() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
