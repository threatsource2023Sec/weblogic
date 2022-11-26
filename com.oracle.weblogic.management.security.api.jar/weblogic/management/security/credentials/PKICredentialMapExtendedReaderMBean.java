package weblogic.management.security.credentials;

import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.NotFoundException;

public interface PKICredentialMapExtendedReaderMBean extends PKICredentialMapReaderMBean {
   String getKeystoreAlias(String var1, String var2, String var3, boolean var4, String var5, String var6) throws NotFoundException;

   String getCurrentInitiatorIdentityDomain(String var1) throws InvalidCursorException;
}
