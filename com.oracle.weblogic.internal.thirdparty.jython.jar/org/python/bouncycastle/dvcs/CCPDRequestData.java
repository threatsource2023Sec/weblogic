package org.python.bouncycastle.dvcs;

import org.python.bouncycastle.asn1.dvcs.Data;

public class CCPDRequestData extends DVCSRequestData {
   CCPDRequestData(Data var1) throws DVCSConstructionException {
      super(var1);
      this.initDigest();
   }

   private void initDigest() throws DVCSConstructionException {
      if (this.data.getMessageImprint() == null) {
         throw new DVCSConstructionException("DVCSRequest.data.messageImprint should be specified for CCPD service");
      }
   }

   public MessageImprint getMessageImprint() {
      return new MessageImprint(this.data.getMessageImprint());
   }
}
