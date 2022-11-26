package org.python.bouncycastle.operator.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.operator.ContentVerifier;
import org.python.bouncycastle.operator.ContentVerifierProvider;
import org.python.bouncycastle.operator.OperatorCreationException;

public abstract class BcContentVerifierProviderBuilder {
   protected BcDigestProvider digestProvider;

   public BcContentVerifierProviderBuilder() {
      this.digestProvider = BcDefaultDigestProvider.INSTANCE;
   }

   public ContentVerifierProvider build(final X509CertificateHolder var1) throws OperatorCreationException {
      return new ContentVerifierProvider() {
         public boolean hasAssociatedCertificate() {
            return true;
         }

         public X509CertificateHolder getAssociatedCertificate() {
            return var1;
         }

         public ContentVerifier get(AlgorithmIdentifier var1x) throws OperatorCreationException {
            try {
               AsymmetricKeyParameter var2 = BcContentVerifierProviderBuilder.this.extractKeyParameters(var1.getSubjectPublicKeyInfo());
               BcSignerOutputStream var3 = BcContentVerifierProviderBuilder.this.createSignatureStream(var1x, var2);
               return BcContentVerifierProviderBuilder.this.new SigVerifier(var1x, var3);
            } catch (IOException var4) {
               throw new OperatorCreationException("exception on setup: " + var4, var4);
            }
         }
      };
   }

   public ContentVerifierProvider build(final AsymmetricKeyParameter var1) throws OperatorCreationException {
      return new ContentVerifierProvider() {
         public boolean hasAssociatedCertificate() {
            return false;
         }

         public X509CertificateHolder getAssociatedCertificate() {
            return null;
         }

         public ContentVerifier get(AlgorithmIdentifier var1x) throws OperatorCreationException {
            BcSignerOutputStream var2 = BcContentVerifierProviderBuilder.this.createSignatureStream(var1x, var1);
            return BcContentVerifierProviderBuilder.this.new SigVerifier(var1x, var2);
         }
      };
   }

   private BcSignerOutputStream createSignatureStream(AlgorithmIdentifier var1, AsymmetricKeyParameter var2) throws OperatorCreationException {
      Signer var3 = this.createSigner(var1);
      var3.init(false, var2);
      return new BcSignerOutputStream(var3);
   }

   protected abstract AsymmetricKeyParameter extractKeyParameters(SubjectPublicKeyInfo var1) throws IOException;

   protected abstract Signer createSigner(AlgorithmIdentifier var1) throws OperatorCreationException;

   private class SigVerifier implements ContentVerifier {
      private BcSignerOutputStream stream;
      private AlgorithmIdentifier algorithm;

      SigVerifier(AlgorithmIdentifier var2, BcSignerOutputStream var3) {
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
         return this.stream.verify(var1);
      }
   }
}
