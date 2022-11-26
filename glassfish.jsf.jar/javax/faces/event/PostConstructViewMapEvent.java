package javax.faces.event;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class PostConstructViewMapEvent extends ComponentSystemEvent {
   private static final long serialVersionUID = 8684338297976265379L;

   public PostConstructViewMapEvent(UIViewRoot root) {
      super(root);
   }

   public PostConstructViewMapEvent(FacesContext facesContext, UIViewRoot root) {
      super(facesContext, root);
   }
}
