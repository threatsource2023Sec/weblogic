package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

public class DebugTreeAdaptor implements TreeAdaptor {
   protected DebugEventListener dbg;
   protected TreeAdaptor adaptor;

   public DebugTreeAdaptor(DebugEventListener dbg, TreeAdaptor adaptor) {
      this.dbg = dbg;
      this.adaptor = adaptor;
   }

   public Object create(Token payload) {
      if (payload.getTokenIndex() < 0) {
         return this.create(payload.getType(), payload.getText());
      } else {
         Object node = this.adaptor.create(payload);
         this.dbg.createNode(node, payload);
         return node;
      }
   }

   public Object errorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
      Object node = this.adaptor.errorNode(input, start, stop, e);
      if (node != null) {
         this.dbg.errorNode(node);
      }

      return node;
   }

   public Object dupTree(Object tree) {
      Object t = this.adaptor.dupTree(tree);
      this.simulateTreeConstruction(t);
      return t;
   }

   protected void simulateTreeConstruction(Object t) {
      this.dbg.createNode(t);
      int n = this.adaptor.getChildCount(t);

      for(int i = 0; i < n; ++i) {
         Object child = this.adaptor.getChild(t, i);
         this.simulateTreeConstruction(child);
         this.dbg.addChild(t, child);
      }

   }

   public Object dupNode(Object treeNode) {
      Object d = this.adaptor.dupNode(treeNode);
      this.dbg.createNode(d);
      return d;
   }

   public Object nil() {
      Object node = this.adaptor.nil();
      this.dbg.nilNode(node);
      return node;
   }

   public boolean isNil(Object tree) {
      return this.adaptor.isNil(tree);
   }

   public void addChild(Object t, Object child) {
      if (t != null && child != null) {
         this.adaptor.addChild(t, child);
         this.dbg.addChild(t, child);
      }
   }

   public Object becomeRoot(Object newRoot, Object oldRoot) {
      Object n = this.adaptor.becomeRoot(newRoot, oldRoot);
      this.dbg.becomeRoot(newRoot, oldRoot);
      return n;
   }

   public Object rulePostProcessing(Object root) {
      return this.adaptor.rulePostProcessing(root);
   }

   public void addChild(Object t, Token child) {
      Object n = this.create(child);
      this.addChild(t, n);
   }

   public Object becomeRoot(Token newRoot, Object oldRoot) {
      Object n = this.create(newRoot);
      this.adaptor.becomeRoot(n, oldRoot);
      this.dbg.becomeRoot(newRoot, oldRoot);
      return n;
   }

   public Object create(int tokenType, Token fromToken) {
      Object node = this.adaptor.create(tokenType, fromToken);
      this.dbg.createNode(node);
      return node;
   }

   public Object create(int tokenType, Token fromToken, String text) {
      Object node = this.adaptor.create(tokenType, fromToken, text);
      this.dbg.createNode(node);
      return node;
   }

   public Object create(int tokenType, String text) {
      Object node = this.adaptor.create(tokenType, text);
      this.dbg.createNode(node);
      return node;
   }

   public int getType(Object t) {
      return this.adaptor.getType(t);
   }

   public void setType(Object t, int type) {
      this.adaptor.setType(t, type);
   }

   public String getText(Object t) {
      return this.adaptor.getText(t);
   }

   public void setText(Object t, String text) {
      this.adaptor.setText(t, text);
   }

   public Token getToken(Object t) {
      return this.adaptor.getToken(t);
   }

   public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
      this.adaptor.setTokenBoundaries(t, startToken, stopToken);
      if (t != null && startToken != null && stopToken != null) {
         this.dbg.setTokenBoundaries(t, startToken.getTokenIndex(), stopToken.getTokenIndex());
      }

   }

   public int getTokenStartIndex(Object t) {
      return this.adaptor.getTokenStartIndex(t);
   }

   public int getTokenStopIndex(Object t) {
      return this.adaptor.getTokenStopIndex(t);
   }

   public Object getChild(Object t, int i) {
      return this.adaptor.getChild(t, i);
   }

   public void setChild(Object t, int i, Object child) {
      this.adaptor.setChild(t, i, child);
   }

   public Object deleteChild(Object t, int i) {
      return this.deleteChild(t, i);
   }

   public int getChildCount(Object t) {
      return this.adaptor.getChildCount(t);
   }

   public int getUniqueID(Object node) {
      return this.adaptor.getUniqueID(node);
   }

   public Object getParent(Object t) {
      return this.adaptor.getParent(t);
   }

   public int getChildIndex(Object t) {
      return this.adaptor.getChildIndex(t);
   }

   public void setParent(Object t, Object parent) {
      this.adaptor.setParent(t, parent);
   }

   public void setChildIndex(Object t, int index) {
      this.adaptor.setChildIndex(t, index);
   }

   public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
      this.adaptor.replaceChildren(parent, startChildIndex, stopChildIndex, t);
   }

   public DebugEventListener getDebugListener() {
      return this.dbg;
   }

   public void setDebugListener(DebugEventListener dbg) {
      this.dbg = dbg;
   }

   public TreeAdaptor getTreeAdaptor() {
      return this.adaptor;
   }
}
