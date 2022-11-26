package com.sun.faces.flow;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.FlowHandlerFactory;

public class FlowHandlerFactoryImpl extends FlowHandlerFactory {
   public FlowHandler createFlowHandler(FacesContext context) {
      return new FlowHandlerImpl();
   }
}
