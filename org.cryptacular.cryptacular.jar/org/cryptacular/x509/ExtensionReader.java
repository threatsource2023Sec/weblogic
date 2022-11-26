package org.cryptacular.x509;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.cryptacular.EncodingException;

public final class ExtensionReader {
   private final X509Certificate certificate;

   public ExtensionReader(X509Certificate cert) {
      this.certificate = cert;
   }

   public ASN1Encodable read(String extensionOidOrName) throws EncodingException {
      if (extensionOidOrName == null) {
         throw new IllegalArgumentException("extensionOidOrName cannot be null.");
      } else {
         return extensionOidOrName.contains(".") ? this.read(ExtensionType.fromOid(extensionOidOrName)) : this.read(ExtensionType.fromName(extensionOidOrName));
      }
   }

   public ASN1Encodable read(ExtensionType extension) {
      byte[] data = this.certificate.getExtensionValue(extension.getOid());
      if (data == null) {
         return null;
      } else {
         try {
            ASN1Encodable der = ASN1Primitive.fromByteArray(data);
            if (der instanceof ASN1OctetString) {
               data = ((ASN1OctetString)der).getOctets();
               der = ASN1Primitive.fromByteArray(data);
            }

            return der;
         } catch (Exception var4) {
            throw new EncodingException("ASN.1 parse error", var4);
         }
      }
   }

   public GeneralNames readSubjectAlternativeName() throws EncodingException {
      try {
         return GeneralNames.getInstance(this.read(ExtensionType.SubjectAlternativeName));
      } catch (RuntimeException var2) {
         throw new EncodingException("GeneralNames parse error", var2);
      }
   }

   public GeneralNames readIssuerAlternativeName() throws EncodingException {
      try {
         return GeneralNames.getInstance(this.read(ExtensionType.IssuerAlternativeName));
      } catch (RuntimeException var2) {
         throw new EncodingException("GeneralNames parse error", var2);
      }
   }

   public BasicConstraints readBasicConstraints() throws EncodingException {
      try {
         return BasicConstraints.getInstance(this.read(ExtensionType.BasicConstraints));
      } catch (RuntimeException var2) {
         throw new EncodingException("BasicConstraints parse error", var2);
      }
   }

   public List readCertificatePolicies() throws EncodingException {
      ASN1Encodable data = this.read(ExtensionType.CertificatePolicies);
      if (data == null) {
         return null;
      } else {
         try {
            ASN1Sequence sequence = ASN1Sequence.getInstance(data);
            List list = new ArrayList(sequence.size());

            for(int i = 0; i < sequence.size(); ++i) {
               list.add(PolicyInformation.getInstance(sequence.getObjectAt(i)));
            }

            return list;
         } catch (RuntimeException var5) {
            throw new EncodingException("PolicyInformation parse error", var5);
         }
      }
   }

   public SubjectKeyIdentifier readSubjectKeyIdentifier() throws EncodingException {
      try {
         return SubjectKeyIdentifier.getInstance(this.read(ExtensionType.SubjectKeyIdentifier));
      } catch (RuntimeException var2) {
         throw new EncodingException("SubjectKeyIdentifier parse error", var2);
      }
   }

   public AuthorityKeyIdentifier readAuthorityKeyIdentifier() throws EncodingException {
      try {
         return AuthorityKeyIdentifier.getInstance(this.read(ExtensionType.AuthorityKeyIdentifier));
      } catch (RuntimeException var2) {
         throw new EncodingException("AuthorityKeyIdentifier parse error", var2);
      }
   }

   public KeyUsage readKeyUsage() throws EncodingException {
      try {
         return KeyUsage.getInstance(this.read(ExtensionType.KeyUsage));
      } catch (RuntimeException var2) {
         throw new EncodingException("KeyUsage parse error", var2);
      }
   }

   public List readExtendedKeyUsage() throws EncodingException {
      ASN1Encodable data = this.read(ExtensionType.ExtendedKeyUsage);
      if (data == null) {
         return null;
      } else {
         try {
            ASN1Sequence sequence = ASN1Sequence.getInstance(data);
            List list = new ArrayList(sequence.size());

            for(int i = 0; i < sequence.size(); ++i) {
               list.add(KeyPurposeId.getInstance(sequence.getObjectAt(i)));
            }

            return list;
         } catch (RuntimeException var5) {
            throw new EncodingException("KeyPurposeId parse error", var5);
         }
      }
   }

   public List readCRLDistributionPoints() throws EncodingException {
      ASN1Encodable data = this.read(ExtensionType.CRLDistributionPoints);
      if (data == null) {
         return null;
      } else {
         try {
            ASN1Sequence sequence = ASN1Sequence.getInstance(data);
            List list = new ArrayList(sequence.size());

            for(int i = 0; i < sequence.size(); ++i) {
               list.add(DistributionPoint.getInstance(sequence.getObjectAt(i)));
            }

            return list;
         } catch (RuntimeException var5) {
            throw new EncodingException("DistributionPoint parse error", var5);
         }
      }
   }

   public List readAuthorityInformationAccess() throws EncodingException {
      ASN1Encodable data = this.read(ExtensionType.AuthorityInformationAccess);
      if (data == null) {
         return null;
      } else {
         try {
            ASN1Sequence sequence = ASN1Sequence.getInstance(data);
            List list = new ArrayList(sequence.size());

            for(int i = 0; i < sequence.size(); ++i) {
               list.add(AccessDescription.getInstance(sequence.getObjectAt(i)));
            }

            return list;
         } catch (RuntimeException var5) {
            throw new EncodingException("AccessDescription parse error", var5);
         }
      }
   }
}
