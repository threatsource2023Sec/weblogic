package com.sun.faces.flow;

import com.sun.faces.flow.builder.FlowBuilderImpl;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.inject.Named;

@Named("csfFLOWDISCOVERYCDIHELPER")
@Dependent
public class FlowDiscoveryCDIHelper implements Serializable {
   private static final long serialVersionUID = 6217421203074690365L;

   @Produces
   @FlowBuilderParameter
   FlowBuilder createFlowBuilder() {
      FacesContext context = FacesContext.getCurrentInstance();
      FlowBuilder result = new FlowBuilderImpl(context);
      return result;
   }
}
