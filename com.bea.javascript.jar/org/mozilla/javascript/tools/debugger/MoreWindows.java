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
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class MoreWindows extends JDialog implements ActionListener {
   private String value = null;
   private JList list;
   Hashtable fileWindows;
   JButton setButton;
   JButton refreshButton;
   JButton cancelButton;

   MoreWindows(JFrame var1, Hashtable var2, String var3, String var4) {
      super(var1, var3, true);
      this.fileWindows = var2;
      this.cancelButton = new JButton("Cancel");
      this.setButton = new JButton("Select");
      this.cancelButton.addActionListener(this);
      this.setButton.addActionListener(this);
      this.getRootPane().setDefaultButton(this.setButton);
      this.list = new JList(new DefaultListModel());
      DefaultListModel var5 = (DefaultListModel)this.list.getModel();
      var5.clear();
      Enumeration var6 = var2.keys();

      while(var6.hasMoreElements()) {
         String var7 = var6.nextElement().toString();
         var5.addElement(var7);
      }

      this.list.setSelectedIndex(0);
      this.setButton.setEnabled(true);
      this.list.setSelectionMode(1);
      this.list.addMouseListener(new MouseHandler());
      JScrollPane var12 = new JScrollPane(this.list);
      var12.setPreferredSize(new Dimension(320, 240));
      var12.setMinimumSize(new Dimension(250, 80));
      var12.setAlignmentX(0.0F);
      JPanel var8 = new JPanel();
      var8.setLayout(new BoxLayout(var8, 1));
      JLabel var9 = new JLabel(var4);
      var9.setLabelFor(this.list);
      var8.add(var9);
      var8.add(Box.createRigidArea(new Dimension(0, 5)));
      var8.add(var12);
      var8.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel var10 = new JPanel();
      var10.setLayout(new BoxLayout(var10, 0));
      var10.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
      var10.add(Box.createHorizontalGlue());
      var10.add(this.cancelButton);
      var10.add(Box.createRigidArea(new Dimension(10, 0)));
      var10.add(this.setButton);
      Container var11 = this.getContentPane();
      var11.add(var8, "Center");
      var11.add(var10, "South");
      this.pack();
      this.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            int var2 = var1.getKeyCode();
            if (var2 == 27) {
               var1.consume();
               MoreWindows.this.value = null;
               MoreWindows.this.setVisible(false);
            }

         }
      });
   }

   // $FF: synthetic method
   static String access$0(MoreWindows var0) {
      return var0.value;
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      if (var2.equals("Cancel")) {
         this.setVisible(false);
         this.value = null;
      } else if (var2.equals("Select")) {
         this.value = (String)this.list.getSelectedValue();
         this.setVisible(false);
         JInternalFrame var3 = (JInternalFrame)this.fileWindows.get(this.value);
         if (var3 != null) {
            try {
               var3.show();
               var3.setSelected(true);
            } catch (Exception var4) {
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
            MoreWindows.this.setButton.doClick();
         }

      }
   }
}
