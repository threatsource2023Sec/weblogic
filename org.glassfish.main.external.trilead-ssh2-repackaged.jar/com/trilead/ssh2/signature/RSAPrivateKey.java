package com.trilead.ssh2.signature;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAPrivateKey {
   private BigInteger d;
   private BigInteger e;
   private BigInteger n;

   public RSAPrivateKey(BigInteger d, BigInteger e, BigInteger n) {
      this.d = d;
      this.e = e;
      this.n = n;
   }

   public BigInteger getD() {
      return this.d;
   }

   public BigInteger getE() {
      return this.e;
   }

   public BigInteger getN() {
      return this.n;
   }

   public RSAPublicKey getPublicKey() {
      return new RSAPublicKey(this.e, this.n);
   }

   public KeyPair toJCEKeyPair() throws GeneralSecurityException {
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return new KeyPair(kf.generatePublic(new RSAPublicKeySpec(this.getN(), this.getE())), kf.generatePrivate(new RSAPrivateKeySpec(this.getN(), this.getD())));
   }
}
