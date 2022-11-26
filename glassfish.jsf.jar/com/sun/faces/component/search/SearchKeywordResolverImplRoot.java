package com.sun.faces.component.search;

import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;

public class SearchKeywordResolverImplRoot extends SearchKeywordResolver {
   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      searchKeywordContext.invokeContextCallback(searchKeywordContext.getSearchExpressionContext().getFacesContext().getViewRoot());
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      return "root".equals(keyword);
   }
}
