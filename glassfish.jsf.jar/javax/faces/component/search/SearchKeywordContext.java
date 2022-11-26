package javax.faces.component.search;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;

public class SearchKeywordContext {
   private final SearchExpressionContext searchExpressionContext;
   private final ContextCallback callback;
   private final String remainingExpression;
   private boolean keywordResolved;

   public SearchKeywordContext(SearchExpressionContext searchExpressionContext, ContextCallback callback, String remainingExpression) {
      this.searchExpressionContext = searchExpressionContext;
      this.callback = callback;
      this.remainingExpression = remainingExpression;
   }

   public void invokeContextCallback(UIComponent target) {
      this.keywordResolved = true;
      this.callback.invokeContextCallback(this.searchExpressionContext.getFacesContext(), target);
   }

   public SearchExpressionContext getSearchExpressionContext() {
      return this.searchExpressionContext;
   }

   public ContextCallback getCallback() {
      return this.callback;
   }

   public String getRemainingExpression() {
      return this.remainingExpression;
   }

   public boolean isKeywordResolved() {
      return this.keywordResolved;
   }

   public void setKeywordResolved(boolean keywordResolved) {
      this.keywordResolved = keywordResolved;
   }
}
