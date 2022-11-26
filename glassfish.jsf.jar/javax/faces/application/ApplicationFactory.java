package javax.faces.application;

import javax.faces.FacesWrapper;

public abstract class ApplicationFactory implements FacesWrapper {
   private ApplicationFactory wrapped;

   /** @deprecated */
   @Deprecated
   public ApplicationFactory() {
   }

   public ApplicationFactory(ApplicationFactory wrapped) {
      this.wrapped = wrapped;
   }

   public ApplicationFactory getWrapped() {
      return this.wrapped;
   }

   public abstract Application getApplication();

   public abstract void setApplication(Application var1);
}
