package javax.faces.context;

import javax.faces.FacesWrapper;

public abstract class PartialViewContextFactory implements FacesWrapper {
   private PartialViewContextFactory wrapped;

   /** @deprecated */
   @Deprecated
   public PartialViewContextFactory() {
   }

   public PartialViewContextFactory(PartialViewContextFactory wrapped) {
      this.wrapped = wrapped;
   }

   public PartialViewContextFactory getWrapped() {
      return this.wrapped;
   }

   public abstract PartialViewContext getPartialViewContext(FacesContext var1);
}
