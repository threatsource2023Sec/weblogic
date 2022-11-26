package org.python.bouncycastle.tsp;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import org.python.bouncycastle.asn1.ASN1Boolean;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.ess.ESSCertID;
import org.python.bouncycastle.asn1.ess.ESSCertIDv2;
import org.python.bouncycastle.asn1.ess.SigningCertificate;
import org.python.bouncycastle.asn1.ess.SigningCertificateV2;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.tsp.Accuracy;
import org.python.bouncycastle.asn1.tsp.MessageImprint;
import org.python.bouncycastle.asn1.tsp.TSTInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.Extensions;
import org.python.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralNames;
import org.python.bouncycastle.asn1.x509.IssuerSerial;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cms.CMSAttributeTableGenerationException;
import org.python.bouncycastle.cms.CMSAttributeTableGenerator;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.CMSProcessableByteArray;
import org.python.bouncycastle.cms.CMSSignedData;
import org.python.bouncycastle.cms.CMSSignedDataGenerator;
import org.python.bouncycastle.cms.SignerInfoGenerator;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.util.CollectionStore;
import org.python.bouncycastle.util.Selector;
import org.python.bouncycastle.util.Store;

public class TimeStampTokenGenerator {
   public static final int R_SECONDS = 0;
   public static final int R_TENTHS_OF_SECONDS = 1;
   public static final int R_MICROSECONDS = 2;
   public static final int R_MILLISECONDS = 3;
   private int resolution;
   private Locale locale;
   private int accuracySeconds;
   private int accuracyMillis;
   private int accuracyMicros;
   boolean ordering;
   GeneralName tsa;
   private ASN1ObjectIdentifier tsaPolicyOID;
   private List certs;
   private List crls;
   private List attrCerts;
   private Map otherRevoc;
   private SignerInfoGenerator signerInfoGen;

   public TimeStampTokenGenerator(SignerInfoGenerator var1, DigestCalculator var2, ASN1ObjectIdentifier var3) throws IllegalArgumentException, TSPException {
      this(var1, var2, var3, false);
   }

   public TimeStampTokenGenerator(final SignerInfoGenerator var1, DigestCalculator var2, ASN1ObjectIdentifier var3, boolean var4) throws IllegalArgumentException, TSPException {
      this.resolution = 0;
      this.locale = null;
      this.accuracySeconds = -1;
      this.accuracyMillis = -1;
      this.accuracyMicros = -1;
      this.ordering = false;
      this.tsa = null;
      this.certs = new ArrayList();
      this.crls = new ArrayList();
      this.attrCerts = new ArrayList();
      this.otherRevoc = new HashMap();
      this.signerInfoGen = var1;
      this.tsaPolicyOID = var3;
      if (!var1.hasAssociatedCertificate()) {
         throw new IllegalArgumentException("SignerInfoGenerator must have an associated certificate");
      } else {
         X509CertificateHolder var5 = var1.getAssociatedCertificate();
         TSPUtil.validateCertificate(var5);

         try {
            OutputStream var6 = var2.getOutputStream();
            var6.write(var5.getEncoded());
            var6.close();
            if (var2.getAlgorithmIdentifier().getAlgorithm().equals(OIWObjectIdentifiers.idSHA1)) {
               final ESSCertID var7 = new ESSCertID(var2.getDigest(), var4 ? new IssuerSerial(new GeneralNames(new GeneralName(var5.getIssuer())), var5.getSerialNumber()) : null);
               this.signerInfoGen = new SignerInfoGenerator(var1, new CMSAttributeTableGenerator() {
                  public AttributeTable getAttributes(Map var1x) throws CMSAttributeTableGenerationException {
                     AttributeTable var2 = var1.getSignedAttributeTableGenerator().getAttributes(var1x);
                     return var2.get(PKCSObjectIdentifiers.id_aa_signingCertificate) == null ? var2.add(PKCSObjectIdentifiers.id_aa_signingCertificate, new SigningCertificate(var7)) : var2;
                  }
               }, var1.getUnsignedAttributeTableGenerator());
            } else {
               AlgorithmIdentifier var10 = new AlgorithmIdentifier(var2.getAlgorithmIdentifier().getAlgorithm());
               final ESSCertIDv2 var8 = new ESSCertIDv2(var10, var2.getDigest(), var4 ? new IssuerSerial(new GeneralNames(new GeneralName(var5.getIssuer())), new ASN1Integer(var5.getSerialNumber())) : null);
               this.signerInfoGen = new SignerInfoGenerator(var1, new CMSAttributeTableGenerator() {
                  public AttributeTable getAttributes(Map var1x) throws CMSAttributeTableGenerationException {
                     AttributeTable var2 = var1.getSignedAttributeTableGenerator().getAttributes(var1x);
                     return var2.get(PKCSObjectIdentifiers.id_aa_signingCertificateV2) == null ? var2.add(PKCSObjectIdentifiers.id_aa_signingCertificateV2, new SigningCertificateV2(var8)) : var2;
                  }
               }, var1.getUnsignedAttributeTableGenerator());
            }

         } catch (IOException var9) {
            throw new TSPException("Exception processing certificate.", var9);
         }
      }
   }

