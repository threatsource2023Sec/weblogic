package org.antlr.runtime.tree;

import java.util.HashMap;
import java.util.Map;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public abstract class BaseTreeAdaptor implements TreeAdaptor {
   protected Map treeToUniqueIDMap;
   protected int uniqueNodeID = 1;

   public Object nil() {
      return this.create((Token)null);
   }

   public Object errorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
      CommonErrorNode t = new CommonErrorNode(input, start, stop, e);
      return t;
   }

   public boolean isNil(Object tree) {
      return ((Tree)tree).isNil();
   }

   public Object dupTree(Object tree) {
      return this.dupTree(tree, (Object)null);
   }

   public Object dupTree(Object t, Object parent) {
      if (t == null) {
         return null;
      } else {
         Object newTree = this.dupNode(t);
         this.setChildIndex(newTree, this.getChildIndex(t));
         this.setParent(newTree, parent);
         int n = this.getChildCount(t);

         for(int i = 0; i < n; ++i) {
            Object child = this.getChild(t, i);
            Object newSubTree = this.dupTree(child, t);
            this.addChild(newTree, newSubTree);
         }

         return newTree;
      }
   }

   public void addChild(Object t, Object child) {
      if (t != null && child != null) {
         ((Tree)t).addChild((Tree)child);
      }

   }

   public Object becomeRoot(Object newRoot, Object oldRoot) {
      Tree newRootTree = (Tree)newRoot;
      Tree oldRootTree = (Tree)oldRoot;
      if (oldRoot == null) {
         return newRoot;
      } else {
         if (newRootTree.isNil()) {
            int nc = newRootTree.getChildCount();
            if (nc == 1) {
               newRootTree = newRootTree.getChild(0);
            } else if (nc > 1) {
               throw new RuntimeException("more than one node as root (TODO: make exception hierarchy)");
            }
         }

         newRootTree.addChild(oldRootTree);
         return newRootTree;
      }
   }

   public Object rulePostProcessing(Object root) {
      Tree r = (Tree)root;
      if (r != null && r.isNil()) {
         if (r.getChildCount() == 0) {
            r = null;
         } else if (r.getChildCount() == 1) {
            r = r.getChild(0);
            r.setParent((Tree)null);
            r.setChildIndex(-1);
         }
      }

      return r;
   }

   public Object becomeRoot(Token newRoot, Object oldRoot) {
      return this.becomeRoot(this.create(newRoot), oldRoot);
   }

   public Object create(int tokenType, Token fromToken) {
      fromToken = this.createToken(fromToken);
      fromToken.setType(tokenType);
      Tree t = (Tree)this.create(fromToken);
      return t;
   }

   public Object create(int tokenType, Token fromToken, String text) {
      if (fromToken == null) {
         return this.create(tokenType, text);
      } else {
         fromToken = this.createToken(fromToken);
         fromToken.setType(tokenType);
         fromToken.setText(text);
         Tree t = (Tree)this.create(fromToken);
         return t;
      }
   }

   public Object create(int tokenType, String text) {
      Token fromToken = this.createToken(tokenType, text);
      Tree t = (Tree)this.create(fromToken);
      return t;
   }

   public int getType(Object t) {
      return ((Tree)t).getType();
   }

   public void setType(Object t, int type) {
      throw new NoSuchMethodError("don't know enough about Tree node");
   }

   public String getText(Object t) {
      return ((Tree)t).getText();
   }

   public void setText(Object t, String text) {
      throw new NoSuchMethodError("don't know enough about Tree node");
   }

   public Object getChild(Object t, int i) {
      return ((Tree)t).getChild(i);
   }

   public void setChild(Object t, int i, Object child) {
      ((Tree)t).setChild(i, (Tree)child);
   }

   public Object deleteChild(Object t, int i) {
      return ((Tree)t).deleteChild(i);
   }

   public int getChildCount(Object t) {
      return ((Tree)t).getChildCount();
   }

   public int getUniqueID(Object node) {
      if (this.treeToUniqueIDMap == null) {
         this.treeToUniqueIDMap = new HashMap();
      }

      Integer prevID = (Integer)this.treeToUniqueIDMap.get(node);
      if (prevID != null) {
         return prevID;
      } else {
         int ID = this.uniqueNodeID;
         this.treeToUniqueIDMap.put(node, ID);
         ++this.uniqueNodeID;
         return ID;
      }
   }

   public abstract Token createToken(int var1, String var2);

   public abstract Token createToken(Token var1);
}
