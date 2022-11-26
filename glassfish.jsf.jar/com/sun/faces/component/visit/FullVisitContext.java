package com.sun.faces.component.visit;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

public class FullVisitContext extends VisitContext {
   private FacesContext facesContext;
   private Set hints;

   public FullVisitContext(FacesContext facesContext) {
      this(facesContext, (Set)null);
   }

   public FullVisitContext(FacesContext facesContext, Set hints) {
      if (facesContext == null) {
         throw new NullPointerException();
      } else {
         this.facesContext = facesContext;
         EnumSet hintsEnumSet = hints != null && !hints.isEmpty() ? EnumSet.copyOf(hints) : EnumSet.noneOf(VisitHint.class);
         this.hints = Collections.unmodifiableSet(hintsEnumSet);
      }
   }

   public FacesContext getFacesContext() {
      return this.facesContext;
   }

   public Collection getIdsToVisit() {
      return ALL_IDS;
   }

   public Collection getSubtreeIdsToVisit(UIComponent component) {
      if (!(component instanceof NamingContainer)) {
         throw new IllegalArgumentException("Component is not a NamingContainer: " + component);
      } else {
         return ALL_IDS;
      }
   }

   public Set getHints() {
      return this.hints;
   }

   public VisitResult invokeVisitCallback(UIComponent component, VisitCallback callback) {
      return callback.visit(this, component);
   }
}
