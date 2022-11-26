package org.python.bouncycastle.cms;

import java.util.ArrayList;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.cms.OriginatorInfo;
import org.python.bouncycastle.asn1.x509.Certificate;
import org.python.bouncycastle.asn1.x509.CertificateList;
import org.python.bouncycastle.cert.X509CRLHolder;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.util.CollectionStore;
import org.python.bouncycastle.util.Store;

public class OriginatorInformation {
   private OriginatorInfo originatorInfo;

   OriginatorInformation(OriginatorInfo var1) {
      this.originatorInfo = var1;
   }

   public Store getCertificates() {
      ASN1Set var1 = this.originatorInfo.getCertificates();
      if (var1 != null) {
         ArrayList var2 = new ArrayList(var1.size());
         Enumeration var3 = var1.getObjects();

         while(var3.hasMoreElements()) {
            ASN1Primitive var4 = ((ASN1Encodable)var3.nextElement()).toASN1Primitive();
            if (var4 instanceof ASN1Sequence) {
               var2.add(new X509CertificateHolder(Certificate.getInstance(var4)));
            }
         }

         return new CollectionStore(var2);
      } else {
         return new CollectionStore(new ArrayList());
      }
   }

   public Store getCRLs() {
      ASN1Set var1 = this.originatorInfo.getCRLs();
      if (var1 != null) {
         ArrayList var2 = new ArrayList(var1.size());
         Enumeration var3 = var1.getObjects();

         while(var3.hasMoreElements()) {
            ASN1Primitive var4 = ((ASN1Encodable)var3.nextElement()).toASN1Primitive();
            if (var4 instanceof ASN1Sequence) {
               var2.add(new X509CRLHolder(CertificateList.getInstance(var4)));
            }
         }

         return new CollectionStore(var2);
      } else {
         return new CollectionStore(new ArrayList());
      }
   }

   public OriginatorInfo toASN1Structure() {
      return this.originatorInfo;
   }
}
