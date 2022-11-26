package org.python.bouncycastle.x509.extension;

import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1String;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.X509Extension;
import org.python.bouncycastle.util.Integers;

public class X509ExtensionUtil {
   public static ASN1Primitive fromExtensionValue(byte[] var0) throws IOException {
      ASN1OctetString var1 = (ASN1OctetString)ASN1Primitive.fromByteArray(var0);
      return ASN1Primitive.fromByteArray(var1.getOctets());
   }

   public static Collection getIssuerAlternativeNames(X509Certificate var0) throws CertificateParsingException {
      byte[] var1 = var0.getExtensionValue(X509Extension.issuerAlternativeName.getId());
      return getAlternativeNames(var1);
   }

   public static Collection getSubjectAlternativeNames(X509Certificate var0) throws CertificateParsingException {
      byte[] var1 = var0.getExtensionValue(X509Extension.subjectAlternativeName.getId());
      return getAlternativeNames(var1);
   }

   private static Collection getAlternativeNames(byte[] var0) throws CertificateParsingException {
      if (var0 == null) {
         return Collections.EMPTY_LIST;
      } else {
         try {
            ArrayList var1 = new ArrayList();

            ArrayList var4;
            for(Enumeration var2 = DERSequence.getInstance(fromExtensionValue(var0)).getObjects(); var2.hasMoreElements(); var1.add(var4)) {
               GeneralName var3 = GeneralName.getInstance(var2.nextElement());
               var4 = new ArrayList();
               var4.add(Integers.valueOf(var3.getTagNo()));
               switch (var3.getTagNo()) {
                  case 0:
                  case 3:
                  case 5:
                     var4.add(var3.getName().toASN1Primitive());
                     break;
                  case 1:
                  case 2:
                  case 6:
                     var4.add(((ASN1String)var3.getName()).getString());
                     break;
                  case 4:
                     var4.add(X500Name.getInstance(var3.getName()).toString());
                     break;
                  case 7:
                     var4.add(DEROctetString.getInstance(var3.getName()).getOctets());
                     break;
                  case 8:
                     var4.add(ASN1ObjectIdentifier.getInstance(var3.getName()).getId());
                     break;
                  default:
                     throw new IOException("Bad tag number: " + var3.getTagNo());
               }
            }

            return Collections.unmodifiableCollection(var1);
         } catch (Exception var5) {
            throw new CertificateParsingException(var5.getMessage());
         }
      }
   }
}
