package org.cryptacular.spec;

import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.Grain128Engine;
import org.bouncycastle.crypto.engines.HC128Engine;
import org.bouncycastle.crypto.engines.HC256Engine;
import org.bouncycastle.crypto.engines.ISAACEngine;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.crypto.engines.VMPCEngine;

public class StreamCipherSpec implements Spec {
   private final String algorithm;

   public StreamCipherSpec(String algName) {
      this.algorithm = algName;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public StreamCipher newInstance() {
      Object cipher;
      if (!"Grainv1".equalsIgnoreCase(this.algorithm) && !"Grain-v1".equalsIgnoreCase(this.algorithm)) {
         if (!"Grain128".equalsIgnoreCase(this.algorithm) && !"Grain-128".equalsIgnoreCase(this.algorithm)) {
            if ("ISAAC".equalsIgnoreCase(this.algorithm)) {
               cipher = new ISAACEngine();
            } else if ("HC128".equalsIgnoreCase(this.algorithm)) {
               cipher = new HC128Engine();
            } else if ("HC256".equalsIgnoreCase(this.algorithm)) {
               cipher = new HC256Engine();
            } else if ("RC4".equalsIgnoreCase(this.algorithm)) {
               cipher = new RC4Engine();
            } else if ("Salsa20".equalsIgnoreCase(this.algorithm)) {
               cipher = new Salsa20Engine();
            } else {
               if (!"VMPC".equalsIgnoreCase(this.algorithm)) {
                  throw new IllegalStateException("Unsupported cipher algorithm " + this.algorithm);
               }

               cipher = new VMPCEngine();
            }
         } else {
            cipher = new Grain128Engine();
         }
      } else {
         cipher = new ISAACEngine();
      }

      return (StreamCipher)cipher;
   }

   public String toString() {
      return this.algorithm;
   }
}
