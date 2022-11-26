package com.solarmetric.profile.gui;

import com.solarmetric.manage.SampleStatistic;
import java.text.DecimalFormat;
import javax.swing.table.AbstractTableModel;

public class SampleStatisticTableModel extends AbstractTableModel {
   private SampleStatistic _stat;
   private String[] columnNames = new String[]{"Name", "Value"};

   public SampleStatisticTableModel(SampleStatistic stat) {
      this._stat = stat;
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public int getRowCount() {
      return 6;
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(int row, int col) {
      if (this._stat == null && col == 1) {
         return "";
      } else {
         DecimalFormat digitsFormat = new DecimalFormat();
         digitsFormat.setMaximumFractionDigits(0);
         String ord = this._stat == null ? "" : this._stat.getOrdinateDescription();
         switch (row) {
            case 0:
               if (col == 0) {
                  return "Sum";
               }

               return digitsFormat.format(this._stat.getSampleSum()) + ord;
            case 1:
               if (col == 0) {
                  return "Average";
               }

               return digitsFormat.format(this._stat.getAveValue()) + ord;
            case 2:
               if (col == 0) {
                  return "Minimum";
               }

               return digitsFormat.format(this._stat.getMinValue()) + ord;
            case 3:
               if (col == 0) {
                  return "Maximum";
               }

               return digitsFormat.format(this._stat.getMaxValue()) + ord;
            case 4:
               if (col == 0) {
                  return "First";
               }

               return digitsFormat.format(this._stat.getFirstValue()) + ord;
            case 5:
               if (col == 0) {
                  return "Count";
               }

               return digitsFormat.format((long)this._stat.getSampleCount()) + " times";
            default:
               return "";
         }
      }
   }

   public Class getColumnClass(int col) {
      return String.class;
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }
}
