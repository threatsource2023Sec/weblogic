package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.swing.XDefaultTreeCellRenderer;
import com.solarmetric.ide.ui.swing.XTree;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.management.MBeanServer;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.TreePath;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;

public class JMXTree extends XTree implements Closeable {
   public JMXTree(boolean rootVisible, Log log) {
      super(new JMXTreeModel(new JMXTreeRootTreeNode(log)));
      this.setShowsRootHandles(true);
      JMXTreeModel model = (JMXTreeModel)this.getModel();
      JMXTreeRootTreeNode root = (JMXTreeRootTreeNode)model.getRoot();
      root.setTreeModel(model);
      this.setRootVisible(rootVisible);
      ToolTipManager.sharedInstance().registerComponent(this);
      this.setCellRenderer(new DescribableRenderer());
      MouseListener ml = new MouseAdapter() {
         public void handleMouseEvent(MouseEvent e) {
            if (e.isPopupTrigger()) {
               int selRow = JMXTree.this.getRowForLocation(e.getX(), e.getY());
               TreePath selPath = JMXTree.this.getPathForLocation(e.getX(), e.getY());
               if (selRow != -1 && selPath.getLastPathComponent() instanceof Popupable) {
                  Popupable popupable = (Popupable)selPath.getLastPathComponent();
                  popupable.doPopup(e);
               }
            }

         }

         public void mousePressed(MouseEvent e) {
            this.handleMouseEvent(e);
         }

         public void mouseReleased(MouseEvent e) {
            this.handleMouseEvent(e);
         }

         public void mouseClicked(MouseEvent e) {
            this.handleMouseEvent(e);
         }
      };
      this.addMouseListener(ml);
   }

   public JMXTreeRootTreeNode getRoot() {
      JMXTreeModel model = (JMXTreeModel)this.getModel();
      JMXTreeRootTreeNode root = (JMXTreeRootTreeNode)model.getRoot();
      return root;
   }

   public void add(MBeanServer mbServer, String name) {
      ((JMXTreeRootTreeNode)this.getModel().getRoot()).add(mbServer, name);
   }

   public void close() {
      JMXTreeModel model = (JMXTreeModel)this.getModel();
      JMXTreeRootTreeNode root = (JMXTreeRootTreeNode)model.getRoot();
      root.close();
   }

   static class DescribableRenderer extends XDefaultTreeCellRenderer {
      public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
         super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
         if (value instanceof Describable) {
            Describable desc = (Describable)value;
            this.setToolTipText(desc.getDescription());
         } else {
            this.setToolTipText((String)null);
         }

         return this;
      }
   }
}
