package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.util.PublicKeyFactory;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.io.Streams;

public class TlsPSKKeyExchange extends AbstractTlsKeyExchange {
   protected TlsPSKIdentity pskIdentity;
   protected TlsPSKIdentityManager pskIdentityManager;
   protected DHParameters dhParameters;
   protected int[] namedCurves;
   protected short[] clientECPointFormats;
   protected short[] serverECPointFormats;
   protected byte[] psk_identity_hint = null;
   protected byte[] psk = null;
   protected DHPrivateKeyParameters dhAgreePrivateKey = null;
   protected DHPublicKeyParameters dhAgreePublicKey = null;
   protected ECPrivateKeyParameters ecAgreePrivateKey = null;
   protected ECPublicKeyParameters ecAgreePublicKey = null;
   protected AsymmetricKeyParameter serverPublicKey = null;
   protected RSAKeyParameters rsaServerPublicKey = null;
   protected TlsEncryptionCredentials serverCredentials = null;
   protected byte[] premasterSecret;

   public TlsPSKKeyExchange(int var1, Vector var2, TlsPSKIdentity var3, TlsPSKIdentityManager var4, DHParameters var5, int[] var6, short[] var7, short[] var8) {
      super(var1, var2);
      switch (var1) {
         case 13:
         case 14:
         case 15:
         case 24:
            this.pskIdentity = var3;
            this.pskIdentityManager = var4;
            this.dhParameters = var5;
            this.namedCurves = var6;
            this.clientECPointFormats = var7;
            this.serverECPointFormats = var8;
            return;
         default:
            throw new IllegalArgumentException("unsupported key exchange algorithm");
      }
   }

   public void skipServerCredentials() throws IOException {
      if (this.keyExchange == 15) {
         throw new TlsFatalAlert((short)10);
      }
   }

   public void processServerCredentials(TlsCredentials var1) throws IOException {
      if (!(var1 instanceof TlsEncryptionCredentials)) {
         throw new TlsFatalAlert((short)80);
      } else {
         this.processServerCertificate(var1.getCertificate());
         this.serverCredentials = (TlsEncryptionCredentials)var1;
      }
   }

   public byte[] generateServerKeyExchange() throws IOException {
      this.psk_identity_hint = this.pskIdentityManager.getHint();
      if (this.psk_identity_hint == null && !this.requiresServerKeyExchange()) {
         return null;
      } else {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         if (this.psk_identity_hint == null) {
            TlsUtils.writeOpaque16(TlsUtils.EMPTY_BYTES, var1);
         } else {
            TlsUtils.writeOpaque16(this.psk_identity_hint, var1);
         }

         if (this.keyExchange == 14) {
            if (this.dhParameters == null) {
               throw new TlsFatalAlert((short)80);
            }

            this.dhAgreePrivateKey = TlsDHUtils.generateEphemeralServerKeyExchange(this.context.getSecureRandom(), this.dhParameters, var1);
         } else if (this.keyExchange == 24) {
            this.ecAgreePrivateKey = TlsECCUtils.generateEphemeralServerKeyExchange(this.context.getSecureRandom(), this.namedCurves, this.clientECPointFormats, var1);
         }

         return var1.toByteArray();
      }
   }

   public void processServerCertificate(Certificate var1) throws IOException {
      if (this.keyExchange != 15) {
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

         if (this.serverPublicKey.isPrivate()) {
            throw new TlsFatalAlert((short)80);
         } else {
            this.rsaServerPublicKey = this.validateRSAPublicKey((RSAKeyParameters)this.serverPublicKey);
            TlsUtils.validateKeyUsage(var2, 32);
            super.processServerCertificate(var1);
         }
      }
   }

   public boolean requiresServerKeyExchange() {
      switch (this.keyExchange) {
         case 14:
         case 24:
            return true;
         default:
            return false;
      }
   }

