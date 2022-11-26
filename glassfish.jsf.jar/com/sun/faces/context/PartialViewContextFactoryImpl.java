package com.sun.faces.context;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextFactory;

public class PartialViewContextFactoryImpl extends PartialViewContextFactory {
   public PartialViewContextFactoryImpl() {
      super((PartialViewContextFactory)null);
   }

   public PartialViewContext getPartialViewContext(FacesContext context) {
      return new PartialViewContextImpl(context);
   }
}
