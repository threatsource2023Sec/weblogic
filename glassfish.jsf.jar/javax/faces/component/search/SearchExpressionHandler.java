package javax.faces.component.search;

import java.util.List;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class SearchExpressionHandler {
   public static final String KEYWORD_PREFIX = "@";
   protected static final char[] EXPRESSION_SEPARATOR_CHARS = new char[]{',', ' '};

   public abstract String resolveClientId(SearchExpressionContext var1, String var2);

   public abstract List resolveClientIds(SearchExpressionContext var1, String var2);

   public abstract void resolveComponent(SearchExpressionContext var1, String var2, ContextCallback var3);

   public abstract void resolveComponents(SearchExpressionContext var1, String var2, ContextCallback var3);

   public void invokeOnComponent(SearchExpressionContext searchExpressionContext, String expression, ContextCallback callback) {
      this.invokeOnComponent(searchExpressionContext, searchExpressionContext.getSource(), expression, callback);
   }

   public abstract void invokeOnComponent(SearchExpressionContext var1, UIComponent var2, String var3, ContextCallback var4);

   public abstract String[] splitExpressions(FacesContext var1, String var2);

   public abstract boolean isPassthroughExpression(SearchExpressionContext var1, String var2);

   public abstract boolean isValidExpression(SearchExpressionContext var1, String var2);

   public char[] getExpressionSeperatorChars(FacesContext context) {
      return EXPRESSION_SEPARATOR_CHARS;
   }
}
