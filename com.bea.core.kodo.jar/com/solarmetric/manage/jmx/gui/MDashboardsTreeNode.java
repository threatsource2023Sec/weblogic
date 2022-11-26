package com.solarmetric.manage.jmx.gui;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeSet;
import javax.management.MBeanServer;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;

public class MDashboardsTreeNode implements TreeNode, Comparable, Closeable {
   private static Localizer s_loc = Localizer.forPackage(MDashboardsTreeNode.class);
   private MBeanServerTreeNode _parent;
   private TreeSet _children;
   private Log _log;

   public MDashboardsTreeNode(MBeanServerTreeNode parent, MBeanServer server, Log log) {
      this._parent = parent;
      this._children = new TreeSet();
      this._log = log;
      File file = Files.getFile("dashboards.dashboards", Thread.currentThread().getContextClassLoader());
      if (file.exists()) {
         DashboardMetaDataParser dmdp = new DashboardMetaDataParser(this._log);
         dmdp.setValidating(false);

         try {
            dmdp.parse(file);
            Collection dashboards = dmdp.getResults();
            dmdp.reset();
            Iterator i = dashboards.iterator();

            while(i.hasNext()) {
               DashboardMetaData dashboard = (DashboardMetaData)i.next();
               MDashboardTreeNode child = new MDashboardTreeNode(dashboard, this, server, log);
               this._children.add(child);
            }
         } catch (IOException var10) {
            this._log.error(s_loc.get("cant-read-dashboards", var10));
            var10.printStackTrace();
         }
      } else {
         this._log.info(s_loc.get("cant-find-dashboards"));
      }

   }

   public Enumeration children() {
      return new Enumeration() {
         private Iterator i;

         {
            this.i = MDashboardsTreeNode.this._children.iterator();
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
      return (TreeNode)this._children.toArray()[childIdx];
   }

   public int getChildCount() {
      return this._children.size();
   }

   public int getIndex(TreeNode child) {
      int index = 0;

      for(Iterator i = this._children.iterator(); i.hasNext(); ++index) {
         if (i.next() == child) {
            return index;
         }
      }

      return -1;
   }

   public TreeNode getParent() {
      return this._parent;
   }

   public boolean isLeaf() {
      return false;
   }

   public int compareTo(Object o) {
      return o == this ? 0 : -1;
   }

   public String toString() {
      return "Dashboards";
   }

   public void close() {
      Iterator i = this._children.iterator();

      while(i.hasNext()) {
         ((MDashboardTreeNode)i.next()).close();
      }

   }
}
