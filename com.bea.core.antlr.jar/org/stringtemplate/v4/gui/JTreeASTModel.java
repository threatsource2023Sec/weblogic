package org.stringtemplate.v4.gui;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

public class JTreeASTModel implements TreeModel {
   TreeAdaptor adaptor;
   Object root;

   public JTreeASTModel(TreeAdaptor adaptor, Object root) {
      this.adaptor = adaptor;
      this.root = root;
   }

   public JTreeASTModel(Object root) {
      this.adaptor = new CommonTreeAdaptor();
      this.root = root;
   }

   public int getChildCount(Object parent) {
      return this.adaptor.getChildCount(parent);
   }

   public int getIndexOfChild(Object parent, Object child) {
      return parent == null ? -1 : this.adaptor.getChildIndex(child);
   }

   public Object getChild(Object parent, int index) {
      return this.adaptor.getChild(parent, index);
   }

   public boolean isLeaf(Object node) {
      return this.getChildCount(node) == 0;
   }

   public Object getRoot() {
      return this.root;
   }

   public void valueForPathChanged(TreePath treePath, Object o) {
   }

   public void addTreeModelListener(TreeModelListener treeModelListener) {
   }

   public void removeTreeModelListener(TreeModelListener treeModelListener) {
   }
}
