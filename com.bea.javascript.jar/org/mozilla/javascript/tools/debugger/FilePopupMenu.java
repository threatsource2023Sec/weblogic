package org.mozilla.javascript.tools.debugger;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

class FilePopupMenu extends JPopupMenu {
   FileTextArea w;
   int x;
   int y;

   FilePopupMenu(FileTextArea var1) {
      this.w = var1;
      JMenuItem var2;
      this.add(var2 = new JMenuItem("Set Breakpoint"));
      var2.addActionListener(var1);
      this.add(var2 = new JMenuItem("Clear Breakpoint"));
      var2.addActionListener(var1);
      this.add(var2 = new JMenuItem("Run"));
      var2.addActionListener(var1);
   }

   void show(JComponent var1, int var2, int var3) {
      this.x = var2;
      this.y = var3;
      super.show(var1, var2, var3);
   }
}
