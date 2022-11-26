package org.apache.openjpa.kernel;

class PDeletedFlushedState extends PDeletedState {
   PCState flush(StateManagerImpl context) {
      return this;
   }

   PCState persist(StateManagerImpl context) {
      context.eraseFlush();
      return PNEW;
   }
}
