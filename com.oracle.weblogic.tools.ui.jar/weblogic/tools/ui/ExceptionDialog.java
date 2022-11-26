package weblogic.tools.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import weblogic.utils.StackTraceUtils;

public class ExceptionDialog extends JDialog implements ActionListener, WindowListener, KeyListener {
   private static MarathonTextFormatter fmt = new MarathonTextFormatter();
   JLabel m_messageLabel;
   JButton ok;
   JButton details;
   ExArea ex;
   JScrollPane sp;

   public static void showExceptionDialog(Component component, String title, Throwable t, boolean showMsg) {
      String exname;
      if (showMsg) {
         exname = t.toString();
      } else {
         exname = t.getClass().getName();
      }

      JOptionPane.showMessageDialog(component, exname, title, 2);
   }

   public ExceptionDialog(Frame f, String title, Throwable t) {
      this(f, title, t, (String)null, false);
   }

   public ExceptionDialog(Frame f, String title, Throwable t, String img) {
      this(f, title, t, img, false);
   }

   public void show() {
      this.ok.requestFocus();
      super.show();
   }

   public ExceptionDialog(Frame f, String title, Throwable t, String img, boolean showMsg) {
      super(f, title, true);
      this.m_messageLabel = null;
      GridBagLayout gb = new GridBagLayout();
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      this.getContentPane().setLayout(gb);
      URL imgURL = getResourceURL("/com/sun/java/swing/plaf/windows/icons/Error.gif");
      String exname = null;
      if (showMsg) {
         exname = t.toString();
      } else {
         exname = t.getClass().getName();
      }

      if (imgURL != null) {
         this.m_messageLabel = new JLabel(exname, new ImageIcon(imgURL), 2);
         this.m_messageLabel.setIconTextGap(11);
      } else {
         this.m_messageLabel = new JLabel(exname);
      }

      gbc.gridy = 0;
      gbc.gridwidth = 2;
      gbc.anchor = 10;
      gbc.weightx = 1.0;
      gbc.fill = 2;
      gbc.anchor = 17;
      gb.setConstraints(this.m_messageLabel, gbc);
      this.getContentPane().add(this.m_messageLabel);
      ++gbc.gridy;
      gbc.gridwidth = 0;
      Component buttons = this.makeButtons();
      gb.setConstraints(buttons, gbc);
      this.getContentPane().add(buttons);
      ++gbc.gridy;
      gbc.anchor = 16;
      gbc.insets = new Insets(0, 0, 0, 0);
      gbc.fill = 1;
      gbc.weightx = gbc.weighty = 1.0;
      this.ex = new ExArea(StackTraceUtils.throwable2StackTrace(t));
      this.ex.setVisible(false);
      Border empty = new EmptyBorder(11, 11, 11, 11) {
         public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Color old = g.getColor();
            g.setColor(ExceptionDialog.this.getBackground());
            g.translate(x, y);
            g.fillRect(x, y, w, this.top);
            g.fillRect(x, y, this.left, h);
            g.fillRect(x, h - this.bottom, w, this.bottom);
            g.fillRect(w - this.right, y, this.right, h);
            g.setColor(old);
         }
      };
      Border bdr = new CompoundBorder(empty, new BevelBorder(1));
      final ExArea xa = this.ex;
      this.sp = new JScrollPane(this.ex) {
         public Dimension getPreferredSize() {
            Dimension d;
            if (xa.isVisible()) {
               JScrollBar sb = this.getHorizontalScrollBar();
               int sbh = sb.getPreferredSize().height;
               d = new Dimension(xa.W, xa.H + 2 * sbh);
            } else {
               d = new Dimension(xa.W, 0);
            }

            return d;
         }

         public Dimension getMaximumSize() {
            return this.getPreferredSize();
         }
      };
      this.sp.setBorder(bdr);
      this.getContentPane().add(this.sp);
      this.setResizable(false);
      this.addWindowListener(this);
      this.pack();
      int w = this.sp.getPreferredSize().width;
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension d = this.getPreferredSize();
      w = (screen.width - d.width) / 2;
      int h = Math.max((screen.height - d.height) / 2 - 100, 100);
      this.setLocation(new Point(w, h));
   }

   public void setMessage(String msg) {
      this.m_messageLabel.setText(msg);
   }

   private JComponent makeButtons() {
      JPanel ret = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(2, 2, 2, 2);
      gbc.anchor = 17;
      gbc.weightx = 1.0;
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.fill = 2;
      ret.add(new JLabel(""), gbc);
      ++gbc.gridx;
      gbc.anchor = 13;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.ok = new JButton(fmt.getContinue());
      this.ok.addKeyListener(this);
      this.ok.setOpaque(false);
      this.ok.addActionListener(this);
      ret.add(this.ok, gbc);
      ++gbc.gridx;
      this.details = new JButton(fmt.getShowDetails());
      this.details.setOpaque(false);
      this.details.addActionListener(this);
      ret.add(this.details, gbc);
      return ret;
   }

   public Dimension getPreferredSize() {
      Dimension d = super.getPreferredSize();
      int w = this.sp.getPreferredSize().width;
      if (!this.ex.isVisible()) {
         w += 6;
      }

      d.width = Math.max(d.width, w);
      return d;
   }

   public void actionPerformed(ActionEvent ev) {
      Object src = ev.getSource();
      if (src == this.ok) {
         this.setVisible(false);
         this.dispose();
      } else if (src == this.details) {
         if (!this.ex.isVisible()) {
            this.details.setLabel(fmt.getHideDetails());
            this.ex.setVisible(true);
         } else {
            this.details.setLabel(fmt.getShowDetails());
            this.ex.setVisible(false);
         }

         this.invalidate();
         this.pack();
      }

   }

   public void keyTyped(KeyEvent ev) {
      if (ev.getSource() == this.ok) {
         if (ev.getKeyCode() == 10 || ev.getKeyChar() == '\n' || ev.getKeyChar() == '\r') {
            this.setVisible(false);
            this.dispose();
         }
      }
   }

   public void keyPressed(KeyEvent ev) {
   }

   public void keyReleased(KeyEvent ev) {
   }

   public void windowOpened(WindowEvent e) {
   }

   public void windowClosing(WindowEvent e) {
      this.setVisible(false);
      this.dispose();
   }

   public void windowClosed(WindowEvent e) {
   }

   public void windowIconified(WindowEvent e) {
   }

   public void windowDeiconified(WindowEvent e) {
   }

   public void windowActivated(WindowEvent e) {
   }

   public void windowDeactivated(WindowEvent e) {
   }

   public static URL getResourceURL(String imgName) {
      URL u = ExceptionDialog.class.getResource(imgName);
      if (u == null) {
         u = ExceptionDialog.class.getResource("/weblogic/graphics/" + imgName);
      }

      return u;
   }

   public static void main(String[] a) throws Exception {
      Util.initLookAndFeel("win");
      String[] dat = new String[]{"Foo", "Bar", "Baz"};
      JFrame f = new JFrame("test");
      JList l = new JList();
      l.setListData(dat);
      f.getContentPane().add(l);
      f.setSize(300, 300);
      f.setVisible(true);

      try {
         deep(0, 20);
      } catch (Exception var6) {
         ExceptionDialog d = new ExceptionDialog(f, "Error", var6);
         d.show();
      }

   }

   private static void deep(int i, int lim) throws Exception {
      ++i;
      if (i < lim) {
         deep(i, lim);
      } else {
         String veryLong = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD";
         throw new Exception(veryLong);
      }
   }
}
