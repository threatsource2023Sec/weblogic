package javax.faces.lifecycle;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class ClientWindowFactory implements FacesWrapper {
   private ClientWindowFactory wrapped;

   /** @deprecated */
   @Deprecated
   public ClientWindowFactory() {
   }

   public ClientWindowFactory(ClientWindowFactory wrapped) {
      this.wrapped = wrapped;
   }

   public ClientWindowFactory getWrapped() {
      return this.wrapped;
   }

   public abstract ClientWindow getClientWindow(FacesContext var1);
}
