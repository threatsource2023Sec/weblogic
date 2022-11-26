package weblogic.tools.ui;

import java.awt.Component;
import java.beans.PropertyDescriptor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class StringProperty extends Property {
   JTextField text;
   boolean inui2model;
   boolean emptyIsNull;
   JComponent focusComponent;

   public StringProperty(Object bean, PropertyDescriptor pd, String label, boolean required) {
      super(bean, pd, label, required);
      this.emptyIsNull = true;
      this.getComponent();
   }

   public StringProperty(PropertyDescriptor pd, String label, boolean required) {
      this((Object)null, pd, label, required);
   }

   public boolean isUIEmpty() {
      if (this.text == null) {
         return false;
      } else {
         String s = this.text.getText();
         return s == null || s.trim().length() == 0;
      }
   }

   public void setAutoCommit(boolean b) {
      super.setAutoCommit(b);
   }

   public void uiToModel() {
      if (this.isRequired() && !this.isAutoCommit()) {
         if (!this.inui2model) {
            try {
               this.inui2model = true;
               String s = (String)this.getCurrentUIValue();
               if (s != null && s.trim().length() != 0) {
                  super.uiToModel();
               } else {
                  Object oldval = this.invokeGetter();
                  this.setCurrentUIValue(oldval);
                  String title = "Invalid field";
                  String msg = "\"" + this.getLabel().getText() + "\" must be non-empty";
                  JOptionPane.showMessageDialog(this.text.getParent(), msg, title, 0);
                  if (this.focusComponent == null) {
                     this.text.requestFocus();
                  } else {
                     this.focusComponent.requestFocus();
                  }
               }
            } finally {
               this.inui2model = false;
            }

         }
      } else {
         super.uiToModel();
      }
   }

   public void setFocusComponent(JComponent c) {
      this.focusComponent = c;
   }

   public Component getComponent() {
      if (this.text == null) {
         this.text = new JTextField();
      }

      return this.text;
   }

   public Object getCurrentUIValue() {
      String s = this.text.getText();
      if (s != null && this.emptyIsNull) {
         if ((s = s.trim()).length() == 0) {
            s = null;
         }

         return s;
      } else {
         return s;
      }
   }

   public void setCurrentUIValue(Object o) {
      if (o == null) {
         o = "";
      }

      this.text.setText(o.toString());
   }

   public void setEmptyIsNull(boolean b) {
      this.emptyIsNull = b;
   }

   public boolean getEmptyIsNull() {
      return this.emptyIsNull;
   }
}
