package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class PreRenderComponentEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = -938817831518520795L;

   public PreRenderComponentEvent(UIComponent component) {
      super(component);
   }

   public PreRenderComponentEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }
}
