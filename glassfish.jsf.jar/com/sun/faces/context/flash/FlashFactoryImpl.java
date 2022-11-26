package com.sun.faces.context.flash;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.context.FlashFactory;

public class FlashFactoryImpl extends FlashFactory {
   public FlashFactoryImpl() {
      super((FlashFactory)null);
   }

   public Flash getFlash(boolean create) {
      ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
      Flash result = ELFlash.getFlash(context, create);
      return result;
   }
}
