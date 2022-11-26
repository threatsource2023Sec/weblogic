package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.agreement.srp.SRP6Client;
import org.python.bouncycastle.crypto.agreement.srp.SRP6Server;
import org.python.bouncycastle.crypto.agreement.srp.SRP6Util;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.SRP6GroupParameters;
import org.python.bouncycastle.crypto.util.PublicKeyFactory;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.BigIntegers;
import org.python.bouncycastle.util.io.TeeInputStream;

public class TlsSRPKeyExchange extends AbstractTlsKeyExchange {
   protected TlsSigner tlsSigner;
   protected TlsSRPGroupVerifier groupVerifier;
   protected byte[] identity;
   protected byte[] password;
   protected AsymmetricKeyParameter serverPublicKey;
   protected SRP6GroupParameters srpGroup;
   protected SRP6Client srpClient;
   protected SRP6Server srpServer;
   protected BigInteger srpPeerCredentials;
   protected BigInteger srpVerifier;
   protected byte[] srpSalt;
   protected TlsSignerCredentials serverCredentials;

   protected static TlsSigner createSigner(int var0) {
      switch (var0) {
         case 21:
            return null;
         case 22:
            return new TlsDSSSigner();
         case 23:
            return new TlsRSASigner();
         default:
            throw new IllegalArgumentException("unsupported key exchange algorithm");
      }
   }

   /** @deprecated */
   public TlsSRPKeyExchange(int var1, Vector var2, byte[] var3, byte[] var4) {
      this(var1, var2, new DefaultTlsSRPGroupVerifier(), var3, var4);
   }

   public TlsSRPKeyExchange(int var1, Vector var2, TlsSRPGroupVerifier var3, byte[] var4, byte[] var5) {
      super(var1, var2);
      this.serverPublicKey = null;
      this.srpGroup = null;
      this.srpClient = null;
      this.srpServer = null;
      this.srpPeerCredentials = null;
      this.srpVerifier = null;
      this.srpSalt = null;
      this.serverCredentials = null;
      this.tlsSigner = createSigner(var1);
      this.groupVerifier = var3;
      this.identity = var4;
      this.password = var5;
      this.srpClient = new SRP6Client();
   }

   public TlsSRPKeyExchange(int var1, Vector var2, byte[] var3, TlsSRPLoginParameters var4) {
      super(var1, var2);
      this.serverPublicKey = null;
      this.srpGroup = null;
      this.srpClient = null;
      this.srpServer = null;
      this.srpPeerCredentials = null;
      this.srpVerifier = null;
      this.srpSalt = null;
      this.serverCredentials = null;
      this.tlsSigner = createSigner(var1);
      this.identity = var3;
      this.srpServer = new SRP6Server();
      this.srpGroup = var4.getGroup();
      this.srpVerifier = var4.getVerifier();
      this.srpSalt = var4.getSalt();
   }

   public void init(TlsContext var1) {
      super.init(var1);
      if (this.tlsSigner != null) {
         this.tlsSigner.init(var1);
      }

   }

   public void skipServerCredentials() throws IOException {
      if (this.tlsSigner != null) {
         throw new TlsFatalAlert((short)10);
      }
   }

   public void processServerCertificate(Certificate var1) throws IOException {
      if (this.tlsSigner == null) {
         throw new TlsFatalAlert((short)10);
      } else if (var1.isEmpty()) {
         throw new TlsFatalAlert((short)42);
      } else {
         org.python.bouncycastle.asn1.x509.Certificate var2 = var1.getCertificateAt(0);
         SubjectPublicKeyInfo var3 = var2.getSubjectPublicKeyInfo();

         try {
            this.serverPublicKey = PublicKeyFactory.createKey(var3);
         } catch (RuntimeException var5) {
            throw new TlsFatalAlert((short)43, var5);
         }

         if (!this.tlsSigner.isValidPublicKey(this.serverPublicKey)) {
            throw new TlsFatalAlert((short)46);
         } else {
            TlsUtils.validateKeyUsage(var2, 128);
            super.processServerCertificate(var1);
         }
      }
   }

   public void processServerCredentials(TlsCredentials var1) throws IOException {
      if (this.keyExchange != 21 && var1 instanceof TlsSignerCredentials) {
         this.processServerCertificate(var1.getCertificate());
         this.serverCredentials = (TlsSignerCredentials)var1;
      } else {
         throw new TlsFatalAlert((short)80);
      }
   }

   public boolean requiresServerKeyExchange() {
      return true;
   }

