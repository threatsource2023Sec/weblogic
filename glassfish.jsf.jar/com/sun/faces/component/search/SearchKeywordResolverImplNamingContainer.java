package com.sun.faces.component.search;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;

public class SearchKeywordResolverImplNamingContainer extends AbstractSearchKeywordResolverImpl {
   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      searchKeywordContext.invokeContextCallback((UIComponent)this.closest(NamingContainer.class, current));
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      return "namingcontainer".equals(keyword);
   }
}
