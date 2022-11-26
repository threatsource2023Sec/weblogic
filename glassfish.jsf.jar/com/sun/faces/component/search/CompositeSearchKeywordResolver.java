package com.sun.faces.component.search;

import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;

public class CompositeSearchKeywordResolver extends SearchKeywordResolver {
   private static final int NUMBER_OF_DEFAULT_IMPLEMENTATIONS = 12;
   private final List resolvers = new ArrayList(12);

   public void add(SearchKeywordResolver searchKeywordResolver) {
      if (searchKeywordResolver == null) {
         throw new NullPointerException();
      } else {
         this.resolvers.add(0, searchKeywordResolver);
      }
   }

   public void resolve(SearchKeywordContext context, UIComponent current, String keyword) {
      context.setKeywordResolved(false);

      for(int i = 0; i < this.resolvers.size(); ++i) {
         SearchKeywordResolver resolver = (SearchKeywordResolver)this.resolvers.get(i);
         if (resolver.isResolverForKeyword(context.getSearchExpressionContext(), keyword)) {
            resolver.resolve(context, current, keyword);
            if (context.isKeywordResolved()) {
               return;
            }
         }
      }

   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      for(int i = 0; i < this.resolvers.size(); ++i) {
         SearchKeywordResolver resolver = (SearchKeywordResolver)this.resolvers.get(i);
         if (resolver.isResolverForKeyword(searchExpressionContext, keyword)) {
            return true;
         }
      }

      return false;
   }

   public boolean isPassthrough(SearchExpressionContext searchExpressionContext, String keyword) {
      for(int i = 0; i < this.resolvers.size(); ++i) {
         SearchKeywordResolver resolver = (SearchKeywordResolver)this.resolvers.get(i);
         if (resolver.isResolverForKeyword(searchExpressionContext, keyword)) {
            return resolver.isPassthrough(searchExpressionContext, keyword);
         }
      }

      return false;
   }

   public boolean isLeaf(SearchExpressionContext searchExpressionContext, String keyword) {
      for(int i = 0; i < this.resolvers.size(); ++i) {
         SearchKeywordResolver resolver = (SearchKeywordResolver)this.resolvers.get(i);
         if (resolver.isResolverForKeyword(searchExpressionContext, keyword)) {
            return resolver.isLeaf(searchExpressionContext, keyword);
         }
      }

      return false;
   }
}
