package org.python.bouncycastle.tsp.cms;

import java.net.URI;
import org.python.bouncycastle.asn1.ASN1Boolean;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.cms.Attributes;
import org.python.bouncycastle.asn1.cms.MetaData;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.operator.DigestCalculator;

public class CMSTimeStampedGenerator {
   protected MetaData metaData;
   protected URI dataUri;

   public void setDataUri(URI var1) {
      this.dataUri = var1;
   }

   public void setMetaData(boolean var1, String var2, String var3) {
      this.setMetaData(var1, (String)var2, (String)var3, (Attributes)null);
   }

   public void setMetaData(boolean var1, String var2, String var3, Attributes var4) {
      DERUTF8String var5 = null;
      if (var2 != null) {
         var5 = new DERUTF8String(var2);
      }

      DERIA5String var6 = null;
      if (var3 != null) {
         var6 = new DERIA5String(var3);
      }

      this.setMetaData(var1, var5, var6, var4);
   }

   private void setMetaData(boolean var1, DERUTF8String var2, DERIA5String var3, Attributes var4) {
      this.metaData = new MetaData(ASN1Boolean.getInstance(var1), var2, var3, var4);
   }

   public void initialiseMessageImprintDigestCalculator(DigestCalculator var1) throws CMSException {
      MetaDataUtil var2 = new MetaDataUtil(this.metaData);
      var2.initialiseMessageImprintDigestCalculator(var1);
   }
}
