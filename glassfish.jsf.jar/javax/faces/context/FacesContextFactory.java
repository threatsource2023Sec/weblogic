package javax.faces.context;

import javax.faces.FacesException;
import javax.faces.FacesWrapper;
import javax.faces.lifecycle.Lifecycle;

public abstract class FacesContextFactory implements FacesWrapper {
   private FacesContextFactory wrapped;

   /** @deprecated */
   @Deprecated
   public FacesContextFactory() {
   }

   public FacesContextFactory(FacesContextFactory wrapped) {
      this.wrapped = wrapped;
   }

   public FacesContextFactory getWrapped() {
      return this.wrapped;
   }

   public abstract FacesContext getFacesContext(Object var1, Object var2, Object var3, Lifecycle var4) throws FacesException;
}
