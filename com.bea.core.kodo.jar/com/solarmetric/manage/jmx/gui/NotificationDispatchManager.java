package com.solarmetric.manage.jmx.gui;

import com.solarmetric.manage.jmx.NotificationDispatchListener;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class NotificationDispatchManager {
   private JMXTree _tree;

   public NotificationDispatchManager(JMXTree tree) {
      this._tree = tree;
   }

   public NotificationDispatchListener get(MBeanServer server, ObjectName name) {
      JMXTreeRootTreeNode root = this._tree.getRoot();
      if (root == null) {
         return null;
      } else {
         MBeanServerTreeNode serverNode = root.getMBeanServerTreeNode(server);
         if (serverNode == null) {
            return null;
         } else {
            MDomainTreeNode domain = serverNode.getMDomainTreeNode(name.getDomain());
            if (domain == null) {
               return null;
            } else {
               MBeanTreeNode mbean = domain.getMBeanTreeNode(name);
               return mbean == null ? null : mbean.getDispatcher();
            }
         }
      }
   }
}
