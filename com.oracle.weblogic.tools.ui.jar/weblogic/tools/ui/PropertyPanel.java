package weblogic.tools.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class PropertyPanel extends BeanRowEditor implements LayoutManager {
   PropertySet ps;
   private JComponent[] comps;
   private Insets insets = new Insets(5, 5, 5, 5);

   static void p(String s) {
      System.err.println("[PPanel]: " + s);
   }

   protected PropertyPanel() {
   }

   public PropertyPanel(PropertySet ps) {
      this.ps = ps;
      this.layoutProps();
   }

   public PropertyPanel(Class bc, PropertyInfo[] info) {
      this.ps = new PropertySet(bc, info);
      this.layoutProps();
   }

   public PropertyPanel(Object bean, Class bc, Object[][] propdata) {
      this.ps = new PropertySet(bean, bc, propdata);
      this.layoutProps();
   }

   public Property findPropByName(String name) {
      return this.ps.findPropByName(name);
   }

   public void setAutoCommit(boolean b) {
      this.ps.setAutoCommit(b);
   }

   public JComponent getFirstFocusComponent() {
      JComponent ret = null;
      if (this.ps != null) {
         Property[] props = this.ps.getProps();
         if (props.length > 0) {
            ret = (JComponent)props[0].getComponent();
         }
      }

      return ret;
   }

   public void setEditingBean(Object o) {
      this.ps.setBean(o);
   }

   public IValidationFeedback getFeedback() {
      ValidationFeedback vf = null;
      Property[] props = this.ps.getProps();

      for(int i = 0; props != null && i < props.length; ++i) {
         Property p = props[i];
         if (p.isRequired() && p.isUIEmpty()) {
            String msg = '"' + deAnnotate(p.getLabel().getText()) + "\" must be entered";
            vf = new ValidationFeedback(msg, p.getComponent());
            break;
         }
      }

      return vf;
   }

   public static String deAnnotate(String s) {
      if (s.startsWith("*")) {
         s = s.substring(1);
      }

      if (s.endsWith(":")) {
         s = s.substring(0, s.length() - 1);
      }

      return s.trim();
   }

   public void modelToUI() {
      this.ps.modelToUI();
   }

   public Object createNewBean() {
      return this.ps.createNewBean();
   }

   public void uiToModel() {
      this.ps.uiToModel();
   }

   private void layoutPropsKeyValuePanel() {
      Property[] props = this.ps.getProps();
      ArrayList widgets = new ArrayList();

      for(int i = 0; i < props.length; ++i) {
         Property prop = props[i];
         if (prop.hasSeparateLabel()) {
            widgets.add(prop.getLabel());
         } else {
            widgets.add(UIFactory.getLabel(""));
         }

         widgets.add(props[i].getComponent());
      }

      JComponent[] components = new JComponent[widgets.size()];
      widgets.toArray(components);
   }

   private static void ppp(String s) {
      System.out.println("[PropertyPanel] " + s);
   }

   private void layoutProps() {
      this.layoutPropsKeyValuePanel();
   }

   public void addLayoutComponent(String name, Component comp) {
   }

   public void layoutContainer(Container cont) {
      int cwidth = cont.getWidth();
      int cheight = cont.getHeight();
      int yoff = this.insets.top;
      Border bdr = this.getBorder();
      if (bdr != null) {
         Insets bInsets = bdr.getBorderInsets(this);
         if (bInsets != null) {
            yoff += bInsets.top;
         }
      }

      int hardwidth = cwidth - this.insets.left - this.insets.right;

      for(int i = 0; i < this.comps.length; ++i) {
         Dimension pref = this.comps[i].getPreferredSize();
         int w = hardwidth;
         if (this.comps[i] instanceof JComboBox || this.comps[i] instanceof NumberBox) {
            w = pref.width;
         }

         if (!(this.comps[i] instanceof JTextField)) {
            yoff += this.insets.top;
         }

         this.comps[i].setBounds(this.insets.left, yoff, w, pref.height);
         yoff += pref.height;
      }

   }

   public Dimension minimumLayoutSize(Container cont) {
      return this.preferredLayoutSize(cont);
   }

   public Dimension preferredLayoutSize(Container cont) {
      int w = 100;
      int h = this.insets.top;
      Border bdr = this.getBorder();
      if (bdr != null) {
         Insets bInsets = bdr.getBorderInsets(this);
         if (bInsets != null) {
            h += bInsets.top;
            h += bInsets.bottom;
         }
      }

      for(int i = 0; i < this.comps.length; ++i) {
         Dimension pref = this.comps[i].getPreferredSize();
         w = Math.max(w, pref.width);
         h += this.insets.top;
         h += pref.height;
      }

      return new Dimension(w, h);
   }

   public void removeLayoutComponent(Component comp) {
   }

   public JComponent getComponent() {
      return this;
   }
}
