package weblogic.security;

import java.security.Key;
import java.security.cert.Certificate;

public final class KeyPairCredential {
   private Certificate[] _certificateChain;
   private Key _privKey;
   boolean _isDisposed = false;

   public KeyPairCredential(Key key, Certificate[] certificate) {
      this._privKey = key;
      this._certificateChain = certificate;
   }

   public Certificate getCertificate() {
      return !this._isDisposed && this._certificateChain != null && this._certificateChain.length != 0 ? this._certificateChain[0] : null;
   }

   public Certificate[] getCertificateChain() {
      return this._certificateChain;
   }

   public Key getKey() {
      return this._privKey;
   }

   public void dispose() {
      this._certificateChain = null;
      this._privKey = null;
      this._isDisposed = true;
   }

   public boolean isDisposed() {
      return this._isDisposed;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[KeyPairCredential; ");
      if (this._isDisposed) {
         buf.append("(disposed)");
      } else if (this._certificateChain != null && this._certificateChain.length != 0) {
         buf.append("\n    certificate:\n\n");
         buf.append(this._certificateChain[0] + "\n\n");
      } else {
         buf.append("\n    chain: none");
      }

      buf.append("]");
      return buf.toString();
   }

   protected void finalize() {
      this.dispose();
   }
}
