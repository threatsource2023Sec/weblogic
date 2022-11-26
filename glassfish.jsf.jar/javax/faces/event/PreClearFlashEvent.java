package javax.faces.event;

import java.util.Map;
import javax.faces.context.FacesContext;

public class PreClearFlashEvent extends SystemEvent {
   private static final long serialVersionUID = -6069648757590884651L;

   public PreClearFlashEvent(Map source) {
      super(source);
   }

   public PreClearFlashEvent(FacesContext facesContext, Map source) {
      super(facesContext, source);
   }
}
