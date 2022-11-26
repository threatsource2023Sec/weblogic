package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.swing.XMenuItem;
import com.solarmetric.ide.ui.swing.XPopupMenu;
import com.solarmetric.manage.jmx.NotificationDispatchListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.util.Localizer;

public class MNotificationsTreeNode implements TreeNode, Popupable {
   private static Localizer s_loc = Localizer.forPackage(MNotificationsTreeNode.class);
   private MBeanTreeNode _parent;
   private MBeanInfo _mbInfo;
   private ArrayList _children;

   public MNotificationsTreeNode(MBeanServer server, MBeanTreeNode parent, ObjectInstance instance, MBeanInfo mbInfo) {
      this._parent = parent;
      this._mbInfo = mbInfo;
      this._children = new ArrayList();
      MBeanNotificationInfo[] notifs = this._mbInfo.getNotifications();

      for(int i = 0; i < notifs.length; ++i) {
         this._children.add(new MNotificationTreeNode(server, this, instance, notifs[i]));
      }

   }

   public Enumeration children() {
      return new Enumeration() {
         private Iterator i;

         {
            this.i = MNotificationsTreeNode.this._children.iterator();
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
      return "Notifications";
   }

   public void doPopup(MouseEvent e) {
      JPopupMenu popup = new XPopupMenu();
      JMenuItem item = new XMenuItem(s_loc.get("notifslisten-action").getMessage());
      item.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            NotificationDispatchListener dispatcher = MNotificationsTreeNode.this._parent.getDispatcher();
            MBeanNotificationInfo[] notifInfos = MNotificationsTreeNode.this._mbInfo.getNotifications();

            for(int i = 0; i < notifInfos.length; ++i) {
               dispatcher.activateNotificationTypes(notifInfos[i], notifInfos[i].getNotifTypes());
            }

         }
      });
      popup.add(item);
      item = new XMenuItem(s_loc.get("notifsstoplisten-action").getMessage());
      item.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            NotificationDispatchListener dispatcher = MNotificationsTreeNode.this._parent.getDispatcher();
            MBeanNotificationInfo[] notifInfos = MNotificationsTreeNode.this._mbInfo.getNotifications();

            for(int i = 0; i < notifInfos.length; ++i) {
               dispatcher.deactivateNotificationTypes(notifInfos[i], notifInfos[i].getNotifTypes());
            }

         }
      });
      popup.add(item);
      popup.show(e.getComponent(), e.getX(), e.getY());
   }
}