   public byte[] generateServerKeyExchange() throws IOException {
      this.srpServer.init(this.srpGroup, this.srpVerifier, TlsUtils.createHash((short)2), this.context.getSecureRandom());
      BigInteger var1 = this.srpServer.generateServerCredentials();
      ServerSRPParams var2 = new ServerSRPParams(this.srpGroup.getN(), this.srpGroup.getG(), this.srpSalt, var1);
      DigestInputBuffer var3 = new DigestInputBuffer();
      var2.encode(var3);
      if (this.serverCredentials != null) {
         SignatureAndHashAlgorithm var4 = TlsUtils.getSignatureAndHashAlgorithm(this.context, this.serverCredentials);
         Digest var5 = TlsUtils.createHash(var4);
         SecurityParameters var6 = this.context.getSecurityParameters();
         var5.update(var6.clientRandom, 0, var6.clientRandom.length);
         var5.update(var6.serverRandom, 0, var6.serverRandom.length);
         var3.updateDigest(var5);
         byte[] var7 = new byte[var5.getDigestSize()];
         var5.doFinal(var7, 0);
         byte[] var8 = this.serverCredentials.generateCertificateSignature(var7);
         DigitallySigned var9 = new DigitallySigned(var4, var8);
         var9.encode(var3);
      }

      return var3.toByteArray();
   }

   public void processServerKeyExchange(InputStream var1) throws IOException {
      SecurityParameters var2 = this.context.getSecurityParameters();
      SignerInputBuffer var3 = null;
      Object var4 = var1;
      if (this.tlsSigner != null) {
         var3 = new SignerInputBuffer();
         var4 = new TeeInputStream(var1, var3);
      }

      ServerSRPParams var5 = ServerSRPParams.parse((InputStream)var4);
      if (var3 != null) {
         DigitallySigned var6 = this.parseSignature(var1);
         Signer var7 = this.initVerifyer(this.tlsSigner, var6.getAlgorithm(), var2);
         var3.updateSigner(var7);
         if (!var7.verifySignature(var6.getSignature())) {
            throw new TlsFatalAlert((short)51);
         }
      }

      this.srpGroup = new SRP6GroupParameters(var5.getN(), var5.getG());
      if (!this.groupVerifier.accept(this.srpGroup)) {
         throw new TlsFatalAlert((short)71);
      } else {
         this.srpSalt = var5.getS();

         try {
            this.srpPeerCredentials = SRP6Util.validatePublicValue(this.srpGroup.getN(), var5.getB());
         } catch (CryptoException var8) {
            throw new TlsFatalAlert((short)47, var8);
         }

         this.srpClient.init(this.srpGroup, TlsUtils.createHash((short)2), this.context.getSecureRandom());
      }
   }

   public void validateCertificateRequest(CertificateRequest var1) throws IOException {
      throw new TlsFatalAlert((short)10);
   }

   public void processClientCredentials(TlsCredentials var1) throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   public void generateClientKeyExchange(OutputStream var1) throws IOException {
      BigInteger var2 = this.srpClient.generateClientCredentials(this.srpSalt, this.identity, this.password);
      TlsSRPUtils.writeSRPParameter(var2, var1);
      this.context.getSecurityParameters().srpIdentity = Arrays.clone(this.identity);
   }

   public void processClientKeyExchange(InputStream var1) throws IOException {
      try {
         this.srpPeerCredentials = SRP6Util.validatePublicValue(this.srpGroup.getN(), TlsSRPUtils.readSRPParameter(var1));
      } catch (CryptoException var3) {
         throw new TlsFatalAlert((short)47, var3);
      }

      this.context.getSecurityParameters().srpIdentity = Arrays.clone(this.identity);
   }

   public byte[] generatePremasterSecret() throws IOException {
      try {
         BigInteger var1 = this.srpServer != null ? this.srpServer.calculateSecret(this.srpPeerCredentials) : this.srpClient.calculateSecret(this.srpPeerCredentials);
         return BigIntegers.asUnsignedByteArray(var1);
      } catch (CryptoException var2) {
         throw new TlsFatalAlert((short)47, var2);
      }
   }

   protected Signer initVerifyer(TlsSigner var1, SignatureAndHashAlgorithm var2, SecurityParameters var3) {
      Signer var4 = var1.createVerifyer(var2, this.serverPublicKey);
      var4.update(var3.clientRandom, 0, var3.clientRandom.length);
      var4.update(var3.serverRandom, 0, var3.serverRandom.length);
      return var4;
   }
}
