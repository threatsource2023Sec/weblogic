package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class PostRestoreStateEvent extends ComponentSystemEvent {
   static final long serialVersionUID = -1007196479122154347L;

   public PostRestoreStateEvent(UIComponent component) {
      super(component);
   }

   public PostRestoreStateEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }

   public void setComponent(UIComponent newComponent) {
      this.source = newComponent;
   }
}
