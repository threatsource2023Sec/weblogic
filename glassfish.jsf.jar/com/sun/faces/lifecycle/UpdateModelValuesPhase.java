package com.sun.faces.lifecycle;

import com.sun.faces.util.FacesLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

public class UpdateModelValuesPhase extends Phase {
   private static Logger LOGGER;

   public void execute(FacesContext facesContext) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Entering UpdateModelValuesPhase");
      }

      UIComponent component = facesContext.getViewRoot();

      assert null != component;

      try {
         component.processUpdates(facesContext);
      } catch (RuntimeException var5) {
         String exceptionMessage = var5.getMessage();
         if (null != exceptionMessage && LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, exceptionMessage, var5);
         }

         throw new FacesException(exceptionMessage, var5);
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Exiting UpdateModelValuesPhase");
      }

   }

   public PhaseId getId() {
      return PhaseId.UPDATE_MODEL_VALUES;
   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
   }
}
