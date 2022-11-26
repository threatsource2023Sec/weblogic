package com.solarmetric.manage.jmx.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.tree.TreeNode;

public class MAttributesTreeNode implements TreeNode {
   private MBeanTreeNode _parent;
   private MBeanInfo _mbInfo;
   private ArrayList _children;

   public MAttributesTreeNode(MBeanServer server, MBeanTreeNode parent, ObjectInstance instance, MBeanInfo mbInfo) {
      this._parent = parent;
      this._mbInfo = mbInfo;
      this._children = new ArrayList();
      MBeanAttributeInfo[] attrs = this._mbInfo.getAttributes();

      for(int i = 0; i < attrs.length; ++i) {
         this._children.add(new MAttributeTreeNode(server, this, instance, attrs[i]));
      }

   }

   public Enumeration children() {
      return new Enumeration() {
         private Iterator i;

         {
            this.i = MAttributesTreeNode.this._children.iterator();
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
      return "Attributes";
   }
}
