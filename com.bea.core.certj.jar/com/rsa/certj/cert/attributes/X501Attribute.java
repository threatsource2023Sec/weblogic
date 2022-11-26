package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.AttributeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public abstract class X501Attribute implements Serializable, Cloneable {
   /** @deprecated */
   public static final int SIGNING_TIME = 0;
   /** @deprecated */
   public static final int CHALLENGE_PASSWORD = 1;
   /** @deprecated */
   public static final int X509_V3_EXTENSION = 2;
   /** @deprecated */
   public static final int FRIENDLY_NAME = 3;
   /** @deprecated */
   public static final int LOCAL_KEY_ID = 4;
   /** @deprecated */
   public static final int SMART_CSP = 5;
   /** @deprecated */
   public static final int CRS_MESSAGE_TYPE = 6;
   /** @deprecated */
   public static final int CRS_PKI_STATUS = 7;
   /** @deprecated */
   public static final int CRS_FAILURE_INFO = 8;
   /** @deprecated */
   public static final int CRS_SENDER_NONCE = 9;
   /** @deprecated */
   public static final int CRS_RECIPIENT_NONCE = 10;
   /** @deprecated */
   public static final int CRS_TRANSACTION_ID = 11;
   /** @deprecated */
   public static final int CRS_VERSION = 12;
   /** @deprecated */
   public static final int CRS_DUAL_STATUS = 13;
   /** @deprecated */
   public static final int CONTENT_TYPE = 14;
   /** @deprecated */
   public static final int MESSAGE_DIGEST = 15;
   /** @deprecated */
   public static final int POSTAL_ADDRESS = 16;
   /** @deprecated */
   public static final int PSEUDONYM = 17;
   /** @deprecated */
   public static final int DATE_OF_BIRTH = 18;
   /** @deprecated */
   public static final int PLACE_OF_BIRTH = 19;
   /** @deprecated */
   public static final int GENDER = 20;
   /** @deprecated */
   public static final int COUNTRY_OF_CITIZENSHIP = 21;
   /** @deprecated */
   public static final int COUNTRY_OF_RESIDENCE = 22;
   /** @deprecated */
   public static final int NON_STANDARD = 23;
   /** @deprecated */
   protected static final byte[][] OID_LIST = new byte[][]{{42, -122, 72, -122, -9, 13, 1, 9, 5}, {42, -122, 72, -122, -9, 13, 1, 9, 7}, {42, -122, 72, -122, -9, 13, 1, 9, 14}, {42, -122, 72, -122, -9, 13, 1, 9, 20}, {42, -122, 72, -122, -9, 13, 1, 9, 21}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 1}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 2}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 3}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 4}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 5}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 6}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 7}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 9}, {96, -122, 72, 1, -122, -8, 69, 1, 9, 10}, {42, -122, 72, -122, -9, 13, 1, 9, 3}, {42, -122, 72, -122, -9, 13, 1, 9, 4}, {85, 4, 16}, {85, 4, 65}, {43, 6, 1, 5, 5, 7, 9, 1}, {43, 6, 1, 5, 5, 7, 9, 2}, {43, 6, 1, 5, 5, 7, 9, 3}, {43, 6, 1, 5, 5, 7, 9, 4}, {43, 6, 1, 5, 5, 7, 9, 5}};
   /** @deprecated */
   protected int attributeTypeFlag;
   byte[] theOID;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   /** @deprecated */
   protected String attributeTypeString;

   /** @deprecated */
   protected X501Attribute(int var1, String var2) {
      this.attributeTypeFlag = var1;
      if (var1 != 23) {
         this.theOID = new byte[OID_LIST[var1].length];
         System.arraycopy(OID_LIST[var1], 0, this.theOID, 0, this.theOID.length);
      }

      this.attributeTypeString = var2;
   }

   /** @deprecated */
   public static X501Attribute getInstance(byte[] var0, int var1, int var2) throws AttributeException {
      if (var0 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         ASN1Container[] var3 = decodeAttribute(var0, var1, var2);
         int var4 = findOID(var3[1].data, var3[1].dataOffset, var3[1].dataLen);
         Object var5 = null;
         switch (var4) {
            case 0:
               var5 = new SigningTime();
               break;
            case 1:
               var5 = new ChallengePassword();
               break;
            case 2:
               var5 = new V3ExtensionAttribute();
               break;
            case 3:
               var5 = new FriendlyName();
               break;
            case 4:
               var5 = new LocalKeyID();
               break;
            case 5:
               var5 = new VeriSignSmartCSP();
               break;
            case 6:
               var5 = new VeriSignCRSMessageType();
               break;
            case 7:
               var5 = new VeriSignCRSPKIStatus();
               break;
            case 8:
               var5 = new VeriSignCRSFailureInfo();
               break;
            case 9:
               var5 = new VeriSignCRSSenderNonce();
               break;
            case 10:
               var5 = new VeriSignCRSRecipientNonce();
               break;
            case 11:
               var5 = new VeriSignCRSTransactionID();
               break;
            case 12:
               var5 = new VeriSignCRSVersion();
               break;
            case 13:
               var5 = new VeriSignCRSDualEnrollmentStatus();
               break;
            case 14:
               var5 = new ContentType();
               break;
            case 15:
               var5 = new MessageDigest();
               break;
            case 16:
               var5 = new PostalAddress();
               break;
            case 17:
               var5 = new Pseudonym();
               break;
            case 18:
               var5 = new DateOfBirth();
               break;
            case 19:
               var5 = new PlaceOfBirth();
               break;
            case 20:
               var5 = new Gender();
               break;
            case 21:
               var5 = new CountryOfCitizenship();
               break;
            case 22:
               var5 = new CountryOfResidence();
               break;
            case 23:
               var5 = new NonStandardAttribute();
               ((X501Attribute)var5).theOID = new byte[var3[1].dataLen];
               System.arraycopy(var3[1].data, var3[1].dataOffset, ((X501Attribute)var5).theOID, 0, var3[1].dataLen);
         }

         if (var5 != null) {
            ((X501Attribute)var5).decodeValue(var3[2].data, var3[2].dataOffset);
         }

         for(int var6 = 0; var6 < var3.length; ++var6) {
            var3[var6].clearSensitiveData();
         }

         return (X501Attribute)var5;
      }
   }

   /** @deprecated */
   protected static ASN1Container[] decodeAttribute(byte[] var0, int var1, int var2) throws AttributeException {
      if (var0 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         try {
            SequenceContainer var3 = new SequenceContainer(var2);
            EndContainer var4 = new EndContainer();
            OIDContainer var5 = new OIDContainer(16777216);
            EncodedContainer var6 = new EncodedContainer(12544);
            ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
            ASN1.berDecode(var0, var1, var7);
            return var7;
         } catch (ASN_Exception var8) {
            throw new AttributeException("Cannot read the BER of the attribute.");
         }
      }
   }

   private static int findOID(byte[] var0, int var1, int var2) {
      if (var0 == null) {
         return 23;
      } else {
         for(int var3 = 0; var3 < 23; ++var3) {
            if (var2 == OID_LIST[var3].length) {
               int var4;
               for(var4 = 0; var4 < var2 && (var0[var4 + var1] & 255) == (OID_LIST[var3][var4] & 255); ++var4) {
               }

               if (var4 >= var2) {
                  return var3;
               }
            }
         }

         return 23;
      }
   }

   /** @deprecated */
   public String getAttributeTypeString() {
      return this.attributeTypeString;
   }

   /** @deprecated */
   protected abstract void decodeValue(byte[] var1, int var2) throws AttributeException;

   /** @deprecated */
   public byte[] getOID() {
      return this.theOID == null ? null : (byte[])((byte[])this.theOID.clone());
   }

   /** @deprecated */
   public boolean compareOID(byte[] var1) {
      if (var1 == null) {
         return false;
      } else if (var1.length != this.theOID.length) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2] != this.theOID[var2]) {
               return false;
            }
         }

         return true;
      }
   }

   /** @deprecated */
   public int getAttributeType() {
      return this.attributeTypeFlag;
   }

   /** @deprecated */
   public int getDERLen(int var1) {
      return this.derEncodeAttributeLen(var1, this.derEncodeValueInit());
   }

   /** @deprecated */
   protected abstract int derEncodeValueInit();

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws AttributeException {
      if (var0 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         try {
            return var1 + ASN1Lengths.determineLength(var0, var1);
         } catch (ASN_Exception var3) {
            throw new AttributeException("Could not read the BER encoding.");
         }
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Passed array is null.");
      } else {
         return this.derEncodeAttribute(var1, var2, var3);
      }
   }

   /** @deprecated */
   public boolean isAttributeType(int var1) {
      return var1 == this.attributeTypeFlag;
   }

   /** @deprecated */
   public int derEncodeAttributeLen(int var1, int var2) {
      if (var2 == 0) {
         return 0;
      } else {
         this.special = var1;

         try {
            SequenceContainer var3 = new SequenceContainer(0, true, 0);
            EndContainer var4 = new EndContainer();
            OIDContainer var5 = new OIDContainer(16777216, true, 0, this.theOID, 0, this.theOID.length);
            EncodedContainer var6 = new EncodedContainer(12544, true, 0, (byte[])null, 0, var2);
            ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
            this.asn1Template = new ASN1Template(var7);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var8) {
            return 0;
         }
      }
   }

   /** @deprecated */
   protected int derEncodeAttribute(byte[] var1, int var2, int var3) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Passed array is null.");
      } else if ((this.asn1Template == null || this.special != var3) && this.getDERLen(var3) == 0) {
         throw new AttributeException("Could not encode, missing data");
      } else {
         int var4;
         try {
            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            return 0;
         }

         var4 += this.derEncodeValue(var1, var2 + var4);
         return var4;
      }
   }

   /** @deprecated */
   protected abstract int derEncodeValue(byte[] var1, int var2);

   /** @deprecated */
   protected void copyValues(X501Attribute var1) {
      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.getDERLen(this.special);
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof X501Attribute)) {
         return false;
      } else {
         int var2 = this.getDERLen(0);
         if (var2 == 0) {
            return false;
         } else {
            byte[] var3 = new byte[var2];

            try {
               this.getDEREncoding(var3, 0, 0);
            } catch (AttributeException var9) {
               return false;
            }

            X501Attribute var4 = (X501Attribute)var1;
            int var5 = var4.getDERLen(0);
            if (var5 == 0) {
               return false;
            } else {
               byte[] var6 = new byte[var2];

               try {
                  var4.getDEREncoding(var6, 0, 0);
               } catch (AttributeException var8) {
                  return false;
               }

               return CertJUtils.byteArraysEqual(var3, var6);
            }
         }
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      if (var1 == 0) {
         return 0;
      } else {
         byte[] var2 = new byte[var1];

         try {
            this.getDEREncoding(var2, 0, 0);
         } catch (AttributeException var4) {
            return 0;
         }

         return Arrays.hashCode(var2);
      }
   }

   /** @deprecated */
   public abstract Object clone() throws CloneNotSupportedException;

   /** @deprecated */
   protected void reset() {
      this.asn1Template = null;
      this.special = 0;
   }

   /** @deprecated */
   protected byte[] utf8Encode(String var1) throws AttributeException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      DataOutputStream var3 = new DataOutputStream(var2);

      try {
         var3.writeUTF(var1);
         var3.flush();
      } catch (IOException var5) {
         throw new AttributeException("pseudonym.utf8Encode: unable to utf8-encode " + var1 + ".", var5);
      }

      return var2.toByteArray();
   }

   /** @deprecated */
   protected String utf8Decode(byte[] var1, int var2, int var3) throws AttributeException {
      byte[] var4 = new byte[var3 + 2];
      var4[0] = (byte)(var3 >> 8 & 255);
      var4[1] = (byte)(var3 & 255);
      System.arraycopy(var1, var2, var4, 2, var3);

      try {
         ByteArrayInputStream var5 = new ByteArrayInputStream(var4, 0, var4.length);
         DataInputStream var6 = new DataInputStream(var5);
         return var6.readUTF();
      } catch (IOException var7) {
         throw new AttributeException("Pseudonym.utf8Decode.", var7);
      }
   }

   /** @deprecated */
   public void clearSensitiveData() {
      this.reset();
   }
}
