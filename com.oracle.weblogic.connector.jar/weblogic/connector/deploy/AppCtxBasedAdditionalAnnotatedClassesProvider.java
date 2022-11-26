package weblogic.connector.deploy;

import java.util.Set;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.connector.configuration.AdditionalAnnotatedClassesProvider;

public class AppCtxBasedAdditionalAnnotatedClassesProvider implements AdditionalAnnotatedClassesProvider {
   private ApplicationContextInternal ctx;

   public AppCtxBasedAdditionalAnnotatedClassesProvider(ApplicationContextInternal ctx) {
      this.ctx = ctx;
   }

   public Set getAnnotatedClasses() throws AnnotationProcessingException {
      return this.ctx.getAnnotatedClasses((Class[])AdditionalAnnotatedClassesProvider.ANNOTATIONS.toArray(new Class[AdditionalAnnotatedClassesProvider.ANNOTATIONS.size()]));
   }
}
