package org.mozilla.javascript.tools.debugger;

import javax.swing.JTable;

class Evaluator extends JTable {
   MyTableModel tableModel = (MyTableModel)this.getModel();

   Evaluator(Main var1) {
      super(new MyTableModel(var1));
   }
}
