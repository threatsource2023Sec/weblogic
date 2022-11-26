package javax.faces.event;

import javax.faces.context.FacesContext;

public class PostKeepFlashValueEvent extends SystemEvent {
   private static final long serialVersionUID = -7137725846753823862L;

   public PostKeepFlashValueEvent(String key) {
      super(null == key ? "javax.faces.context.Flash.NULL_VALUE" : key);
   }

   public PostKeepFlashValueEvent(FacesContext facesContext, String key) {
      super(facesContext, key);
   }

   public String getKey() {
      return this.getSource().toString();
   }
}
