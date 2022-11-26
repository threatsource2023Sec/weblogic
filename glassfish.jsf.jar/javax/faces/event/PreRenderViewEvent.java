package javax.faces.event;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class PreRenderViewEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = -781238104491250220L;

   public PreRenderViewEvent(UIViewRoot root) {
      super(root);
   }

   public PreRenderViewEvent(FacesContext facesContext, UIViewRoot root) {
      super(facesContext, root);
   }
}
