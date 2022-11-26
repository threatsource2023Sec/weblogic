package weblogic.tools.ui;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;

public class NumberProperty extends Property implements PropertyChangeListener {
   NumberBox nbox;

   public NumberProperty(Object bean, PropertyDescriptor pd, String label) {
      super(bean, pd, label);
      this.getComponent();
   }

   public NumberProperty(PropertyDescriptor pd, String label) {
      this((Object)null, pd, label);
   }

   public Component getComponent() {
      if (this.nbox == null) {
         this.nbox = new NumberBox(0, Integer.MAX_VALUE, 0);
         this.nbox.addPropertyChangeListener(this);
      }

      return this.nbox;
   }

   public void propertyChange(PropertyChangeEvent pce) {
      if (pce.getSource() == this.nbox && "value".equalsIgnoreCase(pce.getPropertyName()) && this.isAutoCommit()) {
         this.uiToModel();
      }

   }

   public void setMin(int n) {
      if (n != -1) {
         this.nbox.setMin(n);
      }

   }

   public void setMax(int n) {
      if (n != -1) {
         this.nbox.setMax(n);
      }

   }

   public void setIncrement(int n) {
      if (n != -1) {
         this.nbox.setIncrement(n);
      }

   }

   public Object getCurrentUIValue() {
      int i = 0;
      if (this.nbox != null) {
         i = this.nbox.getValue();
      }

      return new Integer(i);
   }

   public void setCurrentUIValue(Object o) {
      Integer I = (Integer)o;
      this.getComponent();
      this.nbox.setValue(I);
   }
}
