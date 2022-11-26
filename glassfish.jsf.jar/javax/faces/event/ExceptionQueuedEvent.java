package javax.faces.event;

import javax.faces.context.FacesContext;

public class ExceptionQueuedEvent extends SystemEvent {
   private static final long serialVersionUID = -3413872714571466618L;

   public ExceptionQueuedEvent(ExceptionQueuedEventContext eventContext) {
      super(eventContext);
   }

   public ExceptionQueuedEvent(FacesContext facesContext, ExceptionQueuedEventContext eventContext) {
      super(facesContext, eventContext);
   }

   public ExceptionQueuedEventContext getContext() {
      return (ExceptionQueuedEventContext)this.getSource();
   }
}
