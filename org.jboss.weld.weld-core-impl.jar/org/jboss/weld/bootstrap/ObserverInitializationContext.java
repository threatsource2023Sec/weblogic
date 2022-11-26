package org.jboss.weld.bootstrap;

import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.event.ObserverMethodImpl;

public class ObserverInitializationContext {
   private final ObserverMethodImpl observer;
   private final EnhancedAnnotatedMethod annotated;

   public static ObserverInitializationContext of(ObserverMethodImpl observer, EnhancedAnnotatedMethod annotated) {
      return new ObserverInitializationContext(observer, annotated);
   }

   public ObserverInitializationContext(ObserverMethodImpl observer, EnhancedAnnotatedMethod annotated) {
      this.observer = observer;
      this.annotated = annotated;
   }

   public void initialize() {
      this.observer.initialize(this.annotated);
   }

   public ObserverMethodImpl getObserver() {
      return this.observer;
   }
}
