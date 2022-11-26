package org.mozilla.javascript.tools.debugger;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import org.mozilla.javascript.Scriptable;

class FileWindow extends JInternalFrame implements ActionListener {
   Main db;
   FileTextArea textArea;
   FileHeader fileHeader;
   JScrollPane p;
   int currentPos;
   Hashtable breakpoints;
   String url;
   JLabel statusBar;

   FileWindow(Main var1, String var2, String var3) {
      super((new File(var2)).getName(), true, true, true, true);
      this.db = var1;
      this.breakpoints = (Hashtable)var1.breakpointsMap.get(var2);
      if (this.breakpoints == null) {
         this.breakpoints = new Hashtable();
         var1.breakpointsMap.put(var2, this.breakpoints);
      }

      this.setUrl(var2);
      this.currentPos = -1;
      this.textArea = new FileTextArea(this);
      this.textArea.setRows(24);
      this.textArea.setColumns(80);
      this.p = new JScrollPane();
      this.fileHeader = new FileHeader(this);
      this.p.setViewportView(this.textArea);
      this.p.setRowHeaderView(this.fileHeader);
      this.setContentPane(this.p);
      this.pack();
      this.setText(var3);
      this.textArea.select(0);
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      if (!var2.equals("Cut")) {
         if (var2.equals("Copy")) {
            this.textArea.copy();
         } else {
            var2.equals("Paste");
         }
      }

   }

   void clearBreakPoint(int var1) {
      this.db.clearBreakPoint(this.url, var1);
      int var2 = this.getPosition(var1 - 1);
      Integer var3 = new Integer(var2);
      if (this.breakpoints.get(var3) != null) {
         this.breakpoints.remove(var3);
         this.fileHeader.repaint();
      }

   }

   public void dispose() {
      Enumeration var1 = this.breakpoints.keys();

      while(var1.hasMoreElements()) {
         Integer var2 = (Integer)this.breakpoints.get(var1.nextElement());
         this.db.clearBreakPoint(this.url, var2);
      }

      this.db.removeWindow(this);
      super.dispose();
   }

   public int getPosition(int var1) {
      int var2 = -1;

      try {
         var2 = this.textArea.getLineStartOffset(var1);
      } catch (BadLocationException var3) {
      }

      return var2;
   }

   public String getUrl() {
      return this.url;
   }

   boolean isBreakPoint(int var1) {
      int var2 = this.getPosition(var1 - 1);
      return this.breakpoints.get(new Integer(var2)) != null;
   }

   void load() {
      Scriptable var1 = this.db.getScope();
      if (var1 == null) {
         MessageDialogWrapper.showMessageDialog(this.db, "Can't load scripts: no scope available", "Run", 0);
      } else {
         String var2 = this.url;
         if (var2 != null) {
            (new Thread(new LoadFile(this.db, var1, var2))).start();
         }
      }

   }

   void runToCursor(ActionEvent var1) {
      try {
         this.db.runToCursor(this.url, this.textArea.getLineOfOffset(this.textArea.getCaretPosition()) + 1, var1);
      } catch (BadLocationException var2) {
      }

   }

   void select(int var1, int var2) {
      int var3 = this.textArea.getDocument().getLength();
      this.textArea.select(var3, var3);
      this.textArea.select(var1, var2);
   }

   void setBreakPoint(int var1) {
      int var2 = this.db.setBreakPoint(this.url, var1);
      if (var2 != -1) {
         int var3 = this.getPosition(var2 - 1);
         this.breakpoints.put(new Integer(var3), new Integer(var1));
         this.fileHeader.repaint();
      }

   }

   void setPosition(int var1) {
      this.textArea.select(var1);
      this.currentPos = var1;
      this.fileHeader.repaint();
   }

   void setText(String var1) {
      if (!this.textArea.getText().equals(var1)) {
         this.textArea.setText(var1);
         int var2 = 0;
         if (this.currentPos != -1) {
            var2 = this.currentPos;
         }

         this.textArea.select(var2);
      }

      Enumeration var5 = this.breakpoints.keys();

      while(var5.hasMoreElements()) {
         Object var3 = var5.nextElement();
         Integer var4 = (Integer)this.breakpoints.get(var3);
         if (this.db.setBreakPoint(this.url, var4) == -1) {
            this.breakpoints.remove(var3);
         }
      }

      this.fileHeader.update();
      this.fileHeader.repaint();
   }

   public void setUrl(String var1) {
      Component var2 = this.getComponent(1);
      if (var2 != null && var2 instanceof JComponent) {
         ((JComponent)var2).setToolTipText(var1);
      }

      this.url = var1;
   }

   void toggleBreakPoint(int var1) {
      int var2 = this.getPosition(var1 - 1);
      Integer var3 = new Integer(var2);
      if (this.breakpoints.get(var3) == null) {
         this.setBreakPoint(var1);
      } else {
         this.clearBreakPoint(var1);
      }

   }
}
