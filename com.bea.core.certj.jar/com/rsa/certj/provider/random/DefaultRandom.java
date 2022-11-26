package com.rsa.certj.provider.random;

import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.spi.random.RandomInterface;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_SecureRandom;

/** @deprecated */
public final class DefaultRandom extends Provider {
   private static final int SEED_APP = 0;
   private static final int SEED_OS = 2;
   private byte[] seed;

   /** @deprecated */
   public DefaultRandom(String var1) throws InvalidParameterException {
      super(0, var1);
   }

   /** @deprecated */
   public DefaultRandom(String var1, long var2) throws InvalidParameterException {
      super(0, var1);
      this.seed = new byte[8];

      for(int var4 = 0; var4 < 8; ++var4) {
         this.seed[7 - var4] = (byte)((int)(255L & var2));
         var2 >>= 8;
      }

   }

   /** @deprecated */
   public DefaultRandom(String var1, byte[] var2) throws InvalidParameterException {
      super(0, var1);
      this.seed = var2;
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException("DefaultRandom.instantiate.", var3);
      }
   }

   private final class a extends ProviderImplementation implements RandomInterface {
      private JSAFE_SecureRandom b;
      private int c;

      private a(CertJ var2, String var3) throws InvalidParameterException {
         super(var2, var3);
         this.a();
         if (DefaultRandom.this.seed != null) {
            this.c = 0;
            this.b.seed(DefaultRandom.this.seed);
         }

         this.updateRandom();
      }

      private void a() {
         this.b = h.a(this.context);
         this.c = 2;
      }

      public JSAFE_SecureRandom getRandomObject() {
         return this.b;
      }

      public void updateRandom() {
         switch (this.c) {
            case 2:
               if (!this.b.getAlgorithm().equals("FIPS186Random")) {
                  this.b.autoseed();
               }
            case 0:
            default:
         }
      }

      public String toString() {
         return "Default Random provider named: " + super.getName();
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException {
         this(var2, var3);
      }
   }
}
