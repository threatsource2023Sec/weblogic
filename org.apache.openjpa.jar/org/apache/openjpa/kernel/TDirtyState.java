package org.apache.openjpa.kernel;

class TDirtyState extends PCState {
   void initialize(StateManagerImpl context) {
      context.saveFields(false);
      context.setLoaded(true);
      context.setDirty(true);
   }

   PCState commit(StateManagerImpl context) {
      return TCLEAN;
   }

   PCState commitRetain(StateManagerImpl context) {
      return TCLEAN;
   }

   PCState rollback(StateManagerImpl context) {
      return TCLEAN;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      context.restoreFields();
      return TCLEAN;
   }

   PCState persist(StateManagerImpl context) {
      return context.getBroker().isActive() ? PNEW : PNONTRANSNEW;
   }

   PCState delete(StateManagerImpl context) {
      return this.error("transient", context);
   }

   PCState nontransactional(StateManagerImpl context) {
      return this.error("dirty", context);
   }

   boolean isTransactional() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
