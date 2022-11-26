package org.mozilla.javascript.tools.debugger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

class EvalWindow extends JInternalFrame implements ActionListener {
   EvalTextArea evalTextArea;

   public EvalWindow(String var1, Main var2) {
      super(var1, true, false, true, true);
      this.evalTextArea = new EvalTextArea(var2);
      this.evalTextArea.setRows(24);
      this.evalTextArea.setColumns(80);
      JScrollPane var3 = new JScrollPane(this.evalTextArea);
      this.setContentPane(var3);
      this.pack();
      this.setVisible(true);
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      if (var2.equals("Cut")) {
         this.evalTextArea.cut();
      } else if (var2.equals("Copy")) {
         this.evalTextArea.copy();
      } else if (var2.equals("Paste")) {
         this.evalTextArea.paste();
      }

   }

   public void setEnabled(boolean var1) {
      super.setEnabled(var1);
      this.evalTextArea.setEnabled(var1);
   }
}
