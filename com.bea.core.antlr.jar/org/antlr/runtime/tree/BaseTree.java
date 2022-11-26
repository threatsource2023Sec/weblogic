package org.antlr.runtime.tree;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTree implements Tree {
   protected List children;

   public BaseTree() {
   }

   public BaseTree(Tree node) {
   }

   public Tree getChild(int i) {
      return this.children != null && i < this.children.size() ? (Tree)this.children.get(i) : null;
   }

   public List getChildren() {
      return this.children;
   }

   public Tree getFirstChildWithType(int type) {
      for(int i = 0; this.children != null && i < this.children.size(); ++i) {
         Tree t = (Tree)this.children.get(i);
         if (t.getType() == type) {
            return t;
         }
      }

      return null;
   }

   public int getChildCount() {
      return this.children == null ? 0 : this.children.size();
   }

   public void addChild(Tree t) {
      if (t != null) {
         BaseTree childTree = (BaseTree)t;
         if (childTree.isNil()) {
            if (this.children != null && this.children == childTree.children) {
               throw new RuntimeException("attempt to add child list to itself");
            }

            if (childTree.children != null) {
               if (this.children != null) {
                  int n = childTree.children.size();

                  for(int i = 0; i < n; ++i) {
                     Tree c = (Tree)childTree.children.get(i);
                     this.children.add(c);
                     c.setParent(this);
                     c.setChildIndex(this.children.size() - 1);
                  }
               } else {
                  this.children = childTree.children;
                  this.freshenParentAndChildIndexes();
               }
            }
         } else {
            if (this.children == null) {
               this.children = this.createChildrenList();
            }

            this.children.add(t);
            childTree.setParent(this);
            childTree.setChildIndex(this.children.size() - 1);
         }

      }
   }

   public void addChildren(List kids) {
      for(int i = 0; i < kids.size(); ++i) {
         Tree t = (Tree)kids.get(i);
         this.addChild(t);
      }

   }

   public void setChild(int i, Tree t) {
      if (t != null) {
         if (t.isNil()) {
            throw new IllegalArgumentException("Can't set single child to a list");
         } else {
            if (this.children == null) {
               this.children = this.createChildrenList();
            }

            this.children.set(i, t);
            t.setParent(this);
            t.setChildIndex(i);
         }
      }
   }

   public void insertChild(int i, Object t) {
      if (i >= 0 && i <= this.getChildCount()) {
         if (this.children == null) {
            this.children = this.createChildrenList();
         }

         this.children.add(i, t);
         this.freshenParentAndChildIndexes(i);
      } else {
         throw new IndexOutOfBoundsException(i + " out or range");
      }
   }

   public Object deleteChild(int i) {
      if (this.children == null) {
         return null;
      } else {
         Tree killed = (Tree)this.children.remove(i);
         this.freshenParentAndChildIndexes(i);
         return killed;
      }
   }

   public void replaceChildren(int startChildIndex, int stopChildIndex, Object t) {
      if (this.children == null) {
         throw new IllegalArgumentException("indexes invalid; no children in list");
      } else {
         int replacingHowMany = stopChildIndex - startChildIndex + 1;
         BaseTree newTree = (BaseTree)t;
         Object newChildren;
         if (newTree.isNil()) {
            newChildren = newTree.children;
         } else {
            newChildren = new ArrayList(1);
            ((List)newChildren).add(newTree);
         }

         int replacingWithHowMany = ((List)newChildren).size();
         int numNewChildren = ((List)newChildren).size();
         int delta = replacingHowMany - replacingWithHowMany;
         int indexToDelete;
         int j;
         if (delta == 0) {
            indexToDelete = 0;

            for(j = startChildIndex; j <= stopChildIndex; ++j) {
               BaseTree child = (BaseTree)((List)newChildren).get(indexToDelete);
               this.children.set(j, child);
               child.setParent(this);
               child.setChildIndex(j);
               ++indexToDelete;
            }
         } else if (delta > 0) {
            for(indexToDelete = 0; indexToDelete < numNewChildren; ++indexToDelete) {
               this.children.set(startChildIndex + indexToDelete, ((List)newChildren).get(indexToDelete));
            }

            indexToDelete = startChildIndex + numNewChildren;

            for(j = indexToDelete; j <= stopChildIndex; ++j) {
               this.children.remove(indexToDelete);
            }

            this.freshenParentAndChildIndexes(startChildIndex);
         } else {
            for(indexToDelete = 0; indexToDelete < replacingHowMany; ++indexToDelete) {
               this.children.set(startChildIndex + indexToDelete, ((List)newChildren).get(indexToDelete));
            }

            int var10000 = replacingWithHowMany - replacingHowMany;

            for(j = replacingHowMany; j < replacingWithHowMany; ++j) {
               this.children.add(startChildIndex + j, ((List)newChildren).get(j));
            }

            this.freshenParentAndChildIndexes(startChildIndex);
         }

      }
   }

   protected List createChildrenList() {
      return new ArrayList();
   }

   public boolean isNil() {
      return false;
   }

   public void freshenParentAndChildIndexes() {
      this.freshenParentAndChildIndexes(0);
   }

   public void freshenParentAndChildIndexes(int offset) {
      int n = this.getChildCount();

      for(int c = offset; c < n; ++c) {
         Tree child = this.getChild(c);
         child.setChildIndex(c);
         child.setParent(this);
      }

   }

   public void freshenParentAndChildIndexesDeeply() {
      this.freshenParentAndChildIndexesDeeply(0);
   }

   public void freshenParentAndChildIndexesDeeply(int offset) {
      int n = this.getChildCount();

      for(int c = offset; c < n; ++c) {
         BaseTree child = (BaseTree)this.getChild(c);
         child.setChildIndex(c);
         child.setParent(this);
         child.freshenParentAndChildIndexesDeeply();
      }

   }

   public void sanityCheckParentAndChildIndexes() {
      this.sanityCheckParentAndChildIndexes((Tree)null, -1);
   }

   public void sanityCheckParentAndChildIndexes(Tree parent, int i) {
      if (parent != this.getParent()) {
         throw new IllegalStateException("parents don't match; expected " + parent + " found " + this.getParent());
      } else if (i != this.getChildIndex()) {
         throw new IllegalStateException("child indexes don't match; expected " + i + " found " + this.getChildIndex());
      } else {
         int n = this.getChildCount();

         for(int c = 0; c < n; ++c) {
            CommonTree child = (CommonTree)this.getChild(c);
            child.sanityCheckParentAndChildIndexes(this, c);
         }

      }
   }

   public int getChildIndex() {
      return 0;
   }

   public void setChildIndex(int index) {
   }

   public Tree getParent() {
      return null;
   }

   public void setParent(Tree t) {
   }

   public boolean hasAncestor(int ttype) {
      return this.getAncestor(ttype) != null;
   }

   public Tree getAncestor(int ttype) {
      for(Tree t = this.getParent(); t != null; t = t.getParent()) {
         if (t.getType() == ttype) {
            return t;
         }
      }

      return null;
   }

   public List getAncestors() {
      if (this.getParent() == null) {
         return null;
      } else {
         List ancestors = new ArrayList();

         for(Tree t = this.getParent(); t != null; t = t.getParent()) {
            ancestors.add(0, t);
         }

         return ancestors;
      }
   }

   public String toStringTree() {
      if (this.children != null && !this.children.isEmpty()) {
         StringBuilder buf = new StringBuilder();
         if (!this.isNil()) {
            buf.append("(");
            buf.append(this.toString());
            buf.append(' ');
         }

         for(int i = 0; this.children != null && i < this.children.size(); ++i) {
            Tree t = (Tree)this.children.get(i);
            if (i > 0) {
               buf.append(' ');
            }

            buf.append(t.toStringTree());
         }

         if (!this.isNil()) {
            buf.append(")");
         }

         return buf.toString();
      } else {
         return this.toString();
      }
   }

   public int getLine() {
      return 0;
   }

   public int getCharPositionInLine() {
      return 0;
   }

   public abstract String toString();
}
