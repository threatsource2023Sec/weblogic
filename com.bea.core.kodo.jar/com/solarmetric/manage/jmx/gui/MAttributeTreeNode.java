package com.solarmetric.manage.jmx.gui;

import java.util.Enumeration;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.tree.TreeNode;

public class MAttributeTreeNode implements TreeNode, Describable {
   private MBeanServer _server;
   private MAttributesTreeNode _parent;
   private ObjectInstance _instance;
   private MBeanAttributeInfo _attrInfo;

   public MAttributeTreeNode(MBeanServer server, MAttributesTreeNode parent, ObjectInstance instance, MBeanAttributeInfo attrInfo) {
      this._server = server;
      this._parent = parent;
      this._instance = instance;
      this._attrInfo = attrInfo;
   }

   public Enumeration children() {
      return new Enumeration() {
         public boolean hasMoreElements() {
            return false;
         }

         public Object nextElement() {
            return null;
         }
      };
   }

   public boolean getAllowsChildren() {
      return false;
   }

   public TreeNode getChildAt(int childIdx) {
      return null;
   }

   public int getChildCount() {
      return 0;
   }

   public int getIndex(TreeNode child) {
      return -1;
   }

   public TreeNode getParent() {
      return this._parent;
   }

   public boolean isLeaf() {
      return true;
   }

   public String getValueAsString() {
      String val = "unknown";

      try {
         val = "" + this._server.getAttribute(this._instance.getObjectName(), this._attrInfo.getName());
      } catch (Exception var3) {
      }

      return val;
   }

   public String toString() {
      return this._attrInfo.getName() + " = " + this.getValueAsString();
   }

   public String getDescription() {
      return this._attrInfo.getName() + " (" + this._attrInfo.getDescription() + ") = " + this.getValueAsString();
   }
}
