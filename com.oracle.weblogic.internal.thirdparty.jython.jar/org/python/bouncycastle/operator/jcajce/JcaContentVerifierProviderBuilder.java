package org.python.bouncycastle.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.ContentVerifier;
import org.python.bouncycastle.operator.ContentVerifierProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.OperatorStreamException;
import org.python.bouncycastle.operator.RawContentVerifier;
import org.python.bouncycastle.operator.RuntimeOperatorException;

public class JcaContentVerifierProviderBuilder {
   private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());

   public JcaContentVerifierProviderBuilder setProvider(Provider var1) {
      this.helper = new OperatorHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public JcaContentVerifierProviderBuilder setProvider(String var1) {
      this.helper = new OperatorHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public ContentVerifierProvider build(X509CertificateHolder var1) throws OperatorCreationException, CertificateException {
      return this.build(this.helper.convertCertificate(var1));
   }

   public ContentVerifierProvider build(final X509Certificate var1) throws OperatorCreationException {
      final JcaX509CertificateHolder var2;
      try {
         var2 = new JcaX509CertificateHolder(var1);
      } catch (CertificateEncodingException var4) {
         throw new OperatorCreationException("cannot process certificate: " + var4.getMessage(), var4);
      }

      return new ContentVerifierProvider() {
         private SignatureOutputStream stream;

         public boolean hasAssociatedCertificate() {
            return true;
         }

         public X509CertificateHolder getAssociatedCertificate() {
            return var2;
         }

         public ContentVerifier get(AlgorithmIdentifier var1x) throws OperatorCreationException {
            Signature var2x;
            try {
               var2x = JcaContentVerifierProviderBuilder.this.helper.createSignature(var1x);
               var2x.initVerify(var1.getPublicKey());
               this.stream = JcaContentVerifierProviderBuilder.this.new SignatureOutputStream(var2x);
            } catch (GeneralSecurityException var3) {
               throw new OperatorCreationException("exception on setup: " + var3, var3);
            }

            var2x = JcaContentVerifierProviderBuilder.this.createRawSig(var1x, var1.getPublicKey());
            return (ContentVerifier)(var2x != null ? JcaContentVerifierProviderBuilder.this.new RawSigVerifier(var1x, this.stream, var2x) : JcaContentVerifierProviderBuilder.this.new SigVerifier(var1x, this.stream));
         }
      };
   }

   public ContentVerifierProvider build(final PublicKey var1) throws OperatorCreationException {
      return new ContentVerifierProvider() {
         public boolean hasAssociatedCertificate() {
            return false;
         }

         public X509CertificateHolder getAssociatedCertificate() {
            return null;
         }

         public ContentVerifier get(AlgorithmIdentifier var1x) throws OperatorCreationException {
            SignatureOutputStream var2 = JcaContentVerifierProviderBuilder.this.createSignatureStream(var1x, var1);
            Signature var3 = JcaContentVerifierProviderBuilder.this.createRawSig(var1x, var1);
            return (ContentVerifier)(var3 != null ? JcaContentVerifierProviderBuilder.this.new RawSigVerifier(var1x, var2, var3) : JcaContentVerifierProviderBuilder.this.new SigVerifier(var1x, var2));
         }
      };
   }

   public ContentVerifierProvider build(SubjectPublicKeyInfo var1) throws OperatorCreationException {
      return this.build(this.helper.convertPublicKey(var1));
   }

   private SignatureOutputStream createSignatureStream(AlgorithmIdentifier var1, PublicKey var2) throws OperatorCreationException {
      try {
         Signature var3 = this.helper.createSignature(var1);
         var3.initVerify(var2);
         return new SignatureOutputStream(var3);
      } catch (GeneralSecurityException var4) {
         throw new OperatorCreationException("exception on setup: " + var4, var4);
      }
   }

   private Signature createRawSig(AlgorithmIdentifier var1, PublicKey var2) {
      Signature var3;
      try {
         var3 = this.helper.createRawSignature(var1);
         if (var3 != null) {
            var3.initVerify(var2);
         }
      } catch (Exception var5) {
         var3 = null;
      }

      return var3;
   }

   private class RawSigVerifier extends SigVerifier implements RawContentVerifier {
      private Signature rawSignature;

      RawSigVerifier(AlgorithmIdentifier var2, SignatureOutputStream var3, Signature var4) {
         super(var2, var3);
         this.rawSignature = var4;
      }

      public boolean verify(byte[] var1) {
         boolean var2;
         try {
            var2 = super.verify(var1);
         } finally {
            try {
               this.rawSignature.verify(var1);
            } catch (Exception var9) {
            }

         }

         return var2;
      }

      public boolean verify(byte[] var1, byte[] var2) {
         boolean var3;
         try {
            this.rawSignature.update(var1);
            var3 = this.rawSignature.verify(var2);
         } catch (SignatureException var12) {
            throw new RuntimeOperatorException("exception obtaining raw signature: " + var12.getMessage(), var12);
         } finally {
            try {
               this.stream.verify(var2);
            } catch (Exception var11) {
            }

         }

         return var3;
      }
   }

   private class SigVerifier implements ContentVerifier {
      private AlgorithmIdentifier algorithm;
      protected SignatureOutputStream stream;

      SigVerifier(AlgorithmIdentifier var2, SignatureOutputStream var3) {
         this.algorithm = var2;
         this.stream = var3;
      }

      public AlgorithmIdentifier getAlgorithmIdentifier() {
         return this.algorithm;
      }

      public OutputStream getOutputStream() {
         if (this.stream == null) {
            throw new IllegalStateException("verifier not initialised");
         } else {
            return this.stream;
         }
      }

      public boolean verify(byte[] var1) {
         try {
            return this.stream.verify(var1);
         } catch (SignatureException var3) {
            throw new RuntimeOperatorException("exception obtaining signature: " + var3.getMessage(), var3);
         }
      }
   }

   private class SignatureOutputStream extends OutputStream {
      private Signature sig;

      SignatureOutputStream(Signature var2) {
         this.sig = var2;
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         try {
            this.sig.update(var1, var2, var3);
         } catch (SignatureException var5) {
            throw new OperatorStreamException("exception in content signer: " + var5.getMessage(), var5);
         }
      }

      public void write(byte[] var1) throws IOException {
         try {
            this.sig.update(var1);
         } catch (SignatureException var3) {
            throw new OperatorStreamException("exception in content signer: " + var3.getMessage(), var3);
         }
      }

      public void write(int var1) throws IOException {
         try {
            this.sig.update((byte)var1);
         } catch (SignatureException var3) {
            throw new OperatorStreamException("exception in content signer: " + var3.getMessage(), var3);
         }
      }

      boolean verify(byte[] var1) throws SignatureException {
         return this.sig.verify(var1);
      }
   }
}
