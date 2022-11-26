package javax.faces.event;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class PreDestroyApplicationEvent extends SystemEvent {
   private static final long serialVersionUID = 8105212785161493162L;

   public PreDestroyApplicationEvent(Application application) {
      super(application);
   }

   public PreDestroyApplicationEvent(FacesContext facesContext, Application application) {
      super(facesContext, application);
   }

   public Application getApplication() {
      return (Application)this.getSource();
   }
}
