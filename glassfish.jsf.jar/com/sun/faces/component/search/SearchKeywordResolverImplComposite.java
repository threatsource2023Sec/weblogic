package com.sun.faces.component.search;

import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;

public class SearchKeywordResolverImplComposite extends SearchKeywordResolver {
   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      searchKeywordContext.invokeContextCallback(UIComponent.getCompositeComponentParent(current));
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      return "composite".equals(keyword);
   }
}
