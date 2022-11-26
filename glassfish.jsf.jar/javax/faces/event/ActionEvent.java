package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ActionEvent extends FacesEvent {
   private static final long serialVersionUID = 2391694421423935722L;

   public ActionEvent(UIComponent component) {
      super(component);
   }

   public ActionEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return listener instanceof ActionListener;
   }

   public void processListener(FacesListener listener) {
      ((ActionListener)listener).processAction(this);
   }
}
