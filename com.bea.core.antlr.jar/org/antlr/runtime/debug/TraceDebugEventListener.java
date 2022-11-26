package org.antlr.runtime.debug;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeAdaptor;

public class TraceDebugEventListener extends BlankDebugEventListener {
   TreeAdaptor adaptor;

   public TraceDebugEventListener(TreeAdaptor adaptor) {
      this.adaptor = adaptor;
   }

   public void enterRule(String ruleName) {
      System.out.println("enterRule " + ruleName);
   }

   public void exitRule(String ruleName) {
      System.out.println("exitRule " + ruleName);
   }

   public void enterSubRule(int decisionNumber) {
      System.out.println("enterSubRule");
   }

   public void exitSubRule(int decisionNumber) {
      System.out.println("exitSubRule");
   }

   public void location(int line, int pos) {
      System.out.println("location " + line + ":" + pos);
   }

   public void consumeNode(Object t) {
      int ID = this.adaptor.getUniqueID(t);
      String text = this.adaptor.getText(t);
      int type = this.adaptor.getType(t);
      System.out.println("consumeNode " + ID + " " + text + " " + type);
   }

   public void LT(int i, Object t) {
      int ID = this.adaptor.getUniqueID(t);
      String text = this.adaptor.getText(t);
      int type = this.adaptor.getType(t);
      System.out.println("LT " + i + " " + ID + " " + text + " " + type);
   }

   public void nilNode(Object t) {
      System.out.println("nilNode " + this.adaptor.getUniqueID(t));
   }

   public void createNode(Object t) {
      int ID = this.adaptor.getUniqueID(t);
      String text = this.adaptor.getText(t);
      int type = this.adaptor.getType(t);
      System.out.println("create " + ID + ": " + text + ", " + type);
   }

   public void createNode(Object node, Token token) {
      int ID = this.adaptor.getUniqueID(node);
      this.adaptor.getText(node);
      int tokenIndex = token.getTokenIndex();
      System.out.println("create " + ID + ": " + tokenIndex);
   }

   public void becomeRoot(Object newRoot, Object oldRoot) {
      System.out.println("becomeRoot " + this.adaptor.getUniqueID(newRoot) + ", " + this.adaptor.getUniqueID(oldRoot));
   }

   public void addChild(Object root, Object child) {
      System.out.println("addChild " + this.adaptor.getUniqueID(root) + ", " + this.adaptor.getUniqueID(child));
   }

   public void setTokenBoundaries(Object t, int tokenStartIndex, int tokenStopIndex) {
      System.out.println("setTokenBoundaries " + this.adaptor.getUniqueID(t) + ", " + tokenStartIndex + ", " + tokenStopIndex);
   }
}
