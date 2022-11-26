package javax.faces.component.search;

import javax.faces.component.UIComponent;

public abstract class SearchKeywordResolver {
   public abstract void resolve(SearchKeywordContext var1, UIComponent var2, String var3);

   public abstract boolean isResolverForKeyword(SearchExpressionContext var1, String var2);

   public boolean isPassthrough(SearchExpressionContext searchExpressionContext, String keyword) {
      return false;
   }

   public boolean isLeaf(SearchExpressionContext searchExpressionContext, String keyword) {
      return false;
   }
}
