package com.sun.faces.lifecycle;

import com.sun.faces.util.DebugUtil;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.PostRenderViewEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.view.ViewDeclarationLanguage;

public class RenderResponsePhase extends Phase {
   private static Logger LOGGER;

   public void execute(FacesContext facesContext) throws FacesException {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Entering RenderResponsePhase");
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("About to render view " + facesContext.getViewRoot().getViewId());
      }

      facesContext.getPartialViewContext();

      try {
         ViewHandler vh = facesContext.getApplication().getViewHandler();
         ViewDeclarationLanguage vdl = vh.getViewDeclarationLanguage(facesContext, facesContext.getViewRoot().getViewId());
         if (vdl != null) {
            vdl.buildView(facesContext, facesContext.getViewRoot());
         }

         Application application = facesContext.getApplication();

         boolean viewIdsUnchanged;
         do {
            String beforePublishViewId = facesContext.getViewRoot().getViewId();
            application.publishEvent(facesContext, PreRenderViewEvent.class, facesContext.getViewRoot());
            String afterPublishViewId = facesContext.getViewRoot().getViewId();
            viewIdsUnchanged = beforePublishViewId == null && afterPublishViewId == null || beforePublishViewId != null && afterPublishViewId != null && beforePublishViewId.equals(afterPublishViewId);
            if (facesContext.getResponseComplete()) {
               return;
            }
         } while(!viewIdsUnchanged);

         vh.renderView(facesContext, facesContext.getViewRoot());
         application.publishEvent(facesContext, PostRenderViewEvent.class, facesContext.getViewRoot());
      } catch (IOException var8) {
         throw new FacesException(var8.getMessage(), var8);
      }

      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "+=+=+=+=+=+= View structure printout for " + facesContext.getViewRoot().getViewId());
         DebugUtil.printTree(facesContext.getViewRoot(), LOGGER, Level.FINEST);
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Exiting RenderResponsePhase");
      }

   }

   public PhaseId getId() {
      return PhaseId.RENDER_RESPONSE;
   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
   }
}
