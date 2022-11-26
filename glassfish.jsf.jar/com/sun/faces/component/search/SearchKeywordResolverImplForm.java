package com.sun.faces.component.search;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionHint;
import javax.faces.component.search.SearchKeywordContext;

public class SearchKeywordResolverImplForm extends AbstractSearchKeywordResolverImpl {
   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      searchKeywordContext.invokeContextCallback((UIComponent)this.closest(UIForm.class, current));
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      return "form".equals(keyword);
   }

   public boolean isPassthrough(SearchExpressionContext searchExpressionContext, String keyword) {
      return this.isHintSet(searchExpressionContext, SearchExpressionHint.RESOLVE_CLIENT_SIDE);
   }
}
