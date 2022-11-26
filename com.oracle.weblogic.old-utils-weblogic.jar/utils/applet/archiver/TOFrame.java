package utils.applet.archiver;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TOFrame extends Frame implements Runnable, ActionListener {
   Button goAway;

   public TOFrame(String name) {
      super(name);
   }

   public void run() {
      this.setLayout(new BorderLayout());
      TextArea t = new TextArea("", 40, 12, 0);
      TextOut o = new TextOut(t);
      this.setSize(400, 700);
      this.add("North", t);
      Panel p = new Panel();
      this.goAway = new Button("GO AWAY");
      this.goAway.addActionListener(this);
      p.add(this.goAway);
      this.add("South", p);
      System.setOut(o);
      System.setErr(o);
      this.setVisible(true);
      o.println("HelloWorld!");
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.goAway) {
         System.exit(0);
      }

   }
}
