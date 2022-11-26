package weblogic.security.service.internal;

import weblogic.security.service.ApplicationRemovalException;
import weblogic.security.service.ApplicationVersionCreationException;
import weblogic.security.service.ApplicationVersionRemovalException;

public interface ApplicationVersioningService {
   boolean isApplicationVersioningSupported();

   void createApplicationVersion(String var1, String var2) throws ApplicationVersionCreationException;

   void deleteApplicationVersion(String var1) throws ApplicationVersionRemovalException;

   void deleteApplication(String var1) throws ApplicationRemovalException;
}
