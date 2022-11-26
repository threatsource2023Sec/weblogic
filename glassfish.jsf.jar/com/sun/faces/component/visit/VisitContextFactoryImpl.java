package com.sun.faces.component.visit;

import java.util.Collection;
import java.util.Set;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitContextFactory;
import javax.faces.context.FacesContext;

public class VisitContextFactoryImpl extends VisitContextFactory {
   public VisitContextFactoryImpl() {
      super((VisitContextFactory)null);
   }

   public VisitContext getVisitContext(FacesContext context, Collection ids, Set hints) {
      VisitContext result = null;
      if (null == ids) {
         result = new FullVisitContext(context, hints);
      } else {
         result = new PartialVisitContext(context, ids, hints);
      }

      return (VisitContext)result;
   }
}
