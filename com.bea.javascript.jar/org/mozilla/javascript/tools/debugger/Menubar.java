package org.mozilla.javascript.tools.debugger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class Menubar extends JMenuBar implements ActionListener {
   Main db;
   JMenu windowMenu;
   JCheckBoxMenuItem breakOnExceptions;

   Menubar(Main var1) {
      this.db = var1;
      String[] var2 = new String[]{"Open...", "Run...", "", "Exit"};
      String[] var3 = new String[]{"Open", "Load", "", "Exit"};
      char[] var4 = new char[]{'0', 'N', '\u0000', 'X'};
      int[] var5 = new int[]{79, 78, 0, 81};
      String[] var6 = new String[]{"Cut", "Copy", "Paste", "Go to function..."};
      char[] var7 = new char[]{'T', 'C', 'P', 'F'};
      String[] var8 = new String[]{"Break", "Go", "Step Into", "Step Over", "Step Out"};
      char[] var9 = new char[]{'B', 'G', 'I', 'O', 'T'};
      String[] var10 = new String[]{"Metal", "Windows", "Motif"};
      char[] var11 = new char[]{'M', 'W', 'F'};
      int[] var12 = new int[]{19, 116, 122, 118, 119, 0, 0};
      JMenu var13 = new JMenu("File");
      var13.setMnemonic('F');
      JMenu var14 = new JMenu("Edit");
      var14.setMnemonic('E');
      JMenu var15 = new JMenu("Platform");
      var15.setMnemonic('P');
      JMenu var16 = new JMenu("Debug");
      var16.setMnemonic('D');
      this.windowMenu = new JMenu("Window");
      this.windowMenu.setMnemonic('W');

      for(int var17 = 0; var17 < var2.length; ++var17) {
         if (var2[var17].length() == 0) {
            var13.addSeparator();
         } else {
            JMenuItem var18 = new JMenuItem(var2[var17], var4[var17]);
            var18.setActionCommand(var3[var17]);
            var18.addActionListener(this);
            var13.add(var18);
            if (var5[var17] != 0) {
               KeyStroke var19 = KeyStroke.getKeyStroke(var5[var17], 2);
               var18.setAccelerator(var19);
            }
         }
      }

      for(int var23 = 0; var23 < var6.length; ++var23) {
         JMenuItem var24 = new JMenuItem(var6[var23], var7[var23]);
         var24.addActionListener(this);
         var14.add(var24);
      }

      for(int var25 = 0; var25 < var10.length; ++var25) {
         JMenuItem var20 = new JMenuItem(var10[var25], var11[var25]);
         var20.addActionListener(this);
         var15.add(var20);
      }

      JMenuItem var21;
      for(int var26 = 0; var26 < var8.length; ++var26) {
         var21 = new JMenuItem(var8[var26], var9[var26]);
         var21.addActionListener(this);
         if (var12[var26] != 0) {
            KeyStroke var22 = KeyStroke.getKeyStroke(var12[var26], 0);
            var21.setAccelerator(var22);
         }

         if (var26 != 0) {
            var21.setEnabled(false);
         }

         var16.add(var21);
      }

      this.breakOnExceptions = new JCheckBoxMenuItem("Break on Exceptions");
      this.breakOnExceptions.setMnemonic('X');
      this.breakOnExceptions.addActionListener(this);
      this.breakOnExceptions.setSelected(false);
      var16.add(this.breakOnExceptions);
      this.add(var13);
      this.add(var14);
      this.add(var16);
      this.windowMenu.add(var21 = new JMenuItem("Cascade", 65));
      var21.addActionListener(this);
      this.windowMenu.add(var21 = new JMenuItem("Tile", 84));
      var21.addActionListener(this);
      this.windowMenu.addSeparator();
      this.windowMenu.add(var21 = new JMenuItem("Console", 67));
      var21.addActionListener(this);
      this.add(this.windowMenu);
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      String var3 = null;
      if (var2.equals("Metal")) {
         var3 = "javax.swing.plaf.metal.MetalLookAndFeel";
      } else if (var2.equals("Windows")) {
         var3 = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
      } else if (var2.equals("Motif")) {
         var3 = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
      } else {
         if (!var2.equals("Break on Exceptions")) {
            this.db.actionPerformed(var1);
            return;
         }

         this.db.setBreakOnExceptions(this.breakOnExceptions.isSelected());
      }

      try {
         UIManager.setLookAndFeel(var3);
         SwingUtilities.updateComponentTreeUI(this.db);
         SwingUtilities.updateComponentTreeUI(this.db.dlg);
      } catch (Exception var4) {
      }

   }

   public void addFile(String var1) {
      int var2 = this.windowMenu.getItemCount();
      if (var2 == 4) {
         this.windowMenu.addSeparator();
         ++var2;
      }

      JMenuItem var4 = this.windowMenu.getItem(var2 - 1);
      boolean var5 = false;
      int var6 = 5;
      if (var4 != null && var4.getText().equals("More Windows...")) {
         var5 = true;
         ++var6;
      }

      JMenuItem var3;
      if (!var5 && var2 - 4 == 5) {
         this.windowMenu.add(var3 = new JMenuItem("More Windows...", 77));
         var3.setActionCommand("More Windows...");
         var3.addActionListener(this);
      } else if (var2 - 4 <= var6) {
         if (var5) {
            --var2;
            this.windowMenu.remove(var4);
         }

         File var7 = new File(var1);
         this.windowMenu.add(var3 = new JMenuItem((char)(48 + (var2 - 4)) + " " + var7.getName(), 48 + (var2 - 4)));
         if (var5) {
            this.windowMenu.add(var4);
         }

         var3.setActionCommand(var1);
         var3.addActionListener(this);
      }
   }

   JMenu getDebugMenu() {
      return this.getMenu(2);
   }
}
