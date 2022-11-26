package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class PostValidateEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = -4213993829669626297L;

   public PostValidateEvent(UIComponent component) {
      super(component);
   }

   public PostValidateEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }
}
