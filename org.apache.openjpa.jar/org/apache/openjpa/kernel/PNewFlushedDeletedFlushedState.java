package org.apache.openjpa.kernel;

class PNewFlushedDeletedFlushedState extends PNewFlushedDeletedState {
   void initialize(StateManagerImpl context) {
   }

   PCState persist(StateManagerImpl context) {
      context.eraseFlush();
      return PNEW;
   }
}
