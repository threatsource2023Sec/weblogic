package weblogic.security.SSL.jsseadapter;

import weblogic.security.SSL.SSLEngineFactory;
import weblogic.security.utils.SSLContextWrapper;

public final class JaSSLEngineFactoryBuilder {
   public static SSLEngineFactory getFactoryInstance(SSLContextWrapper context) {
      return new JaSSLEngineFactoryImpl(context);
   }
}
