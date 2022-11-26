package org.python.bouncycastle.tsp.cms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.BEROctetString;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.cms.Evidence;
import org.python.bouncycastle.asn1.cms.TimeStampAndCRL;
import org.python.bouncycastle.asn1.cms.TimeStampTokenEvidence;
import org.python.bouncycastle.asn1.cms.TimeStampedData;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.tsp.TimeStampToken;
import org.python.bouncycastle.util.io.Streams;

public class CMSTimeStampedDataGenerator extends CMSTimeStampedGenerator {
   public CMSTimeStampedData generate(TimeStampToken var1) throws CMSException {
      return this.generate(var1, (InputStream)null);
   }

   public CMSTimeStampedData generate(TimeStampToken var1, byte[] var2) throws CMSException {
      return this.generate(var1, (InputStream)(new ByteArrayInputStream(var2)));
   }

   public CMSTimeStampedData generate(TimeStampToken var1, InputStream var2) throws CMSException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      if (var2 != null) {
         try {
            Streams.pipeAll(var2, var3);
         } catch (IOException var7) {
            throw new CMSException("exception encapsulating content: " + var7.getMessage(), var7);
         }
      }

      BEROctetString var4 = null;
      if (var3.size() != 0) {
         var4 = new BEROctetString(var3.toByteArray());
      }

      TimeStampAndCRL var5 = new TimeStampAndCRL(var1.toCMSSignedData().toASN1Structure());
      DERIA5String var6 = null;
      if (this.dataUri != null) {
         var6 = new DERIA5String(this.dataUri.toString());
      }

      return new CMSTimeStampedData(new ContentInfo(CMSObjectIdentifiers.timestampedData, new TimeStampedData(var6, this.metaData, var4, new Evidence(new TimeStampTokenEvidence(var5)))));
   }
}
