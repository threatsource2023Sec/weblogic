package org.apache.openjpa.kernel;

class TCleanState extends PCState {
   void initialize(StateManagerImpl context) {
      context.clearSavedFields();
      context.setLoaded(true);
      context.setDirty(false);
      context.proxyFields(true, false);
   }

   PCState persist(StateManagerImpl context) {
      return context.getBroker().isActive() ? PNEW : PNONTRANSNEW;
   }

   PCState delete(StateManagerImpl context) {
      return this.error("transient", context);
   }

   PCState nontransactional(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return TDIRTY;
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return TDIRTY;
   }
}
