package org.mozilla.javascript.tools.shell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class JSConsole extends JFrame implements ActionListener {
   private File CWD;
   private JFileChooser dlg;
   private ConsoleTextArea consoleTextArea;

   public JSConsole(String[] var1) {
      super("Rhino JavaScript Console");
      JMenuBar var2 = new JMenuBar();
      this.createFileChooser();
      String[] var3 = new String[]{"Load...", "Exit"};
      String[] var4 = new String[]{"Load", "Exit"};
      char[] var5 = new char[]{'L', 'X'};
      String[] var6 = new String[]{"Cut", "Copy", "Paste"};
      char[] var7 = new char[]{'T', 'C', 'P'};
      String[] var8 = new String[]{"Metal", "Windows", "Motif"};
      boolean[] var9 = new boolean[]{true, false, false};
      JMenu var10 = new JMenu("File");
      var10.setMnemonic('F');
      JMenu var11 = new JMenu("Edit");
      var11.setMnemonic('E');
      JMenu var12 = new JMenu("Platform");
      var12.setMnemonic('P');

      for(int var13 = 0; var13 < var3.length; ++var13) {
         JMenuItem var14 = new JMenuItem(var3[var13], var5[var13]);
         var14.setActionCommand(var4[var13]);
         var14.addActionListener(this);
         var10.add(var14);
      }

      for(int var18 = 0; var18 < var6.length; ++var18) {
         JMenuItem var15 = new JMenuItem(var6[var18], var7[var18]);
         var15.addActionListener(this);
         var11.add(var15);
      }

      ButtonGroup var19 = new ButtonGroup();

      for(int var16 = 0; var16 < var8.length; ++var16) {
         JRadioButtonMenuItem var17 = new JRadioButtonMenuItem(var8[var16], var9[var16]);
         var19.add(var17);
         var17.addActionListener(this);
         var12.add(var17);
      }

      var2.add(var10);
      var2.add(var11);
      var2.add(var12);
      this.setJMenuBar(var2);
      this.consoleTextArea = new ConsoleTextArea(var1);
      JScrollPane var20 = new JScrollPane(this.consoleTextArea);
      this.setContentPane(var20);
      this.consoleTextArea.setRows(24);
      this.consoleTextArea.setColumns(80);
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            System.exit(0);
         }
      });
      this.pack();
      this.setVisible(true);
      System.setIn(this.consoleTextArea.getIn());
      System.setOut(this.consoleTextArea.getOut());
      System.setErr(this.consoleTextArea.getErr());
      Main.exec(var1);
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      String var3 = null;
      if (var2.equals("Load")) {
         String var4 = this.chooseFile();
         if (var4 != null) {
            var4 = var4.replace('\\', '/');
            this.consoleTextArea.eval("load(\"" + var4 + "\");");
         }
      } else if (var2.equals("Exit")) {
         System.exit(0);
      } else if (var2.equals("Cut")) {
         this.consoleTextArea.cut();
      } else if (var2.equals("Copy")) {
         this.consoleTextArea.copy();
      } else if (var2.equals("Paste")) {
         this.consoleTextArea.paste();
      } else {
         if (var2.equals("Metal")) {
            var3 = "javax.swing.plaf.metal.MetalLookAndFeel";
         } else if (var2.equals("Windows")) {
            var3 = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
         } else if (var2.equals("Motif")) {
            var3 = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
         }

         if (var3 != null) {
            try {
               UIManager.setLookAndFeel(var3);
               SwingUtilities.updateComponentTreeUI(this);
               this.consoleTextArea.postUpdateUI();
               this.createFileChooser();
            } catch (Exception var5) {
               JOptionPane.showMessageDialog(this, var5.getMessage(), "Platform", 0);
            }
         }
      }

   }

   public String chooseFile() {
      if (this.CWD == null) {
         String var1 = System.getProperty("user.dir");
         if (var1 != null) {
            this.CWD = new File(var1);
         }
      }

      if (this.CWD != null) {
         this.dlg.setCurrentDirectory(this.CWD);
      }

      this.dlg.setDialogTitle("Select a file to load");
      int var3 = this.dlg.showOpenDialog(this);
      if (var3 == 0) {
         String var2 = this.dlg.getSelectedFile().getPath();
         this.CWD = new File(this.dlg.getSelectedFile().getParent());
         return var2;
      } else {
         return null;
      }
   }

   public void createFileChooser() {
      this.dlg = new JFileChooser();
      FileFilter var1 = new FileFilter() {
         public boolean accept(File var1) {
            if (var1.isDirectory()) {
               return true;
            } else {
               String var2 = var1.getName();
               int var3 = var2.lastIndexOf(46);
               if (var3 > 0 && var3 < var2.length() - 1) {
                  String var4 = var2.substring(var3 + 1).toLowerCase();
                  if (var4.equals("js")) {
                     return true;
                  }
               }

               return false;
            }
         }

         public String getDescription() {
            return "JavaScript Files (*.js)";
         }
      };
      this.dlg.addChoosableFileFilter(var1);
   }

   public static void main(String[] var0) {
      new JSConsole(var0);
   }
}
