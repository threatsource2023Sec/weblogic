package org.python.bouncycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.python.bouncycastle.jce.interfaces.IESKey;

public class IEKeySpec implements KeySpec, IESKey {
   private PublicKey pubKey;
   private PrivateKey privKey;

   public IEKeySpec(PrivateKey var1, PublicKey var2) {
      this.privKey = var1;
      this.pubKey = var2;
   }

   public PublicKey getPublic() {
      return this.pubKey;
   }

   public PrivateKey getPrivate() {
      return this.privKey;
   }

   public String getAlgorithm() {
      return "IES";
   }

   public String getFormat() {
      return null;
   }

   public byte[] getEncoded() {
      return null;
   }
}
