package com.sun.faces.component.search;

import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.context.FacesContext;

public class SearchExpressionContextImpl extends SearchExpressionContext {
   private final FacesContext facesContext;
   private UIComponent source;
   private Set visitHints;
   private Set expressionHints;

   public SearchExpressionContextImpl(FacesContext facesContext) {
      this.facesContext = facesContext;
   }

   public UIComponent getSource() {
      return this.source;
   }

   public void setSource(UIComponent source) {
      this.source = source;
   }

   public Set getVisitHints() {
      return this.visitHints;
   }

   public void setVisitHints(Set visitHints) {
      this.visitHints = visitHints;
   }

   public Set getExpressionHints() {
      return this.expressionHints;
   }

   public void setExpressionHints(Set expressionHints) {
      this.expressionHints = expressionHints;
   }

   public FacesContext getFacesContext() {
      return this.facesContext;
   }
}
