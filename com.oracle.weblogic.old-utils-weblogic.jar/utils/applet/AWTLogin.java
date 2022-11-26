package utils.applet;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import weblogic.utils.encoders.BASE64Encoder;

public class AWTLogin extends Dialog {
   public boolean returnValue;
   private TextField user;
   private TextField password;

   public static String showLoginDialog(Frame parent, String msg) {
      AWTLogin dialog = new AWTLogin(parent, "Login", msg);
      dialog.show();
      return dialog.getAuthorization();
   }

   public AWTLogin(Frame parent, String title, String msg) {
      this(parent, title, msg, true);
   }

   public AWTLogin(Frame parent, String title, String msg, boolean modal) {
      super(parent, title, modal);
      this.returnValue = false;
      this.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(3, 3, 3, 3);
      gbc.gridx = gbc.gridy = 0;
      gbc.anchor = 10;
      gbc.weightx = 1.0;
      gbc.gridwidth = 0;
      this.add(new Label(msg), gbc);
      ++gbc.gridy;
      Label userLabel = new Label("User name");
      gbc.gridwidth = 1;
      this.add(userLabel, gbc);
      this.user = new TextField("", 15);
      ++gbc.gridx;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.add(this.user, gbc);
      gbc.fill = 0;
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.add(new Label(""), gbc);
      gbc.weightx = 0.0;
      ++gbc.gridy;
      gbc.gridx = 0;
      Label passwordLabel = new Label("Password");
      this.add(passwordLabel, gbc);
      ++gbc.gridx;
      this.password = new TextField("", 15);
      this.password.setEchoChar('*');
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.add(this.password, gbc);
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.add(new Label(), gbc);
      ++gbc.gridy;
      gbc.weighty = 1.0;
      this.add(new Label(""), gbc);
      gbc.weightx = 0.0;
      gbc.weighty = 0.0;
      ++gbc.gridy;
      gbc.gridx = 0;
      Button ok = new Button("Ok");
      Button cancel = new Button("Cancel");
      Panel buttonPanel = new Panel(new GridLayout(1, 2, 3, 3));
      buttonPanel.add(ok);
      buttonPanel.add(cancel);
      Panel centerPanel = new Panel();
      centerPanel.add(buttonPanel);
      gbc.weightx = 1.0;
      gbc.anchor = 10;
      gbc.gridwidth = 0;
      this.add(centerPanel, gbc);
      gbc.weightx = 0.0;
      this.pack();
      centerOnWindow(this, parent);
      ok.addActionListener(new CloseButtonListener(this, true));
      cancel.addActionListener(new CloseButtonListener(this, false));
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent we) {
            AWTLogin.this.returnValue = false;
            AWTLogin.this.setVisible(false);
         }
      });
   }

   public static void frontAndCenter(Window w) {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension sz = w.getSize();
      int xt = (screen.width - sz.width) / 2;
      int yt = (screen.height - sz.height) / 2;
      w.setLocation(new Point(xt, yt));
   }

   public static void centerOnWindow(Window A, Window B) {
      if (!B.isVisible()) {
         frontAndCenter(A);
      } else {
         Dimension as = A.getSize();
         Dimension bs = B.getSize();
         Point bpos = B.getLocation();
         if (bs.width - as.width < 20 && bs.height - as.height < 20) {
            bpos.x += 20;
         } else {
            bpos.x += (bs.width - as.width) / 2;
         }

         bpos.y += (bs.height - as.height) / 2;
         bpos.x = Math.max(bpos.x, 5);
         bpos.y = Math.max(bpos.y, 5);
         A.setLocation(bpos);
      }
   }

   public String getAuthorization() {
      if (!this.returnValue) {
         return null;
      } else if (this.user.getText() != null && this.user.getText().length() != 0 && this.password.getText() != null && this.password.getText().length() != 0) {
         BASE64Encoder encoder = new BASE64Encoder();
         return encoder.encodeBuffer((this.user.getText() + ":" + this.password.getText()).getBytes());
      } else {
         return null;
      }
   }

   class CloseButtonListener implements ActionListener {
      AWTLogin dialog;
      boolean value;

      public CloseButtonListener(AWTLogin dialog, boolean value) {
         this.dialog = dialog;
         this.value = value;
      }

      public void actionPerformed(ActionEvent ae) {
         this.dialog.returnValue = this.value;
         this.dialog.setVisible(false);
      }
   }
}
