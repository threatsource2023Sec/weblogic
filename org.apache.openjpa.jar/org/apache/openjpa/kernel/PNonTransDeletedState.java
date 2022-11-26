package org.apache.openjpa.kernel;

public class PNonTransDeletedState extends PCState {
   PCState persist(StateManagerImpl context) {
      return PNONTRANS;
   }

   PCState transactional(StateManagerImpl context) {
      return PDELETED;
   }

   PCState beforeRead(StateManagerImpl context, int field) {
      return this.error("deleted", context);
   }

   PCState beforeNontransactionalRead(StateManagerImpl context, int field) {
      return this.error("deleted", context);
   }

   PCState beforeOptimisticRead(StateManagerImpl context, int field) {
      return this.error("deleted", context);
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return this.error("deleted", context);
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return this.error("deleted", context);
   }

   PCState beforeNontransactionalWrite(StateManagerImpl context, int field, boolean mutate) {
      return this.error("deleted", context);
   }

   boolean isPendingTransactional() {
      return true;
   }

   boolean isPersistent() {
      return true;
   }

   boolean isDeleted() {
      return true;
   }

   boolean isDirty() {
      return true;
   }
}
