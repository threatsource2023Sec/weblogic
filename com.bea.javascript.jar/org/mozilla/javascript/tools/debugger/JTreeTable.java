package org.mozilla.javascript.tools.debugger;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class JTreeTable extends JTable {
   protected TreeTableCellRenderer tree;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$tools$debugger$TreeTableModel;

   public JTreeTable(TreeTableModel var1) {
      this.tree = new TreeTableCellRenderer(var1);
      super.setModel(new TreeTableModelAdapter(var1, this.tree));
      ListToTreeSelectionModelWrapper var2 = new ListToTreeSelectionModelWrapper();
      this.tree.setSelectionModel(var2);
      this.setSelectionModel(var2.getListSelectionModel());
      this.setDefaultRenderer(class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? class$org$mozilla$javascript$tools$debugger$TreeTableModel : (class$org$mozilla$javascript$tools$debugger$TreeTableModel = class$("org.mozilla.javascript.tools.debugger.TreeTableModel")), this.tree);
      this.setDefaultEditor(class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? class$org$mozilla$javascript$tools$debugger$TreeTableModel : (class$org$mozilla$javascript$tools$debugger$TreeTableModel = class$("org.mozilla.javascript.tools.debugger.TreeTableModel")), new TreeTableCellEditor());
      this.setShowGrid(false);
      this.setIntercellSpacing(new Dimension(0, 0));
      if (this.tree.getRowHeight() < 1) {
         this.setRowHeight(18);
      }

   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public int getEditingRow() {
      return this.getColumnClass(super.editingColumn) == (class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? class$org$mozilla$javascript$tools$debugger$TreeTableModel : (class$org$mozilla$javascript$tools$debugger$TreeTableModel = class$("org.mozilla.javascript.tools.debugger.TreeTableModel"))) ? -1 : super.editingRow;
   }

   public JTree getTree() {
      return this.tree;
   }

   public void setRowHeight(int var1) {
      super.setRowHeight(var1);
      if (this.tree != null && this.tree.getRowHeight() != var1) {
         this.tree.setRowHeight(this.getRowHeight());
      }

   }

   public void updateUI() {
      super.updateUI();
      if (this.tree != null) {
         this.tree.updateUI();
      }

      LookAndFeel.installColorsAndFont(this, "Tree.background", "Tree.foreground", "Tree.font");
   }

   public class TreeTableCellRenderer extends JTree implements TableCellRenderer {
      protected int visibleRow;

      public TreeTableCellRenderer(TreeModel var2) {
         super(var2);
      }

      public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
         if (var3) {
            this.setBackground(var1.getSelectionBackground());
         } else {
            this.setBackground(var1.getBackground());
         }

         this.visibleRow = var5;
         return this;
      }

      public void paint(Graphics var1) {
         var1.translate(0, -this.visibleRow * this.getRowHeight());
         super.paint(var1);
      }

      public void setBounds(int var1, int var2, int var3, int var4) {
         super.setBounds(var1, 0, var3, JTreeTable.this.getHeight());
      }

      public void setRowHeight(int var1) {
         if (var1 > 0) {
            super.setRowHeight(var1);
            if (JTreeTable.this != null && JTreeTable.this.getRowHeight() != var1) {
               JTreeTable.this.setRowHeight(this.getRowHeight());
            }
         }

      }

      public void updateUI() {
         super.updateUI();
         TreeCellRenderer var1 = this.getCellRenderer();
         if (var1 instanceof DefaultTreeCellRenderer) {
            DefaultTreeCellRenderer var2 = (DefaultTreeCellRenderer)var1;
            var2.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
            var2.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
         }

      }
   }

   public class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {
      public Component getTableCellEditorComponent(JTable var1, Object var2, boolean var3, int var4, int var5) {
         return JTreeTable.this.tree;
      }

      public boolean isCellEditable(EventObject var1) {
         if (var1 instanceof MouseEvent) {
            for(int var2 = JTreeTable.this.getColumnCount() - 1; var2 >= 0; --var2) {
               if (JTreeTable.this.getColumnClass(var2) == (JTreeTable.class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? JTreeTable.class$org$mozilla$javascript$tools$debugger$TreeTableModel : (JTreeTable.class$org$mozilla$javascript$tools$debugger$TreeTableModel = JTreeTable.class$("org.mozilla.javascript.tools.debugger.TreeTableModel")))) {
                  MouseEvent var3 = (MouseEvent)var1;
                  MouseEvent var4 = new MouseEvent(JTreeTable.this.tree, var3.getID(), var3.getWhen(), var3.getModifiers(), var3.getX() - JTreeTable.this.getCellRect(0, var2, true).x, var3.getY(), var3.getClickCount(), var3.isPopupTrigger());
                  JTreeTable.this.tree.dispatchEvent(var4);
                  break;
               }
            }
         }

         return false;
      }
   }

   class ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel {
      protected boolean updatingListSelectionModel;

      public ListToTreeSelectionModelWrapper() {
         this.getListSelectionModel().addListSelectionListener(this.createListSelectionListener());
      }

      protected ListSelectionListener createListSelectionListener() {
         return new ListSelectionHandler();
      }

      ListSelectionModel getListSelectionModel() {
         return super.listSelectionModel;
      }

      public void resetRowSelection() {
         if (!this.updatingListSelectionModel) {
            this.updatingListSelectionModel = true;

            try {
               super.resetRowSelection();
            } finally {
               this.updatingListSelectionModel = false;
            }
         }

      }

      protected void updateSelectedPathsFromSelectedRows() {
         if (!this.updatingListSelectionModel) {
            this.updatingListSelectionModel = true;

            try {
               int var3 = super.listSelectionModel.getMinSelectionIndex();
               int var4 = super.listSelectionModel.getMaxSelectionIndex();
               this.clearSelection();
               if (var3 != -1 && var4 != -1) {
                  for(int var5 = var3; var5 <= var4; ++var5) {
                     if (super.listSelectionModel.isSelectedIndex(var5)) {
                        TreePath var6 = JTreeTable.this.tree.getPathForRow(var5);
                        if (var6 != null) {
                           this.addSelectionPath(var6);
                        }
                     }
                  }
               }
            } finally {
               this.updatingListSelectionModel = false;
            }
         }

      }

      class ListSelectionHandler implements ListSelectionListener {
         public void valueChanged(ListSelectionEvent var1) {
            ListToTreeSelectionModelWrapper.this.updateSelectedPathsFromSelectedRows();
         }
      }
   }
}
