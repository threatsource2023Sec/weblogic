package org.jboss.weld.bootstrap;

import org.jboss.weld.event.ContainerLifecycleEventObserverMethod;

class UnsupportedObserverMethodException extends Exception {
   private static final long serialVersionUID = -2164722035016351775L;
   private final ContainerLifecycleEventObserverMethod observer;

   public UnsupportedObserverMethodException(ContainerLifecycleEventObserverMethod observer) {
      this.observer = observer;
   }

   public ContainerLifecycleEventObserverMethod getObserver() {
      return this.observer;
   }
}
