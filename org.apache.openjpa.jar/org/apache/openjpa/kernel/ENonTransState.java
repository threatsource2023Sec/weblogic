package org.apache.openjpa.kernel;

class ENonTransState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setDirty(false);
      context.clearSavedFields();
      context.proxyFields(true, true);
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return EDELETED;
   }

   PCState transactional(StateManagerImpl context) {
      if (!context.getBroker().getOptimistic()) {
         context.clearFields();
      }

      return ECLEAN;
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState evict(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState beforeRead(StateManagerImpl context, int field) {
      return this.error("embed-ref", context);
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return this.error("embed-ref", context);
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return EDIRTY;
   }

   boolean isPersistent() {
      return true;
   }
}
