package org.python.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.BEROctetString;
import org.python.bouncycastle.asn1.BERSet;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.cms.AuthenticatedData;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.io.TeeOutputStream;

public class CMSAuthenticatedDataGenerator extends CMSAuthenticatedGenerator {
   public CMSAuthenticatedData generate(CMSTypedData var1, MacCalculator var2) throws CMSException {
      return this.generate(var1, var2, (DigestCalculator)null);
   }

   public CMSAuthenticatedData generate(CMSTypedData var1, MacCalculator var2, final DigestCalculator var3) throws CMSException {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      Iterator var5 = this.recipientInfoGenerators.iterator();

      while(var5.hasNext()) {
         RecipientInfoGenerator var6 = (RecipientInfoGenerator)var5.next();
         var4.add(var6.generate(var2.getKey()));
      }

      TeeOutputStream var7;
      BEROctetString var8;
      DEROctetString var10;
      AuthenticatedData var15;
      ByteArrayOutputStream var16;
      if (var3 != null) {
         try {
            var16 = new ByteArrayOutputStream();
            var7 = new TeeOutputStream(var3.getOutputStream(), var16);
            var1.write(var7);
            var7.close();
            var8 = new BEROctetString(var16.toByteArray());
         } catch (IOException var14) {
            throw new CMSException("unable to perform digest calculation: " + var14.getMessage(), var14);
         }

         Map var17 = this.getBaseParameters(var1.getContentType(), var3.getAlgorithmIdentifier(), var2.getAlgorithmIdentifier(), var3.getDigest());
         if (this.authGen == null) {
            this.authGen = new DefaultAuthenticatedAttributeTableGenerator();
         }

         DERSet var19 = new DERSet(this.authGen.getAttributes(Collections.unmodifiableMap(var17)).toASN1EncodableVector());

         try {
            OutputStream var9 = var2.getOutputStream();
            var9.write(var19.getEncoded("DER"));
            var9.close();
            var10 = new DEROctetString(var2.getMac());
         } catch (IOException var13) {
            throw new CMSException("exception decoding algorithm parameters.", var13);
         }

         BERSet var22 = this.unauthGen != null ? new BERSet(this.unauthGen.getAttributes(Collections.unmodifiableMap(var17)).toASN1EncodableVector()) : null;
         ContentInfo var11 = new ContentInfo(CMSObjectIdentifiers.data, var8);
         var15 = new AuthenticatedData(this.originatorInfo, new DERSet(var4), var2.getAlgorithmIdentifier(), var3.getAlgorithmIdentifier(), var11, var19, var10, var22);
      } else {
         try {
            var16 = new ByteArrayOutputStream();
            var7 = new TeeOutputStream(var16, var2.getOutputStream());
            var1.write(var7);
            var7.close();
            var8 = new BEROctetString(var16.toByteArray());
            var10 = new DEROctetString(var2.getMac());
         } catch (IOException var12) {
            throw new CMSException("exception decoding algorithm parameters.", var12);
         }

         BERSet var18 = this.unauthGen != null ? new BERSet(this.unauthGen.getAttributes(new HashMap()).toASN1EncodableVector()) : null;
         ContentInfo var21 = new ContentInfo(CMSObjectIdentifiers.data, var8);
         var15 = new AuthenticatedData(this.originatorInfo, new DERSet(var4), var2.getAlgorithmIdentifier(), (AlgorithmIdentifier)null, var21, (ASN1Set)null, var10, var18);
      }

      ContentInfo var20 = new ContentInfo(CMSObjectIdentifiers.authenticatedData, var15);
      return new CMSAuthenticatedData(var20, new DigestCalculatorProvider() {
         public DigestCalculator get(AlgorithmIdentifier var1) throws OperatorCreationException {
            return var3;
         }
      });
   }
}
