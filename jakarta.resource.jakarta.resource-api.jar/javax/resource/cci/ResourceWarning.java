package javax.resource.cci;

import javax.resource.ResourceException;

public class ResourceWarning extends ResourceException {
   public ResourceWarning() {
   }

   public ResourceWarning(String message) {
      super(message);
   }

   public ResourceWarning(Throwable cause) {
      super(cause);
   }

   public ResourceWarning(String message, Throwable cause) {
      super(message, cause);
   }

   public ResourceWarning(String message, String errorCode) {
      super(message, errorCode);
   }

   /** @deprecated */
   public ResourceWarning getLinkedWarning() {
      try {
         return (ResourceWarning)this.getLinkedException();
      } catch (ClassCastException var2) {
         return null;
      }
   }

   /** @deprecated */
   public void setLinkedWarning(ResourceWarning warning) {
      this.setLinkedException(warning);
   }
}
