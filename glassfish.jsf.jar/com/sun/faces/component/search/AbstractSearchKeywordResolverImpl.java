package com.sun.faces.component.search;

import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionHint;
import javax.faces.component.search.SearchKeywordResolver;

public abstract class AbstractSearchKeywordResolverImpl extends SearchKeywordResolver {
   protected Object closest(Class type, UIComponent base) {
      for(UIComponent parent = base.getParent(); parent != null; parent = parent.getParent()) {
         if (type.isAssignableFrom(parent.getClass())) {
            return parent;
         }
      }

      return null;
   }

   protected boolean isHintSet(SearchExpressionContext searchExpressionContext, SearchExpressionHint hint) {
      return searchExpressionContext.getExpressionHints().contains(hint);
   }
}
