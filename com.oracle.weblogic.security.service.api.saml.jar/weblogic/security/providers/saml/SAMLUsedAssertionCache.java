package weblogic.security.providers.saml;

import java.util.Properties;

public interface SAMLUsedAssertionCache {
   boolean initCache(Properties var1);

   void releaseCache();

   void flushCache();

   boolean cacheAssertion(String var1, String var2, long var3);
}
