package com.sun.faces.component.search;

import java.util.HashSet;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionContextFactory;
import javax.faces.context.FacesContext;

public class SearchExpressionContextFactoryImpl extends SearchExpressionContextFactory {
   public SearchExpressionContextFactoryImpl() {
      super((SearchExpressionContextFactory)null);
   }

   public SearchExpressionContextFactoryImpl(SearchExpressionContextFactory wrapped) {
      super(wrapped);
   }

   public SearchExpressionContext getSearchExpressionContext(FacesContext context, UIComponent source, Set expressionHints, Set visitHints) {
      SearchExpressionContextImpl searchExpressionContext = new SearchExpressionContextImpl(context);
      searchExpressionContext.setSource(source);
      searchExpressionContext.setExpressionHints((Set)(expressionHints == null ? new HashSet(2) : expressionHints));
      searchExpressionContext.setVisitHints(visitHints);
      return searchExpressionContext;
   }
}
