package org.python.bouncycastle.eac;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.DERApplicationSpecific;
import org.python.bouncycastle.asn1.eac.CVCertificate;
import org.python.bouncycastle.asn1.eac.CertificateBody;
import org.python.bouncycastle.asn1.eac.CertificateHolderAuthorization;
import org.python.bouncycastle.asn1.eac.CertificateHolderReference;
import org.python.bouncycastle.asn1.eac.CertificationAuthorityReference;
import org.python.bouncycastle.asn1.eac.PackedDate;
import org.python.bouncycastle.asn1.eac.PublicKeyDataObject;
import org.python.bouncycastle.eac.operator.EACSigner;

public class EACCertificateBuilder {
   private static final byte[] ZeroArray = new byte[]{0};
   private PublicKeyDataObject publicKey;
   private CertificateHolderAuthorization certificateHolderAuthorization;
   private PackedDate certificateEffectiveDate;
   private PackedDate certificateExpirationDate;
   private CertificateHolderReference certificateHolderReference;
   private CertificationAuthorityReference certificationAuthorityReference;

   public EACCertificateBuilder(CertificationAuthorityReference var1, PublicKeyDataObject var2, CertificateHolderReference var3, CertificateHolderAuthorization var4, PackedDate var5, PackedDate var6) {
      this.certificationAuthorityReference = var1;
      this.publicKey = var2;
      this.certificateHolderReference = var3;
      this.certificateHolderAuthorization = var4;
      this.certificateEffectiveDate = var5;
      this.certificateExpirationDate = var6;
   }

   private CertificateBody buildBody() {
      DERApplicationSpecific var1 = new DERApplicationSpecific(41, ZeroArray);
      CertificateBody var2 = new CertificateBody(var1, this.certificationAuthorityReference, this.publicKey, this.certificateHolderReference, this.certificateHolderAuthorization, this.certificateEffectiveDate, this.certificateExpirationDate);
      return var2;
   }

   public EACCertificateHolder build(EACSigner var1) throws EACException {
      try {
         CertificateBody var2 = this.buildBody();
         OutputStream var3 = var1.getOutputStream();
         var3.write(var2.getEncoded("DER"));
         var3.close();
         return new EACCertificateHolder(new CVCertificate(var2, var1.getSignature()));
      } catch (Exception var4) {
         throw new EACException("unable to process signature: " + var4.getMessage(), var4);
      }
   }
}
