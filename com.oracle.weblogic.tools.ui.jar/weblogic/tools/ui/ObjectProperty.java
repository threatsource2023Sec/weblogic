package weblogic.tools.ui;

import java.awt.Component;
import java.beans.PropertyDescriptor;
import javax.swing.JComboBox;

public class ObjectProperty extends Property {
   JComboBox jc;
   private boolean allowNull;
   private static final Object NULL_OBJECT = new Object() {
      public String toString() {
         return "";
      }
   };

   public ObjectProperty(Object bean, PropertyDescriptor pd, String label, Object[] cons, boolean required) {
      super(bean, pd, label, required, new SortedComboBox(cons));
      this.allowNull = false;
      this.jc = (JComboBox)this.component;
   }

   public ObjectProperty(PropertyDescriptor pd, String label, Object[] cons, boolean required) {
      this((Object)null, pd, label, cons, required);
   }

   public boolean isUIEmpty() {
      return this.jc.getSelectedIndex() == -1 || this.jc.getSelectedItem() == NULL_OBJECT;
   }

   public Component getComponent() {
      return this.jc;
   }

   public void setAllowNull(boolean b) {
      if (b != this.allowNull) {
         this.allowNull = b;
         if (!this.allowNull) {
            this.jc.removeItemAt(0);
         } else {
            int cnt = this.jc.getItemCount();
            Object[] x = new Object[cnt + 1];
            x[0] = NULL_OBJECT;

            for(int i = 1; i <= cnt; ++i) {
               x[i] = this.jc.getItemAt(i);
            }

            this.setConstrained(x);
         }

      }
   }

   public Object getCurrentUIValue() {
      Object val = this.jc.getSelectedItem();
      if (val == null && this.allowNull) {
         return null;
      } else if (val == NULL_OBJECT) {
         return null;
      } else if (val != null) {
         return val;
      } else if (this.jc.getItemCount() == 0) {
         return null;
      } else {
         this.jc.setSelectedIndex(0);
         return this.jc.getSelectedItem();
      }
   }

   public void setCurrentUIValue(Object o) {
      if (o == null) {
         this.jc.setSelectedIndex(-1);
      } else {
         this.jc.setSelectedItem(o);
      }
   }

   public void setConstrained(Object[] o) {
      this.jc.removeAllItems();
      if (this.allowNull) {
         this.jc.addItem(NULL_OBJECT);
      }

      for(int i = 0; o != null && i < o.length; ++i) {
         this.jc.addItem(o[i]);
      }

   }

   private static void p(String s) {
      System.err.println("[ObjProp]: " + s);
   }
}
