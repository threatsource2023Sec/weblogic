package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import org.python.bouncycastle.util.Arrays;

public final class SessionParameters {
   private int cipherSuite;
   private short compressionAlgorithm;
   private byte[] masterSecret;
   private Certificate peerCertificate;
   private byte[] pskIdentity;
   private byte[] srpIdentity;
   private byte[] encodedServerExtensions;

   private SessionParameters(int var1, short var2, byte[] var3, Certificate var4, byte[] var5, byte[] var6, byte[] var7) {
      this.pskIdentity = null;
      this.srpIdentity = null;
      this.cipherSuite = var1;
      this.compressionAlgorithm = var2;
      this.masterSecret = Arrays.clone(var3);
      this.peerCertificate = var4;
      this.pskIdentity = Arrays.clone(var5);
      this.srpIdentity = Arrays.clone(var6);
      this.encodedServerExtensions = var7;
   }

   public void clear() {
      if (this.masterSecret != null) {
         Arrays.fill((byte[])this.masterSecret, (byte)0);
      }

   }

   public SessionParameters copy() {
      return new SessionParameters(this.cipherSuite, this.compressionAlgorithm, this.masterSecret, this.peerCertificate, this.pskIdentity, this.srpIdentity, this.encodedServerExtensions);
   }

   public int getCipherSuite() {
      return this.cipherSuite;
   }

   public short getCompressionAlgorithm() {
      return this.compressionAlgorithm;
   }

   public byte[] getMasterSecret() {
      return this.masterSecret;
   }

   public Certificate getPeerCertificate() {
      return this.peerCertificate;
   }

   /** @deprecated */
   public byte[] getPskIdentity() {
      return this.pskIdentity;
   }

   public byte[] getPSKIdentity() {
      return this.pskIdentity;
   }

   public byte[] getSRPIdentity() {
      return this.srpIdentity;
   }

   public Hashtable readServerExtensions() throws IOException {
      if (this.encodedServerExtensions == null) {
         return null;
      } else {
         ByteArrayInputStream var1 = new ByteArrayInputStream(this.encodedServerExtensions);
         return TlsProtocol.readExtensions(var1);
      }
   }

   // $FF: synthetic method
   SessionParameters(int var1, short var2, byte[] var3, Certificate var4, byte[] var5, byte[] var6, byte[] var7, Object var8) {
      this(var1, var2, var3, var4, var5, var6, var7);
   }

   public static final class Builder {
      private int cipherSuite = -1;
      private short compressionAlgorithm = -1;
      private byte[] masterSecret = null;
      private Certificate peerCertificate = null;
      private byte[] pskIdentity = null;
      private byte[] srpIdentity = null;
      private byte[] encodedServerExtensions = null;

      public SessionParameters build() {
         this.validate(this.cipherSuite >= 0, "cipherSuite");
         this.validate(this.compressionAlgorithm >= 0, "compressionAlgorithm");
         this.validate(this.masterSecret != null, "masterSecret");
         return new SessionParameters(this.cipherSuite, this.compressionAlgorithm, this.masterSecret, this.peerCertificate, this.pskIdentity, this.srpIdentity, this.encodedServerExtensions);
      }

      public Builder setCipherSuite(int var1) {
         this.cipherSuite = var1;
         return this;
      }

      public Builder setCompressionAlgorithm(short var1) {
         this.compressionAlgorithm = var1;
         return this;
      }

      public Builder setMasterSecret(byte[] var1) {
         this.masterSecret = var1;
         return this;
      }

      public Builder setPeerCertificate(Certificate var1) {
         this.peerCertificate = var1;
         return this;
      }

      /** @deprecated */
      public Builder setPskIdentity(byte[] var1) {
         this.pskIdentity = var1;
         return this;
      }

      public Builder setPSKIdentity(byte[] var1) {
         this.pskIdentity = var1;
         return this;
      }

      public Builder setSRPIdentity(byte[] var1) {
         this.srpIdentity = var1;
         return this;
      }

      public Builder setServerExtensions(Hashtable var1) throws IOException {
         if (var1 == null) {
            this.encodedServerExtensions = null;
         } else {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            TlsProtocol.writeExtensions(var2, var1);
            this.encodedServerExtensions = var2.toByteArray();
         }

         return this;
      }

      private void validate(boolean var1, String var2) {
         if (!var1) {
            throw new IllegalStateException("Required session parameter '" + var2 + "' not configured");
         }
      }
   }
}
