package javax.faces.event;

import javax.faces.context.FacesContext;

public class PreRemoveFlashValueEvent extends SystemEvent {
   private static final long serialVersionUID = -82999687346960430L;

   public PreRemoveFlashValueEvent(String key) {
      super(null == key ? "javax.faces.context.Flash.NULL_VALUE" : key);
   }

   public PreRemoveFlashValueEvent(FacesContext facesContext, String key) {
      super(facesContext, key);
   }

   public String getKey() {
      return this.getSource().toString();
   }
}
