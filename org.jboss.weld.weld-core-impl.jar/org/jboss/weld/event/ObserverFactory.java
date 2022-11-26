package org.jboss.weld.event;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.builtin.ExtensionBean;
import org.jboss.weld.manager.BeanManagerImpl;

public class ObserverFactory {
   private ObserverFactory() {
   }

   public static ObserverMethodImpl create(EnhancedAnnotatedMethod method, RIBean declaringBean, BeanManagerImpl manager, boolean isAsync) {
      return (ObserverMethodImpl)(declaringBean instanceof ExtensionBean ? new ExtensionObserverMethodImpl(method, declaringBean, manager, isAsync) : new ObserverMethodImpl(method, declaringBean, manager, isAsync));
   }

   public static TransactionPhase getTransactionalPhase(EnhancedAnnotatedMethod observer) {
      EnhancedAnnotatedParameter parameter = (EnhancedAnnotatedParameter)observer.getEnhancedParameters(Observes.class).iterator().next();
      return ((Observes)parameter.getAnnotation(Observes.class)).during();
   }
}
