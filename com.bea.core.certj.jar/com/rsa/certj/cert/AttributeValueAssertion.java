package com.rsa.certj.cert;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BMPStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.TeletexStringContainer;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.asn1.UniversalStringContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.CompatibilityType;
import com.rsa.certj.x.e;
import com.rsa.jsafe.CryptoJ;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/** @deprecated */
public class AttributeValueAssertion implements Serializable, Cloneable {
   /** @deprecated */
   public static final int UNKNOWN_ATTRIBUTE_TYPE = -1;
   /** @deprecated */
   public static final int BOUND_UNLIMITED = -1;
   /** @deprecated */
   public static final int UNSUPPORTED_ASN1_TYPE = -1;
   /** @deprecated */
   public static final int COMMON_NAME = 0;
   /** @deprecated */
   public static final byte[] COMMON_NAME_OID = new byte[]{85, 4, 3};
   /** @deprecated */
   public static final int UB_COMMON_NAME = 64;
   private static final int UB_INTERNAL = 500;
   /** @deprecated */
   public static final int COUNTRY_NAME = 1;
   /** @deprecated */
   public static final byte[] COUNTRY_NAME_OID = new byte[]{85, 4, 6};
   /** @deprecated */
   public static final int COUNTRY_NAME_LENGTH = 2;
   /** @deprecated */
   public static final int LOCALITY_NAME = 2;
   /** @deprecated */
   public static final byte[] LOCALITY_NAME_OID = new byte[]{85, 4, 7};
   /** @deprecated */
   public static final int UB_LOCALITY_NAME = 128;
   /** @deprecated */
   public static final int STATE_NAME = 3;
   /** @deprecated */
   public static final byte[] STATE_NAME_OID = new byte[]{85, 4, 8};
   /** @deprecated */
   public static final int UB_STATE_NAME = 128;
   /** @deprecated */
   public static final int ORGANIZATION_NAME = 4;
   /** @deprecated */
   public static final byte[] ORGANIZATION_NAME_OID = new byte[]{85, 4, 10};
   /** @deprecated */
   public static final int UB_ORGANIZATION_NAME = 64;
   /** @deprecated */
   public static final int ORGANIZATIONAL_UNIT_NAME = 5;
   /** @deprecated */
   public static final byte[] ORGANIZATIONAL_UNIT_NAME_OID = new byte[]{85, 4, 11};
   /** @deprecated */
   public static final int UB_ORGANIZATIONAL_UNIT_NAME = 128;
   /** @deprecated */
   public static final int TELEPHONE_NUMBER = 6;
   /** @deprecated */
   public static final byte[] TELEPHONE_NUMBER_OID = new byte[]{85, 4, 20};
   /** @deprecated */
   public static final int UB_TELEPHONE_NUMBER = 32;
   /** @deprecated */
   public static final int EMAIL_ADDRESS = 7;
   /** @deprecated */
   public static final byte[] EMAIL_ADDRESS_OID = new byte[]{42, -122, 72, -122, -9, 13, 1, 9, 1};
   /** @deprecated */
   public static final int UB_EMAIL_ADDRESS = 64;
   /** @deprecated */
   public static final int TITLE = 8;
   /** @deprecated */
   public static final byte[] TITLE_OID = new byte[]{85, 4, 12};
   /** @deprecated */
   public static final int UB_TITLE = 64;
   /** @deprecated */
   public static final int STREET_ADDRESS = 9;
   /** @deprecated */
   public static final byte[] STREET_ADDRESS_OID = new byte[]{85, 4, 9};
   /** @deprecated */
   public static final int UB_STREET_ADDRESS = 128;
   /** @deprecated */
   public static final int BUSINESS_CATEGORY = 10;
   /** @deprecated */
   public static final byte[] BUSINESS_CATEGORY_OID = new byte[]{85, 4, 15};
   /** @deprecated */
   public static final int UB_BUSINESS_CATEGORY = 128;
   /** @deprecated */
   public static final int POSTAL_CODE = 11;
   /** @deprecated */
   public static final byte[] POSTAL_CODE_OID = new byte[]{85, 4, 17};
   /** @deprecated */
   public static final int UB_POSTAL_CODE = 40;
   /** @deprecated */
   public static final int SURNAME = 12;
   /** @deprecated */
   public static final byte[] SURNAME_OID = new byte[]{85, 4, 4};
   /** @deprecated */
   public static final int UB_SURNAME = 32768;
   /** @deprecated */
   public static final int GIVEN_NAME = 13;
   /** @deprecated */
   public static final byte[] GIVEN_NAME_OID = new byte[]{85, 4, 42};
   /** @deprecated */
   public static final int UB_GIVEN_NAME = 32768;
   /** @deprecated */
   public static final int SERIAL_NUMBER = 14;
   /** @deprecated */
   public static final byte[] SERIAL_NUMBER_OID = new byte[]{85, 4, 5};
   /** @deprecated */
   public static final int UB_SERIAL_NUMBER = 64;
   /** @deprecated */
   public static final int INITIALS = 15;
   /** @deprecated */
   public static final byte[] INITIALS_OID = new byte[]{85, 4, 43};
   /** @deprecated */
   public static final int UB_INITIALS = 32768;
   /** @deprecated */
   public static final int GENERATION_QUALIFIER = 16;
   /** @deprecated */
   public static final byte[] GENERATION_QUALIFIER_OID = new byte[]{85, 4, 44};
   /** @deprecated */
   public static final int UB_GENERATION_QUALIFIER = 32768;
   /** @deprecated */
   public static final int NAME = 17;
   /** @deprecated */
   public static final byte[] NAME_OID = new byte[]{85, 4, 41};
   /** @deprecated */
   public static final int UB_NAME = 32768;
   /** @deprecated */
   public static final int DN_QUALIFIER = 18;
   /** @deprecated */
   public static final byte[] DN_QUALIFIER_OID = new byte[]{85, 4, 46};
   /** @deprecated */
   public static final int UB_DN_QUALIFIER = -1;
   /** @deprecated */
   public static final int DOMAIN_COMPONENT = 19;
   /** @deprecated */
   public static final byte[] DOMAIN_COMPONENT_OID = new byte[]{9, -110, 38, -119, -109, -14, 44, 100, 1, 25};
   /** @deprecated */
   public static final int UB_DOMAIN_COMPONENT = 64;
   /** @deprecated */
   public static final int POSTAL_ADDRESS = 20;
   /** @deprecated */
   public static final byte[] POSTAL_ADDRESS_OID = new byte[]{85, 4, 16};
   /** @deprecated */
   public static final int UB_POSTAL_ADDRESS = -1;
   /** @deprecated */
   public static final int PSEUDONYM = 21;
   /** @deprecated */
   public static final byte[] PSEUDONYM_OID = new byte[]{85, 4, 65};
   /** @deprecated */
   public static final int UB_PSEUDONYM = -1;
   /** @deprecated */
   public static final int DATE_OF_BIRTH = 22;
   /** @deprecated */
   public static final byte[] DATE_OF_BIRTH_OID = new byte[]{43, 6, 1, 5, 5, 7, 9, 1};
   /** @deprecated */
   public static final int UB_DATE_OF_BIRTH = -1;
   /** @deprecated */
   public static final int PLACE_OF_BIRTH = 23;
   /** @deprecated */
   public static final byte[] PLACE_OF_BIRTH_OID = new byte[]{43, 6, 1, 5, 5, 7, 9, 2};
   /** @deprecated */
   public static final int UB_PLACE_OF_BIRTH = 32768;
   /** @deprecated */
   public static final int GENDER = 24;
   /** @deprecated */
   public static final byte[] GENDER_OID = new byte[]{43, 6, 1, 5, 5, 7, 9, 3};
   /** @deprecated */
   public static final int UB_GENDER = 1;
   /** @deprecated */
   public static final int COUNTRY_OF_CITIZENSHIP = 25;
   /** @deprecated */
   public static final byte[] COUNTRY_OF_CITIZENSHIP_OID = new byte[]{43, 6, 1, 5, 5, 7, 9, 4};
   /** @deprecated */
   public static final int UB_COUNTRY_OF_CITIZENSHIP = 2;
   /** @deprecated */
   public static final int COUNTRY_OF_RESIDENCE = 26;
   /** @deprecated */
   public static final byte[] COUNTRY_OF_RESIDENCE_OID = new byte[]{43, 6, 1, 5, 5, 7, 9, 5};
   /** @deprecated */
   public static final int UB_COUNTRY_OF_RESIDENCE = 2;
   /** @deprecated */
   public static final int JURIS_OF_INCORP_LOCALITY_NAME = 27;
   /** @deprecated */
   public static final byte[] JURIS_OF_INCORP_LOCALITY_NAME_OID = new byte[]{43, 6, 1, 4, 1, -126, 55, 60, 2, 1, 1};
   /** @deprecated */
   public static final int UB_JURIS_OF_INCORP_LOCALITY_NAME = 128;
   /** @deprecated */
   public static final int JURIS_OF_INCORP_STATE_NAME = 28;
   /** @deprecated */
   public static final byte[] JURIS_OF_INCORP_STATE_NAME_OID = new byte[]{43, 6, 1, 4, 1, -126, 55, 60, 2, 1, 2};
   /** @deprecated */
   public static final int UB_JURIS_OF_INCORP_STATE_NAME = 128;
   /** @deprecated */
   public static final int JURIS_OF_INCORP_COUNTRY_NAME = 29;
   /** @deprecated */
   public static final byte[] JURIS_OF_INCORP_COUNTRY_NAME_OID = new byte[]{43, 6, 1, 4, 1, -126, 55, 60, 2, 1, 3};
   /** @deprecated */
   public static final int UB_JURIS_OF_INCORP_COUNTRY_NAME = 128;
   /** @deprecated */
   public static final int USER_ID = 30;
   /** @deprecated */
   public static final byte[] USER_ID_OID = new byte[]{9, -110, 38, -119, -109, -14, 44, 100, 1, 1};
   /** @deprecated */
   public static final int UB_USER_ID = 128;
   /** @deprecated */
   protected static final NameAttributeID[] ALL_NAME_ATTRIBUTE_IDS;
   /** @deprecated */
   protected static AlternativeAttributeName[] alternativeAttributeNames;
   private int attributeType;
   private byte[] attributeOID;
   private byte[] valueDER;
   private int valueOffset;
   private int valueLen;
   private int valueType = -1;
   private String valueString;
   private static final char UNKNOWN_CHAR = '?';
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   protected AttributeValueAssertion() {
   }

