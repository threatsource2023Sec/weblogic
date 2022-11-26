package javax.faces.flow;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class FlowHandlerFactoryWrapper extends FlowHandlerFactory implements FacesWrapper {
   private FlowHandlerFactory wrapped;

   /** @deprecated */
   @Deprecated
   public FlowHandlerFactoryWrapper() {
   }

   public FlowHandlerFactoryWrapper(FlowHandlerFactory wrapped) {
      this.wrapped = wrapped;
   }

   public FlowHandlerFactory getWrapped() {
      return this.wrapped;
   }

   public FlowHandler createFlowHandler(FacesContext context) {
      return this.getWrapped().createFlowHandler(context);
   }
}
