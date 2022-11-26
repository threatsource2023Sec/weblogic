package javax.faces.event;

import javax.faces.context.FacesContext;

public class PostPutFlashValueEvent extends SystemEvent {
   private static final long serialVersionUID = -6422204761759384353L;

   public PostPutFlashValueEvent(String key) {
      super(null == key ? "javax.faces.context.Flash.NULL_VALUE" : key);
   }

   public PostPutFlashValueEvent(FacesContext facesContext, String key) {
      super(facesContext, key);
   }

   public String getKey() {
      return this.getSource().toString();
   }
}
