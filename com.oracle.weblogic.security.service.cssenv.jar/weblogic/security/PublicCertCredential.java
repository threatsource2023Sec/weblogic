package weblogic.security;

import java.security.cert.Certificate;

public final class PublicCertCredential {
   private Certificate _publicCert;
   boolean _isDisposed = false;

   public PublicCertCredential(Certificate certificate) {
      this._publicCert = certificate;
   }

   public Certificate getCertificate() {
      return this._publicCert;
   }

   public void dispose() {
      this._publicCert = null;
      this._isDisposed = true;
   }

   public boolean isDisposed() {
      return this._isDisposed;
   }

   protected void finalize() {
      this.dispose();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[PublicCertCredential; ");
      if (this._isDisposed) {
         buf.append("(disposed)");
      } else {
         buf.append("\n    certificate:\n\n");
         buf.append(this._publicCert + "\n\n");
      }

      buf.append("]");
      return buf.toString();
   }
}
