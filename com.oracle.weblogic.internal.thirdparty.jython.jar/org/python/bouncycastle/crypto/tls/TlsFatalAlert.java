package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

public class TlsFatalAlert extends IOException {
   private static final long serialVersionUID = 3584313123679111168L;
   protected short alertDescription;
   protected Throwable alertCause;

   public TlsFatalAlert(short var1) {
      this(var1, (Throwable)null);
   }

   public TlsFatalAlert(short var1, Throwable var2) {
      super(AlertDescription.getText(var1));
      this.alertDescription = var1;
      this.alertCause = var2;
   }

   public short getAlertDescription() {
      return this.alertDescription;
   }

   public Throwable getCause() {
      return this.alertCause;
   }
}
