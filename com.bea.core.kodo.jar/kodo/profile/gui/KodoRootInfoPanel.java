package kodo.profile.gui;

import com.solarmetric.ide.ui.HeaderFooterPanel;
import com.solarmetric.ide.ui.swing.XLabel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XSplitPane;
import com.solarmetric.ide.ui.swing.XTextArea;
import com.solarmetric.ide.ui.swing.XTree;
import com.solarmetric.ide.ui.treetable.JTreeTable;
import com.solarmetric.profile.EventInfo;
import com.solarmetric.profile.gui.EventInfoPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import kodo.profile.KodoRootInfo;
import org.apache.openjpa.lib.util.Localizer;

public class KodoRootInfoPanel extends EventInfoPanel {
   private static Localizer _loc = Localizer.forPackage(KodoRootInfoPanel.class);
   JTextArea _infoArea;
   HeaderFooterPanel _header;
   JTreeTable _treetable;
   FetchDataTreeTableModel _model;
   JSplitPane _primarySplitPane;
   JSplitPane _secondarySplitPane;
   CollectionStatsTreeModel _collectionModel;
   JTree _collectionTree;

   public KodoRootInfoPanel(KodoRootInfo info) {
      this();
      this.setInfo(info);
   }

   public KodoRootInfoPanel() {
      this.setLayout(new GridLayout(1, 1));
      this._model = null;
      this._treetable = new JTreeTable(new EmptyFetchDataTreeTableModel());
      this.setupColumns();
      this._infoArea = new XTextArea();
      this._infoArea.setLineWrap(true);
      this._infoArea.setWrapStyleWord(true);
      this._collectionTree = new XTree();
      this._collectionModel = null;
      this._secondarySplitPane = new XSplitPane(0, new XScrollPane(this._treetable), new XScrollPane(this._collectionTree));
      this._secondarySplitPane.setDividerLocation(150);
      this._secondarySplitPane.setDividerSize(3);
      this._primarySplitPane = new XSplitPane(0, new XScrollPane(this._infoArea), this._secondarySplitPane);
      this._primarySplitPane.setDividerLocation(50);
      this._primarySplitPane.setDividerSize(3);
      this._header = new HeaderFooterPanel(this._primarySplitPane, "Node: <not selected>", (String)null);
      this.add(this._header);
      ToolTipManager.sharedInstance().registerComponent(this);
   }

   public void setInfo(EventInfo info) {
      this._infoArea.setText(info.getDescription());
      this._model = new FetchDataTreeTableModel((KodoRootInfo)info);
      this._treetable = new JTreeTable(this._model);
      this._collectionModel = new CollectionStatsTreeModel((KodoRootInfo)info);
      this._collectionTree = new XTree(this._collectionModel);
      this.setupColumns();
      this.expandTree(this._treetable.getTree());
      this._secondarySplitPane.setTopComponent(new XScrollPane(this._treetable));
      this._secondarySplitPane.setBottomComponent(new XScrollPane(this._collectionTree));
      this._header.setHeaderLabel("Node: " + info.getName());
   }

   private void expandTree(JTree tree) {
      for(int i = 0; i < tree.getRowCount(); ++i) {
         tree.expandRow(i);
      }

   }

   public void setupColumns() {
      TableColumn col = this._treetable.getColumnModel().getColumn(0);
      col.setPreferredWidth(220);
      col = this._treetable.getColumnModel().getColumn(1);
      col.setPreferredWidth(20);
      col = this._treetable.getColumnModel().getColumn(2);
      col.setPreferredWidth(80);
      col.setCellRenderer(new FetchDataRenderer());
      col = this._treetable.getColumnModel().getColumn(3);
      col.setPreferredWidth(80);
      col.setCellRenderer(new FetchDataRenderer());
   }

   public void update() {
      if (this._model != null) {
         this._model.fireUpdate();
         this.expandTree(this._treetable.getTree());
         this._collectionModel.fireUpdate();
         this.expandTree(this._collectionTree);
      }

   }

   public class FetchDataRenderer extends XLabel implements TableCellRenderer {
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
         String strVal = value.toString();
         int marking = KodoRootInfoPanel.this._model.getMarkingAt(KodoRootInfoPanel.this._treetable.getTree().getPathForRow(row).getLastPathComponent(), col);
         if (marking == 1 && col == 2) {
            this.setForeground(Color.red);
            this.setToolTipText(KodoRootInfoPanel._loc.get("overloaded").getMessage());
         } else if (marking == 2 && col == 3) {
            this.setForeground(Color.red);
            this.setToolTipText(KodoRootInfoPanel._loc.get("underloaded").getMessage());
         } else {
            this.setForeground(Color.black);
            this.setToolTipText("");
         }

         this.setText(strVal);
         return this;
      }
   }
}
