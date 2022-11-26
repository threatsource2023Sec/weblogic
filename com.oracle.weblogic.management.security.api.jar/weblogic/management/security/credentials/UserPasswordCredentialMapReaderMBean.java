package weblogic.management.security.credentials;

import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.ListerMBean;
import weblogic.management.utils.NotFoundException;

public interface UserPasswordCredentialMapReaderMBean extends ListerMBean {
   String getRemoteUserName(String var1, String var2) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   String getRemotePassword(String var1, String var2) throws NotFoundException;

   String listCredentials(String var1);

   String getCurrentCredentialRemoteUserName(String var1) throws InvalidCursorException;

   /** @deprecated */
   @Deprecated
   String getCurrentCredentialRemotePassword(String var1) throws InvalidCursorException;

   String listMappings(String var1);

   String getCurrentMappingWLSUserName(String var1) throws InvalidCursorException;

   String getCurrentMappingRemoteUserName(String var1) throws InvalidCursorException;
}
