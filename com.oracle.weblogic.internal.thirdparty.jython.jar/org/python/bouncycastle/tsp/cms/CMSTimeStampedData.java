package org.python.bouncycastle.tsp.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.cms.Evidence;
import org.python.bouncycastle.asn1.cms.TimeStampAndCRL;
import org.python.bouncycastle.asn1.cms.TimeStampTokenEvidence;
import org.python.bouncycastle.asn1.cms.TimeStampedData;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.tsp.TimeStampToken;

public class CMSTimeStampedData {
   private TimeStampedData timeStampedData;
   private ContentInfo contentInfo;
   private TimeStampDataUtil util;

   public CMSTimeStampedData(ContentInfo var1) {
      this.initialize(var1);
   }

   public CMSTimeStampedData(InputStream var1) throws IOException {
      try {
         this.initialize(ContentInfo.getInstance((new ASN1InputStream(var1)).readObject()));
      } catch (ClassCastException var3) {
         throw new IOException("Malformed content: " + var3);
      } catch (IllegalArgumentException var4) {
         throw new IOException("Malformed content: " + var4);
      }
   }

   public CMSTimeStampedData(byte[] var1) throws IOException {
      this((InputStream)(new ByteArrayInputStream(var1)));
   }

   private void initialize(ContentInfo var1) {
      this.contentInfo = var1;
      if (CMSObjectIdentifiers.timestampedData.equals(var1.getContentType())) {
         this.timeStampedData = TimeStampedData.getInstance(var1.getContent());
         this.util = new TimeStampDataUtil(this.timeStampedData);
      } else {
         throw new IllegalArgumentException("Malformed content - type must be " + CMSObjectIdentifiers.timestampedData.getId());
      }
   }

   public byte[] calculateNextHash(DigestCalculator var1) throws CMSException {
      return this.util.calculateNextHash(var1);
   }

   public CMSTimeStampedData addTimeStamp(TimeStampToken var1) throws CMSException {
      TimeStampAndCRL[] var2 = this.util.getTimeStamps();
      TimeStampAndCRL[] var3 = new TimeStampAndCRL[var2.length + 1];
      System.arraycopy(var2, 0, var3, 0, var2.length);
      var3[var2.length] = new TimeStampAndCRL(var1.toCMSSignedData().toASN1Structure());
      return new CMSTimeStampedData(new ContentInfo(CMSObjectIdentifiers.timestampedData, new TimeStampedData(this.timeStampedData.getDataUri(), this.timeStampedData.getMetaData(), this.timeStampedData.getContent(), new Evidence(new TimeStampTokenEvidence(var3)))));
   }

   public byte[] getContent() {
      return this.timeStampedData.getContent() != null ? this.timeStampedData.getContent().getOctets() : null;
   }

   public URI getDataUri() throws URISyntaxException {
      DERIA5String var1 = this.timeStampedData.getDataUri();
      return var1 != null ? new URI(var1.getString()) : null;
   }

   public String getFileName() {
      return this.util.getFileName();
   }

   public String getMediaType() {
      return this.util.getMediaType();
   }

   public AttributeTable getOtherMetaData() {
      return this.util.getOtherMetaData();
   }

   public TimeStampToken[] getTimeStampTokens() throws CMSException {
      return this.util.getTimeStampTokens();
   }

   public void initialiseMessageImprintDigestCalculator(DigestCalculator var1) throws CMSException {
      this.util.initialiseMessageImprintDigestCalculator(var1);
   }

   public DigestCalculator getMessageImprintDigestCalculator(DigestCalculatorProvider var1) throws OperatorCreationException {
      return this.util.getMessageImprintDigestCalculator(var1);
   }

   public void validate(DigestCalculatorProvider var1, byte[] var2) throws ImprintDigestInvalidException, CMSException {
      this.util.validate(var1, var2);
   }

   public void validate(DigestCalculatorProvider var1, byte[] var2, TimeStampToken var3) throws ImprintDigestInvalidException, CMSException {
      this.util.validate(var1, var2, var3);
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }
}
