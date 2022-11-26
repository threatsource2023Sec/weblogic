package weblogic.management.security.credentials;

import java.security.cert.Certificate;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.ListerMBean;
import weblogic.management.utils.NotFoundException;

public interface PKICredentialMapReaderMBean extends ListerMBean {
   String getKeystoreAlias(String var1, String var2, boolean var3, String var4, String var5) throws NotFoundException;

   String getCurrentInitiatorName(String var1) throws InvalidCursorException;

   boolean isInitiatorUserName(String var1) throws InvalidCursorException;

   String getCurrentCredAction(String var1) throws InvalidCursorException;

   String getCurrentCredential(String var1) throws InvalidCursorException;

   String listMappings(String var1, String var2);

   String getCurrentResourceId(String var1) throws InvalidCursorException;

   String listMappingsByPattern(String var1, int var2, String var3);

   String[] listAllCertEntryAliases() throws NotFoundException;

   String[] listAllKeypairEntryAliases() throws NotFoundException;

   Certificate getCertificate(String var1) throws NotFoundException;
}
