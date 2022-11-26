package weblogic.tools.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class NumberBox extends JPanel implements ActionListener, KeyListener, FocusListener, InputMethodListener {
   public static final String PROPERTY_VALUE = "Value";
   JTextField tf;
   ArrowButton up;
   ArrowButton down;
   private int min;
   private int max;
   private int val;
   private int inc;
   PropertyChangeSupport pcs;
   private static final int BUTTON_WIDTH = 14;
   private static final int BUTTON_HEIGHT = 8;
   boolean inFL;

   public NumberBox(int min, int max, int initalValue, int increment) {
      this();
      this.min = min;
      this.max = max;
      this.setValue(initalValue);
      this.inc = increment;
      double d = (double)max;
      int col = (int)Math.ceil(Math.log(d) / Math.log(10.0));
      this.tf.setColumns(col);
   }

   public NumberBox(int min, int max, int initialValue) {
      this(min, max, initialValue, 1);
   }

   public NumberBox() {
      super(new GridBagLayout());
      this.min = 0;
      this.max = Integer.MAX_VALUE;
      this.val = 0;
      this.inc = 1;
      this.pcs = null;
      this.inFL = false;
      this.init();
   }

   private void setNoDraw(int v) {
      int old = this.val;
      this.val = v;
      this.pcs.firePropertyChange("Value", old, this.val);
   }

   private void justSet(int v) {
      this.setNoDraw(v);
      String s = String.valueOf(this.val);
      this.tf.setText(s);
      this.tf.setCaretPosition(s.length());
   }

   public void setValue(int v) {
      if (v <= this.max && v >= this.min) {
         this.justSet(v);
      }

   }

   public int getValue() {
      return this.val;
   }

   public void setMin(int m) {
      if (m < this.max) {
         this.min = m;
         this.adjustToBounds();
      }

   }

   public int getMin() {
      return this.min;
   }

   public void setMax(int m) {
      if (m > this.min) {
         this.adjustToBounds();
      }

      double d = (double)m;
      int col = (int)Math.ceil(Math.log(d) / Math.log(10.0));
      this.tf.setColumns(col);
      this.doLayout();
   }

   public int getMax() {
      return this.max;
   }

   public void setIncrement(int i) {
      this.inc = i;
   }

   public int getIncrement() {
      return this.inc;
   }

   public void setTextBackground(Color c) {
      this.tf.setBackground(c);
   }

   protected void illegalValueHook() {
      String msg = "A value between " + this.getMin() + " and " + this.getMax() + " must be entered.";
      JOptionPane.showMessageDialog(this, msg, "Illegal number", 2);
      if (this.getValue() < this.getMin()) {
         this.setValue(this.getMin());
      } else if (this.getValue() < this.getMax()) {
         this.setValue(this.getMax());
      }

      this.requestFocus();
   }

   public void setEditable(boolean f) {
      this.tf.setEditable(f);
   }

   private void init() {
      this.pcs = new PropertyChangeSupport(this);
      this.setOpaque(false);
      GridBagLayout gb = (GridBagLayout)this.getLayout();
      GridBagConstraints gbc = new GridBagConstraints();
      this.tf = UIFactory.getTextField("");
      this.tf.setColumns(10);
      Border b = this.tf.getBorder();
      this.setBorder(b);
      this.tf.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.tf.addKeyListener(this);
      this.tf.addFocusListener(this);
      this.tf.addActionListener(this);
      gbc.fill = 2;
      gbc.gridheight = 0;
      gbc.gridx = gbc.gridy = 0;
      gbc.weightx = 0.0;
      gbc.weighty = 0.0;
      gbc.insets = new Insets(0, 0, 0, 0);
      gb.setConstraints(this.tf, gbc);
      this.add(this.tf);
      this.up = new ArrowButton(1);
      gbc.fill = 0;
      gbc.gridheight = 1;
      gbc.gridy = 0;
      gbc.gridx = 1;
      gbc.weightx = 0.0;
      this.up.addActionListener(this);
      this.up.addKeyListener(this);
      this.up.setFocusTraversable(false);
      gb.setConstraints(this.up, gbc);
      this.add(this.up);
      gbc.gridy = 1;
      this.down = new ArrowButton(5);
      this.down.addActionListener(this);
      this.down.addKeyListener(this);
      this.down.setFocusTraversable(false);
      gb.setConstraints(this.down, gbc);
      this.add(this.down);
      Dimension d = new Dimension(14, 8);
      this.up.setPreferredSize(d);
      this.down.setPreferredSize(d);
   }

   public void doLayout() {
      Dimension size = this.getSize();
      int startx = 0;
      int starty = 0;
      int endx = size.width;
      int endy = size.height;
      Border bdr = this.getBorder();
      if (bdr != null) {
         Insets I = bdr.getBorderInsets(this);
         startx += I.left;
         starty += I.top;
         endx -= I.right;
         endy -= I.bottom;
      }

      Dimension bPref = this.up.getPreferredSize();
      Dimension tPref = this.tf.getPreferredSize();
      int x = endx - bPref.width;
      int w = bPref.width;
      int h = (endy - starty) / 2;
      this.up.setBounds(x, starty, w, h);
      int y = starty + h;
      this.down.setBounds(x, y, w, h);
      w = endx - w - startx;
      h = endy - starty;
      this.tf.setBounds(startx, starty, w, h);
   }

   public void addPropertyChangeListener(PropertyChangeListener pcl) {
      this.pcs.addPropertyChangeListener(pcl);
   }

   public void removePropertyChangeListener(PropertyChangeListener pcl) {
      this.pcs.removePropertyChangeListener(pcl);
   }

   public void setEnabled(boolean b) {
      this.tf.setEnabled(b);
      this.up.setEnabled(b);
      this.down.setEnabled(b);
      super.setEnabled(b);
   }

   public void setToolTipText(String s) {
      this.tf.setToolTipText(s);
      this.up.setToolTipText(s);
      this.down.setToolTipText(s);
      super.setToolTipText(s);
   }

   public void requestFocus() {
      this.tf.requestFocus();
   }

   private void adjustToBounds() {
      if (this.val > this.min) {
         this.justSet(this.min);
      } else if (this.val > this.max) {
         this.justSet(this.max);
      }

   }

   public void actionPerformed(ActionEvent ev) {
      Object src = ev.getSource();
      if (src == this.up) {
         if (this.val + this.inc <= this.max && this.val + this.inc >= this.min) {
            this.justSet(this.val + this.inc);
         }
      } else if (src == this.down) {
         if (this.val - this.inc >= this.min && this.val - this.inc <= this.max) {
            this.justSet(this.val - this.inc);
         }
      } else if (src == this.tf && (this.getValue() > this.getMax() || this.getValue() < this.getMin())) {
         this.illegalValueHook();
      }

   }

   public void focusGained(FocusEvent fe) {
   }

   public void focusLost(FocusEvent fe) {
      if (!this.inFL) {
         try {
            this.inFL = true;
            String s = this.tf.getText();
            if (s != null && s.length() != 0) {
               this.justSet(this.getInt(s));
            } else {
               this.justSet(this.getMin());
            }

            if (this.getValue() > this.getMax() || this.getValue() < this.getMin()) {
               this.illegalValueHook();
            }
         } finally {
            this.inFL = false;
         }

      }
   }

   public void keyPressed(KeyEvent kv) {
      int c = kv.getKeyCode();
      if (c == 38) {
         this.justSet(this.getValue() + this.inc);
         kv.consume();
      } else if (c == 40) {
         this.justSet(this.getValue() - this.inc);
         kv.consume();
      }

   }

   public void keyReleased(KeyEvent kv) {
   }

   public void keyTyped(KeyEvent kv) {
      int pos = this.tf.getCaretPosition();
      char c = kv.getKeyChar();
      String oldtxt;
      if (c == '-') {
         oldtxt = this.tf.getText();
         if ("".equals(oldtxt) || "0".equals(oldtxt)) {
            this.tf.setText("-");
         }
      } else if (c != '\b' && c != 127) {
         if (Character.isDigit(c)) {
            oldtxt = this.tf.getText();
            String[] sel = this.clearSelection();
            String newtxt = sel[0] + c + sel[1];

            try {
               this.getInt(newtxt);
               this.tf.setText(newtxt);
            } catch (NumberFormatException var8) {
               kv.consume();
               return;
            }

            pos = sel[0].length() + 1;
            int tlen = this.tf.getText().length();
            if (pos > tlen) {
               pos = tlen;
            }

            this.tf.setCaretPosition(pos);
         }
      } else {
         int start = this.tf.getSelectionStart();
         int end = this.tf.getSelectionEnd();
         String[] sel = this.clearSelection();
         if (start >= end && sel[0].length() > 0) {
            sel[0] = sel[0].substring(0, sel[0].length() - 1);
         }

         String txt = sel[0] + sel[1];
         this.tf.setText(txt);
         this.tf.setCaretPosition(sel[0].length());
      }

      kv.consume();
   }

   private String[] clearSelection() {
      String[] ret = new String[2];
      String txt = this.tf.getText();
      int pos = this.tf.getCaretPosition();
      int start = this.tf.getSelectionStart();
      int end = this.tf.getSelectionEnd();
      if (start >= end) {
         ret[0] = txt.substring(0, pos);
         ret[1] = txt.substring(pos);
         return ret;
      } else {
         ret[0] = txt.substring(0, start);
         ret[1] = txt.substring(end);
         txt = ret[0] + ret[1];
         this.tf.setText(txt);
         end = Math.min(end, txt.length());
         this.tf.setCaretPosition(end);
         return ret;
      }
   }

   public void caretPositionChanged(InputMethodEvent ev) {
   }

   public void inputMethodTextChanged(InputMethodEvent ev) {
   }

   private int getInt(String s) {
      int newval = 0;
      if (s != null && (s = s.trim()).length() > 0) {
         if (s.equals("-")) {
            return 0;
         }

         long l = Long.parseLong(s);
         if (l > 2147483647L || l < -2147483648L) {
            throw new NumberFormatException("out of range: " + s);
         }

         newval = Integer.parseInt(s);
      }

      return newval;
   }

   public static void main(String[] a) {
      Util.initLookAndFeel("win");
      JFrame f = new JFrame("Number Box Test");
      NumberBox nb = new NumberBox();
      nb.setMin(0);
      nb.setMax(1000);
      nb.setValue(50);
      Container c = f.getContentPane();
      GridBagLayout gb = new GridBagLayout();
      GridBagConstraints gbc = new GridBagConstraints();
      c.setLayout(gb);
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 1;
      JLabel l = new JLabel("min " + nb.getMin());
      gb.setConstraints(l, gbc);
      c.add(l);
      gbc.gridx = 1;
      l = new JLabel("max " + nb.getMax());
      gb.setConstraints(l, gbc);
      c.add(l);
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.fill = 1;
      gb.setConstraints(nb, gbc);
      c.add(nb);
      WindowListener wl = new WindowAdapter() {
         public void windowClosing(WindowEvent ev) {
            System.exit(0);
         }
      };
      f.addWindowListener(wl);
      f.setSize(300, 300);
      f.setVisible(true);
   }

   private void p(String s) {
      System.err.println("[NumBox (min=" + this.getMin() + " val=" + this.getValue() + ")]: " + s);
   }
}
