package org.apache.openjpa.kernel;

import org.apache.openjpa.enhance.StateManager;

class TransientState extends PCState {
   void initialize(StateManagerImpl context) {
      context.unproxyFields();
      context.getPersistenceCapable().pcReplaceStateManager((StateManager)null);
      context.getBroker().setStateManager(context.getId(), context, 1);
   }
}
