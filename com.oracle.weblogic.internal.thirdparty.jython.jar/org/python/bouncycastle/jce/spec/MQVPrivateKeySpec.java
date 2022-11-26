package org.python.bouncycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.python.bouncycastle.jce.interfaces.MQVPrivateKey;

/** @deprecated */
public class MQVPrivateKeySpec implements KeySpec, MQVPrivateKey {
   private PrivateKey staticPrivateKey;
   private PrivateKey ephemeralPrivateKey;
   private PublicKey ephemeralPublicKey;

   public MQVPrivateKeySpec(PrivateKey var1, PrivateKey var2) {
      this(var1, var2, (PublicKey)null);
   }

   public MQVPrivateKeySpec(PrivateKey var1, PrivateKey var2, PublicKey var3) {
      this.staticPrivateKey = var1;
      this.ephemeralPrivateKey = var2;
      this.ephemeralPublicKey = var3;
   }

   public PrivateKey getStaticPrivateKey() {
      return this.staticPrivateKey;
   }

   public PrivateKey getEphemeralPrivateKey() {
      return this.ephemeralPrivateKey;
   }

   public PublicKey getEphemeralPublicKey() {
      return this.ephemeralPublicKey;
   }

   public String getAlgorithm() {
      return "ECMQV";
   }

   public String getFormat() {
      return null;
   }

   public byte[] getEncoded() {
      return null;
   }
}
