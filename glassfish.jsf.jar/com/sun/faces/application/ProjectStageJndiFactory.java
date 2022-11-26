package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ProjectStage;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class ProjectStageJndiFactory implements ObjectFactory {
   private static final Logger LOGGER;

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      if (obj instanceof Reference) {
         Reference ref = (Reference)obj;
         RefAddr addr = ref.get("stage");
         if (addr != null) {
            String val = (String)addr.getContent();
            if (val != null) {
               return val.trim();
            }
         } else if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning("'stage' property not defined.  Defaulting to Production");
         }
      }

      return ProjectStage.Production.toString();
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
