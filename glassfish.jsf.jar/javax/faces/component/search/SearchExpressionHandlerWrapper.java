package javax.faces.component.search;

import java.util.List;
import javax.faces.FacesWrapper;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class SearchExpressionHandlerWrapper extends SearchExpressionHandler implements FacesWrapper {
   private final SearchExpressionHandler wrapped;

   public SearchExpressionHandlerWrapper(SearchExpressionHandler wrapped) {
      this.wrapped = wrapped;
   }

   public SearchExpressionHandler getWrapped() {
      return this.wrapped;
   }

   public String resolveClientId(SearchExpressionContext searchExpressionContext, String expression) {
      return this.getWrapped().resolveClientId(searchExpressionContext, expression);
   }

   public List resolveClientIds(SearchExpressionContext searchExpressionContext, String expressions) {
      return this.getWrapped().resolveClientIds(searchExpressionContext, expressions);
   }

   public void resolveComponent(SearchExpressionContext searchExpressionContext, String expression, ContextCallback callback) {
      this.getWrapped().resolveComponent(searchExpressionContext, expression, callback);
   }

   public void resolveComponents(SearchExpressionContext searchExpressionContext, String expressions, ContextCallback callback) {
      this.getWrapped().resolveComponents(searchExpressionContext, expressions, callback);
   }

   public void invokeOnComponent(SearchExpressionContext searchExpressionContext, String expression, ContextCallback callback) {
      this.getWrapped().invokeOnComponent(searchExpressionContext, expression, callback);
   }

   public void invokeOnComponent(SearchExpressionContext searchExpressionContext, UIComponent previous, String expression, ContextCallback callback) {
      this.getWrapped().invokeOnComponent(searchExpressionContext, previous, expression, callback);
   }

   public boolean isValidExpression(SearchExpressionContext searchExpressionContext, String expression) {
      return this.getWrapped().isValidExpression(searchExpressionContext, expression);
   }

   public boolean isPassthroughExpression(SearchExpressionContext searchExpressionContext, String expression) {
      return this.getWrapped().isPassthroughExpression(searchExpressionContext, expression);
   }

   public String[] splitExpressions(FacesContext context, String expressions) {
      return this.getWrapped().splitExpressions(context, expressions);
   }

   public char[] getExpressionSeperatorChars(FacesContext context) {
      return this.getWrapped().getExpressionSeperatorChars(context);
   }
}
