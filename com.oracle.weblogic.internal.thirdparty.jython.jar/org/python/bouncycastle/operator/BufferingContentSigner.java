package org.python.bouncycastle.operator;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.util.io.BufferingOutputStream;

public class BufferingContentSigner implements ContentSigner {
   private final ContentSigner contentSigner;
   private final OutputStream output;

   public BufferingContentSigner(ContentSigner var1) {
      this.contentSigner = var1;
      this.output = new BufferingOutputStream(var1.getOutputStream());
   }

   public BufferingContentSigner(ContentSigner var1, int var2) {
      this.contentSigner = var1;
      this.output = new BufferingOutputStream(var1.getOutputStream(), var2);
   }

   public AlgorithmIdentifier getAlgorithmIdentifier() {
      return this.contentSigner.getAlgorithmIdentifier();
   }

   public OutputStream getOutputStream() {
      return this.output;
   }

   public byte[] getSignature() {
      return this.contentSigner.getSignature();
   }
}
