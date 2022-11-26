package javax.faces.context;

import javax.faces.FacesException;
import javax.faces.FacesWrapper;

public abstract class ExternalContextFactory implements FacesWrapper {
   private ExternalContextFactory wrapped;

   /** @deprecated */
   @Deprecated
   public ExternalContextFactory() {
   }

   public ExternalContextFactory(ExternalContextFactory wrapped) {
      this.wrapped = wrapped;
   }

   public ExternalContextFactory getWrapped() {
      return this.wrapped;
   }

   public abstract ExternalContext getExternalContext(Object var1, Object var2, Object var3) throws FacesException;
}
