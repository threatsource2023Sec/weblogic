package org.jboss.weld.bootstrap;

import java.util.regex.Pattern;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import org.jboss.weld.util.AnnotatedTypes;

class WeldVetoExtension implements Extension {
   private Pattern vetoAnnotatedTypePattern;

   WeldVetoExtension(String regex) {
      this.vetoAnnotatedTypePattern = Pattern.compile(regex);
   }

   void processAnnotatedType(@Observes ProcessAnnotatedType event) {
      if (this.vetoAnnotatedTypePattern.matcher(event.getAnnotatedType().getJavaClass().getName()).matches() && !AnnotatedTypes.hasBeanDefiningAnnotation(event.getAnnotatedType())) {
         event.veto();
      }

   }

   void cleanupAfterBoot(@Observes AfterDeploymentValidation event) {
      this.vetoAnnotatedTypePattern = null;
   }
}
