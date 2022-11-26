package weblogic.security.providers.saml;

import java.util.Properties;
import org.w3c.dom.Element;

public interface SAMLAssertionStore {
   boolean initStore(Properties var1);

   void releaseStore();

   void flushStore();

   boolean storeAssertion(String var1, long var2, Element var4);

   Element retrieveAssertion(String var1);
}
