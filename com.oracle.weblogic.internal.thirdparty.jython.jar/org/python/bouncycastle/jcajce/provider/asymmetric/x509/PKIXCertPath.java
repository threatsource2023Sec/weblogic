package org.python.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.pkcs.ContentInfo;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.SignedData;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.util.io.pem.PemObject;
import org.python.bouncycastle.util.io.pem.PemWriter;

public class PKIXCertPath extends CertPath {
   private final JcaJceHelper helper = new BCJcaJceHelper();
   static final List certPathEncodings;
   private List certificates;

   private List sortCerts(List var1) {
      if (var1.size() < 2) {
         return var1;
      } else {
         X500Principal var2 = ((X509Certificate)var1.get(0)).getIssuerX500Principal();
         boolean var3 = true;

         for(int var4 = 1; var4 != var1.size(); ++var4) {
            X509Certificate var5 = (X509Certificate)var1.get(var4);
            if (!var2.equals(var5.getSubjectX500Principal())) {
               var3 = false;
               break;
            }

            var2 = ((X509Certificate)var1.get(var4)).getIssuerX500Principal();
         }

         if (var3) {
            return var1;
         } else {
            ArrayList var12 = new ArrayList(var1.size());
            ArrayList var13 = new ArrayList(var1);

            int var6;
            for(var6 = 0; var6 < var1.size(); ++var6) {
               X509Certificate var7 = (X509Certificate)var1.get(var6);
               boolean var8 = false;
               X500Principal var9 = var7.getSubjectX500Principal();

               for(int var10 = 0; var10 != var1.size(); ++var10) {
                  X509Certificate var11 = (X509Certificate)var1.get(var10);
                  if (var11.getIssuerX500Principal().equals(var9)) {
                     var8 = true;
                     break;
                  }
               }

               if (!var8) {
                  var12.add(var7);
                  var1.remove(var6);
               }
            }

            if (var12.size() > 1) {
               return var13;
            } else {
               for(var6 = 0; var6 != var12.size(); ++var6) {
                  var2 = ((X509Certificate)var12.get(var6)).getIssuerX500Principal();

                  for(int var14 = 0; var14 < var1.size(); ++var14) {
                     X509Certificate var15 = (X509Certificate)var1.get(var14);
                     if (var2.equals(var15.getSubjectX500Principal())) {
                        var12.add(var15);
                        var1.remove(var14);
                        break;
                     }
                  }
               }

               if (var1.size() > 0) {
                  return var13;
               } else {
                  return var12;
               }
            }
         }
      }
   }

   PKIXCertPath(List var1) {
      super("X.509");
      this.certificates = this.sortCerts(new ArrayList(var1));
   }

   PKIXCertPath(InputStream var1, String var2) throws CertificateException {
      super("X.509");

      try {
         if (var2.equalsIgnoreCase("PkiPath")) {
            ASN1InputStream var3 = new ASN1InputStream(var1);
            ASN1Primitive var4 = var3.readObject();
            if (!(var4 instanceof ASN1Sequence)) {
               throw new CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
            }

            Enumeration var5 = ((ASN1Sequence)var4).getObjects();
            this.certificates = new ArrayList();
            java.security.cert.CertificateFactory var6 = this.helper.createCertificateFactory("X.509");

            while(var5.hasMoreElements()) {
               ASN1Encodable var7 = (ASN1Encodable)var5.nextElement();
               byte[] var8 = var7.toASN1Primitive().getEncoded("DER");
               this.certificates.add(0, var6.generateCertificate(new ByteArrayInputStream(var8)));
            }
         } else {
            if (!var2.equalsIgnoreCase("PKCS7") && !var2.equalsIgnoreCase("PEM")) {
               throw new CertificateException("unsupported encoding: " + var2);
            }

            BufferedInputStream var11 = new BufferedInputStream(var1);
            this.certificates = new ArrayList();
            java.security.cert.CertificateFactory var12 = this.helper.createCertificateFactory("X.509");

            Certificate var13;
            while((var13 = var12.generateCertificate(var11)) != null) {
               this.certificates.add(var13);
            }
         }
      } catch (IOException var9) {
         throw new CertificateException("IOException throw while decoding CertPath:\n" + var9.toString());
      } catch (NoSuchProviderException var10) {
         throw new CertificateException("BouncyCastle provider not found while trying to get a CertificateFactory:\n" + var10.toString());
      }

      this.certificates = this.sortCerts(this.certificates);
   }

   public Iterator getEncodings() {
      return certPathEncodings.iterator();
   }

   public byte[] getEncoded() throws CertificateEncodingException {
      Iterator var1 = this.getEncodings();
      if (var1.hasNext()) {
         Object var2 = var1.next();
         if (var2 instanceof String) {
            return this.getEncoded((String)var2);
         }
      }

      return null;
   }

   public byte[] getEncoded(String var1) throws CertificateEncodingException {
      if (var1.equalsIgnoreCase("PkiPath")) {
         ASN1EncodableVector var7 = new ASN1EncodableVector();
         ListIterator var9 = this.certificates.listIterator(this.certificates.size());

         while(var9.hasPrevious()) {
            var7.add(this.toASN1Object((X509Certificate)var9.previous()));
         }

         return this.toDEREncoded(new DERSequence(var7));
      } else {
         int var4;
         if (var1.equalsIgnoreCase("PKCS7")) {
            ContentInfo var6 = new ContentInfo(PKCSObjectIdentifiers.data, (ASN1Encodable)null);
            ASN1EncodableVector var8 = new ASN1EncodableVector();

            for(var4 = 0; var4 != this.certificates.size(); ++var4) {
               var8.add(this.toASN1Object((X509Certificate)this.certificates.get(var4)));
            }

            SignedData var10 = new SignedData(new ASN1Integer(1L), new DERSet(), var6, new DERSet(var8), (ASN1Set)null, new DERSet());
            return this.toDEREncoded(new ContentInfo(PKCSObjectIdentifiers.signedData, var10));
         } else if (var1.equalsIgnoreCase("PEM")) {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            PemWriter var3 = new PemWriter(new OutputStreamWriter(var2));

            try {
               for(var4 = 0; var4 != this.certificates.size(); ++var4) {
                  var3.writeObject(new PemObject("CERTIFICATE", ((X509Certificate)this.certificates.get(var4)).getEncoded()));
               }

               var3.close();
            } catch (Exception var5) {
               throw new CertificateEncodingException("can't encode certificate for PEM encoded path");
            }

            return var2.toByteArray();
         } else {
            throw new CertificateEncodingException("unsupported encoding: " + var1);
         }
      }
   }

   public List getCertificates() {
      return Collections.unmodifiableList(new ArrayList(this.certificates));
   }

   private ASN1Primitive toASN1Object(X509Certificate var1) throws CertificateEncodingException {
      try {
         return (new ASN1InputStream(var1.getEncoded())).readObject();
      } catch (Exception var3) {
         throw new CertificateEncodingException("Exception while encoding certificate: " + var3.toString());
      }
   }

   private byte[] toDEREncoded(ASN1Encodable var1) throws CertificateEncodingException {
      try {
         return var1.toASN1Primitive().getEncoded("DER");
      } catch (IOException var3) {
         throw new CertificateEncodingException("Exception thrown: " + var3);
      }
   }

   static {
      ArrayList var0 = new ArrayList();
      var0.add("PkiPath");
      var0.add("PEM");
      var0.add("PKCS7");
      certPathEncodings = Collections.unmodifiableList(var0);
   }
}