   public void addCertificates(Store var1) {
      this.certs.addAll(var1.getMatches((Selector)null));
   }

   public void addCRLs(Store var1) {
      this.crls.addAll(var1.getMatches((Selector)null));
   }

   public void addAttributeCertificates(Store var1) {
      this.attrCerts.addAll(var1.getMatches((Selector)null));
   }

   public void addOtherRevocationInfo(ASN1ObjectIdentifier var1, Store var2) {
      this.otherRevoc.put(var1, var2.getMatches((Selector)null));
   }

   public void setResolution(int var1) {
      this.resolution = var1;
   }

   public void setLocale(Locale var1) {
      this.locale = var1;
   }

   public void setAccuracySeconds(int var1) {
      this.accuracySeconds = var1;
   }

   public void setAccuracyMillis(int var1) {
      this.accuracyMillis = var1;
   }

   public void setAccuracyMicros(int var1) {
      this.accuracyMicros = var1;
   }

   public void setOrdering(boolean var1) {
      this.ordering = var1;
   }

   public void setTSA(GeneralName var1) {
      this.tsa = var1;
   }

   public TimeStampToken generate(TimeStampRequest var1, BigInteger var2, Date var3) throws TSPException {
      return this.generate(var1, var2, var3, (Extensions)null);
   }

