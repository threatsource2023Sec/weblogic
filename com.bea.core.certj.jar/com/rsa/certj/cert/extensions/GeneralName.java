package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;

/** @deprecated */
public class GeneralName implements Serializable, Cloneable {
   private static final int OTHERNAME_SPECIAL = 8388608;
   private static final int RFC822NAME_SPECIAL = 8388609;
   private static final int DNSNAME_SPECIAL = 8388610;
   private static final int ORADDRESS_SPECIAL = 8388611;
   private static final int DIRNAME_SPECIAL = 10485764;
   private static final int EDINAME_SPECIAL = 8388613;
   private static final int URL_SPECIAL = 8388614;
   private static final int IPADDRESS_SPECIAL = 8388615;
   private static final int REGISTEREDID_SPECIAL = 8388616;
   private String stringValue;
   private ORAddress orAddressValue;
   private EDIPartyName ediValue;
   private OtherName otherValue;
   private X500Name x500Value;
   private byte[] byteValue;
   private int type;
   /** @deprecated */
   public static final int OTHER_NAME_TYPE = 1;
   /** @deprecated */
   public static final int RFC822_NAME_TYPE = 2;
   /** @deprecated */
   public static final int DNS_NAME_TYPE = 3;
   /** @deprecated */
   public static final int X400ADDRESS_NAME_TYPE = 4;
   /** @deprecated */
   public static final int DIRECTORY_NAME_TYPE = 5;
   /** @deprecated */
   public static final int EDIPARTY_NAME_TYPE = 6;
   /** @deprecated */
   public static final int URL_NAME_TYPE = 7;
   /** @deprecated */
   public static final int IPADDRESS_NAME_TYPE = 8;
   /** @deprecated */
   public static final int REGISTERID_NAME_TYPE = 9;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public GeneralName(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            ChoiceContainer var4 = new ChoiceContainer(var3);
            EncodedContainer var5 = new EncodedContainer(8400896);
            IA5StringContainer var6 = new IA5StringContainer(8388609);
            IA5StringContainer var7 = new IA5StringContainer(8388610);
            EncodedContainer var8 = new EncodedContainer(8400899);
            EncodedContainer var9 = new EncodedContainer(10498052);
            EncodedContainer var10 = new EncodedContainer(8400901);
            IA5StringContainer var11 = new IA5StringContainer(8388614);
            OctetStringContainer var12 = new OctetStringContainer(8388615);
            OIDContainer var13 = new OIDContainer(25165832);
            EndContainer var14 = new EndContainer();
            ASN1Container[] var15 = new ASN1Container[]{var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14};
            ASN1.berDecode(var1, var2, var15);
            byte[] var16;
            if (var5.dataPresent) {
               var16 = new byte[var5.dataLen];
               System.arraycopy(var5.data, var5.dataOffset, var16, 0, var5.dataLen);
               this.otherValue = new OtherName();
               this.otherValue.decodeValue(var16, 0, 8388608);
               this.type = 1;
            } else if (var6.dataPresent) {
               this.stringValue = var6.getValueAsString();
               this.type = 2;
            } else if (var7.dataPresent) {
               this.stringValue = var7.getValueAsString();
               this.type = 3;
            } else if (var8.dataPresent) {
               var16 = new byte[var8.dataLen];
               System.arraycopy(var8.data, var8.dataOffset, var16, 0, var8.dataLen);
               this.orAddressValue = new ORAddress(var16, 0, 8388611);
               this.type = 4;
            } else if (var9.dataPresent) {
               var16 = new byte[var9.dataLen];
               System.arraycopy(var9.data, var9.dataOffset, var16, 0, var9.dataLen);
               this.x500Value = new X500Name(var16, 0, 10485764);
               this.type = 5;
            } else if (var10.dataPresent) {
               var16 = new byte[var10.dataLen];
               System.arraycopy(var10.data, var10.dataOffset, var16, 0, var10.dataLen);
               this.ediValue = new EDIPartyName(var16, 0, 8388613);
               this.type = 6;
            } else if (var11.dataPresent) {
               this.stringValue = var11.getValueAsString();
               this.type = 7;
            } else if (var12.dataPresent) {
               this.byteValue = new byte[var12.dataLen];
               System.arraycopy(var12.data, var12.dataOffset, this.byteValue, 0, var12.dataLen);
               this.type = 8;
            } else if (var13.dataPresent) {
               this.byteValue = new byte[var13.dataLen];
               System.arraycopy(var13.data, var13.dataOffset, this.byteValue, 0, var13.dataLen);
               this.type = 9;
            }

         } catch (ASN_Exception var17) {
            throw new NameException("Cannot decode the BER of the name.");
         }
      }
   }

   /** @deprecated */
   public GeneralName() {
   }

   /** @deprecated */
   public void setGeneralName(Object var1, int var2) throws NameException {
      this.setGeneralName(var1, var2, false);
   }

   /** @deprecated */
   public void setGeneralName(Object var1, int var2, boolean var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Name is null.");
      } else {
         this.type = var2;
         switch (var2) {
            case 1:
               if (!(var1 instanceof OtherName)) {
                  throw new NameException("GeneralName.setGeneralName: name argument should be an OtherName for the OTHER_NAME_TYPE type.");
               }

               this.otherValue = (OtherName)var1;
               break;
            case 2:
               if (!(var1 instanceof String)) {
                  throw new NameException("GeneralName.setGeneralName: name argument should be a String for type(" + var2 + ").");
               }

               if (var3) {
                  NameUtils.validateRFC822Name((String)var1);
               }

               this.stringValue = (String)var1;
               break;
            case 3:
               if (!(var1 instanceof String)) {
                  throw new NameException("GeneralName.setGeneralName: name argument should be a String for type(" + var2 + ").");
               }

               if (var3) {
                  NameUtils.validateDnsName((String)var1);
               }

               this.stringValue = (String)var1;
               break;
            case 4:
               if (!(var1 instanceof ORAddress)) {
                  throw new NameException("GeneralName.setGeneralName: name argument should be an ORAddress for the X400ADDRESS_NAME_TYPE type.");
               }

               this.orAddressValue = (ORAddress)var1;
               break;
            case 5:
               if (!(var1 instanceof X500Name)) {
                  throw new NameException("GeneralName.setGeneralName: name argument should be an X500Name for the DIRECTORY_NAME_TYPE type.");
               }

               this.x500Value = (X500Name)var1;
               break;
            case 6:
               if (!(var1 instanceof EDIPartyName)) {
                  throw new NameException("GeneralName.setGeneralName: name argument should be an EDIPartyName for the EDIPARTY_NAME_TYPE type.");
               }

               this.ediValue = (EDIPartyName)var1;
               break;
            case 7:
               if (var1 instanceof String) {
                  if (var3) {
                     NameUtils.validateURLName((String)var1);
                  }

                  this.stringValue = (String)var1;
               } else {
                  if (!(var1 instanceof URL)) {
                     throw new NameException("GeneralName.setGeneralName: name argument should be either a String or a URL for the URL_NAME_TYPE type.");
                  }

                  this.stringValue = var1.toString();
               }
               break;
            case 8:
               if (var1 instanceof byte[]) {
                  this.byteValue = (byte[])((byte[])var1);
               } else {
                  if (!(var1 instanceof InetAddress)) {
                     throw new NameException("GeneralName.setGeneralName: name argument should be either a byte array or an InetAddress for the IPADDRESS_NAME_TYPE type.");
                  }

                  this.byteValue = ((InetAddress)var1).getAddress();
               }
               break;
            case 9:
               if (!(var1 instanceof byte[])) {
                  throw new NameException("GeneralName.setGeneralName: name argument should be a byte array for the REGISTERID_NAME_TYPE type.");
               }

               this.byteValue = (byte[])((byte[])var1);
               break;
            default:
               throw new NameException("GeneralName.setGeneralName: unrecognized type value(" + var2 + ").");
         }

      }
   }

   /** @deprecated */
   public Object getGeneralName() {
      switch (this.type) {
         case 1:
            return this.otherValue;
         case 2:
         case 3:
         case 7:
            return this.stringValue;
         case 4:
            return this.orAddressValue;
         case 5:
            return this.x500Value;
         case 6:
            return this.ediValue;
         case 8:
         case 9:
            return this.byteValue;
         default:
            return null;
      }
   }

   /** @deprecated */
   public int getGeneralNameType() {
      return this.type;
   }

   /** @deprecated */
   public String toString() {
      switch (this.type) {
         case 1:
            return this.otherValue.toString();
         case 2:
         case 3:
         case 7:
            return this.stringValue;
         case 4:
            return this.orAddressValue.toString();
         case 5:
            return this.x500Value.toString();
         case 6:
            return this.ediValue.toString();
         case 8:
         case 9:
            StringBuffer var1 = new StringBuffer();
            var1.append("0x");

            for(int var2 = 0; var2 < this.byteValue.length; ++var2) {
               String var3 = Integer.toHexString(this.byteValue[var2]);
               int var4 = var3.length();
               if (var4 < 2) {
                  var1.append('0');
                  var1.append(var3.charAt(0));
               } else if (var4 > 2) {
                  var1.append(var3.charAt(var4 - 2));
                  var1.append(var3.charAt(var4 - 1));
               } else {
                  var1.append(var3);
               }
            }

            return var1.toString();
         default:
            return null;
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws NameException {
      if (var0 == null) {
         throw new NameException("Encoding is null.");
      } else if (var0[var1] == 0 && var0[var1 + 1] == 0) {
         return var1 + 2;
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new NameException("Unable to determine length of the BER");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws NameException {
      this.special = var1;
      return this.derEncodeInit();
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified array is null.");
      } else {
         try {
            int var4;
            if (this.asn1Template == null || var3 != this.special) {
               var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Unable to encode GeneralName.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode GeneralName.");
         }
      }
   }

   private int derEncodeInit() {
      try {
         ChoiceContainer var1 = new ChoiceContainer(this.special, 0);
         EndContainer var2 = new EndContainer();
         Object var3;
         int var4;
         byte[] var5;
         switch (this.type) {
            case 1:
               if (this.otherValue != null) {
                  var4 = this.otherValue.getDERLen(8388608);
                  var5 = new byte[var4];
                  var4 = this.otherValue.getDEREncoding(var5, 0, 8388608);
                  var3 = new EncodedContainer(8400896, true, 0, var5, 0, var4);
               } else {
                  var3 = new EncodedContainer(8400896, false, 0, (byte[])null, 0, 0);
               }
               break;
            case 2:
               var3 = new IA5StringContainer(8388609, true, 0, this.stringValue);
               break;
            case 3:
               var3 = new IA5StringContainer(8388610, true, 0, this.stringValue);
               break;
            case 4:
               if (this.orAddressValue != null) {
                  var4 = this.orAddressValue.getDERLen(8388611);
                  var5 = new byte[var4];
                  var4 = this.orAddressValue.getDEREncoding(var5, 0, 8388611);
                  var3 = new EncodedContainer(8400899, true, 0, var5, 0, var4);
               } else {
                  var3 = new EncodedContainer(8400899, false, 0, (byte[])null, 0, 0);
               }
               break;
            case 5:
               if (this.x500Value != null) {
                  var4 = this.x500Value.getDERLen(10485764);
                  var5 = new byte[var4];
                  int var6 = this.x500Value.getDEREncoding(var5, 0, 10485764);
                  var3 = new EncodedContainer(10498052, true, 0, var5, 0, var6);
               } else {
                  var3 = new EncodedContainer(10498052, false, 0, (byte[])null, 0, 0);
               }
               break;
            case 6:
               if (this.ediValue != null) {
                  var4 = this.ediValue.getDERLen(8388613);
                  var5 = new byte[var4];
                  var4 = this.ediValue.getDEREncoding(var5, 0, 8388613);
                  var3 = new EncodedContainer(8400901, true, 0, var5, 0, var4);
               } else {
                  var3 = new EncodedContainer(8400901, false, 0, (byte[])null, 0, 0);
               }
               break;
            case 7:
               var3 = new IA5StringContainer(8388614, true, 0, this.stringValue);
               break;
            case 8:
               if (this.byteValue != null) {
                  var3 = new OctetStringContainer(8388615, true, 0, this.byteValue, 0, this.byteValue.length);
               } else {
                  var3 = new OctetStringContainer(8388615, false, 0, this.byteValue, 0, 0);
               }
               break;
            case 9:
               if (this.byteValue != null) {
                  var3 = new OIDContainer(25165832, true, 0, this.byteValue, 0, this.byteValue.length);
               } else {
                  var3 = new OIDContainer(25165832, true, 0, this.byteValue, 0, 0);
               }
               break;
            default:
               return 0;
         }

         ASN1Container[] var9 = new ASN1Container[]{var1, (ASN1Container)var3, var2};
         this.asn1Template = new ASN1Template(var9);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var7) {
         return 0;
      } catch (NameException var8) {
         return 0;
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GeneralName) {
         GeneralName var2 = (GeneralName)var1;
         if (this.type != var2.type) {
            return false;
         } else {
            if (this.stringValue != null) {
               if (!this.stringValue.equals(var2.stringValue)) {
                  return false;
               }
            } else if (var2.stringValue != null) {
               return false;
            }

            if (this.byteValue != null) {
               if (!CertJUtils.byteArraysEqual(this.byteValue, var2.byteValue)) {
                  return false;
               }
            } else if (var2.byteValue != null) {
               return false;
            }

            if (this.orAddressValue != null) {
               if (!this.orAddressValue.equals(var2.orAddressValue)) {
                  return false;
               }
            } else if (var2.orAddressValue != null) {
               return false;
            }

            if (this.ediValue != null) {
               if (!this.ediValue.equals(var2.ediValue)) {
                  return false;
               }
            } else if (var2.ediValue != null) {
               return false;
            }

            if (this.otherValue != null) {
               if (!this.otherValue.equals(var2.otherValue)) {
                  return false;
               }
            } else if (var2.otherValue != null) {
               return false;
            }

            if (this.x500Value != null) {
               if (!this.x500Value.equals(var2.x500Value)) {
                  return false;
               }
            } else if (var2.x500Value != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      switch (this.type) {
         case 1:
            return this.otherValue.hashCode();
         case 2:
         case 3:
         case 7:
            return this.stringValue.hashCode();
         case 4:
            return this.orAddressValue.hashCode();
         case 5:
            return this.x500Value.hashCode();
         case 6:
            return this.ediValue.hashCode();
         case 8:
         case 9:
            return Arrays.hashCode(this.byteValue);
         default:
            return 0;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      GeneralName var1 = new GeneralName();
      var1.type = this.type;
      if (this.stringValue != null) {
         var1.stringValue = this.stringValue;
      }

      var1.byteValue = this.byteValue;
      var1.orAddressValue = this.orAddressValue;
      var1.ediValue = this.ediValue;
      var1.otherValue = this.otherValue;
      var1.x500Value = this.x500Value;
      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}
