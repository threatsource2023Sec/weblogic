package org.mozilla.javascript.tools.debugger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.PrintStream;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.mozilla.javascript.tools.shell.ConsoleTextArea;

class JSInternalConsole extends JInternalFrame implements ActionListener {
   ConsoleTextArea consoleTextArea = new ConsoleTextArea((String[])null);

   public JSInternalConsole(String var1) {
      super(var1, true, false, true, true);
      this.consoleTextArea.setRows(24);
      this.consoleTextArea.setColumns(80);
      JScrollPane var2 = new JScrollPane(this.consoleTextArea);
      this.setContentPane(var2);
      this.pack();
      this.addInternalFrameListener(new InternalFrameAdapter() {
         public void internalFrameActivated(InternalFrameEvent var1) {
            if (JSInternalConsole.this.consoleTextArea.hasFocus()) {
               JSInternalConsole.this.consoleTextArea.getCaret().setVisible(false);
               JSInternalConsole.this.consoleTextArea.getCaret().setVisible(true);
            }

         }
      });
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      if (var2.equals("Cut")) {
         this.consoleTextArea.cut();
      } else if (var2.equals("Copy")) {
         this.consoleTextArea.copy();
      } else if (var2.equals("Paste")) {
         this.consoleTextArea.paste();
      }

   }

   public PrintStream getErr() {
      return this.consoleTextArea.getErr();
   }

   public InputStream getIn() {
      return this.consoleTextArea.getIn();
   }

   public PrintStream getOut() {
      return this.consoleTextArea.getOut();
   }
}
