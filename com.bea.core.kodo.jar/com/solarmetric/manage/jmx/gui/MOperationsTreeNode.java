package com.solarmetric.manage.jmx.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;

public class MOperationsTreeNode implements TreeNode {
   private MBeanTreeNode _parent;
   private MBeanInfo _mbInfo;
   private ArrayList _children;
   private Log _log;

   public MOperationsTreeNode(MBeanServer server, MBeanTreeNode parent, ObjectInstance instance, MBeanInfo mbInfo, Log log) {
      this._parent = parent;
      this._mbInfo = mbInfo;
      this._children = new ArrayList();
      this._log = log;
      MBeanOperationInfo[] opers = this._mbInfo.getOperations();

      for(int i = 0; i < opers.length; ++i) {
         this._children.add(new MOperationTreeNode(server, this, instance, opers[i], this._log));
      }

   }

   public Enumeration children() {
      return new Enumeration() {
         private Iterator i;

         {
            this.i = MOperationsTreeNode.this._children.iterator();
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
      return this._parent;
   }

   public boolean isLeaf() {
      return false;
   }

   public String toString() {
      return "Operations";
   }
}
