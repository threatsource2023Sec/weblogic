package org.python.bouncycastle.asn1.eac;

import java.io.IOException;
import java.util.Hashtable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERApplicationSpecific;
import org.python.bouncycastle.util.Integers;

public class CertificateHolderAuthorization extends ASN1Object {
   ASN1ObjectIdentifier oid;
   DERApplicationSpecific accessRights;
   public static final ASN1ObjectIdentifier id_role_EAC;
   public static final int CVCA = 192;
   public static final int DV_DOMESTIC = 128;
   public static final int DV_FOREIGN = 64;
   public static final int IS = 0;
   public static final int RADG4 = 2;
   public static final int RADG3 = 1;
   static Hashtable RightsDecodeMap;
   static BidirectionalMap AuthorizationRole;
   static Hashtable ReverseMap;

   public static String getRoleDescription(int var0) {
      return (String)AuthorizationRole.get(Integers.valueOf(var0));
   }

   public static int getFlag(String var0) {
      Integer var1 = (Integer)AuthorizationRole.getReverse(var0);
      if (var1 == null) {
         throw new IllegalArgumentException("Unknown value " + var0);
      } else {
         return var1;
      }
   }

   private void setPrivateData(ASN1InputStream var1) throws IOException {
      ASN1Primitive var2 = var1.readObject();
      if (var2 instanceof ASN1ObjectIdentifier) {
         this.oid = (ASN1ObjectIdentifier)var2;
         var2 = var1.readObject();
         if (var2 instanceof DERApplicationSpecific) {
            this.accessRights = (DERApplicationSpecific)var2;
         } else {
            throw new IllegalArgumentException("No access rights in CerticateHolderAuthorization");
         }
      } else {
         throw new IllegalArgumentException("no Oid in CerticateHolderAuthorization");
      }
   }

   public CertificateHolderAuthorization(ASN1ObjectIdentifier var1, int var2) throws IOException {
      this.setOid(var1);
      this.setAccessRights((byte)var2);
   }

   public CertificateHolderAuthorization(DERApplicationSpecific var1) throws IOException {
      if (var1.getApplicationTag() == 76) {
         this.setPrivateData(new ASN1InputStream(var1.getContents()));
      }

   }

   public int getAccessRights() {
      return this.accessRights.getContents()[0] & 255;
   }

   private void setAccessRights(byte var1) {
      byte[] var2 = new byte[]{var1};
      this.accessRights = new DERApplicationSpecific(19, var2);
   }

   public ASN1ObjectIdentifier getOid() {
      return this.oid;
   }

   private void setOid(ASN1ObjectIdentifier var1) {
      this.oid = var1;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.oid);
      var1.add(this.accessRights);
      return new DERApplicationSpecific(76, var1);
   }

   static {
      id_role_EAC = EACObjectIdentifiers.bsi_de.branch("3.1.2.1");
      RightsDecodeMap = new Hashtable();
      AuthorizationRole = new BidirectionalMap();
      ReverseMap = new Hashtable();
      RightsDecodeMap.put(Integers.valueOf(2), "RADG4");
      RightsDecodeMap.put(Integers.valueOf(1), "RADG3");
      AuthorizationRole.put(Integers.valueOf(192), "CVCA");
      AuthorizationRole.put(Integers.valueOf(128), "DV_DOMESTIC");
      AuthorizationRole.put(Integers.valueOf(64), "DV_FOREIGN");
      AuthorizationRole.put(Integers.valueOf(0), "IS");
   }
}
