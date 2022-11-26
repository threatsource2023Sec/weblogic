package weblogic.management.security.credentials;

import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.NotFoundException;

public interface UserPasswordCredentialMapExtendedV2ReaderMBean extends UserPasswordCredentialMapExtendedReaderMBean {
   String getRemoteUserName(String var1, String var2, String var3) throws NotFoundException;

   String getCurrentMappingWLSUserIdentityDomain(String var1) throws InvalidCursorException;
}
