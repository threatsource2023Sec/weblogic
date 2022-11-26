package org.antlr.runtime.tree;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;

public class CommonTreeAdaptor extends BaseTreeAdaptor {
   public Object dupNode(Object t) {
      return t == null ? null : ((Tree)t).dupNode();
   }

   public Object create(Token payload) {
      return new CommonTree(payload);
   }

   public Token createToken(int tokenType, String text) {
      return new CommonToken(tokenType, text);
   }

   public Token createToken(Token fromToken) {
      return new CommonToken(fromToken);
   }

   public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
      if (t != null) {
         int start = 0;
         int stop = 0;
         if (startToken != null) {
            start = startToken.getTokenIndex();
         }

         if (stopToken != null) {
            stop = stopToken.getTokenIndex();
         }

         ((Tree)t).setTokenStartIndex(start);
         ((Tree)t).setTokenStopIndex(stop);
      }
   }

   public int getTokenStartIndex(Object t) {
      return t == null ? -1 : ((Tree)t).getTokenStartIndex();
   }

   public int getTokenStopIndex(Object t) {
      return t == null ? -1 : ((Tree)t).getTokenStopIndex();
   }

   public String getText(Object t) {
      return t == null ? null : ((Tree)t).getText();
   }

   public int getType(Object t) {
      return t == null ? 0 : ((Tree)t).getType();
   }

   public Token getToken(Object t) {
      return t instanceof CommonTree ? ((CommonTree)t).getToken() : null;
   }

   public Object getChild(Object t, int i) {
      return t == null ? null : ((Tree)t).getChild(i);
   }

   public int getChildCount(Object t) {
      return t == null ? 0 : ((Tree)t).getChildCount();
   }

   public Object getParent(Object t) {
      return t == null ? null : ((Tree)t).getParent();
   }

   public void setParent(Object t, Object parent) {
      if (t != null) {
         ((Tree)t).setParent((Tree)parent);
      }

   }

   public int getChildIndex(Object t) {
      return t == null ? 0 : ((Tree)t).getChildIndex();
   }

   public void setChildIndex(Object t, int index) {
      if (t != null) {
         ((Tree)t).setChildIndex(index);
      }

   }

   public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
      if (parent != null) {
         ((Tree)parent).replaceChildren(startChildIndex, stopChildIndex, t);
      }

   }
}
