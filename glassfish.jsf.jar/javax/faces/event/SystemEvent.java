package javax.faces.event;

import java.util.EventObject;
import javax.faces.context.FacesContext;

public abstract class SystemEvent extends EventObject {
   private static final long serialVersionUID = 2696415667461888462L;
   private transient FacesContext facesContext;

   public SystemEvent(Object source) {
      super(source);
   }

   public SystemEvent(FacesContext facesContext, Object source) {
      super(source);
      this.facesContext = facesContext;
   }

   public FacesContext getFacesContext() {
      if (this.facesContext == null) {
         this.facesContext = FacesContext.getCurrentInstance();
      }

      return this.facesContext;
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return listener instanceof SystemEventListener;
   }

   public void processListener(FacesListener listener) {
      ((SystemEventListener)listener).processEvent(this);
   }
}
