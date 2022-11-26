package org.apache.openjpa.kernel;

class HollowState extends PCState {
   void initialize(StateManagerImpl context) {
      context.clearFields();
      context.clearSavedFields();
      context.setDirty(false);
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return PDELETED;
   }

   PCState transactional(StateManagerImpl context) {
      return PCLEAN;
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState beforeRead(StateManagerImpl context, int field) {
      return PCLEAN;
   }

   PCState beforeOptimisticRead(StateManagerImpl context, int field) {
      return PNONTRANS;
   }

   PCState beforeNontransactionalRead(StateManagerImpl context, int field) {
      return PNONTRANS;
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return PDIRTY;
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return PDIRTY;
   }

   PCState beforeNontransactionalWrite(StateManagerImpl context, int field, boolean mutate) {
      return PNONTRANS;
   }

   boolean isPersistent() {
      return true;
   }
}
