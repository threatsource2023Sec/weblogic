package weblogic.tools.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyDescriptor;
import javax.swing.JCheckBox;

public class BooleanProperty extends Property implements ActionListener {
   JCheckBox bool;

   public BooleanProperty(Object bean, PropertyDescriptor pd, String label) {
      super(bean, pd, label);
   }

   public BooleanProperty(PropertyDescriptor pd, String label) {
      this((Object)null, pd, label);
   }

   public boolean hasSeparateLabel() {
      return false;
   }

   public Component getComponent() {
      if (this.bool == null) {
         this.bool = new JCheckBox(this.label.getText());
         this.bool.addActionListener(this);
      }

      return this.bool;
   }

   public Object getCurrentUIValue() {
      return new Boolean(this.bool.isSelected());
   }

   public void setCurrentUIValue(Object o) {
      this.bool.setSelected("true".equals(o.toString()));
   }

   public void actionPerformed(ActionEvent ev) {
      if (this.autoCommit && ev != null && ev.getSource() == this.getComponent()) {
         this.uiToModel();
      }

   }
}
