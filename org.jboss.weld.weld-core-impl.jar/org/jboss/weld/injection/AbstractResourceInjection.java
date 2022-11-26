package org.jboss.weld.injection;

import java.lang.reflect.Member;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

abstract class AbstractResourceInjection implements ResourceInjection {
   private final ResourceReferenceFactory factory;

   AbstractResourceInjection(ResourceReferenceFactory factory) {
      this.factory = factory;
   }

   public Object getResourceReference(CreationalContext ctx) {
      ResourceReference reference = null;
      if (this.factory != null) {
         reference = this.factory.createResource();
      }

      if (reference != null) {
         if (ctx instanceof WeldCreationalContext) {
            ((WeldCreationalContext)Reflections.cast(ctx)).addDependentResourceReference(reference);
         }

         return reference.getInstance();
      } else {
         UtilLogger.LOG.unableToInjectResource(this.getMember(), Formats.formatAsStackTraceElement(this.getMember()));
         return null;
      }
   }

   public void injectResourceReference(Object declaringInstance, CreationalContext ctx) {
      this.injectMember(declaringInstance, this.getResourceReference(ctx));
   }

   protected abstract void injectMember(Object var1, Object var2);

   abstract Member getMember();
}
