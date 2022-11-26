package com.solarmetric.manage.jmx.gui;

import com.solarmetric.manage.jmx.NotificationDispatchListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;

public class MBeanTreeNode implements TreeNode, Comparable, Describable, Closeable {
   private ObjectInstance _instance;
   private MDomainTreeNode _parent;
   private ArrayList _children;
   private MBeanInfo _mbInfo;
   private MBeanServer _server;
   private NotificationDispatchListener _dispatcher;
   private Log _log;

   public MBeanTreeNode(ObjectName name, MDomainTreeNode parent, MBeanServer server, Log log) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
      this._server = server;
      this._log = log;
      this._instance = this._server.getObjectInstance(name);
      this._mbInfo = this._server.getMBeanInfo(name);
      this._parent = parent;
      this._dispatcher = new NotificationDispatchListener(this._server, this._instance, this._log);
      this._children = new ArrayList();
      this._children.add(new MAttributesTreeNode(this._server, this, this._instance, this._mbInfo));
      this._children.add(new MOperationsTreeNode(this._server, this, this._instance, this._mbInfo, this._log));
      this._children.add(new MNotificationsTreeNode(this._server, this, this._instance, this._mbInfo));
   }

   public MBeanServer getServer() {
      return this._server;
   }

   public ObjectInstance getInstance() {
      return this._instance;
   }

   public MBeanInfo getMBeanInfo() {
      return this._mbInfo;
   }

   public NotificationDispatchListener getDispatcher() {
      return this._dispatcher;
   }

   public Enumeration children() {
      return new Enumeration() {
         private Iterator i;

         {
            this.i = MBeanTreeNode.this._children.iterator();
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
      return this._instance.getObjectName().toString();
   }

   public int compareTo(Object o) {
      return this.toString().compareTo(o.toString());
   }

   public String getDescription() {
      return this._mbInfo.getDescription();
   }

   public void close() {
      this._dispatcher.close();
   }
}
