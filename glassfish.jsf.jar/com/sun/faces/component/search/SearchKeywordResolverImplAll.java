package com.sun.faces.component.search;

import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionHint;
import javax.faces.component.search.SearchKeywordContext;

public class SearchKeywordResolverImplAll extends AbstractSearchKeywordResolverImpl {
   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      if (current.getParent() != null) {
         UIComponent parent;
         for(parent = current.getParent(); parent.getParent() != null; parent = parent.getParent()) {
         }

         searchKeywordContext.invokeContextCallback(parent);
      } else {
         searchKeywordContext.setKeywordResolved(true);
      }

   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      return "all".equals(keyword);
   }

   public boolean isPassthrough(SearchExpressionContext searchExpressionContext, String keyword) {
      return this.isHintSet(searchExpressionContext, SearchExpressionHint.RESOLVE_CLIENT_SIDE);
   }

   public boolean isLeaf(SearchExpressionContext searchExpressionContext, String keyword) {
      return true;
   }
}
