package com.sun.faces.component.search;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;
import javax.faces.component.search.UntargetableComponent;

public class SearchKeywordResolverImplPrevious extends SearchKeywordResolver {
   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      UIComponent parent = current.getParent();
      if (parent.getChildCount() > 1) {
         List children = parent.getChildren();
         int index = children.indexOf(current);
         if (index > 0) {
            int nextIndex = -1;

            do {
               --index;
               if (!(children.get(index) instanceof UntargetableComponent)) {
                  nextIndex = index;
               }
            } while(nextIndex == -1 && index > 0);

            if (nextIndex != -1) {
               searchKeywordContext.invokeContextCallback((UIComponent)children.get(nextIndex));
            }
         }
      }

      searchKeywordContext.setKeywordResolved(true);
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      return "previous".equals(keyword);
   }
}
