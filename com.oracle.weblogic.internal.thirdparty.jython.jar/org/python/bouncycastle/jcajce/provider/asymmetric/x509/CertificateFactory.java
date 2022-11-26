package org.python.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.SignedData;
import org.python.bouncycastle.asn1.x509.CertificateList;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.util.io.Streams;

public class CertificateFactory extends CertificateFactorySpi {
   private final JcaJceHelper bcHelper = new BCJcaJceHelper();
   private static final PEMUtil PEM_CERT_PARSER = new PEMUtil("CERTIFICATE");
   private static final PEMUtil PEM_CRL_PARSER = new PEMUtil("CRL");
   private static final PEMUtil PEM_PKCS7_PARSER = new PEMUtil("PKCS7");
   private ASN1Set sData = null;
   private int sDataObjectCount = 0;
   private InputStream currentStream = null;
   private ASN1Set sCrlData = null;
   private int sCrlDataObjectCount = 0;
   private InputStream currentCrlStream = null;

   private Certificate readDERCertificate(ASN1InputStream var1) throws IOException, CertificateParsingException {
      return this.getCertificate(ASN1Sequence.getInstance(var1.readObject()));
   }

   private Certificate readPEMCertificate(InputStream var1) throws IOException, CertificateParsingException {
      return this.getCertificate(PEM_CERT_PARSER.readPEMObject(var1));
   }

