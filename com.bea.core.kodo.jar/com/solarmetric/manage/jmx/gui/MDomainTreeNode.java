package com.solarmetric.manage.jmx.gui;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;

public class MDomainTreeNode implements TreeNode, Comparable, Closeable {
   private String _domain;
   private MBeanServerTreeNode _parent;
   private TreeSet _children;
   private MBeanServer _server;
   private boolean _dirty = true;
   private JMXTreeModel _model;
   private Log _log;

   public void setTreeModel(JMXTreeModel model) {
      this._model = model;
   }

   public boolean refresh() {
      return this.refresh(false);
   }

   public boolean refresh(boolean ifDirtyIs) {
      if (ifDirtyIs != this._dirty) {
         return false;
      } else {
         boolean changed = false;
         Set nameSet = this._server.queryNames((ObjectName)null, (QueryExp)null);
         Iterator i = nameSet.iterator();

         while(i.hasNext()) {
            ObjectName name = (ObjectName)i.next();
            if (name.getDomain().equals(this._domain)) {
               try {
                  MBeanTreeNode mb = new MBeanTreeNode(name, this, this._server, this._log);
                  if (this._children.add(mb)) {
                     if (this._model != null) {
                        this._model.nodeAdded(this, mb);
                     }

                     changed = true;
                  }
               } catch (JMException var7) {
                  var7.printStackTrace();
               }
            }
         }

         this._dirty = false;
         return changed;
      }
   }

   public MDomainTreeNode(String domain, MBeanServerTreeNode parent, MBeanServer server, Log log) {
      this._domain = domain;
      this._parent = parent;
      this._children = new TreeSet();
      this._server = server;
      this._log = log;
   }

   public MBeanTreeNode getMBeanTreeNode(ObjectName name) {
      this.refresh(true);
      Iterator i = this._children.iterator();

      MBeanTreeNode node;
      do {
         if (!i.hasNext()) {
            return null;
         }

         node = (MBeanTreeNode)i.next();
      } while(!node.getInstance().getObjectName().toString().equals(name.toString()));

      return node;
   }

   public String getDomain() {
      return this._domain;
   }

   public Enumeration children() {
      this.refresh(true);
      return new Enumeration() {
         private Iterator i;

         {
            this.i = MDomainTreeNode.this._children.iterator();
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
      this.refresh(true);
      return (TreeNode)this._children.toArray()[childIdx];
   }

   public int getChildCount() {
      this.refresh(true);
      return this._children.size();
   }

   public int getIndex(TreeNode child) {
      this.refresh(true);
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
      return o instanceof MDomainTreeNode ? this._domain.compareTo(((MDomainTreeNode)o).getDomain()) : 1;
   }

   public String toString() {
      return this._domain;
   }

   public void close() {
      Iterator i = this._children.iterator();

      while(i.hasNext()) {
         ((MBeanTreeNode)i.next()).close();
      }

   }
}
