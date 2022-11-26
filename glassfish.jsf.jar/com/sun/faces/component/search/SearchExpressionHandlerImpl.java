package com.sun.faces.component.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.search.ComponentNotFoundException;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.component.search.SearchExpressionHint;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;
import javax.faces.context.FacesContext;

public class SearchExpressionHandlerImpl extends SearchExpressionHandler {
   protected void addHint(SearchExpressionContext searchExpressionContext, SearchExpressionHint hint) {
      if (!searchExpressionContext.getExpressionHints().contains(hint)) {
         searchExpressionContext.getExpressionHints().add(hint);
      }

   }

   public String resolveClientId(SearchExpressionContext searchExpressionContext, String expression) {
      if (expression == null) {
         expression = "";
      } else {
         expression = expression.trim();
      }

      this.addHint(searchExpressionContext, SearchExpressionHint.RESOLVE_SINGLE_COMPONENT);
      FacesContext facesContext = searchExpressionContext.getFacesContext();
      SearchExpressionHandler handler = facesContext.getApplication().getSearchExpressionHandler();
      if (!expression.isEmpty() && handler.isPassthroughExpression(searchExpressionContext, expression)) {
         return expression;
      } else {
         ResolveClientIdCallback internalCallback = new ResolveClientIdCallback();
         if (!expression.isEmpty()) {
            handler.invokeOnComponent(searchExpressionContext, expression, internalCallback);
         }

         String clientId = internalCallback.getClientId();
         if (clientId == null && !this.isHintSet(searchExpressionContext, SearchExpressionHint.IGNORE_NO_RESULT)) {
            throw new ComponentNotFoundException("Cannot find component for expression \"" + expression + "\" referenced from \"" + searchExpressionContext.getSource().getClientId(facesContext) + "\".");
         } else {
            return clientId;
         }
      }
   }

   public List resolveClientIds(SearchExpressionContext searchExpressionContext, String expressions) {
      if (expressions == null) {
         expressions = "";
      } else {
         expressions = expressions.trim();
      }

      FacesContext facesContext = searchExpressionContext.getFacesContext();
      SearchExpressionHandler handler = facesContext.getApplication().getSearchExpressionHandler();
      ResolveClientIdsCallback internalCallback = new ResolveClientIdsCallback();
      if (!expressions.isEmpty()) {
         String[] var6 = handler.splitExpressions(facesContext, expressions);
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String expression = var6[var8];
            if (handler.isPassthroughExpression(searchExpressionContext, expression)) {
               internalCallback.addClientId(expression);
            } else {
               handler.invokeOnComponent(searchExpressionContext, expression, internalCallback);
            }
         }
      }

