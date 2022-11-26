package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.swing.XMenuItem;
import com.solarmetric.ide.ui.swing.XPopupMenu;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreeNode;
import org.apache.openjpa.lib.log.Log;

public class MOperationTreeNode implements TreeNode, Describable, Popupable {
   private MBeanServer _server;
   private MOperationsTreeNode _parent;
   private ObjectInstance _instance;
   private MBeanOperationInfo _operInfo;
   private Log _log;

   public MOperationTreeNode(MBeanServer server, MOperationsTreeNode parent, ObjectInstance instance, MBeanOperationInfo operInfo, Log log) {
      this._server = server;
      this._parent = parent;
      this._instance = instance;
      this._operInfo = operInfo;
      this._log = log;
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
      return this._operInfo.getName();
   }

   public String getDescription() {
      return this._operInfo.getName() + " (" + this._operInfo.getDescription() + ")";
   }

   public void doPopup(MouseEvent e) {
      OperationExecuteAction opexecAction = new OperationExecuteAction(this._server, this._instance, this._operInfo, this._log);
      JPopupMenu popup = new XPopupMenu();
      JMenuItem item = new XMenuItem(opexecAction);
      popup.add(item);
      popup.show(e.getComponent(), e.getX(), e.getY());
   }
}
