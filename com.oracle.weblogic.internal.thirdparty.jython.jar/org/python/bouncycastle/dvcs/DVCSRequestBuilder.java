package org.python.bouncycastle.dvcs;

import java.io.IOException;
import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.dvcs.DVCSObjectIdentifiers;
import org.python.bouncycastle.asn1.dvcs.DVCSRequestInformationBuilder;
import org.python.bouncycastle.asn1.dvcs.Data;
import org.python.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralNames;
import org.python.bouncycastle.cms.CMSSignedDataGenerator;

public abstract class DVCSRequestBuilder {
   private final ExtensionsGenerator extGenerator = new ExtensionsGenerator();
   private final CMSSignedDataGenerator signedDataGen = new CMSSignedDataGenerator();
   protected final DVCSRequestInformationBuilder requestInformationBuilder;

   protected DVCSRequestBuilder(DVCSRequestInformationBuilder var1) {
      this.requestInformationBuilder = var1;
   }

   public void setNonce(BigInteger var1) {
      this.requestInformationBuilder.setNonce(var1);
   }

   public void setRequester(GeneralName var1) {
      this.requestInformationBuilder.setRequester(var1);
   }

   public void setDVCS(GeneralName var1) {
      this.requestInformationBuilder.setDVCS(var1);
   }

   public void setDVCS(GeneralNames var1) {
      this.requestInformationBuilder.setDVCS(var1);
   }

   public void setDataLocations(GeneralName var1) {
      this.requestInformationBuilder.setDataLocations(var1);
   }

   public void setDataLocations(GeneralNames var1) {
      this.requestInformationBuilder.setDataLocations(var1);
   }

   public void addExtension(ASN1ObjectIdentifier var1, boolean var2, ASN1Encodable var3) throws DVCSException {
      try {
         this.extGenerator.addExtension(var1, var2, var3);
      } catch (IOException var5) {
         throw new DVCSException("cannot encode extension: " + var5.getMessage(), var5);
      }
   }

   protected DVCSRequest createDVCRequest(Data var1) throws DVCSException {
      if (!this.extGenerator.isEmpty()) {
         this.requestInformationBuilder.setExtensions(this.extGenerator.generate());
      }

      org.python.bouncycastle.asn1.dvcs.DVCSRequest var2 = new org.python.bouncycastle.asn1.dvcs.DVCSRequest(this.requestInformationBuilder.build(), var1);
      return new DVCSRequest(new ContentInfo(DVCSObjectIdentifiers.id_ct_DVCSRequestData, var2));
   }
}
