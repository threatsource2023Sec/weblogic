package javax.faces.event;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class PreDestroyViewMapEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = 4470489935758914483L;

   public PreDestroyViewMapEvent(UIViewRoot root) {
      super(root);
   }

   public PreDestroyViewMapEvent(FacesContext facesContext, UIViewRoot root) {
      super(facesContext, root);
   }
}
