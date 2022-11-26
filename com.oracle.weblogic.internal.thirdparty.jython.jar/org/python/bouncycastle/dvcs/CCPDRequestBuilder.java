package org.python.bouncycastle.dvcs;

import org.python.bouncycastle.asn1.dvcs.DVCSRequestInformationBuilder;
import org.python.bouncycastle.asn1.dvcs.Data;
import org.python.bouncycastle.asn1.dvcs.ServiceType;

public class CCPDRequestBuilder extends DVCSRequestBuilder {
   public CCPDRequestBuilder() {
      super(new DVCSRequestInformationBuilder(ServiceType.CCPD));
   }

   public DVCSRequest build(MessageImprint var1) throws DVCSException {
      Data var2 = new Data(var1.toASN1Structure());
      return this.createDVCRequest(var2);
   }
}
