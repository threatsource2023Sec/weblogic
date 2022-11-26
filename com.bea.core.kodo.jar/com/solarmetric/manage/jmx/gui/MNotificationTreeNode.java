package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.swing.XMenuItem;
import com.solarmetric.ide.ui.swing.XPopupMenu;
import com.solarmetric.manage.jmx.NotificationDispatchListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.util.Localizer;

public class MNotificationTreeNode implements TreeNode, Describable, Popupable {
   private static Localizer s_loc = Localizer.forPackage(MNotificationTreeNode.class);
   private MNotificationsTreeNode _parent;
   private MBeanNotificationInfo _notifInfo;

   public MNotificationTreeNode(MBeanServer server, MNotificationsTreeNode parent, ObjectInstance instance, MBeanNotificationInfo notifInfo) {
      this._parent = parent;
      this._notifInfo = notifInfo;
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
      if (this._notifInfo.getNotifTypes().length == 0) {
         return this._notifInfo.getName();
      } else {
         StringBuffer name = new StringBuffer(this._notifInfo.getName());
         name.append(" (");
         name.append(this._notifInfo.getNotifTypes()[0]);

         for(int i = 1; i < this._notifInfo.getNotifTypes().length; ++i) {
            name.append(", ");
            name.append(this._notifInfo.getNotifTypes()[i]);
         }

         name.append(")");
         return name.toString();
      }
   }

   public String getDescription() {
      return this._notifInfo.getName() + " (" + this._notifInfo.getDescription() + ")";
   }

   public void doPopup(MouseEvent e) {
      JPopupMenu popup = new XPopupMenu();
      JMenuItem item = new XMenuItem(s_loc.get("notiflisten-action").getMessage());
      item.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            NotificationDispatchListener dispatcher = ((MBeanTreeNode)MNotificationTreeNode.this._parent.getParent()).getDispatcher();
            dispatcher.activateNotificationTypes(MNotificationTreeNode.this._notifInfo, MNotificationTreeNode.this._notifInfo.getNotifTypes());
         }
      });
      popup.add(item);
      item = new XMenuItem(s_loc.get("notifstoplisten-action").getMessage());
      item.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            NotificationDispatchListener dispatcher = ((MBeanTreeNode)MNotificationTreeNode.this._parent.getParent()).getDispatcher();
            dispatcher.deactivateNotificationTypes(MNotificationTreeNode.this._notifInfo, MNotificationTreeNode.this._notifInfo.getNotifTypes());
         }
      });
      popup.add(item);
      popup.show(e.getComponent(), e.getX(), e.getY());
   }
}
