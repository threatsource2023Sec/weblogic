package com.solarmetric.manage.jmx.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.management.MBeanServer;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;

public class JMXTreeRootTreeNode implements TreeNode, Closeable {
   private ArrayList _children = new ArrayList();
   private JMXTreeModel _model;
   private Log _log;

   public JMXTreeRootTreeNode(Log log) {
      this._log = log;
   }

   public void setTreeModel(JMXTreeModel model) {
      this._model = model;
      Iterator i = this._children.iterator();

      while(i.hasNext()) {
         ((MBeanServerTreeNode)i.next()).setTreeModel(model);
      }

   }

   public void add(MBeanServer mbServer, String name) {
      MBeanServerTreeNode child = new MBeanServerTreeNode(this, mbServer, name, this._log);
      this._children.add(child);
      if (this._model != null) {
         child.setTreeModel(this._model);
         this._model.nodeAdded(this, child);
      }

   }

   public MBeanServerTreeNode getMBeanServerTreeNode(MBeanServer server) {
      Iterator i = this._children.iterator();

      MBeanServerTreeNode node;
      do {
         if (!i.hasNext()) {
            return null;
         }

         node = (MBeanServerTreeNode)i.next();
      } while(node.getMBeanServer() != server);

      return node;
   }

   public Enumeration children() {
      return new Enumeration() {
         private Iterator i;

         {
            this.i = JMXTreeRootTreeNode.this._children.iterator();
         }

         public boolean hasMoreElements() {
            return this.i.hasNext();
         }

         public Object nextElement() {
            return this.i.next();
         }
      };
   }

   public boolean getAllowsChildren() {
      return true;
   }

   public TreeNode getChildAt(int childIdx) {
      return (TreeNode)this._children.get(childIdx);
   }

   public int getChildCount() {
      return this._children.size();
   }

   public int getIndex(TreeNode child) {
      return this._children.indexOf(child);
   }

   public TreeNode getParent() {
      return null;
   }

   public boolean isLeaf() {
      return false;
   }

   public String toString() {
      return "JMXExplorer";
   }

   public void close() {
      Iterator i = this._children.iterator();

      while(i.hasNext()) {
         ((MBeanServerTreeNode)i.next()).close();
      }

   }
}
