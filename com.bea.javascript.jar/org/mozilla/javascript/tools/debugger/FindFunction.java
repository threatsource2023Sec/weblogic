package org.mozilla.javascript.tools.debugger;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.mozilla.javascript.debug.DebuggableScript;

class FindFunction extends JDialog implements ActionListener {
   private String value = null;
   private JList list;
   Hashtable functionMap;
   Main db;
   JButton setButton;
   JButton refreshButton;
   JButton cancelButton;

   FindFunction(Main var1, Hashtable var2, String var3, String var4) {
      super(var1, var3, true);
      this.functionMap = var2;
      this.db = var1;
      this.cancelButton = new JButton("Cancel");
      this.setButton = new JButton("Select");
      this.cancelButton.addActionListener(this);
      this.setButton.addActionListener(this);
      this.getRootPane().setDefaultButton(this.setButton);
      this.list = new JList(new DefaultListModel());
      DefaultListModel var5 = (DefaultListModel)this.list.getModel();
      var5.clear();
      Enumeration var6 = var2.keys();
      String[] var7 = new String[var2.size()];

      int var8;
      for(var8 = 0; var6.hasMoreElements(); var7[var8++] = var6.nextElement().toString()) {
      }

      Arrays.sort(var7);

      for(var8 = 0; var8 < var7.length; ++var8) {
         var5.addElement(var7[var8]);
      }

      this.list.setSelectedIndex(0);
      this.setButton.setEnabled(var7.length > 0);
      this.list.setSelectionMode(1);
      this.list.addMouseListener(new MouseHandler());
      JScrollPane var9 = new JScrollPane(this.list);
      var9.setPreferredSize(new Dimension(320, 240));
      var9.setMinimumSize(new Dimension(250, 80));
      var9.setAlignmentX(0.0F);
      JPanel var10 = new JPanel();
      var10.setLayout(new BoxLayout(var10, 1));
      JLabel var11 = new JLabel(var4);
      var11.setLabelFor(this.list);
      var10.add(var11);
      var10.add(Box.createRigidArea(new Dimension(0, 5)));
      var10.add(var9);
      var10.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel var12 = new JPanel();
      var12.setLayout(new BoxLayout(var12, 0));
      var12.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
      var12.add(Box.createHorizontalGlue());
      var12.add(this.cancelButton);
      var12.add(Box.createRigidArea(new Dimension(10, 0)));
      var12.add(this.setButton);
      Container var13 = this.getContentPane();
      var13.add(var10, "Center");
      var13.add(var12, "South");
      this.pack();
      this.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            int var2 = var1.getKeyCode();
            if (var2 == 27) {
               var1.consume();
               FindFunction.this.value = null;
               FindFunction.this.setVisible(false);
            }

         }
      });
   }

   // $FF: synthetic method
   static String access$0(FindFunction var0) {
      return var0.value;
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      if (var2.equals("Cancel")) {
         this.setVisible(false);
         this.value = null;
      } else if (var2.equals("Select")) {
         if (this.list.getSelectedIndex() < 0) {
            return;
         }

         try {
            this.value = (String)this.list.getSelectedValue();
         } catch (ArrayIndexOutOfBoundsException var13) {
            return;
         }

         this.setVisible(false);
         Main.SourceEntry var3 = (Main.SourceEntry)this.functionMap.get(this.value);
         DebuggableScript var4 = var3.fnOrScript;
         if (var4 != null) {
            String var5 = var4.getSourceName();
            int[] var6 = var4.getLineNumbers();
            int var7 = -1;

            for(int var8 = 0; var8 != var6.length; ++var8) {
               if (var7 == -1) {
                  var7 = var6[var8];
               } else if (var6[var8] < var7) {
                  var7 = var6[var8];
               }
            }

            FileWindow var9 = this.db.getFileWindow(var5);
            if (var9 == null) {
               (new CreateFileWindow(this.db, var5, var3.source.toString(), var7)).run();
               var9 = this.db.getFileWindow(var5);
               var9.setPosition(-1);
            }

            int var10 = var9.getPosition(var7 - 1);
            int var11 = var9.getPosition(var7) - 1;
            var9.textArea.select(var10);
            var9.textArea.setCaretPosition(var10);
            var9.textArea.moveCaretPosition(var11);

            try {
               var9.show();
               this.db.requestFocus();
               var9.requestFocus();
               var9.textArea.requestFocus();
            } catch (Exception var12) {
            }
         }
      }

   }

   private void setValue(String var1) {
      this.value = var1;
      this.list.setSelectedValue(this.value, true);
   }

   public String showDialog(Component var1) {
      this.value = null;
      this.setLocationRelativeTo(var1);
      this.setVisible(true);
      return this.value;
   }

   class MouseHandler extends MouseAdapter {
      public void mouseClicked(MouseEvent var1) {
         if (var1.getClickCount() == 2) {
            FindFunction.this.setButton.doClick();
         }

      }
   }
}
