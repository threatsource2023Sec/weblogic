package org.apache.openjpa.kernel;

class PNonTransNewState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setLoaded(true);
      context.setDirty(true);
   }

   PCState delete(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState transactional(StateManagerImpl context) {
      return PNEW;
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
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

   boolean isPendingTransactional() {
      return true;
   }
}
