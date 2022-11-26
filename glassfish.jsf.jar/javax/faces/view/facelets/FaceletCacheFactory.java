package javax.faces.view.facelets;

import javax.faces.FacesWrapper;

public abstract class FaceletCacheFactory implements FacesWrapper {
   private FaceletCacheFactory wrapped;

   /** @deprecated */
   @Deprecated
   public FaceletCacheFactory() {
   }

   public FaceletCacheFactory(FaceletCacheFactory wrapped) {
      this.wrapped = wrapped;
   }

   public FaceletCacheFactory getWrapped() {
      return this.wrapped;
   }

   public abstract FaceletCache getFaceletCache();
}
