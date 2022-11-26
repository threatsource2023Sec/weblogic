package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.swing.XDefaultTreeCellRenderer;
import com.solarmetric.ide.ui.swing.XMenuItem;
import com.solarmetric.ide.ui.swing.XPopupMenu;
import com.solarmetric.ide.ui.swing.XTree;
import com.solarmetric.profile.Node;
import com.solarmetric.profile.ProfilingAgentImpl;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;
import org.apache.openjpa.lib.util.Localizer;

public class ProfilingTree extends XTree {
   private static final Localizer s_loc = Localizer.forPackage(ProfilingTree.class);
   ProfilingTreeModel _model;
   ProfilingAgentImpl _agent;
   ProfilingInterfaceImpl _interface;

   public ProfilingTree(ProfilingTreeModel model, ProfilingAgentImpl agent, ProfilingInterfaceImpl iface) {
      super(model);
      this._model = model;
      this._agent = agent;
      this._interface = iface;
      this.getSelectionModel().setSelectionMode(1);
      MouseListener ml = new MouseAdapter() {
         public void handleMouseEvent(MouseEvent e) {
            if (e.isPopupTrigger()) {
               ProfilingTree.this.doPopup(e);
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
      this.setCellRenderer(new XDefaultTreeCellRenderer() {
         public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            if (value instanceof Node) {
               value = ((Node)value).toString(!ProfilingTree.this._model.getShowAncestors());
            } else if (value instanceof Collection) {
               List l = new ArrayList(((Collection)value).size());
               Iterator iter = ((Collection)value).iterator();

               while(iter.hasNext()) {
                  Object elem = iter.next();
                  if (elem instanceof Node) {
                     l.add(((Node)elem).toString(!ProfilingTree.this._model.getShowAncestors()));
                  } else {
                     l.add(elem);
                  }
               }
            }

            return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
         }
      });
   }

   public ProfilingTreeModel getProfilingTreeModel() {
      return this._model;
   }

   public boolean showAncestor(boolean b) {
      Object newRoot = null;
      TreePath path = this.getSelectionModel().getSelectionPath();
      if (path != null) {
         newRoot = path.getLastPathComponent();
      }

      return this._model.showAncestor(b, newRoot);
   }

   public void doPopup(MouseEvent e) {
      JPopupMenu popup = new XPopupMenu();
      popup.add(new XMenuItem(this._interface.refreshAction));
      if (this._model.getShowAncestors()) {
         popup.add(new XMenuItem(this._interface.showDescendantsAction));
      }

      if (!this._model.getShowAncestors() && this.getRowForLocation(e.getX(), e.getY()) != -1) {
         JMenuItem item = new XMenuItem(s_loc.get("show-ancestor-action").getMessage());
         TreePath selPath = this.getPathForLocation(e.getX(), e.getY());
         Object node = selPath.getLastPathComponent();
         item.addActionListener(new AncestorActionListener(node));
         popup.add(item);
      }

      popup.add(new XMenuItem(this._interface.exportAction));
      popup.add(new XMenuItem(this._interface.resetAction));
      popup.show(e.getComponent(), e.getX(), e.getY());
   }

   protected synchronized void updateTree() {
      this._agent.processQueue();
      Enumeration paths = this.getDescendantToggledPaths(new TreePath(new Object[]{this.treeModel.getRoot()}));

      while(paths.hasMoreElements()) {
         TreePath path = (TreePath)paths.nextElement();
         Object parent = path.getLastPathComponent();
         int count = this.treeModel.getChildCount(parent);
         int[] indexes = new int[count];
         Object[] children = new Object[count];

         for(int i = 0; i < count; ++i) {
            indexes[i] = i;
            children[i] = this.treeModel.getChild(parent, i);
         }

         TreeModelEvent tmev = new TreeModelEvent(this, path, indexes, children);
         this._model.fireTreeNodesChangedEvent(tmev);
      }

   }

   class AncestorActionListener implements ActionListener {
      Object node;

      AncestorActionListener(Object node) {
         this.node = node;
      }

      public void actionPerformed(ActionEvent e) {
         ProfilingTree.this._model.showAncestor(true, this.node);
         ProfilingTree.this._interface.showDescendantsAction.setEnabled(true);
      }
   }
}
