package com.sun.faces.renderkit;

import com.sun.faces.util.MessageUtils;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsfJsResourcePhaseListener implements PhaseListener {
   private static final long serialVersionUID = 1L;

   public void afterPhase(PhaseEvent event) {
   }

   public void beforePhase(PhaseEvent event) {
      FacesContext context = event.getFacesContext();
      Object obj = context.getExternalContext().getRequest();
      if (obj instanceof HttpServletRequest) {
         HttpServletRequest request = (HttpServletRequest)obj;
         if (request.getRequestURI().contains("com_sun_faces_sunjsf.js")) {
            HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
            response.addHeader("Cache-Control", "max-age=3600");
            response.setContentType("text/javascript");

            try {
               RenderKitUtils.writeSunJS(context, response.getWriter());
            } catch (IOException var7) {
               throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.JS_RESOURCE_WRITING_ERROR"), var7);
            }

            context.responseComplete();
         }
      }

   }

   public PhaseId getPhaseId() {
      return PhaseId.RESTORE_VIEW;
   }
}