   public void processServerKeyExchange(InputStream var1) throws IOException {
      this.psk_identity_hint = TlsUtils.readOpaque16(var1);
      if (this.keyExchange == 14) {
         ServerDHParams var2 = ServerDHParams.parse(var1);
         this.dhAgreePublicKey = TlsDHUtils.validateDHPublicKey(var2.getPublicKey());
         this.dhParameters = this.dhAgreePublicKey.getParameters();
      } else if (this.keyExchange == 24) {
         ECDomainParameters var4 = TlsECCUtils.readECParameters(this.namedCurves, this.clientECPointFormats, var1);
         byte[] var3 = TlsUtils.readOpaque8(var1);
         this.ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(this.clientECPointFormats, var4, var3));
      }

   }

   public void validateCertificateRequest(CertificateRequest var1) throws IOException {
      throw new TlsFatalAlert((short)10);
   }

   public void processClientCredentials(TlsCredentials var1) throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   public void generateClientKeyExchange(OutputStream var1) throws IOException {
      if (this.psk_identity_hint == null) {
         this.pskIdentity.skipIdentityHint();
      } else {
         this.pskIdentity.notifyIdentityHint(this.psk_identity_hint);
      }

      byte[] var2 = this.pskIdentity.getPSKIdentity();
      if (var2 == null) {
         throw new TlsFatalAlert((short)80);
      } else {
         this.psk = this.pskIdentity.getPSK();
         if (this.psk == null) {
            throw new TlsFatalAlert((short)80);
         } else {
            TlsUtils.writeOpaque16(var2, var1);
            this.context.getSecurityParameters().pskIdentity = Arrays.clone(var2);
            if (this.keyExchange == 14) {
               this.dhAgreePrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), this.dhParameters, var1);
            } else if (this.keyExchange == 24) {
               this.ecAgreePrivateKey = TlsECCUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), this.serverECPointFormats, this.ecAgreePublicKey.getParameters(), var1);
            } else if (this.keyExchange == 15) {
               this.premasterSecret = TlsRSAUtils.generateEncryptedPreMasterSecret(this.context, this.rsaServerPublicKey, var1);
            }

         }
      }
   }

   public void processClientKeyExchange(InputStream var1) throws IOException {
      byte[] var2 = TlsUtils.readOpaque16(var1);
      this.psk = this.pskIdentityManager.getPSK(var2);
      if (this.psk == null) {
         throw new TlsFatalAlert((short)115);
      } else {
         this.context.getSecurityParameters().pskIdentity = var2;
         if (this.keyExchange == 14) {
            BigInteger var3 = TlsDHUtils.readDHParameter(var1);
            this.dhAgreePublicKey = TlsDHUtils.validateDHPublicKey(new DHPublicKeyParameters(var3, this.dhParameters));
         } else {
            byte[] var5;
            if (this.keyExchange == 24) {
               var5 = TlsUtils.readOpaque8(var1);
               ECDomainParameters var4 = this.ecAgreePrivateKey.getParameters();
               this.ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(this.serverECPointFormats, var4, var5));
            } else if (this.keyExchange == 15) {
               if (TlsUtils.isSSL(this.context)) {
                  var5 = Streams.readAll(var1);
               } else {
                  var5 = TlsUtils.readOpaque16(var1);
               }

               this.premasterSecret = this.serverCredentials.decryptPreMasterSecret(var5);
            }
         }

      }
   }

   public byte[] generatePremasterSecret() throws IOException {
      byte[] var1 = this.generateOtherSecret(this.psk.length);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(4 + var1.length + this.psk.length);
      TlsUtils.writeOpaque16(var1, var2);
      TlsUtils.writeOpaque16(this.psk, var2);
      Arrays.fill((byte[])this.psk, (byte)0);
      this.psk = null;
      return var2.toByteArray();
   }

   protected byte[] generateOtherSecret(int var1) throws IOException {
      if (this.keyExchange == 14) {
         if (this.dhAgreePrivateKey != null) {
            return TlsDHUtils.calculateDHBasicAgreement(this.dhAgreePublicKey, this.dhAgreePrivateKey);
         } else {
            throw new TlsFatalAlert((short)80);
         }
      } else if (this.keyExchange == 24) {
         if (this.ecAgreePrivateKey != null) {
            return TlsECCUtils.calculateECDHBasicAgreement(this.ecAgreePublicKey, this.ecAgreePrivateKey);
         } else {
            throw new TlsFatalAlert((short)80);
         }
      } else {
         return this.keyExchange == 15 ? this.premasterSecret : new byte[var1];
      }
   }

   protected RSAKeyParameters validateRSAPublicKey(RSAKeyParameters var1) throws IOException {
      if (!var1.getExponent().isProbablePrime(2)) {
         throw new TlsFatalAlert((short)47);
      } else {
         return var1;
      }
   }
}
