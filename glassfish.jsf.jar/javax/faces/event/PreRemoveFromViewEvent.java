package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class PreRemoveFromViewEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = 1715650557625760365L;

   public PreRemoveFromViewEvent(UIComponent component) {
      super(component);
   }

   public PreRemoveFromViewEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return listener instanceof SystemEventListener;
   }
}
