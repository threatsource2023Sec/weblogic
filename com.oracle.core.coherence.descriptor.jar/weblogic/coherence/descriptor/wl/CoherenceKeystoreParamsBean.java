package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceKeystoreParamsBean extends SettableBean {
   String getCoherenceIdentityAlias();

   void setCoherenceIdentityAlias(String var1);

   String getCoherencePrivateKeyPassPhrase();

   void setCoherencePrivateKeyPassPhrase(String var1);

   byte[] getCoherencePrivateKeyPassPhraseEncrypted();

   void setCoherencePrivateKeyPassPhraseEncrypted(byte[] var1);
}
