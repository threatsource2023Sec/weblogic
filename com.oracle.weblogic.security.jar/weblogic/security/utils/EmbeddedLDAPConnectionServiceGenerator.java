package weblogic.security.utils;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface EmbeddedLDAPConnectionServiceGenerator {
   EmbeddedLDAPConnectionService createEmbeddedLDAPConnectionService(boolean var1, boolean var2, boolean var3);
}