      if (internalCallback.getClientIds() == null && !this.isHintSet(searchExpressionContext, SearchExpressionHint.IGNORE_NO_RESULT)) {
         throw new ComponentNotFoundException("Cannot find component for expressions \"" + expressions + "\" referenced from \"" + searchExpressionContext.getSource().getClientId(facesContext) + "\".");
      } else {
         List clientIds = internalCallback.getClientIds();
         if (clientIds == null) {
            clientIds = Collections.emptyList();
         }

         return clientIds;
      }
   }

   public void resolveComponent(SearchExpressionContext searchExpressionContext, String expression, ContextCallback callback) {
      if (expression != null) {
         expression = expression.trim();
      }

      this.addHint(searchExpressionContext, SearchExpressionHint.RESOLVE_SINGLE_COMPONENT);
      FacesContext facesContext = searchExpressionContext.getFacesContext();
      SearchExpressionHandler handler = facesContext.getApplication().getSearchExpressionHandler();
      ResolveComponentCallback internalCallback = new ResolveComponentCallback(callback);
      handler.invokeOnComponent(searchExpressionContext, expression, internalCallback);
      if (!internalCallback.isInvoked() && !this.isHintSet(searchExpressionContext, SearchExpressionHint.IGNORE_NO_RESULT)) {
         throw new ComponentNotFoundException("Cannot find component for expression \"" + expression + "\" referenced from \"" + searchExpressionContext.getSource().getClientId(facesContext) + "\".");
      }
   }

   public void resolveComponents(SearchExpressionContext searchExpressionContext, String expressions, ContextCallback callback) {
      if (expressions != null) {
         expressions = expressions.trim();
      }

      FacesContext facesContext = searchExpressionContext.getFacesContext();
      SearchExpressionHandler handler = facesContext.getApplication().getSearchExpressionHandler();
      ResolveComponentsCallback internalCallback = new ResolveComponentsCallback(callback);
      if (expressions != null) {
         String[] var7 = handler.splitExpressions(facesContext, expressions);
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String expression = var7[var9];
            handler.invokeOnComponent(searchExpressionContext, expression, internalCallback);
         }
      }

      if (!internalCallback.isInvoked() && !this.isHintSet(searchExpressionContext, SearchExpressionHint.IGNORE_NO_RESULT)) {
         throw new ComponentNotFoundException("Cannot find component for expressions \"" + expressions + "\" referenced from \"" + searchExpressionContext.getSource().getClientId(facesContext) + "\".");
      }
   }

   public void invokeOnComponent(final SearchExpressionContext searchExpressionContext, UIComponent previous, String expression, final ContextCallback callback) {
      if (expression != null && previous != null) {
         expression = expression.trim();
         FacesContext facesContext = searchExpressionContext.getFacesContext();
         final SearchExpressionHandler handler = facesContext.getApplication().getSearchExpressionHandler();
         if (expression.contains("@")) {
            char separatorChar = facesContext.getNamingContainerSeparatorChar();
            if (expression.charAt(0) == separatorChar && expression.charAt(1) == "@".charAt(0)) {
               handler.invokeOnComponent(searchExpressionContext, facesContext.getViewRoot(), expression.substring(1), callback);
               return;
            }

            String command = this.extractFirstCommand(facesContext, expression);
            final String remainingExpression = null;
            if (command.length() < expression.length()) {
               remainingExpression = expression.substring(command.length() + 1);
            }

            if (command.startsWith("@")) {
               String keyword = command.substring("@".length());
               if (remainingExpression == null) {
                  this.invokeKeywordResolvers(searchExpressionContext, previous, keyword, (String)null, callback);
               } else {
                  if (facesContext.getApplication().getSearchKeywordResolver().isLeaf(searchExpressionContext, keyword)) {
                     throw new FacesException("It's not valid to place a keyword or id after a leaf keyword: @" + keyword + ". Expression: " + expression);
                  }

                  this.invokeKeywordResolvers(searchExpressionContext, previous, keyword, remainingExpression, new ContextCallback() {
                     public void invokeContextCallback(FacesContext facesContext, UIComponent target) {
                        handler.invokeOnComponent(searchExpressionContext, target, remainingExpression, callback);
                     }
                  });
               }
            } else {
               UIComponent target = previous.findComponent(command);
               if (target != null) {
                  if (remainingExpression == null) {
                     callback.invokeContextCallback(facesContext, target);
                  } else {
                     handler.invokeOnComponent(searchExpressionContext, target, remainingExpression, callback);
                  }
               }
            }
         } else {
            UIComponent target = previous.findComponent(expression);
            if (target != null) {
               callback.invokeContextCallback(facesContext, target);
            } else if (!this.isHintSet(searchExpressionContext, SearchExpressionHint.SKIP_VIRTUAL_COMPONENTS)) {
               char separatorChar = facesContext.getNamingContainerSeparatorChar();
               if (expression.charAt(0) == separatorChar) {
                  expression = expression.substring(1);
               }

               facesContext.getViewRoot().invokeOnComponent(facesContext, expression, callback);
            }
         }

      }
   }

   protected void invokeKeywordResolvers(SearchExpressionContext searchExpressionContext, UIComponent previous, String keyword, String remainingExpression, ContextCallback callback) {
      SearchKeywordContext searchContext = new SearchKeywordContext(searchExpressionContext, callback, remainingExpression);
      searchExpressionContext.getFacesContext().getApplication().getSearchKeywordResolver().resolve(searchContext, previous, keyword);
   }

   public String[] splitExpressions(FacesContext context, String expressions) {
      List tokens = new ArrayList();
      StringBuilder buffer = new StringBuilder();
      char[] separators = this.getExpressionSeperatorChars(context);
      int parenthesesCounter = 0;
      char[] charArray = expressions.toCharArray();
      char[] var8 = charArray;
      int var9 = charArray.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         char c = var8[var10];
         if (c == '(') {
            ++parenthesesCounter;
         }

         if (c == ')') {
            --parenthesesCounter;
         }

         if (parenthesesCounter != 0) {
            buffer.append(c);
         } else {
            boolean isSeparator = false;
            char[] var13 = separators;
            int var14 = separators.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               char separator = var13[var15];
               if (c == separator) {
                  isSeparator = true;
               }
            }

            if (isSeparator) {
               String bufferAsString = buffer.toString().trim();
               if (bufferAsString.length() > 0) {
                  tokens.add(bufferAsString);
               }

               buffer.delete(0, buffer.length());
            } else {
               buffer.append(c);
            }
         }
      }

      tokens.add(buffer.toString());
      return (String[])tokens.toArray(new String[tokens.size()]);
   }

   public boolean isPassthroughExpression(SearchExpressionContext searchExpressionContext, String expression) {
      if (expression != null) {
         expression = expression.trim();
      }

      if (expression != null && expression.contains("@")) {
         FacesContext facesContext = searchExpressionContext.getFacesContext();
         String command = this.extractFirstCommand(facesContext, expression);
         String remainingExpression = null;
         if (command.length() < expression.length()) {
            remainingExpression = expression.substring(command.length() + 1);
         }

         if (command.startsWith("@") && remainingExpression == null) {
            String keyword = command.substring("@".length());
            SearchKeywordResolver keywordResolver = facesContext.getApplication().getSearchKeywordResolver();
            return keywordResolver.isPassthrough(searchExpressionContext, keyword);
         } else {
            SearchExpressionHandler handler = facesContext.getApplication().getSearchExpressionHandler();
            return handler.isPassthroughExpression(searchExpressionContext, remainingExpression);
         }
      } else {
         return false;
      }
   }

   public boolean isValidExpression(SearchExpressionContext searchExpressionContext, String expression) {
      if (expression != null) {
         expression = expression.trim();
      }

      if (expression != null && !expression.isEmpty()) {
         if (expression.contains("@")) {
            FacesContext facesContext = searchExpressionContext.getFacesContext();
            SearchExpressionHandler handler = facesContext.getApplication().getSearchExpressionHandler();
            char separatorChar = facesContext.getNamingContainerSeparatorChar();
            if (expression.charAt(0) == separatorChar) {
               expression = expression.substring(1);
            }

            String command = this.extractFirstCommand(facesContext, expression);
            String remainingExpression = null;
            if (command.length() < expression.length()) {
               remainingExpression = expression.substring(command.length() + 1);
            }

            if (command.startsWith("@")) {
               String keyword = command.substring("@".length());
               SearchKeywordResolver keywordResolver = facesContext.getApplication().getSearchKeywordResolver();
               if (!keywordResolver.isResolverForKeyword(searchExpressionContext, keyword)) {
                  return false;
               }

               if (remainingExpression != null && !remainingExpression.trim().isEmpty()) {
                  if (keywordResolver.isLeaf(searchExpressionContext, keyword)) {
                     return false;
                  }

                  return handler.isValidExpression(searchExpressionContext, remainingExpression);
               }
            } else if (remainingExpression != null) {
               return handler.isValidExpression(searchExpressionContext, remainingExpression);
            }
         }

         return true;
      } else {
         return true;
      }
   }

   protected boolean isHintSet(SearchExpressionContext searchExpressionContext, SearchExpressionHint hint) {
      return searchExpressionContext.getExpressionHints().contains(hint);
   }

   protected String extractFirstCommand(FacesContext facesContext, String expression) {
      int parenthesesCounter = -1;
      int count = -1;

      for(int i = 0; i < expression.length(); ++i) {
         char c = expression.charAt(i);
         if (c == '(') {
            if (parenthesesCounter == -1) {
               parenthesesCounter = 0;
            }

            ++parenthesesCounter;
         }

         if (c == ')') {
            --parenthesesCounter;
         }

         if (parenthesesCounter == 0) {
            count = i + 1;
            break;
         }

         if (parenthesesCounter == -1 && i > 0 && c == facesContext.getNamingContainerSeparatorChar()) {
            count = i;
            break;
         }
      }

      return count == -1 ? expression : expression.substring(0, count);
   }

   private static class ResolveComponentsCallback implements ContextCallback {
      private final ContextCallback callback;
      private boolean invoked;

      public ResolveComponentsCallback(ContextCallback callback) {
         this.callback = callback;
         this.invoked = false;
      }

      public void invokeContextCallback(FacesContext context, UIComponent target) {
         this.invoked = true;
         this.callback.invokeContextCallback(context, target);
      }

      public boolean isInvoked() {
         return this.invoked;
      }
   }

   private static class ResolveComponentCallback implements ContextCallback {
      private final ContextCallback callback;
      private boolean invoked;

      public ResolveComponentCallback(ContextCallback callback) {
         this.callback = callback;
         this.invoked = false;
      }

      public void invokeContextCallback(FacesContext context, UIComponent target) {
         if (!this.isInvoked()) {
            this.invoked = true;
            this.callback.invokeContextCallback(context, target);
         }

      }

      public boolean isInvoked() {
         return this.invoked;
      }
   }

   private static class ResolveClientIdsCallback implements ContextCallback {
      private List clientIds;

      private ResolveClientIdsCallback() {
         this.clientIds = null;
      }

      public void invokeContextCallback(FacesContext context, UIComponent target) {
         this.addClientId(target.getClientId(context));
      }

      public List getClientIds() {
         return this.clientIds;
      }

      public void addClientId(String clientId) {
         if (this.clientIds == null) {
            this.clientIds = new ArrayList();
         }

         this.clientIds.add(clientId);
      }

      // $FF: synthetic method
      ResolveClientIdsCallback(Object x0) {
         this();
      }
   }

   private static class ResolveClientIdCallback implements ContextCallback {
      private String clientId;

      private ResolveClientIdCallback() {
         this.clientId = null;
      }

      public void invokeContextCallback(FacesContext context, UIComponent target) {
         if (this.clientId == null) {
            this.clientId = target.getClientId(context);
         }

      }

      public String getClientId() {
         return this.clientId;
      }

      // $FF: synthetic method
      ResolveClientIdCallback(Object x0) {
         this();
      }
   }
}