   public TimeStampToken generate(TimeStampRequest var1, BigInteger var2, Date var3, Extensions var4) throws TSPException {
      ASN1ObjectIdentifier var5 = var1.getMessageImprintAlgOID();
      AlgorithmIdentifier var6 = new AlgorithmIdentifier(var5, DERNull.INSTANCE);
      MessageImprint var7 = new MessageImprint(var6, var1.getMessageImprintDigest());
      Accuracy var8 = null;
      ASN1Integer var10;
      if (this.accuracySeconds > 0 || this.accuracyMillis > 0 || this.accuracyMicros > 0) {
         ASN1Integer var9 = null;
         if (this.accuracySeconds > 0) {
            var9 = new ASN1Integer((long)this.accuracySeconds);
         }

         var10 = null;
         if (this.accuracyMillis > 0) {
            var10 = new ASN1Integer((long)this.accuracyMillis);
         }

         ASN1Integer var11 = null;
         if (this.accuracyMicros > 0) {
            var11 = new ASN1Integer((long)this.accuracyMicros);
         }

         var8 = new Accuracy(var9, var10, var11);
      }

      ASN1Boolean var20 = null;
      if (this.ordering) {
         var20 = ASN1Boolean.getInstance(this.ordering);
      }

      var10 = null;
      if (var1.getNonce() != null) {
         var10 = new ASN1Integer(var1.getNonce());
      }

      ASN1ObjectIdentifier var21 = this.tsaPolicyOID;
      if (var1.getReqPolicy() != null) {
         var21 = var1.getReqPolicy();
      }

      Extensions var12 = var1.getExtensions();
      if (var4 != null) {
         ExtensionsGenerator var13 = new ExtensionsGenerator();
         Enumeration var14;
         if (var12 != null) {
            var14 = var12.oids();

            while(var14.hasMoreElements()) {
               var13.addExtension(var12.getExtension(ASN1ObjectIdentifier.getInstance(var14.nextElement())));
            }
         }

         var14 = var4.oids();

         while(var14.hasMoreElements()) {
            var13.addExtension(var4.getExtension(ASN1ObjectIdentifier.getInstance(var14.nextElement())));
         }

         var12 = var13.generate();
      }

      ASN1GeneralizedTime var22;
      if (this.resolution == 0) {
         var22 = this.locale == null ? new ASN1GeneralizedTime(var3) : new ASN1GeneralizedTime(var3, this.locale);
      } else {
         var22 = this.createGeneralizedTime(var3);
      }

      TSTInfo var23 = new TSTInfo(var21, var7, new ASN1Integer(var2), var22, var8, var20, var10, this.tsa, var12);

      try {
         CMSSignedDataGenerator var15 = new CMSSignedDataGenerator();
         if (var1.getCertReq()) {
            var15.addCertificates(new CollectionStore(this.certs));
            var15.addAttributeCertificates(new CollectionStore(this.attrCerts));
         }

         var15.addCRLs(new CollectionStore(this.crls));
         if (!this.otherRevoc.isEmpty()) {
            Iterator var16 = this.otherRevoc.keySet().iterator();

            while(var16.hasNext()) {
               ASN1ObjectIdentifier var17 = (ASN1ObjectIdentifier)var16.next();
               var15.addOtherRevocationInfo(var17, new CollectionStore((Collection)this.otherRevoc.get(var17)));
            }
         }

         var15.addSignerInfoGenerator(this.signerInfoGen);
         byte[] var24 = var23.getEncoded("DER");
         CMSSignedData var25 = var15.generate(new CMSProcessableByteArray(PKCSObjectIdentifiers.id_ct_TSTInfo, var24), true);
         return new TimeStampToken(var25);
      } catch (CMSException var18) {
         throw new TSPException("Error generating time-stamp token", var18);
      } catch (IOException var19) {
         throw new TSPException("Exception encoding info", var19);
      }
   }

   private ASN1GeneralizedTime createGeneralizedTime(Date var1) throws TSPException {
      String var2 = "yyyyMMddHHmmss.SSS";
      SimpleDateFormat var3 = this.locale == null ? new SimpleDateFormat(var2) : new SimpleDateFormat(var2, this.locale);
      var3.setTimeZone(new SimpleTimeZone(0, "Z"));
      StringBuilder var4 = new StringBuilder(var3.format(var1));
      int var5 = var4.indexOf(".");
      if (var5 < 0) {
         var4.append("Z");
         return new ASN1GeneralizedTime(var4.toString());
      } else {
         switch (this.resolution) {
            case 1:
               if (var4.length() > var5 + 2) {
                  var4.delete(var5 + 2, var4.length());
               }
               break;
            case 2:
               if (var4.length() > var5 + 3) {
                  var4.delete(var5 + 3, var4.length());
               }
            case 3:
               break;
            default:
               throw new TSPException("unknown time-stamp resolution: " + this.resolution);
         }

         while(var4.charAt(var4.length() - 1) == '0') {
            var4.deleteCharAt(var4.length() - 1);
         }

         if (var4.length() - 1 == var5) {
            var4.deleteCharAt(var4.length() - 1);
         }

         var4.append("Z");
         return new ASN1GeneralizedTime(var4.toString());
      }
   }
}
