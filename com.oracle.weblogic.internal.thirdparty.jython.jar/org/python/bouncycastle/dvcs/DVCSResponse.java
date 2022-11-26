package org.python.bouncycastle.dvcs;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.cms.SignedData;
import org.python.bouncycastle.asn1.dvcs.DVCSObjectIdentifiers;
import org.python.bouncycastle.cms.CMSSignedData;

public class DVCSResponse extends DVCSMessage {
   private org.python.bouncycastle.asn1.dvcs.DVCSResponse asn1;

   public DVCSResponse(CMSSignedData var1) throws DVCSConstructionException {
      this(SignedData.getInstance(var1.toASN1Structure().getContent()).getEncapContentInfo());
   }

   public DVCSResponse(ContentInfo var1) throws DVCSConstructionException {
      super(var1);
      if (!DVCSObjectIdentifiers.id_ct_DVCSResponseData.equals(var1.getContentType())) {
         throw new DVCSConstructionException("ContentInfo not a DVCS Response");
      } else {
         try {
            if (var1.getContent().toASN1Primitive() instanceof ASN1Sequence) {
               this.asn1 = org.python.bouncycastle.asn1.dvcs.DVCSResponse.getInstance(var1.getContent());
            } else {
               this.asn1 = org.python.bouncycastle.asn1.dvcs.DVCSResponse.getInstance(ASN1OctetString.getInstance(var1.getContent()).getOctets());
            }

         } catch (Exception var3) {
            throw new DVCSConstructionException("Unable to parse content: " + var3.getMessage(), var3);
         }
      }
   }

   public ASN1Encodable getContent() {
      return this.asn1;
   }
}
