package org.antlr.runtime.tree;

import java.util.Iterator;
import org.antlr.runtime.misc.FastQueue;

public class TreeIterator implements Iterator {
   protected TreeAdaptor adaptor;
   protected Object root;
   protected Object tree;
   protected boolean firstTime;
   public Object up;
   public Object down;
   public Object eof;
   protected FastQueue nodes;

   public TreeIterator(Object tree) {
      this(new CommonTreeAdaptor(), tree);
   }

   public TreeIterator(TreeAdaptor adaptor, Object tree) {
      this.firstTime = true;
      this.adaptor = adaptor;
      this.tree = tree;
      this.root = tree;
      this.nodes = new FastQueue();
      this.down = adaptor.create(2, (String)"DOWN");
      this.up = adaptor.create(3, (String)"UP");
      this.eof = adaptor.create(-1, (String)"EOF");
   }

   public void reset() {
      this.firstTime = true;
      this.tree = this.root;
      this.nodes.clear();
   }

   public boolean hasNext() {
      if (this.firstTime) {
         return this.root != null;
      } else if (this.nodes != null && this.nodes.size() > 0) {
         return true;
      } else if (this.tree == null) {
         return false;
      } else if (this.adaptor.getChildCount(this.tree) > 0) {
         return true;
      } else {
         return this.adaptor.getParent(this.tree) != null;
      }
   }

   public Object next() {
      if (this.firstTime) {
         this.firstTime = false;
         if (this.adaptor.getChildCount(this.tree) == 0) {
            this.nodes.add(this.eof);
            return this.tree;
         } else {
            return this.tree;
         }
      } else if (this.nodes != null && this.nodes.size() > 0) {
         return this.nodes.remove();
      } else if (this.tree == null) {
         return this.eof;
      } else if (this.adaptor.getChildCount(this.tree) > 0) {
         this.tree = this.adaptor.getChild(this.tree, 0);
         this.nodes.add(this.tree);
         return this.down;
      } else {
         Object parent;
         for(parent = this.adaptor.getParent(this.tree); parent != null && this.adaptor.getChildIndex(this.tree) + 1 >= this.adaptor.getChildCount(parent); parent = this.adaptor.getParent(this.tree)) {
            this.nodes.add(this.up);
            this.tree = parent;
         }

         if (parent == null) {
            this.tree = null;
            this.nodes.add(this.eof);
            return this.nodes.remove();
         } else {
            int nextSiblingIndex = this.adaptor.getChildIndex(this.tree) + 1;
            this.tree = this.adaptor.getChild(parent, nextSiblingIndex);
            this.nodes.add(this.tree);
            return this.nodes.remove();
         }
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
