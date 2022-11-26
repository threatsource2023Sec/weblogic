package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.HeaderFooterPanel;
import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XTable;
import com.solarmetric.manage.SampleStatistic;
import java.awt.GridLayout;
import javax.swing.JTable;

public class StatisticPanel extends XPanel {
   private JTable _table;
   private HeaderFooterPanel _header;
   private SampleStatisticTableModel _model;

   public StatisticPanel() {
      this.setLayout(new GridLayout(1, 1));
      this._table = new XTable();
      this._header = new HeaderFooterPanel(new XScrollPane(this._table), "Statistics", (String)null);
      this.add(this._header);
   }

   public void setStatistic(SampleStatistic stat) {
      this._model = new SampleStatisticTableModel(stat);
      this._table.setModel(this._model);
   }

   public void update() {
      this._model.fireTableDataChanged();
   }
}
