package javax.faces.component.visit;

import java.util.Collection;
import java.util.Set;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class VisitContextFactory implements FacesWrapper {
   private VisitContextFactory wrapped;

   /** @deprecated */
   @Deprecated
   public VisitContextFactory() {
   }

   public VisitContextFactory(VisitContextFactory wrapped) {
      this.wrapped = wrapped;
   }

   public VisitContextFactory getWrapped() {
      return this.wrapped;
   }

   public abstract VisitContext getVisitContext(FacesContext var1, Collection var2, Set var3);
}