   private Certificate getCertificate(ASN1Sequence var1) throws CertificateParsingException {
      if (var1 == null) {
         return null;
      } else if (var1.size() > 1 && var1.getObjectAt(0) instanceof ASN1ObjectIdentifier && var1.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
         this.sData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)var1.getObjectAt(1), true)).getCertificates();
         return this.getCertificate();
      } else {
         return new X509CertificateObject(this.bcHelper, org.python.bouncycastle.asn1.x509.Certificate.getInstance(var1));
      }
   }

   private Certificate getCertificate() throws CertificateParsingException {
      if (this.sData != null) {
         while(this.sDataObjectCount < this.sData.size()) {
            ASN1Encodable var1 = this.sData.getObjectAt(this.sDataObjectCount++);
            if (var1 instanceof ASN1Sequence) {
               return new X509CertificateObject(this.bcHelper, org.python.bouncycastle.asn1.x509.Certificate.getInstance(var1));
            }
         }
      }

      return null;
   }

   protected CRL createCRL(CertificateList var1) throws CRLException {
      return new X509CRLObject(this.bcHelper, var1);
   }

   private CRL readPEMCRL(InputStream var1) throws IOException, CRLException {
      return this.getCRL(PEM_CRL_PARSER.readPEMObject(var1));
   }

   private CRL readDERCRL(ASN1InputStream var1) throws IOException, CRLException {
      return this.getCRL(ASN1Sequence.getInstance(var1.readObject()));
   }

   private CRL getCRL(ASN1Sequence var1) throws CRLException {
      if (var1 == null) {
         return null;
      } else if (var1.size() > 1 && var1.getObjectAt(0) instanceof ASN1ObjectIdentifier && var1.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
         this.sCrlData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)var1.getObjectAt(1), true)).getCRLs();
         return this.getCRL();
      } else {
         return this.createCRL(CertificateList.getInstance(var1));
      }
   }

   private CRL getCRL() throws CRLException {
      return this.sCrlData != null && this.sCrlDataObjectCount < this.sCrlData.size() ? this.createCRL(CertificateList.getInstance(this.sCrlData.getObjectAt(this.sCrlDataObjectCount++))) : null;
   }

   public Certificate engineGenerateCertificate(InputStream var1) throws CertificateException {
      if (this.currentStream == null) {
         this.currentStream = var1;
         this.sData = null;
         this.sDataObjectCount = 0;
      } else if (this.currentStream != var1) {
         this.currentStream = var1;
         this.sData = null;
         this.sDataObjectCount = 0;
      }

      try {
         if (this.sData != null) {
            if (this.sDataObjectCount != this.sData.size()) {
               return this.getCertificate();
            } else {
               this.sData = null;
               this.sDataObjectCount = 0;
               return null;
            }
         } else {
            Object var2;
            if (var1.markSupported()) {
               var2 = var1;
            } else {
               var2 = new ByteArrayInputStream(Streams.readAll(var1));
            }

            ((InputStream)var2).mark(1);
            int var3 = ((InputStream)var2).read();
            if (var3 == -1) {
               return null;
            } else {
               ((InputStream)var2).reset();
               return var3 != 48 ? this.readPEMCertificate((InputStream)var2) : this.readDERCertificate(new ASN1InputStream((InputStream)var2));
            }
         }
      } catch (Exception var4) {
         throw new ExCertificateException(var4);
      }
   }

   public Collection engineGenerateCertificates(InputStream var1) throws CertificateException {
      BufferedInputStream var2 = new BufferedInputStream(var1);
      ArrayList var3 = new ArrayList();

      Certificate var4;
      while((var4 = this.engineGenerateCertificate(var2)) != null) {
         var3.add(var4);
      }

      return var3;
   }

   public CRL engineGenerateCRL(InputStream var1) throws CRLException {
      if (this.currentCrlStream == null) {
         this.currentCrlStream = var1;
         this.sCrlData = null;
         this.sCrlDataObjectCount = 0;
      } else if (this.currentCrlStream != var1) {
         this.currentCrlStream = var1;
         this.sCrlData = null;
         this.sCrlDataObjectCount = 0;
      }

      try {
         if (this.sCrlData != null) {
            if (this.sCrlDataObjectCount != this.sCrlData.size()) {
               return this.getCRL();
            } else {
               this.sCrlData = null;
               this.sCrlDataObjectCount = 0;
               return null;
            }
         } else {
            Object var2;
            if (var1.markSupported()) {
               var2 = var1;
            } else {
               var2 = new ByteArrayInputStream(Streams.readAll(var1));
            }

            ((InputStream)var2).mark(1);
            int var3 = ((InputStream)var2).read();
            if (var3 == -1) {
               return null;
            } else {
               ((InputStream)var2).reset();
               return var3 != 48 ? this.readPEMCRL((InputStream)var2) : this.readDERCRL(new ASN1InputStream((InputStream)var2, true));
            }
         }
      } catch (CRLException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new CRLException(var5.toString());
      }
   }

   public Collection engineGenerateCRLs(InputStream var1) throws CRLException {
      ArrayList var2 = new ArrayList();
      BufferedInputStream var3 = new BufferedInputStream(var1);

      CRL var4;
      while((var4 = this.engineGenerateCRL(var3)) != null) {
         var2.add(var4);
      }

      return var2;
   }

   public Iterator engineGetCertPathEncodings() {
      return PKIXCertPath.certPathEncodings.iterator();
   }

   public CertPath engineGenerateCertPath(InputStream var1) throws CertificateException {
      return this.engineGenerateCertPath(var1, "PkiPath");
   }

   public CertPath engineGenerateCertPath(InputStream var1, String var2) throws CertificateException {
      return new PKIXCertPath(var1, var2);
   }

   public CertPath engineGenerateCertPath(List var1) throws CertificateException {
      Iterator var2 = var1.iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            return new PKIXCertPath(var1);
         }

         var3 = var2.next();
      } while(var3 == null || var3 instanceof X509Certificate);

      throw new CertificateException("list contains non X509Certificate object while creating CertPath\n" + var3.toString());
   }

   private class ExCertificateException extends CertificateException {
      private Throwable cause;

      public ExCertificateException(Throwable var2) {
         this.cause = var2;
      }

      public ExCertificateException(String var2, Throwable var3) {
         super(var2);
         this.cause = var3;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }
}
