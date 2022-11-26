package weblogic.tools.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

class GUIPrintFrame extends JFrame implements Runnable, ActionListener {
   JButton goAway;

   public GUIPrintFrame(String name) {
      super(name);
   }

   public void run() {
      this.getContentPane().setLayout(new BorderLayout());
      JTextArea t = new JTextArea("", 40, 12);
      t.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new BevelBorder(1, Color.lightGray, Color.darkGray)));
      GUIPrintStream o = new GUIPrintStream(t);
      PrintStream ps = new PrintStream(o);
      this.setSize(400, 700);
      this.getContentPane().add("North", t);
      JPanel p = new JPanel();
      this.goAway = new JButton("GO AWAY");
      this.goAway.addActionListener(this);
      p.add(this.goAway);
      this.getContentPane().add("South", p);
      System.setOut(ps);
      System.setErr(ps);
      this.setVisible(true);
      o.println("HelloWorld!");
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.goAway) {
         System.exit(0);
      }

   }
}
