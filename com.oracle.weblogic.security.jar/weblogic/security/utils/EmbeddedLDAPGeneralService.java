package weblogic.security.utils;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface EmbeddedLDAPGeneralService {
   String getEmbeddedLDAPHost();

   int getEmbeddedLDAPPort();

   boolean getEmbeddedLDAPUseSSL();
}
