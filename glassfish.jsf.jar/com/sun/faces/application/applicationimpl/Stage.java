package com.sun.faces.application.applicationimpl;

import com.sun.faces.application.ValidateComponentNesting;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.faces.event.PostAddToViewEvent;

public class Stage {
   private static final Logger LOGGER;
   private ProjectStage projectStage;

   public ProjectStage getProjectStage(Application application) {
      if (this.projectStage == null) {
         String value = this.fetchProjectStageFromConfig();
         this.setProjectStageFromValue(value, ProjectStage.Production);
         if (this.projectStage == ProjectStage.Development) {
            application.subscribeToEvent(PostAddToViewEvent.class, new ValidateComponentNesting());
         }
      }

      return this.projectStage;
   }

   private String fetchProjectStageFromConfig() {
      WebConfiguration webConfig = WebConfiguration.getInstance(FacesContext.getCurrentInstance().getExternalContext());
      String value = webConfig.getEnvironmentEntry(WebConfiguration.WebEnvironmentEntry.ProjectStage);
      if (value != null) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "ProjectStage configured via JNDI: {0}", value);
         }
      } else {
         value = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.JavaxFacesProjectStage);
         if (value != null && LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "ProjectStage configured via servlet context init parameter: {0}", value);
         }
      }

      return value;
   }

   private void setProjectStageFromValue(String value, ProjectStage defaultStage) {
      if (value != null) {
         try {
            this.projectStage = ProjectStage.valueOf(value);
         } catch (IllegalArgumentException var4) {
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "Unable to discern ProjectStage for value {0}.", value);
            }
         }
      }

      if (this.projectStage == null) {
         this.projectStage = defaultStage;
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
