package javax.security.auth.message.callback;

import java.security.KeyStore;
import javax.security.auth.callback.Callback;

public class TrustStoreCallback implements Callback {
   private KeyStore trustStore;

   public void setTrustStore(KeyStore trustStore) {
      this.trustStore = trustStore;
   }

   public KeyStore getTrustStore() {
      return this.trustStore;
   }
}
