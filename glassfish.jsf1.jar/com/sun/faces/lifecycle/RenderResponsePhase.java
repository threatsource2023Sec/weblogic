package com.sun.faces.lifecycle;

import com.sun.faces.util.DebugUtil;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.TypedCollections;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

public class RenderResponsePhase extends Phase {
   private static Logger LOGGER;

   public void execute(FacesContext facesContext) throws FacesException {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Entering RenderResponsePhase");
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("About to render view " + facesContext.getViewRoot().getViewId());
      }

      try {
         if (LOGGER.isLoggable(Level.INFO)) {
            Iterator clientIdIter = facesContext.getClientIdsWithMessages();
            if (clientIdIter.hasNext()) {
               Set clientIds = new HashSet();

               while(clientIdIter.hasNext()) {
                  clientIds.add(clientIdIter.next());
               }

               RequestStateManager.set(facesContext, "com.sun.faces.clientIdMessagesNotDisplayed", clientIds);
            }
         }

         facesContext.getApplication().getViewHandler().renderView(facesContext, facesContext.getViewRoot());
         if (LOGGER.isLoggable(Level.INFO) && RequestStateManager.containsKey(facesContext, "com.sun.faces.clientIdMessagesNotDisplayed")) {
            Set clientIds = TypedCollections.dynamicallyCastSet((Set)RequestStateManager.remove(facesContext, "com.sun.faces.clientIdMessagesNotDisplayed"), String.class);
            if (!clientIds.isEmpty()) {
               StringBuilder builder = new StringBuilder();
               Iterator i$ = clientIds.iterator();

               while(i$.hasNext()) {
                  String clientId = (String)i$.next();
                  Iterator messages = facesContext.getMessages(clientId);

                  while(messages.hasNext()) {
                     FacesMessage message = (FacesMessage)messages.next();
                     builder.append("\n");
                     builder.append("sourceId=").append(clientId);
                     builder.append("[severity=(").append(message.getSeverity());
                     builder.append("), summary=(").append(message.getSummary());
                     builder.append("), detail=(").append(message.getDetail()).append(")]");
                  }
               }

               LOGGER.log(Level.INFO, "jsf.non_displayed_message", builder.toString());
            }
         }
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
