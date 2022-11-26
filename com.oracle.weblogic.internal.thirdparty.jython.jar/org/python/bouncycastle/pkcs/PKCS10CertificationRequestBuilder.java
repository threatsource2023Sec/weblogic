package org.python.bouncycastle.pkcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.pkcs.Attribute;
import org.python.bouncycastle.asn1.pkcs.CertificationRequest;
import org.python.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.operator.ContentSigner;

public class PKCS10CertificationRequestBuilder {
   private SubjectPublicKeyInfo publicKeyInfo;
   private X500Name subject;
   private List attributes = new ArrayList();
   private boolean leaveOffEmpty = false;

   public PKCS10CertificationRequestBuilder(PKCS10CertificationRequestBuilder var1) {
      this.publicKeyInfo = var1.publicKeyInfo;
      this.subject = var1.subject;
      this.leaveOffEmpty = var1.leaveOffEmpty;
      this.attributes = new ArrayList(var1.attributes);
   }

   public PKCS10CertificationRequestBuilder(X500Name var1, SubjectPublicKeyInfo var2) {
      this.subject = var1;
      this.publicKeyInfo = var2;
   }

   public PKCS10CertificationRequestBuilder setAttribute(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      Iterator var3 = this.attributes.iterator();

      do {
         if (!var3.hasNext()) {
            this.addAttribute(var1, var2);
            return this;
         }
      } while(!((Attribute)var3.next()).getAttrType().equals(var1));

      throw new IllegalStateException("Attribute " + var1.toString() + " is already set");
   }

   public PKCS10CertificationRequestBuilder setAttribute(ASN1ObjectIdentifier var1, ASN1Encodable[] var2) {
      Iterator var3 = this.attributes.iterator();

      do {
         if (!var3.hasNext()) {
            this.addAttribute(var1, var2);
            return this;
         }
      } while(!((Attribute)var3.next()).getAttrType().equals(var1));

      throw new IllegalStateException("Attribute " + var1.toString() + " is already set");
   }

   public PKCS10CertificationRequestBuilder addAttribute(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.attributes.add(new Attribute(var1, new DERSet(var2)));
      return this;
   }

   public PKCS10CertificationRequestBuilder addAttribute(ASN1ObjectIdentifier var1, ASN1Encodable[] var2) {
      this.attributes.add(new Attribute(var1, new DERSet(var2)));
      return this;
   }

   public PKCS10CertificationRequestBuilder setLeaveOffEmptyAttributes(boolean var1) {
      this.leaveOffEmpty = var1;
      return this;
   }

   public PKCS10CertificationRequest build(ContentSigner var1) {
      CertificationRequestInfo var2;
      if (this.attributes.isEmpty()) {
         if (this.leaveOffEmpty) {
            var2 = new CertificationRequestInfo(this.subject, this.publicKeyInfo, (ASN1Set)null);
         } else {
            var2 = new CertificationRequestInfo(this.subject, this.publicKeyInfo, new DERSet());
         }
      } else {
         ASN1EncodableVector var3 = new ASN1EncodableVector();
         Iterator var4 = this.attributes.iterator();

         while(var4.hasNext()) {
            var3.add(Attribute.getInstance(var4.next()));
         }

         var2 = new CertificationRequestInfo(this.subject, this.publicKeyInfo, new DERSet(var3));
      }

      try {
         OutputStream var6 = var1.getOutputStream();
         var6.write(var2.getEncoded("DER"));
         var6.close();
         return new PKCS10CertificationRequest(new CertificationRequest(var2, var1.getAlgorithmIdentifier(), new DERBitString(var1.getSignature())));
      } catch (IOException var5) {
         throw new IllegalStateException("cannot produce certification request signature");
      }
   }
}
