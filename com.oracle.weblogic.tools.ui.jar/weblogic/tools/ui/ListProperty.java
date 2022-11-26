package weblogic.tools.ui;

import java.awt.Component;
import java.beans.PropertyDescriptor;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ListProperty extends Property {
   JComboBox jc;
   boolean selectFirstElement;
   boolean allowEditing;
   JTextField editor;

   public ListProperty(Object bean, PropertyDescriptor pd, String label, String[] cons, boolean required) {
      super(bean, pd, label, required, new SortedComboBox(cons));
      this.selectFirstElement = false;
      this.allowEditing = false;
      this.jc = (JComboBox)this.component;
      Component c = this.jc.getEditor().getEditorComponent();
      if (c instanceof JTextField) {
         this.editor = (JTextField)c;
      }

   }

   public ListProperty(PropertyDescriptor pd, String label, String[] cons, boolean required) {
      this((Object)null, pd, label, cons, required);
   }

   public boolean isUIEmpty() {
      if (this.allowEditing) {
         String s = this.editor.getText();
         if (s != null && s.trim().length() > 0) {
            return false;
         }
      }

      return this.jc.getSelectedIndex() == -1;
   }

   public Component getComponent() {
      return this.jc;
   }

   public void setAllowEditing(boolean b) {
      this.jc.setEditable(this.allowEditing = b);
   }

   public boolean getAllowEditing() {
      return this.allowEditing;
   }

   public Object getCurrentUIValue() {
      if (this.allowEditing) {
         String s = this.editor.getText();
         if (s != null) {
            s = s.trim();
            if (s.length() == 0) {
               s = null;
            }
         }

         return s;
      } else {
         Object val = this.jc.getSelectedItem();
         if (val != null) {
            return val;
         } else {
            this.jc.setSelectedIndex(-1);
            return this.jc.getSelectedItem();
         }
      }
   }

   public void setSelectFirstElement(boolean b) {
      this.selectFirstElement = b;
   }

   public boolean getSelectFirstElement() {
      return this.selectFirstElement;
   }

   public void setCurrentUIValue(Object o) {
      this.jc.setSelectedItem(o);
      if (this.jc.getSelectedItem() == null) {
         if (this.getSelectFirstElement() && this.jc.getItemCount() > 0) {
            this.jc.setSelectedIndex(0);
         } else {
            this.jc.setSelectedIndex(-1);
         }
      }

   }
}
