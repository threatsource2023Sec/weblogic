package javax.faces.lifecycle;

import java.util.Map;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class ClientWindowWrapper extends ClientWindow implements FacesWrapper {
   private ClientWindow wrapped;

   /** @deprecated */
   @Deprecated
   public ClientWindowWrapper() {
   }

   public ClientWindowWrapper(ClientWindow wrapped) {
      this.wrapped = wrapped;
   }

   public ClientWindow getWrapped() {
      return this.wrapped;
   }

   public String getId() {
      return this.getWrapped().getId();
   }

   public Map getQueryURLParameters(FacesContext context) {
      return this.getWrapped().getQueryURLParameters(context);
   }

   public void disableClientWindowRenderMode(FacesContext context) {
      this.getWrapped().disableClientWindowRenderMode(context);
   }

   public void enableClientWindowRenderMode(FacesContext context) {
      this.getWrapped().enableClientWindowRenderMode(context);
   }

   public boolean isClientWindowRenderModeEnabled(FacesContext context) {
      return this.getWrapped().isClientWindowRenderModeEnabled(context);
   }

   public void decode(FacesContext context) {
      this.getWrapped().decode(context);
   }
}
