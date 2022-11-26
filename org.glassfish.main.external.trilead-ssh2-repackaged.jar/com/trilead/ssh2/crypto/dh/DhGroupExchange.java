package com.trilead.ssh2.crypto.dh;

import com.trilead.ssh2.DHGexParameters;
import com.trilead.ssh2.crypto.digest.HashForSSH2Types;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DhGroupExchange {
   private BigInteger p;
   private BigInteger g;
   private BigInteger e;
   private BigInteger x;
   private BigInteger f;
   private BigInteger k;

   public DhGroupExchange(BigInteger p, BigInteger g) {
      this.p = p;
      this.g = g;
   }

   public void init(SecureRandom rnd) {
      this.k = null;
      this.x = new BigInteger(this.p.bitLength() - 1, rnd);
      this.e = this.g.modPow(this.x, this.p);
   }

   public BigInteger getE() {
      if (this.e == null) {
         throw new IllegalStateException("Not initialized!");
      } else {
         return this.e;
      }
   }

   public BigInteger getK() {
      if (this.k == null) {
         throw new IllegalStateException("Shared secret not yet known, need f first!");
      } else {
         return this.k;
      }
   }

   public void setF(BigInteger f) {
      if (this.e == null) {
         throw new IllegalStateException("Not initialized!");
      } else {
         BigInteger zero = BigInteger.valueOf(0L);
         if (zero.compareTo(f) < 0 && this.p.compareTo(f) > 0) {
            this.f = f;
            this.k = f.modPow(this.x, this.p);
         } else {
            throw new IllegalArgumentException("Invalid f specified!");
         }
      }
   }

   public byte[] calculateH(byte[] clientversion, byte[] serverversion, byte[] clientKexPayload, byte[] serverKexPayload, byte[] hostKey, DHGexParameters para) {
      HashForSSH2Types hash = new HashForSSH2Types("SHA1");
      hash.updateByteString(clientversion);
      hash.updateByteString(serverversion);
      hash.updateByteString(clientKexPayload);
      hash.updateByteString(serverKexPayload);
      hash.updateByteString(hostKey);
      if (para.getMin_group_len() > 0) {
         hash.updateUINT32(para.getMin_group_len());
      }

      hash.updateUINT32(para.getPref_group_len());
      if (para.getMax_group_len() > 0) {
         hash.updateUINT32(para.getMax_group_len());
      }

      hash.updateBigInt(this.p);
      hash.updateBigInt(this.g);
      hash.updateBigInt(this.e);
      hash.updateBigInt(this.f);
      hash.updateBigInt(this.k);
      return hash.getDigest();
   }
}
