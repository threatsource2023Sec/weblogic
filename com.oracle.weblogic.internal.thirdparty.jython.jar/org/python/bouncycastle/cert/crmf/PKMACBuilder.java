package org.python.bouncycastle.cert.crmf;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.cmp.CMPObjectIdentifiers;
import org.python.bouncycastle.asn1.cmp.PBMParameter;
import org.python.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.RuntimeOperatorException;
import org.python.bouncycastle.util.Strings;

public class PKMACBuilder {
   private AlgorithmIdentifier owf;
   private int iterationCount;
   private AlgorithmIdentifier mac;
   private int saltLength;
   private SecureRandom random;
   private PKMACValuesCalculator calculator;
   private PBMParameter parameters;
   private int maxIterations;

   public PKMACBuilder(PKMACValuesCalculator var1) {
      this(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1), 1000, new AlgorithmIdentifier(IANAObjectIdentifiers.hmacSHA1, DERNull.INSTANCE), var1);
   }

   public PKMACBuilder(PKMACValuesCalculator var1, int var2) {
      this.saltLength = 20;
      this.maxIterations = var2;
      this.calculator = var1;
   }

   private PKMACBuilder(AlgorithmIdentifier var1, int var2, AlgorithmIdentifier var3, PKMACValuesCalculator var4) {
      this.saltLength = 20;
      this.owf = var1;
      this.iterationCount = var2;
      this.mac = var3;
      this.calculator = var4;
   }

   public PKMACBuilder setSaltLength(int var1) {
      if (var1 < 8) {
         throw new IllegalArgumentException("salt length must be at least 8 bytes");
      } else {
         this.saltLength = var1;
         return this;
      }
   }

   public PKMACBuilder setIterationCount(int var1) {
      if (var1 < 100) {
         throw new IllegalArgumentException("iteration count must be at least 100");
      } else {
         this.checkIterationCountCeiling(var1);
         this.iterationCount = var1;
         return this;
      }
   }

   public PKMACBuilder setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public PKMACBuilder setParameters(PBMParameter var1) {
      this.checkIterationCountCeiling(var1.getIterationCount().getValue().intValue());
      this.parameters = var1;
      return this;
   }

   public MacCalculator build(char[] var1) throws CRMFException {
      if (this.parameters != null) {
         return this.genCalculator(this.parameters, var1);
      } else {
         byte[] var2 = new byte[this.saltLength];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var2);
         return this.genCalculator(new PBMParameter(var2, this.owf, this.iterationCount, this.mac), var1);
      }
   }

   private void checkIterationCountCeiling(int var1) {
      if (this.maxIterations > 0 && var1 > this.maxIterations) {
         throw new IllegalArgumentException("iteration count exceeds limit (" + var1 + " > " + this.maxIterations + ")");
      }
   }

   private MacCalculator genCalculator(final PBMParameter var1, char[] var2) throws CRMFException {
      byte[] var3 = Strings.toUTF8ByteArray(var2);
      byte[] var4 = var1.getSalt().getOctets();
      final byte[] var5 = new byte[var3.length + var4.length];
      System.arraycopy(var3, 0, var5, 0, var3.length);
      System.arraycopy(var4, 0, var5, var3.length, var4.length);
      this.calculator.setup(var1.getOwf(), var1.getMac());
      int var6 = var1.getIterationCount().getValue().intValue();

      do {
         var5 = this.calculator.calculateDigest(var5);
         --var6;
      } while(var6 > 0);

      return new MacCalculator() {
         ByteArrayOutputStream bOut = new ByteArrayOutputStream();

         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return new AlgorithmIdentifier(CMPObjectIdentifiers.passwordBasedMac, var1);
         }

         public GenericKey getKey() {
            return new GenericKey(this.getAlgorithmIdentifier(), var5);
         }

         public OutputStream getOutputStream() {
            return this.bOut;
         }

         public byte[] getMac() {
            try {
               return PKMACBuilder.this.calculator.calculateMac(var5, this.bOut.toByteArray());
            } catch (CRMFException var2) {
               throw new RuntimeOperatorException("exception calculating mac: " + var2.getMessage(), var2);
            }
         }
      };
   }
}
