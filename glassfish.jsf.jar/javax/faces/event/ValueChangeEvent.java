package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ValueChangeEvent extends FacesEvent {
   private static final long serialVersionUID = 2455861757565618446L;
   private Object oldValue = null;
   private Object newValue = null;

   public ValueChangeEvent(UIComponent component, Object oldValue, Object newValue) {
      super(component);
      this.oldValue = oldValue;
      this.newValue = newValue;
   }

   public ValueChangeEvent(FacesContext facesContext, UIComponent component, Object oldValue, Object newValue) {
      super(facesContext, component);
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
