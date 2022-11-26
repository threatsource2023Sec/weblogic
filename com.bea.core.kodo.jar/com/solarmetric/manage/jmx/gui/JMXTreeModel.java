package com.solarmetric.manage.jmx.gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class JMXTreeModel implements TreeModel {
   private HashSet _listeners = new HashSet();
   private TreeNode _root;

   public JMXTreeModel(TreeNode root) {
      this._root = root;
   }

   public Object getChild(Object parent, int index) {
      return ((TreeNode)parent).getChildAt(index);
   }

   public int getChildCount(Object parent) {
      return ((TreeNode)parent).getChildCount();
   }

   public int getIndexOfChild(Object parent, Object child) {
      return ((TreeNode)parent).getIndex((TreeNode)child);
   }

   public Object getRoot() {
      return this._root;
   }

   public boolean isLeaf(Object node) {
      return ((TreeNode)node).isLeaf();
   }

   public void valueForPathChanged(TreePath path, Object newValue) {
   }

   public void addTreeModelListener(TreeModelListener listener) {
      this._listeners.add(listener);
   }

   public void removeTreeModelListener(TreeModelListener listener) {
      this._listeners.remove(listener);
   }

   private void fireTreeNodesInserted(TreeModelEvent e) {
      Iterator i = this._listeners.iterator();

      while(i.hasNext()) {
         ((TreeModelListener)i.next()).treeNodesInserted(e);
      }

   }

   public synchronized void nodeAdded(TreeNode parent, TreeNode child) {
      this.fireTreeNodesInserted(new TreeModelEvent(parent, this.getPathToNode(parent), new int[]{parent.getIndex(child)}, new Object[]{child}));
   }

   private Object[] getPathToNode(TreeNode node) {
      LinkedList list = new LinkedList();
      list.addFirst(node);

      while(node.getParent() != null) {
         node = node.getParent();
         list.addFirst(node);
      }

      return list.toArray();
   }
}
