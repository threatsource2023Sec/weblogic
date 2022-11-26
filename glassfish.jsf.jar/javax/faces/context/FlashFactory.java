package javax.faces.context;

import javax.faces.FacesWrapper;

public abstract class FlashFactory implements FacesWrapper {
   private FlashFactory wrapped;

   /** @deprecated */
   @Deprecated
   public FlashFactory() {
   }

   public FlashFactory(FlashFactory wrapped) {
      this.wrapped = wrapped;
   }

   public FlashFactory getWrapped() {
      return this.wrapped;
   }

   public abstract Flash getFlash(boolean var1);
}
