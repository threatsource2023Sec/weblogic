package com.sun.faces.component.search;

import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;

public class SearchKeywordResolverImplParent extends SearchKeywordResolver {
   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      searchKeywordContext.invokeContextCallback(current.getParent());
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      return "parent".equals(keyword);
   }
}
