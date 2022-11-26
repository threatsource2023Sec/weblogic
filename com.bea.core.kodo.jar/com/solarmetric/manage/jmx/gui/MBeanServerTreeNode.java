package com.solarmetric.manage.jmx.gui;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;

public class MBeanServerTreeNode implements TreeNode, Closeable {
   private static final Localizer s_loc = Localizer.forPackage(MBeanServerTreeNode.class);
   private MBeanServer _server;
   private TreeSet _children;
   private boolean _dirty = true;
   private String _name;
   private JMXTreeModel _model;
   private JMXTreeRootTreeNode _parent;
   private NotificationListener _listener;
   private Log _log;

   public MBeanServer getMBeanServer() {
      return this._server;
   }

   public void setTreeModel(JMXTreeModel model) {
      this._model = model;
      Iterator i = this._children.iterator();

      while(i.hasNext()) {
         Object child = i.next();
         if (child instanceof MDomainTreeNode) {
            ((MDomainTreeNode)child).setTreeModel(model);
         }
      }

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

         MDomainTreeNode domain;
         while(i.hasNext()) {
            ObjectName name = (ObjectName)i.next();
            domain = new MDomainTreeNode(name.getDomain(), this, this._server, this._log);
            if (this._children.add(domain)) {
               if (this._model != null) {
                  domain.setTreeModel(this._model);
                  this._model.nodeAdded(this, domain);
               }

               changed = true;
            }
         }

         if (!ifDirtyIs) {
            i = this._children.iterator();

            while(i.hasNext()) {
               Object child = i.next();
               if (child instanceof MDomainTreeNode) {
                  domain = (MDomainTreeNode)i.next();
                  domain.refresh();
               }
            }
         }

         this._dirty = false;
         return changed;
      }
   }

   public MBeanServerTreeNode(JMXTreeRootTreeNode root, MBeanServer server, String name, Log log) {
      this._server = server;
      this._name = name;
      this._children = new TreeSet();
      this._parent = root;
      this._log = log;
      this._children.add(new MDashboardsTreeNode(this, server, log));

      try {
         this._listener = new NotificationListener() {
            public void handleNotification(Notification notification, Object handback) {
               MBeanServerTreeNode.this.refresh();
            }
         };
         this._server.addNotificationListener(new ObjectName("JMImplementation:type=MBeanServerDelegate"), this._listener, (NotificationFilter)null, (Object)null);
      } catch (JMException var6) {
         var6.printStackTrace();
      }

   }

   public MDomainTreeNode getMDomainTreeNode(String domain) {
      Iterator i = this._children.iterator();

      while(i.hasNext()) {
         Object node = i.next();
         if (node instanceof MDomainTreeNode) {
            MDomainTreeNode domainNode = (MDomainTreeNode)node;
            if (domainNode.getDomain().equals(domain)) {
               return domainNode;
            }
         }
      }

      return null;
   }

   public Enumeration children() {
      this.refresh(true);
      return new Enumeration() {
         private Iterator i;

         {
            this.i = MBeanServerTreeNode.this._children.iterator();
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

   public String toString() {
      return this._name;
   }

   public void close() {
      try {
         this._server.removeNotificationListener(new ObjectName("JMImplementation:type=MBeanServerDelegate"), this._listener);
         if (this._server instanceof Closeable) {
            ((Closeable)this._server).close();
         }
      } catch (Exception var4) {
         this._log.error(s_loc.get("cant-close"));
         this._log.trace(s_loc.get("cant-close"), var4);
      }

      Iterator i = this._children.iterator();

      while(i.hasNext()) {
         try {
            ((Closeable)i.next()).close();
         } catch (Exception var3) {
            this._log.error(s_loc.get("cant-close"));
            this._log.trace(s_loc.get("cant-close"), var3);
         }
      }

   }
}
