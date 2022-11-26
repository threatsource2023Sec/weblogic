package javax.faces.event;

import javax.faces.component.UIComponent;

public class ValueChangeEvent extends FacesEvent {
   private Object oldValue = null;
   private Object newValue = null;

   public ValueChangeEvent(UIComponent component, Object oldValue, Object newValue) {
      super(component);
      this.oldValue = oldValue;
      this.newValue = newValue;
   }

   public Object getOldValue() {
      return this.oldValue;
   }

   public Object getNewValue() {
      return this.newValue;
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return listener instanceof ValueChangeListener;
   }

   public void processListener(FacesListener listener) {
      ((ValueChangeListener)listener).processValueChange(this);
   }
}
