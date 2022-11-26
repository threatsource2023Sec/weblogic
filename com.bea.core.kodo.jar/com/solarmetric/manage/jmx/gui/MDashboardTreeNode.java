package com.solarmetric.manage.jmx.gui;

import java.util.Enumeration;
import javax.management.MBeanServer;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;

public class MDashboardTreeNode implements TreeNode, Comparable, Describable, Closeable {
   private DashboardMetaData _dashboard;
   private MDashboardsTreeNode _parent;
   private MBeanServer _server;

   public MDashboardTreeNode(DashboardMetaData dashboard, MDashboardsTreeNode parent, MBeanServer server, Log log) {
      this._server = server;
      this._dashboard = dashboard;
      this._parent = parent;
   }

   public MBeanServer getServer() {
      return this._server;
   }

   public DashboardMetaData getDashboard() {
      return this._dashboard;
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

   public String toString() {
      return this._dashboard.getName();
   }

   public int compareTo(Object o) {
      return this.toString().compareTo(o.toString());
   }

   public String getDescription() {
      return "Dashboard";
   }

   public void close() {
   }
}
