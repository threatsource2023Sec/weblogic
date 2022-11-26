package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class PreValidateEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = -248088758942796822L;

   public PreValidateEvent(UIComponent component) {
      super(component);
   }

   public PreValidateEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }
}