   public AttributeValueAssertion(String var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("AVA string is null");
      } else {
         String[] var3 = var1.split("=", 2);
         if (var3.length != 2) {
            throw new NameException("AVA representation is invalid, should be 'type=value'");
         } else {
            String var4 = var3[0].trim();
            String var5 = var3[1];
            int var6 = findAttributeType(var4);
            if (var6 == -1) {
               byte[] var7;
               try {
                  var7 = CertJUtils.oidStringToBytes(var4);
               } catch (IllegalArgumentException var9) {
                  throw new NameException("Invalid OID: should be the dotted-decimal encoding with at least 2 components.");
               }

               byte[] var8 = this.berStringToBytes(var5);
               this.createAVA(var7, var8);
            } else {
               String var10 = this.encodeStringAsType(var5, var2);
               this.attributeType = var6;
               if (var2 == 0) {
                  this.valueType = getDefaultValueType(var6);
               } else {
                  this.valueType = var2;
               }

               this.verifyAttribute(this.attributeType, this.valueType, var10);
               if (!this.supportedASN1Type(this.valueType)) {
                  throw new NameException("Unsupported ASN1 type. Use another constructor that takes the DER form.");
               }

               this.valueString = var10;
            }

         }
      }
   }

   private String encodeStringAsType(String var1, int var2) throws NameException {
      Object var3 = null;

      try {
         byte[] var6;
         switch (var2) {
            case 3072:
            case 7168:
            case 7680:
            default:
               return var1;
            case 4864:
               var6 = var1.getBytes("US-ASCII");

               for(int var4 = 0; var4 < var6.length; ++var4) {
                  if (var6[var4] < 97 && var6[var4] > 122 && var6[var4] < 65 && var6[var4] > 90 && var6[var4] < 48 && var6[var4] > 57 && var6[var4] != 32 && var6[var4] != 39 && var6[var4] != 40 && var6[var4] != 41 && var6[var4] != 43 && var6[var4] != 44 && var6[var4] != 45 && var6[var4] != 46 && var6[var4] != 47 && var6[var4] != 58 && var6[var4] != 61 && var6[var4] != 63) {
                     var6[var4] = 63;
                  }
               }

               return new String(var6, "US-ASCII");
            case 5120:
               var6 = var1.getBytes("ISO-8859-1");
               return new String(var6, "ISO-8859-1");
            case 5632:
               var6 = var1.getBytes("US-ASCII");
               return new String(var6, "US-ASCII");
         }
      } catch (UnsupportedEncodingException var5) {
         throw new NameException("Cannot convert to chosen string encoding type");
      }
   }

   private byte[] berStringToBytes(String var1) {
      String var2;
      if (var1.startsWith("#")) {
         var2 = var1.substring(1);
      } else {
         var2 = var1;
      }

      int var3 = var2.length();
      byte[] var4;
      if (var3 % 2 == 0) {
         var4 = new byte[var3 / 2];
      } else {
         var4 = new byte[var3 / 2 + 1];
      }

      int var5 = var4.length - 1;

      for(int var6 = var3; var5 >= 0; var6 -= 2) {
         if (var6 == 1) {
            var4[var5] = (byte)Integer.parseInt(var2.substring(var6 - 1, var6), 16);
         } else if (var6 == var3) {
            var4[var5] = (byte)Integer.parseInt(var2.substring(var6 - 2), 16);
         } else {
            var4[var5] = (byte)Integer.parseInt(var2.substring(var6 - 2, var6), 16);
         }

         --var5;
      }

      return var4;
   }

   /** @deprecated */
   public AttributeValueAssertion(int var1, byte[] var2, byte[] var3, int var4, int var5) throws NameException {
      this.attributeType = var1;
      if (var1 < -1 || var1 >= ALL_NAME_ATTRIBUTE_IDS.length) {
         this.attributeType = -1;
      }

      if (var3 != null && var5 != 0) {
         this.valueDER = var3;
         this.valueOffset = var4;
         this.valueLen = var5;
         if (this.attributeType == -1) {
            if (var2 == null) {
               throw new NameException("AVA type is missing.");
            } else {
               this.attributeOID = var2;

               try {
                  this.decodeDirectoryString(this.valueDER, this.valueOffset, -1);
               } catch (NameException var7) {
                  this.valueString = null;
               }

            }
         } else {
            this.decodeString(var3, var4);
         }
      } else {
         throw new NameException("BER encoding is null.");
      }
   }

   /** @deprecated */
   public AttributeValueAssertion(int var1, byte[] var2, int var3, String var4) throws NameException {
      this.attributeType = var1;
      if (var1 < -1 || var1 >= ALL_NAME_ATTRIBUTE_IDS.length) {
         this.attributeType = -1;
      }

      if (var3 == 0) {
         var3 = getDefaultValueType(var1);
      }

      if (var1 == 14 && var3 == 1280 && var4 == null) {
         var3 = 4864;
         var4 = this.getDRBGDefaultString();
      }

      this.valueType = var3;
      if (this.attributeType == -1) {
         if (var2 == null) {
            throw new NameException("AVA type is missing.");
         }

         this.attributeOID = var2;
      } else {
         this.verifyAttribute(this.attributeType, this.valueType, var4);
      }

      if (!this.supportedASN1Type(this.valueType)) {
         throw new NameException("Unsupported ASN1 type. Use another constructor that takes the DER form.");
      } else if (var4 == null) {
         throw new NameException("AVA value is missing.");
      } else {
         this.valueString = var4;
      }
   }

   private String getDRBGDefaultString() {
      SecureRandom var1 = CryptoJ.getDefaultRandom();
      byte[] var2 = new byte[32];
      var1.nextBytes(var2);
      return e.a(var2);
   }

   /** @deprecated */
   public AttributeValueAssertion(byte[] var1, byte[] var2) throws NameException {
      this.createAVA(var1, var2);
   }

   /** @deprecated */
   public void createAVA(byte[] var1, byte[] var2) throws NameException {
      if (var1 != null && var2 != null) {
         this.attributeType = findOID(var1, 0, var1.length);
         this.attributeOID = var1;
         this.valueDER = var2;
         this.valueOffset = 0;
         this.valueLen = var2.length;

         try {
            this.decodeDirectoryString(this.valueDER, this.valueOffset, -1);
         } catch (NameException var4) {
            this.valueString = null;
         }

      } else {
         throw new NameException("AVA data is missing.");
      }
   }

   /** @deprecated */
   public int getAttributeType() {
      return this.attributeType;
   }

   /** @deprecated */
   public int getValueType() {
      return this.valueType;
   }

   private static int getDefaultValueType(int var0) throws NameException {
      if (var0 == -1) {
         throw new NameException("ASN.1 type is missing.");
      } else {
         switch (var0) {
            case 1:
            case 6:
            case 14:
            case 18:
            case 24:
            case 25:
            case 26:
            case 29:
               return 4864;
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            case 21:
            case 23:
            case 27:
            case 28:
            default:
               return 3072;
            case 7:
            case 19:
               return 5632;
            case 22:
               return 6144;
         }
      }
   }

   /** @deprecated */
   public void setAttributeValue(String var1) {
      if (var1 != null) {
         this.valueString = var1;
      }

   }

   /** @deprecated */
   public void setAttributeValue(byte[] var1, int var2, int var3) throws NameException {
      if (var1 != null && var3 != 0) {
         this.valueDER = var1;
         this.valueOffset = var2;
         this.valueLen = var3;
         if (this.attributeType != -1) {
            this.decodeString(var1, var2);
         }
      } else {
         throw new NameException("BER encoding is null.");
      }
   }

   private void decodeString(byte[] var1, int var2) throws NameException {
      if (CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_STRICT_CERT)) {
         this.decodeStringStrict(var1, var2);
      } else {
         this.decodeStringInternal(var1, var2);
      }

   }

   private void decodeStringInternal(byte[] var1, int var2) throws NameException {
      switch (this.attributeType) {
         case 0:
            this.decodeDirectoryString(var1, var2, Math.max(64, 500));
            break;
         case 1:
            this.decodeDirectoryString(var1, var2, 500);
            this.valueType = 4864;
            break;
         case 2:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
            break;
         case 3:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
            break;
         case 4:
            this.decodeDirectoryString(var1, var2, Math.max(64, 500));
            break;
         case 5:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
            break;
         case 6:
            this.decodeDirectoryString(var1, var2, Math.max(32, 500));
            this.valueType = 4864;
            break;
         case 7:
            this.decodeDirectoryString(var1, var2, Math.max(64, 500));
            break;
         case 8:
            this.decodeDirectoryString(var1, var2, Math.max(64, 500));
            break;
         case 9:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
            break;
         case 10:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
            break;
         case 11:
            this.decodeDirectoryString(var1, var2, Math.max(40, 500));
            break;
         case 12:
            this.decodeDirectoryString(var1, var2, Math.max(32768, 500));
            break;
         case 13:
            this.decodeDirectoryString(var1, var2, Math.max(32768, 500));
            break;
         case 14:
            this.decodePrintableString(var1, var2, 1, Math.max(64, 500));
            break;
         case 15:
            this.decodeDirectoryString(var1, var2, Math.max(32768, 500));
            break;
         case 16:
            this.decodeDirectoryString(var1, var2, Math.max(32768, 500));
            break;
         case 17:
            this.decodeDirectoryString(var1, var2, Math.max(32768, 500));
            break;
         case 18:
            this.decodeDirectoryString(var1, var2, -1);
            this.valueType = 4864;
            break;
         case 19:
            this.decodeIA5String(var1, var2, 1, Math.max(64, 500));
            break;
         case 20:
            this.decodeSeqDirectoryString(var1, var2, -1);
            break;
         case 21:
            this.decodeDirectoryString(var1, var2, -1);
            break;
         case 22:
            this.decodeTime(var1, var2);
            break;
         case 23:
            this.decodeDirectoryString(var1, var2, Math.max(32768, 500));
            break;
         case 24:
            this.decodeDirectoryString(var1, var2, Math.max(1, 500));
            this.valueType = 4864;
            break;
         case 25:
            this.decodeDirectoryString(var1, var2, Math.max(2, 500));
            this.valueType = 4864;
            break;
         case 26:
            this.decodeDirectoryString(var1, var2, Math.max(2, 500));
            this.valueType = 4864;
            break;
         case 27:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
            break;
         case 28:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
            break;
         case 29:
            this.decodeDirectoryString(var1, var2, 500);
            this.valueType = 4864;
            break;
         case 30:
            this.decodeDirectoryString(var1, var2, Math.max(128, 500));
      }

   }

   private void decodeStringStrict(byte[] var1, int var2) throws NameException {
      switch (this.attributeType) {
         case 0:
            this.decodeDirectoryString(var1, var2, 64);
            break;
         case 1:
            this.decodePrintableString(var1, var2, 2, 2);
            break;
         case 2:
            this.decodeDirectoryString(var1, var2, 128);
            break;
         case 3:
            this.decodeDirectoryString(var1, var2, 128);
            break;
         case 4:
            this.decodeDirectoryString(var1, var2, 64);
            break;
         case 5:
            this.decodeDirectoryString(var1, var2, 128);
            break;
         case 6:
            this.decodePrintableString(var1, var2, 1, 32);
            break;
         case 7:
            this.decodeDirectoryString(var1, var2, 64);
            break;
         case 8:
            this.decodeDirectoryString(var1, var2, 64);
            break;
         case 9:
            this.decodeDirectoryString(var1, var2, 128);
            break;
         case 10:
            this.decodeDirectoryString(var1, var2, 128);
            break;
         case 11:
            this.decodeDirectoryString(var1, var2, 40);
            break;
         case 12:
            this.decodeDirectoryString(var1, var2, 32768);
            break;
         case 13:
            this.decodeDirectoryString(var1, var2, 32768);
            break;
         case 14:
            this.decodePrintableString(var1, var2, 1, 64);
            break;
         case 15:
            this.decodeDirectoryString(var1, var2, 32768);
            break;
         case 16:
            this.decodeDirectoryString(var1, var2, 32768);
            break;
         case 17:
            this.decodeDirectoryString(var1, var2, 32768);
            break;
         case 18:
            this.decodePrintableString(var1, var2, 1, -1);
            break;
         case 19:
            this.decodeIA5String(var1, var2, 1, 64);
            break;
         case 20:
            this.decodeSeqDirectoryString(var1, var2, -1);
            break;
         case 21:
            this.decodeDirectoryString(var1, var2, -1);
            break;
         case 22:
            this.decodeTime(var1, var2);
            break;
         case 23:
            this.decodeDirectoryString(var1, var2, 32768);
            break;
         case 24:
            this.decodePrintableString(var1, var2, 1, 1);
            break;
         case 25:
            this.decodePrintableString(var1, var2, 2, 2);
            break;
         case 26:
            this.decodePrintableString(var1, var2, 2, 2);
            break;
         case 27:
            this.decodeDirectoryString(var1, var2, 128);
            break;
         case 28:
            this.decodeDirectoryString(var1, var2, 128);
            break;
         case 29:
            this.decodePrintableString(var1, var2, 2, 2);
            break;
         case 30:
            this.decodeDirectoryString(var1, var2, 128);
      }

   }

   /** @deprecated */
   public String getStringAttribute() throws NameException {
      return this.getStringAttribute(true);
   }

   /** @deprecated */
   public String getStringAttribute(boolean var1) throws NameException {
      if (this.valueString == null) {
         if (this.valueDER == null) {
            throw new NameException("Cannot form the attribute's value as a String.");
         } else {
            return this.valueString();
         }
      } else {
         StringBuffer var2 = new StringBuffer();
         StringTokenizer var3 = new StringTokenizer(this.valueString, ",+\"\\<>;", true);

         String var4;
         while(var3.hasMoreElements()) {
            var4 = var3.nextToken();
            if (var4.length() == 1) {
               char var5 = var4.charAt(0);
               switch (var5) {
                  case '"':
                  case '+':
                  case ',':
                  case ';':
                  case '<':
                  case '>':
                  case '\\':
                     var2.append('\\');
                     var2.append(var5);
                     break;
                  default:
                     var2.append(var5);
               }
            } else {
               var2.append(var4);
            }
         }

         var4 = var2.toString();
         var2 = new StringBuffer();
         var3 = new StringTokenizer(var4, "#", true);

         String var15;
         while(var3.hasMoreElements()) {
            var15 = var3.nextToken();
            if (var15.equals("#")) {
               if (var2.length() == 0) {
                  var2.append("\\#");
               } else {
                  var2.append("\\23");
               }
            } else {
               var2.append(var15);
            }
         }

         var15 = var2.toString();
         boolean var6 = var15.length() == 1;
         if (var15.startsWith(" ")) {
            var2 = new StringBuffer();
            var2.append("\\");
            var2.append(var15);
            var15 = var2.toString();
         }

         if (var15.endsWith(" ") && !var6) {
            var2 = new StringBuffer();
            var2.append(var15);
            var2.insert(var15.length() - 1, "\\");
            var15 = var2.toString();
         }

         StringBuffer var7 = new StringBuffer();
         if (var1) {
            return var15;
         } else {
            for(int var8 = 0; var8 < var15.length(); ++var8) {
               char var9 = var15.charAt(var8);
               if (var9 <= 128 && var9 >= ' ') {
                  var7.append(var9);
               } else {
                  String var10 = var15.substring(var8, var8 + 1);

                  byte[] var11;
                  try {
                     var11 = var10.getBytes("UTF-8");
                  } catch (UnsupportedEncodingException var14) {
                     throw new NameException(var14);
                  }

                  for(int var12 = 0; var12 < var11.length; ++var12) {
                     var7.append('\\');
                     String var13 = Integer.toHexString(var11[var12] & 255).toUpperCase();
                     if (var13.length() == 1) {
                        var7.append('0');
                     }

                     var7.append(var13);
                  }
               }
            }

            return var7.toString();
         }
      }
   }

   /** @deprecated */
   public String getStringAttributeNoEscapeSequences() throws NameException {
      if (this.valueString == null && this.valueDER == null) {
         throw new NameException("Cannot form the attribute's value as a String.");
      } else {
         return this.valueString == null ? this.valueString() : this.valueString;
      }
   }

   /** @deprecated */
   protected Object clone() throws CloneNotSupportedException {
      AttributeValueAssertion var1 = new AttributeValueAssertion();
      var1.attributeType = this.attributeType;
      if (this.attributeOID == null) {
         var1.attributeOID = null;
      } else {
         var1.attributeOID = new byte[this.attributeOID.length];
         System.arraycopy(this.attributeOID, 0, var1.attributeOID, 0, this.attributeOID.length);
      }

      if (this.valueDER == null) {
         var1.valueDER = null;
      } else {
         var1.valueDER = new byte[this.valueDER.length];
         System.arraycopy(this.valueDER, 0, var1.valueDER, 0, this.valueDER.length);
      }

      var1.valueOffset = this.valueOffset;
      var1.valueLen = this.valueLen;
      var1.valueType = this.valueType;
      var1.valueString = this.valueString;
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof AttributeValueAssertion) {
         AttributeValueAssertion var2 = (AttributeValueAssertion)var1;
         if (!this.oidEquals(var2)) {
            return false;
         } else {
            return this.valueEquals(var2);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      StringBuffer var1 = new StringBuffer();
      if (this.attributeOID != null) {
         var1.append(new String(this.attributeOID));
      }

      if (this.valueString != null) {
         var1.append(NameMatcher.compressWhiteSpaces(this.valueString));
      } else if (this.valueDER != null) {
         var1.append(new String(this.valueDER));
      }

      return var1.toString().hashCode();
   }

   private boolean oidEquals(AttributeValueAssertion var1) {
      byte[] var2;
      if (this.attributeOID == null) {
         if (var1.attributeOID == null) {
            return this.attributeType == var1.attributeType;
         } else {
            var2 = ALL_NAME_ATTRIBUTE_IDS[this.attributeType].a;
            return CertJUtils.byteArraysEqual(var2, var1.attributeOID);
         }
      } else if (var1.attributeOID == null) {
         var2 = ALL_NAME_ATTRIBUTE_IDS[var1.attributeType].a;
         return CertJUtils.byteArraysEqual(this.attributeOID, var2);
      } else {
         return CertJUtils.byteArraysEqual(this.attributeOID, var1.attributeOID);
      }
   }

   /** @deprecated */
   public boolean valueEquals(AttributeValueAssertion var1) {
      if (this.valueString != null) {
         if (var1.valueString == null) {
            return false;
         } else if (this.valueType == 4864) {
            String var2 = NameMatcher.compressWhiteSpaces(this.valueString);
            String var3 = NameMatcher.compressWhiteSpaces(var1.valueString);
            return var2.equalsIgnoreCase(var3);
         } else {
            return this.valueString.equals(var1.valueString);
         }
      } else if (var1.valueString != null) {
         return false;
      } else if (this.valueDER != null) {
         return var1.valueDER == null ? false : CertJUtils.byteArraysEqual(this.valueDER, this.valueOffset, this.valueLen, var1.valueDER, var1.valueOffset, var1.valueLen);
      } else {
         return false;
      }
   }

   static int findOID(byte[] var0, int var1, int var2) {
      if (var0 != null && var2 != 0) {
         for(int var3 = 0; var3 < ALL_NAME_ATTRIBUTE_IDS.length; ++var3) {
            if (var2 == ALL_NAME_ATTRIBUTE_IDS[var3].a.length) {
               int var4;
               for(var4 = 0; var4 < var2 && (var0[var4 + var1] & 255) == (ALL_NAME_ATTRIBUTE_IDS[var3].a[var4] & 255); ++var4) {
               }

               if (var4 >= var2) {
                  return var3;
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   /** @deprecated */
   public static int findAttributeType(String var0) {
      if (var0 == null) {
         return -1;
      } else {
         int var1;
         for(var1 = 0; var1 < ALL_NAME_ATTRIBUTE_IDS.length; ++var1) {
            if (var0.equalsIgnoreCase(ALL_NAME_ATTRIBUTE_IDS[var1].b)) {
               return var1;
            }
         }

         for(var1 = 0; var1 < alternativeAttributeNames.length; ++var1) {
            if (var0.equalsIgnoreCase(alternativeAttributeNames[var1].b)) {
               return alternativeAttributeNames[var1].a;
            }
         }

         return -1;
      }
   }

   /** @deprecated */
   protected static byte[] getAttributeOID(int var0) {
      return var0 >= 0 && var0 < ALL_NAME_ATTRIBUTE_IDS.length ? ALL_NAME_ATTRIBUTE_IDS[var0].a : null;
   }

   private void verifyAttribute(int var1, int var2, String var3) throws NameException {
      if (var3 == null) {
         throw new NameException("AVA value is null.");
      } else {
         if (CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_STRICT_CERT)) {
            this.verifyAttributeStrict(var1, var2, var3);
         } else {
            this.verifyAttributeInternal(var1, var2, var3);
         }

      }
   }

   private void verifyAttributeStrict(int var1, int var2, String var3) throws NameException {
      switch (var1) {
         case 0:
            this.verifyDirectoryString(var2, var3, 64);
            break;
         case 1:
            this.verifyPrintableString(var2, var3, 2, 2);
            break;
         case 2:
            this.verifyDirectoryString(var2, var3, 128);
            break;
         case 3:
            this.verifyDirectoryString(var2, var3, 128);
         case 28:
            this.verifyDirectoryString(var2, var3, 128);
            break;
         case 4:
            this.verifyDirectoryString(var2, var3, 64);
            break;
         case 5:
            this.verifyDirectoryString(var2, var3, 128);
            break;
         case 6:
            this.verifyPrintableString(var2, var3, 1, 32);
            break;
         case 7:
            this.verifyIA5String(var2, var3, 1, 64);
            break;
         case 8:
            this.verifyDirectoryString(var2, var3, 64);
            break;
         case 9:
            this.verifyDirectoryString(var2, var3, 128);
            break;
         case 10:
            this.verifyDirectoryString(var2, var3, 128);
            break;
         case 11:
            this.verifyDirectoryString(var2, var3, 40);
            break;
         case 12:
            this.verifyDirectoryString(var2, var3, 32768);
            break;
         case 13:
            this.verifyDirectoryString(var2, var3, 32768);
            break;
         case 14:
            this.verifyPrintableString(var2, var3, 1, 64);
            break;
         case 15:
            this.verifyDirectoryString(var2, var3, 32768);
            break;
         case 16:
            this.verifyDirectoryString(var2, var3, 32768);
            break;
         case 17:
            this.verifyDirectoryString(var2, var3, 32768);
            break;
         case 18:
            this.verifyPrintableString(var2, var3, 1, -1);
            break;
         case 19:
            this.verifyIA5String(var2, var3, 1, 64);
            break;
         case 20:
            this.verifyDirectoryString(var2, var3, -1);
            break;
         case 21:
            this.verifyDirectoryString(var2, var3, -1);
            break;
         case 22:
            this.verifyTimeString(var2);
            break;
         case 23:
            this.verifyDirectoryString(var2, var3, 32768);
            break;
         case 24:
            this.verifyPrintableString(var2, var3, 1, 1);
            break;
         case 25:
            this.verifyPrintableString(var2, var3, 2, 2);
            break;
         case 26:
            this.verifyPrintableString(var2, var3, 2, 2);
            break;
         case 27:
            this.verifyDirectoryString(var2, var3, 128);
            break;
         case 29:
            this.verifyPrintableString(var2, var3, 2, 2);
            break;
         case 30:
            this.verifyDirectoryString(var2, var3, 128);
      }

   }

   private void verifyAttributeInternal(int var1, int var2, String var3) throws NameException {
      switch (var1) {
         case 0:
            this.verifyDirectoryString(var2, var3, Math.max(64, 500));
            break;
         case 1:
            this.verifyDirectoryString(var2, var3, 500);
            this.valueType = 4864;
            break;
         case 2:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
            break;
         case 3:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
            break;
         case 4:
            this.verifyDirectoryString(var2, var3, Math.max(64, 500));
            break;
         case 5:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
            break;
         case 6:
            this.verifyPrintableString(var2, var3, 1, Math.max(32, 500));
            break;
         case 7:
            this.verifyIA5String(var2, var3, 1, Math.max(64, 500));
            break;
         case 8:
            this.verifyDirectoryString(var2, var3, Math.max(64, 500));
            break;
         case 9:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
            break;
         case 10:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
            break;
         case 11:
            this.verifyDirectoryString(var2, var3, Math.max(40, 500));
            break;
         case 12:
            this.verifyDirectoryString(var2, var3, Math.max(32768, 500));
            break;
         case 13:
            this.verifyDirectoryString(var2, var3, Math.max(32768, 500));
            break;
         case 14:
            this.verifyPrintableString(var2, var3, 1, Math.max(64, 500));
            break;
         case 15:
            this.verifyDirectoryString(var2, var3, Math.max(32768, 500));
            break;
         case 16:
            this.verifyDirectoryString(var2, var3, Math.max(32768, 500));
            break;
         case 17:
            this.verifyDirectoryString(var2, var3, Math.max(32768, 500));
            break;
         case 18:
            this.verifyPrintableString(var2, var3, 1, -1);
            break;
         case 19:
            this.verifyIA5String(var2, var3, 1, Math.max(64, 500));
            break;
         case 20:
            this.verifyDirectoryString(var2, var3, -1);
            break;
         case 21:
            this.verifyDirectoryString(var2, var3, -1);
            break;
         case 22:
            this.verifyTimeString(var2);
            break;
         case 23:
            this.verifyDirectoryString(var2, var3, Math.max(32768, 500));
            break;
         case 24:
            this.verifyPrintableString(var2, var3, 1, Math.max(1, 500));
            break;
         case 25:
            this.verifyDirectoryString(var2, var3, 500);
            this.valueType = 4864;
            break;
         case 26:
            this.verifyDirectoryString(var2, var3, 500);
            this.valueType = 4864;
            break;
         case 27:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
            break;
         case 28:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
            break;
         case 29:
            this.verifyDirectoryString(var2, var3, 500);
            this.valueType = 4864;
            break;
         case 30:
            this.verifyDirectoryString(var2, var3, Math.max(128, 500));
      }

   }

   private void verifyDirectoryString(int var1, String var2, int var3) throws NameException {
      if (var1 != 4864 && var1 != 5120 && var1 != 7168 && var1 != 3072 && var1 != 7680 && var1 != 5632) {
         throw new NameException("DirectoryString expected.");
      } else if (var2.length() < 1) {
         throw new NameException("DirectoryString too small.");
      } else if (var3 != -1 && var2.length() > var3) {
         throw new NameException("DirectoryString too large.");
      }
   }

   private void verifyPrintableString(int var1, String var2, int var3, int var4) throws NameException {
      if (var1 != 4864) {
         throw new NameException("PrintableString expected.");
      } else if (var3 != -1 && var2.length() < var3) {
         throw new NameException("PrintableString too small.");
      } else if (var4 != -1 && var2.length() > var4) {
         throw new NameException("PrintableString too large.");
      }
   }

   private void verifyTimeString(int var1) throws NameException {
      if (var1 != 6144) {
         throw new NameException("GenTime is expected.");
      }
   }

   private void verifyIA5String(int var1, String var2, int var3, int var4) throws NameException {
      if (var1 != 5632) {
         throw new NameException("IA5String expected.");
      } else if (var3 != -1 && var2.length() < var3) {
         throw new NameException("IA5String too small.");
      } else if (var4 != -1 && var2.length() > var4) {
         throw new NameException("IA5String too large.");
      }
   }

   private boolean supportedASN1Type(int var1) {
      if (var1 == 4864) {
         return true;
      } else if (var1 == 5120) {
         return true;
      } else if (var1 == 7168) {
         return true;
      } else if (var1 == 5632) {
         return true;
      } else if (var1 == 7680) {
         return true;
      } else if (var1 == 3072) {
         return true;
      } else {
         return var1 == 6144;
      }
   }

   void decodeDirectoryString(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         PrintStringContainer var4;
         TeletexStringContainer var5;
         UniversalStringContainer var6;
         BMPStringContainer var7;
         UTF8StringContainer var8;
         IA5StringContainer var9;
         try {
            ChoiceContainer var10 = new ChoiceContainer(0);
            var4 = new PrintStringContainer(0, 1, var3);
            var5 = new TeletexStringContainer(0, 1, var3);
            var6 = new UniversalStringContainer(0, 1, var3);
            var7 = new BMPStringContainer(0, 1, var3);
            var8 = new UTF8StringContainer(0);
            var9 = new IA5StringContainer(0, 1, var3);
            EndContainer var11 = new EndContainer();
            ASN1Container[] var12 = new ASN1Container[]{var10, var4, var5, var6, var8, var7, var9, var11};
            ASN1.berDecode(var1, var2, var12);
         } catch (ASN_Exception var13) {
            throw new NameException("DirectoryString expected.");
         }

         if (var4.dataPresent) {
            this.valueType = 4864;
            this.valueString = var4.getValueAsString();
         } else if (var5.dataPresent) {
            this.valueType = 5120;
            this.valueString = var5.getValueAsString();
         } else if (var6.dataPresent) {
            this.valueType = 7168;
            this.valueString = var6.getValueAsString();
         } else if (var7.dataPresent) {
            this.valueType = 7680;
            this.valueString = var7.getValueAsString();
         } else if (var8.dataPresent) {
            this.valueType = 3072;
            if (var8.data != null && var8.dataLen != 0) {
               this.valueString = this.utf8Decode(var8.data, var8.dataOffset, var8.dataLen);
               this.verifyAttribute(this.attributeType, this.valueType, this.valueString);
            } else {
               this.valueString = null;
            }
         } else {
            if (!var9.dataPresent) {
               throw new NameException("DirectoryString expected.");
            }

            this.valueType = 5632;
            this.valueString = var9.getValueAsString();
         }

      }
   }

   void decodeSeqDirectoryString(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            OfContainer var4 = new OfContainer(0, 12288, new EncodedContainer(65280));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            StringBuffer var7 = new StringBuffer();

            for(int var8 = 0; var8 < var4.getContainerCount(); ++var8) {
               EncodedContainer var6 = (EncodedContainer)var4.containerAt(var8);
               this.decodeDirectoryString(var6.data, var6.dataOffset, var3);
               if (var8 != 0) {
                  var7.append(", ");
               }

               var7.append(this.valueString);
            }

            this.valueString = var7.toString();
            this.valueType = 3072;
         } catch (ASN_Exception var9) {
            throw new NameException("Invalid encoding. ", var9);
         }
      }
   }

   void decodePrintableString(byte[] var1, int var2, int var3, int var4) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         PrintStringContainer var5;
         try {
            var5 = new PrintStringContainer(0, var3, var4);
            ASN1Container[] var6 = new ASN1Container[]{var5};
            ASN1.berDecode(var1, var2, var6);
         } catch (ASN_Exception var7) {
            throw new NameException("PrintableString expected.");
         }

         this.valueType = 4864;
         this.valueString = var5.getValueAsString();
      }
   }

   void decodeTime(byte[] var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         GenTimeContainer var3;
         try {
            var3 = new GenTimeContainer(0);
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
         } catch (ASN_Exception var5) {
            throw new NameException("GenTime expected.", var5);
         }

         this.valueType = 6144;
         SimpleDateFormat var6 = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss z");
         this.valueString = var6.format(var3.theTime);
      }
   }

   void decodeIA5String(byte[] var1, int var2, int var3, int var4) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         IA5StringContainer var5;
         try {
            var5 = new IA5StringContainer(0, var3, var4);
            ASN1Container[] var6 = new ASN1Container[]{var5};
            ASN1.berDecode(var1, var2, var6);
         } catch (ASN_Exception var7) {
            throw new NameException("IA5String expected.");
         }

         this.valueType = 5632;
         this.valueString = var5.getValueAsString();
      }
   }

   /** @deprecated */
   public String toString() {
      return this.toString(true);
   }

   /** @deprecated */
   public String toString(boolean var1) {
      String var2 = null;
      StringBuffer var3 = new StringBuffer();

      try {
         if (this.valueString != null) {
            var2 = this.getStringAttribute(var1);
         }
      } catch (NameException var5) {
      }

      if (this.attributeType == -1) {
         var3.append(this.oidString());
         var3.append("=");
         if (var2 != null) {
            var3.append(var2);
         } else if (this.valueDER != null) {
            var3.append(this.valueString());
         }
      } else {
         if (CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_EMAIL_AVA_EA) && this.attributeType == 7) {
            var3.append("Ea");
         } else {
            var3.append(ALL_NAME_ATTRIBUTE_IDS[this.attributeType].b);
         }

         var3.append("=");
         var3.append(var2);
      }

      return var3.toString();
   }

   private String oidString() {
      if (this.attributeOID == null) {
         return "";
      } else {
         StringBuffer var1 = new StringBuffer();
         int var2 = 0;
         int var3 = 0;
         var1 = var1.append((this.attributeOID[var2] & 255) / 40);
         var1 = var1.append('.');
         var1 = var1.append((this.attributeOID[var2] & 255) % 40);
         ++var2;

         while(var2 < this.attributeOID.length) {
            var1 = var1.append('.');

            do {
               var3 = (var3 << 7) + (this.attributeOID[var2] & 127 & 255);
            } while((this.attributeOID[var2++] & 128) != 0 && var2 < this.attributeOID.length);

            var1 = var1.append(var3);
            var3 = 0;
         }

         return var1.toString();
      }
   }

   private String valueString() {
      if (this.valueDER == null) {
         return "";
      } else {
         StringBuffer var3 = new StringBuffer();
         var3 = var3.append("#");

         for(int var4 = this.valueOffset; var4 < this.valueLen + this.valueOffset; ++var4) {
            int var1 = this.valueDER[var4] & 255;
            String var2 = Integer.toHexString(var1);
            if (var2.length() == 1) {
               var3 = var3.append("0");
            }

            var3 = var3.append(var2);
         }

         return var3.toString();
      }
   }

   private ASN1Container createTimeContainer() {
      SimpleDateFormat var1 = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss z");
      ParsePosition var2 = new ParsePosition(0);
      Date var3 = var1.parse(this.valueString, var2);
      return new GenTimeContainer(0, true, 0, var3);
   }

   private ASN1Container createSeqContainer(int var1, int var2) throws NameException {
      try {
         ASN1Container var3 = this.createStringContainer(var1, var2);
         SequenceContainer var4 = new SequenceContainer(0);
         EndContainer var5 = new EndContainer();
         ASN1Container[] var6 = new ASN1Container[]{var4, var3, var5};
         this.asn1Template = new ASN1Template(var6);
         int var7 = this.asn1Template.derEncodeInit();
         byte[] var8 = new byte[var7];
         var7 = this.asn1Template.derEncode(var8, 0);
         return new EncodedContainer(12288, true, 0, var8, 0, var7);
      } catch (ASN_Exception var9) {
         throw new NameException("Invalid String.", var9);
      }
   }

   private ASN1Container createStringContainer(int var1, int var2) throws NameException {
      try {
         switch (this.valueType) {
            case 3072:
               byte[] var5 = this.utf8Encode(this.valueString);
               if (var5.length < 2) {
                  throw new NameException("AttributeValueAssertion.createStringContainer: DataOutputStream.writeUTF() did not contain necessary 2 bytes specifying the encoding length.");
               }

               return new UTF8StringContainer(0, true, 0, var5, 2, var5.length - 2);
            case 4864:
               boolean var3 = CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_STRICT_CERT);
               int var4 = var3 ? 0 : 134217728;
               return new PrintStringContainer(var4, true, 0, this.valueString, var1, var2);
            case 5120:
               return new TeletexStringContainer(0, true, 0, this.valueString, var1, var2);
            case 5632:
               return new IA5StringContainer(0, true, 0, this.valueString, var1, var2);
            case 7168:
               return new UniversalStringContainer(0, true, 0, this.valueString, var1, var2);
            case 7680:
               return new BMPStringContainer(0, true, 0, this.valueString, var1, var2);
            default:
               return null;
         }
      } catch (ASN_Exception var6) {
         throw new NameException("Invalid String.", var6);
      }
   }

   private byte[] utf8Encode(String var1) throws NameException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      DataOutputStream var3 = new DataOutputStream(var2);

      try {
         var3.writeUTF(var1);
         var3.flush();
      } catch (IOException var5) {
         throw new NameException("AttributeValueAssertion.utf8Encode: unable to utf8-encode " + var1 + ".", var5);
      }

      return var2.toByteArray();
   }

   private String utf8Decode(byte[] var1, int var2, int var3) throws NameException {
      try {
         return new String(var1, var2, var3, "UTF-8");
      } catch (IOException var5) {
         throw new NameException("AttributeValueAssertion.utf8Decode.", var5);
      }
   }

   /** @deprecated */
   public int getDERLen() {
      try {
         SequenceContainer var1 = new SequenceContainer(0, true, 0);
         OIDContainer var2;
         if (this.attributeType != -1) {
            byte[] var3 = ALL_NAME_ATTRIBUTE_IDS[this.attributeType].a;
            var2 = new OIDContainer(16777216, true, 0, var3, 0, var3.length);
         } else {
            var2 = new OIDContainer(16777216, true, 0, this.attributeOID, 0, this.attributeOID.length);
         }

         Object var8;
         if (this.valueType == -1) {
            var8 = new EncodedContainer(65280, true, 0, this.valueDER, this.valueOffset, this.valueLen);
         } else if (this.valueType == 20) {
            var8 = this.createSeqContainer(this.getLowerBound(this.attributeType), this.getUpperBound(this.attributeType));
         } else if (this.valueType == 22) {
            var8 = this.createTimeContainer();
         } else {
            var8 = this.createStringContainer(this.getLowerBound(this.attributeType), this.getUpperBound(this.attributeType));
         }

         EndContainer var4 = new EndContainer();
         ASN1Container[] var5 = new ASN1Container[]{var1, var2, (ASN1Container)var8, var4};
         this.asn1Template = new ASN1Template(var5);
         return this.asn1Template.derEncodeInit();
      } catch (NameException var6) {
         return 0;
      } catch (ASN_Exception var7) {
         return 0;
      }
   }

   private int getLowerBound(int var1) {
      boolean var2 = CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_STRICT_CERT);
      switch (var1) {
         case 0:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 23:
         case 24:
         case 27:
         case 28:
         case 30:
            return 1;
         case 1:
         case 25:
         case 26:
         case 29:
            return var2 ? 2 : 1;
         case 22:
         default:
            return -1;
      }
   }

   private int getUpperBound(int var1) {
      boolean var2 = CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_STRICT_CERT);
      switch (var1) {
         case 0:
            return var2 ? 64 : Math.max(64, 500);
         case 1:
            return var2 ? 2 : Math.max(2, 500);
         case 2:
            return var2 ? 128 : Math.max(128, 500);
         case 3:
            return var2 ? 128 : Math.max(128, 500);
         case 4:
            return var2 ? 64 : Math.max(64, 500);
         case 5:
            return var2 ? 128 : Math.max(128, 500);
         case 6:
            return var2 ? 32 : Math.max(32, 500);
         case 7:
            return var2 ? 64 : Math.max(64, 500);
         case 8:
            return var2 ? 64 : Math.max(64, 500);
         case 9:
            return var2 ? 128 : Math.max(128, 500);
         case 10:
            return var2 ? 128 : Math.max(128, 500);
         case 11:
            return var2 ? 40 : Math.max(40, 500);
         case 12:
            return var2 ? '耀' : Math.max(32768, 500);
         case 13:
            return var2 ? '耀' : Math.max(32768, 500);
         case 14:
            return var2 ? 64 : Math.max(64, 500);
         case 15:
            return var2 ? '耀' : Math.max(32768, 500);
         case 16:
            return var2 ? '耀' : Math.max(32768, 500);
         case 17:
            return var2 ? '耀' : Math.max(32768, 500);
         case 18:
            return -1;
         case 19:
            return var2 ? 64 : Math.max(64, 500);
         case 20:
            return -1;
         case 21:
            return -1;
         case 22:
            return -1;
         case 23:
            return var2 ? '耀' : Math.max(32768, 500);
         case 24:
            return var2 ? 1 : Math.max(1, 500);
         case 25:
            return var2 ? 2 : Math.max(2, 500);
         case 26:
            return var2 ? 2 : Math.max(2, 500);
         case 27:
            return var2 ? 128 : Math.max(128, 500);
         case 28:
            return var2 ? 128 : Math.max(128, 500);
         case 29:
            return var2 ? 128 : Math.max(128, 500);
         case 30:
            return var2 ? 128 : Math.max(128, 500);
         default:
            return -1;
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified array is null.");
      } else {
         try {
            if (this.asn1Template == null) {
               int var3 = this.getDERLen();
               if (var3 == 0) {
                  throw new NameException("Could not encode AVA. ");
               }
            }

            return this.asn1Template.derEncode(var1, var2);
         } catch (ASN_Exception var4) {
            this.asn1Template = null;
            throw new NameException("Could not encode: ", var4);
         }
      }
   }

   static {
      ALL_NAME_ATTRIBUTE_IDS = new NameAttributeID[]{new NameAttributeID(COMMON_NAME_OID, "CN"), new NameAttributeID(COUNTRY_NAME_OID, "C"), new NameAttributeID(LOCALITY_NAME_OID, "L"), new NameAttributeID(STATE_NAME_OID, "ST"), new NameAttributeID(ORGANIZATION_NAME_OID, "O"), new NameAttributeID(ORGANIZATIONAL_UNIT_NAME_OID, "OU"), new NameAttributeID(TELEPHONE_NUMBER_OID, "TEL"), new NameAttributeID(EMAIL_ADDRESS_OID, "E"), new NameAttributeID(TITLE_OID, "TITLE"), new NameAttributeID(STREET_ADDRESS_OID, "STREET"), new NameAttributeID(BUSINESS_CATEGORY_OID, "BC"), new NameAttributeID(POSTAL_CODE_OID, "postalCode"), new NameAttributeID(SURNAME_OID, "SN"), new NameAttributeID(GIVEN_NAME_OID, "givenName"), new NameAttributeID(SERIAL_NUMBER_OID, "serialNumber"), new NameAttributeID(INITIALS_OID, "initials"), new NameAttributeID(GENERATION_QUALIFIER_OID, "generationQualifier"), new NameAttributeID(NAME_OID, "name"), new NameAttributeID(DN_QUALIFIER_OID, "dnQualifier"), new NameAttributeID(DOMAIN_COMPONENT_OID, "dc"), new NameAttributeID(POSTAL_ADDRESS_OID, "postalAddress"), new NameAttributeID(PSEUDONYM_OID, "pseudonym"), new NameAttributeID(DATE_OF_BIRTH_OID, "dateOfBirth"), new NameAttributeID(PLACE_OF_BIRTH_OID, "placeOfBirth"), new NameAttributeID(GENDER_OID, "gender"), new NameAttributeID(COUNTRY_OF_CITIZENSHIP_OID, "citizenship"), new NameAttributeID(COUNTRY_OF_RESIDENCE_OID, "residence"), new NameAttributeID(JURIS_OF_INCORP_LOCALITY_NAME_OID, "jurusdictionOfIncorporationLocalityName"), new NameAttributeID(JURIS_OF_INCORP_STATE_NAME_OID, "jurusdictionOfIncorporationStateOrProvinceName"), new NameAttributeID(JURIS_OF_INCORP_COUNTRY_NAME_OID, "jurusdictionOfIncorporationCountryName"), new NameAttributeID(USER_ID_OID, "uid")};
      alternativeAttributeNames = new AlternativeAttributeName[]{new AlternativeAttributeName(7, "email"), new AlternativeAttributeName(7, "mail"), new AlternativeAttributeName(7, "EmailAddress"), new AlternativeAttributeName(7, "Ea")};
   }
}
