package utils.applet.archiver;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Enumeration;

class AttsDialog extends Dialog implements ActionListener, WindowListener, Runnable {
   TextArea tf;
   AppletFrame af;
   Button ok;

   public AttsDialog(AppletFrame f) {
      super(f, "Attributes", false);
      this.af = f;
      GridBagLayout gb = new GridBagLayout();
      GridBagConstraints gbc = new GridBagConstraints();
      this.setLayout(gb);
      gbc.insets = new Insets(5, 5, 5, 5);
      this.tf = new TextArea("", 15, 15, 0);
      this.tf.setEditable(false);

      char q;
      Object k;
      String v;
      for(Enumeration e = f.atts.keys(); e.hasMoreElements(); this.tf.appendText(k.toString() + '=' + q + v + q + '\n')) {
         q = '"';
         k = e.nextElement();
         v = f.atts.get(k).toString();
         if (v.indexOf(q) != -1) {
            q = '\'';
         }
      }

      gbc.weightx = gbc.weighty = 1.0;
      gbc.fill = 1;
      gbc.gridwidth = 0;
      gbc.gridheight = -1;
      gb.setConstraints(this.tf, gbc);
      this.add(this.tf);
      ++gbc.gridy;
      gbc.gridheight = 0;
      gbc.fill = 0;
      gbc.weightx = gbc.weighty = 0.0;
      this.ok = new Button("OK");
      this.ok.addActionListener(this);
      gb.setConstraints(this.ok, gbc);
      this.add(this.ok);
      this.setSize(400, 400);
      this.addWindowListener(this);
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension sz = this.getSize();
      Point p = new Point(0, 0);
      p.x = (screen.width - sz.width) / 2;
      p.y = (screen.height - sz.height) / 2;
      this.setLocation(p);
   }

   public void run() {
      this.setVisible(true);
   }

   public void actionPerformed(ActionEvent ev) {
      if (ev.getSource() == this.ok) {
         this.setVisible(false);
         this.dispose();
      }

   }

   public void windowActivated(WindowEvent e) {
   }

   public void windowClosed(WindowEvent e) {
   }

   public void windowClosing(WindowEvent e) {
      this.setVisible(false);
      this.dispose();
   }

   public void windowDeactivated(WindowEvent e) {
   }

   public void windowDeiconified(WindowEvent e) {
   }

   public void windowIconified(WindowEvent e) {
   }

   public void windowOpened(WindowEvent e) {
   }
}
