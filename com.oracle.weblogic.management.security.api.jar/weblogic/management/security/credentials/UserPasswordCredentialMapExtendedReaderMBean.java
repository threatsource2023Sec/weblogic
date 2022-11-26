package weblogic.management.security.credentials;

import weblogic.management.utils.InvalidCursorException;

public interface UserPasswordCredentialMapExtendedReaderMBean extends UserPasswordCredentialMapReaderMBean {
   String listMappingsByPattern(String var1, int var2);

   String getCurrentMappingResourceID(String var1) throws InvalidCursorException;
}
