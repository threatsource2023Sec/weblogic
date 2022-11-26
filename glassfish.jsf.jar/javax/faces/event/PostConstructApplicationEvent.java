package javax.faces.event;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class PostConstructApplicationEvent extends SystemEvent {
   private static final long serialVersionUID = -3918703770970591309L;

   public PostConstructApplicationEvent(Application application) {
      super(application);
   }

   public PostConstructApplicationEvent(FacesContext facesContext, Application application) {
      super(facesContext, application);
   }

   public Application getApplication() {
      return (Application)this.getSource();
   }
}
