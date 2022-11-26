package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class PostAddToViewEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = -1113592223476173895L;

   public PostAddToViewEvent(UIComponent component) {
      super(component);
   }

   public PostAddToViewEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return listener instanceof SystemEventListener;
   }
}
