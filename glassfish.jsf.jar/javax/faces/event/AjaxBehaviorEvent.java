package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;

public class AjaxBehaviorEvent extends BehaviorEvent {
   private static final long serialVersionUID = -2533217384414744239L;

   public AjaxBehaviorEvent(UIComponent component, Behavior behavior) {
      super(component, behavior);
   }

   public AjaxBehaviorEvent(FacesContext facesContext, UIComponent component, Behavior behavior) {
      super(facesContext, component, behavior);
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return listener instanceof AjaxBehaviorListener;
   }

   public void processListener(FacesListener listener) {
      ((AjaxBehaviorListener)listener).processAjaxBehavior(this);
   }
}
