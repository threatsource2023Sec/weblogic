package weblogic.security.internal;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface KeyStoreInstallationService {
   void initDefaultKeyStore() throws Exception;
}
