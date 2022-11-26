package org.apache.openjpa.kernel;

class TLoadedState extends PCState {
   void initialize(StateManagerImpl context) {
      context.setLoaded(true);
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
   }
}
