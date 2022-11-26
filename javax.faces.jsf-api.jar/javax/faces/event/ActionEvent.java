package javax.faces.event;

import javax.faces.component.UIComponent;

public class ActionEvent extends FacesEvent {
   public ActionEvent(UIComponent component) {
      super(component);
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return listener instanceof ActionListener;
   }

   public void processListener(FacesListener listener) {
      ((ActionListener)listener).processAction(this);
   }
}
