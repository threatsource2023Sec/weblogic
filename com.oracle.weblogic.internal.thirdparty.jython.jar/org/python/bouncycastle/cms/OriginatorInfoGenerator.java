package org.python.bouncycastle.cms;

import java.util.ArrayList;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.cms.OriginatorInfo;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.util.Store;

public class OriginatorInfoGenerator {
   private final List origCerts;
   private final List origCRLs;

   public OriginatorInfoGenerator(X509CertificateHolder var1) {
      this.origCerts = new ArrayList(1);
      this.origCRLs = null;
      this.origCerts.add(var1.toASN1Structure());
   }

   public OriginatorInfoGenerator(Store var1) throws CMSException {
      this(var1, (Store)null);
   }

   public OriginatorInfoGenerator(Store var1, Store var2) throws CMSException {
      this.origCerts = CMSUtils.getCertificatesFromStore(var1);
      if (var2 != null) {
         this.origCRLs = CMSUtils.getCRLsFromStore(var2);
      } else {
         this.origCRLs = null;
      }

   }

   public OriginatorInformation generate() {
      return this.origCRLs != null ? new OriginatorInformation(new OriginatorInfo(CMSUtils.createDerSetFromList(this.origCerts), CMSUtils.createDerSetFromList(this.origCRLs))) : new OriginatorInformation(new OriginatorInfo(CMSUtils.createDerSetFromList(this.origCerts), (ASN1Set)null));
   }
}
