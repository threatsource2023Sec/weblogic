package weblogic.tools.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import weblogic.utils.Debug;

public class KeyValueLayout implements LayoutManager {
   public static final String KEY = "Key";
   public static final String VALUE = "Value";
   public static final String CHOOSER = "Chooser";
   private boolean m_verbose = false;
   public static final int VERTICAL_GAP = 5;
   public static final int TOP_GAP = 5;
   public static final int BOTTOM_GAP = 15;
   public static final int LEFT_GAP = 10;
   public static final int RIGHT_GAP = 10;
   public static final int LABEL_WIDGET_OFFSET = 6;
   public static final int CHOOSER_GAP = 5;
   private int HEIGHT = 20;
   private HashMap m_components = new HashMap();

   public void layoutContainer(Container container) {
      Insets insets = container.getInsets();
      int x = 10 + insets.left;
      int y = 5 + insets.top;
      int vgap = true;
      int hgap = true;
      int w = (int)container.getSize().getWidth() - 10 - 10 - insets.left - insets.right;
      int h = this.HEIGHT;
      int valueHeight = this.HEIGHT;
      int ncomponents = container.countComponents();
      String nextConstraint = null;

      for(int i = 0; i < ncomponents; ++i) {
         Component comp = container.getComponent(i);
         h = (int)comp.getPreferredSize().getHeight();
         if (i + 1 < ncomponents) {
            nextConstraint = (String)this.m_components.get(container.getComponent(i + 1));
         }

         String constraint = (String)this.m_components.get(comp);
         Debug.assertion(null != constraint, "KeyValueLayout: no constraint was specified for component " + comp.getClass());
         if ("Key".equals(constraint)) {
            this.resizeComponent(comp, constraint, x, y, (double)w, (double)h);
            y += h + 5 - 6;
         } else if ("Value".equals(constraint)) {
            valueHeight = h;
            if ("Chooser".equals(nextConstraint)) {
               JComponent chooser = (JComponent)container.getComponent(i + 1);
               int chooserWidth = (int)chooser.getPreferredSize().getWidth();
               this.resizeComponent(comp, constraint, x, y, (double)(w - chooserWidth - 5), (double)h);
            } else {
               this.resizeComponent(comp, constraint, x, y, (double)w, (double)h);
               nextConstraint = null;
               y += h + 5;
            }
         } else {
            int chooserWidth = (int)comp.getPreferredSize().getWidth();
            this.resizeComponent(comp, constraint, x + w - chooserWidth, y, (double)chooserWidth, (double)valueHeight);
            y += valueHeight + 5;
         }
      }

   }

   private void resizeComponent(Component comp, String constraint, int x, int y, double w, double h) {
      comp.setBounds(x, y, (int)w, (int)h);
   }

   public Dimension preferredLayoutSize(Container container) {
      Dimension result = this.minimumLayoutSize(container);
      return result;
   }

   private int getMinimumHeight(Container container, int nComponents) {
      int lines = false;
      int height = 0;
      int ncomponents = container.countComponents();

      int result;
      for(result = 0; result < ncomponents; ++result) {
         Component comp = container.getComponent(result);
         String constraint = (String)this.m_components.get(comp);
         if (!"Chooser".equals(constraint)) {
            height += (int)comp.getPreferredSize().getHeight();
         }

         if (comp instanceof JComponent) {
            JComponent jc = (JComponent)comp;
            Border b = jc.getBorder();
            if (b != null) {
               Insets I = b.getBorderInsets(jc);
               if (I != null) {
                  height += I.top;
                  height += I.bottom;
               }
            }
         }
      }

      result = height;
      Insets insets = container.getInsets();
      if (container instanceof JComponent) {
         Border b = ((JComponent)container).getBorder();
         if (null != b) {
            Insets borderInsets = b.getBorderInsets(container);
            if (null != borderInsets) {
               result = height + borderInsets.top + borderInsets.bottom;
            }
         }
      }

      return result + 15 + 5;
   }

   public Dimension minimumLayoutSize(Container container) {
      int nComponents = container.countComponents();
      Dimension result = new Dimension(100, this.getMinimumHeight(container, nComponents));
      return result;
   }

   public void addLayoutComponent(String constraint, Component component) {
      this.m_components.put(component, constraint);
   }

   public void removeLayoutComponent(Component component) {
   }

   private void log(String s) {
      if (this.m_verbose) {
         System.out.println("[KeyValueLayout] " + s);
      }

   }

   private static void ppp(String s) {
      System.out.println("[KeyValueLayout] " + s);
   }

   public static void main(String[] argv) {
      JFrame f = new JFrame("Test KeyValue");
      JPanel p = new JPanel(new KeyValueLayout());
      JLabel l = new JLabel("Last Name:");
      JTextField tf = new JTextField(30);
      p.add(l, "Key");
      p.add(tf, "Value");
      p.add(new JLabel("First name"), "Key");
      p.add(new JComboBox(), "Value");
      p.add(new JLabel("First name"), "Key");
      p.add(new JComboBox(), "Value");
      p.add(new JLabel("First name"), "Key");
      p.add(new JComboBox(), "Value");
      p.add(new JCheckBox("Toggle"), "Key");

      for(int i = 0; i < 9; ++i) {
         p.add(new JLabel("Age"), "Key");
         p.add(new NumberBox(1, 100, 30), "Value");
      }

      p.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(), "Test"));
      f.getContentPane().add(p);
      f.pack();
      f.setVisible(true);
   }
}
