package com.sun.faces.lifecycle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

public class HttpMethodRestrictionsPhaseListener implements PhaseListener {
   private static final long serialVersionUID = 179883834600711161L;

   public void afterPhase(PhaseEvent event) {
   }

   public void beforePhase(PhaseEvent event) {
      FacesContext context = event.getFacesContext();
      ExternalContext extContext = context.getExternalContext();
      Object requestObj = extContext.getRequest();
      if (requestObj instanceof HttpServletRequest) {
         String method = ((HttpServletRequest)requestObj).getMethod();
         if (method.equals("OPTIONS")) {
            context.responseComplete();
         }
      }

   }

   public PhaseId getPhaseId() {
      return PhaseId.RESTORE_VIEW;
   }
}
