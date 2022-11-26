package javax.faces.event;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class PostRenderViewEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = 2790603812421768241L;

   public PostRenderViewEvent(UIViewRoot root) {
      super(root);
   }

   public PostRenderViewEvent(FacesContext facesContext, UIViewRoot root) {
      super(facesContext, root);
   }
}
