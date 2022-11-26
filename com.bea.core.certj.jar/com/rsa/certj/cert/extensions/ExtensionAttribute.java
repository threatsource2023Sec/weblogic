package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.TeletexStringContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Vector;

/** @deprecated */
public class ExtensionAttribute implements Serializable, Cloneable {
   /** @deprecated */
   public static final int COMMON_NAME = 1;
   /** @deprecated */
   public static final int TELETEX_COMMON_NAME = 2;
   /** @deprecated */
   public static final int TELETEX_ORGANIZATION_NAME = 3;
   /** @deprecated */
   public static final int TELETEX_PERSONAL_NAME = 4;
   /** @deprecated */
   public static final int TELETEX_ORGANIZATIONAL_UNIT_NAMES = 5;
   /** @deprecated */
   public static final int TELETEX_DOMAIN_DEFINED_ATTRIBUTES = 6;
   /** @deprecated */
   public static final int PDS_NAME = 7;
   /** @deprecated */
   public static final int PHYSICAL_DELIVERY_COUNTRY_NAME = 8;
   /** @deprecated */
   public static final int POSTAL_CODE = 9;
   /** @deprecated */
   public static final int PHYSICAL_DELIVERY_OFFICE_NAME = 10;
   /** @deprecated */
   public static final int PHYSICAL_DELIVERY_OFFICE_NUMBER = 11;
   /** @deprecated */
   public static final int EXTENSION_OR_ADDRESS_COMPONENTS = 12;
   /** @deprecated */
   public static final int PHYSICAL_DELIVERY_PERSONAL_NAME = 13;
   /** @deprecated */
   public static final int PHYSICAL_DELIVERY_ORGANIZATION_NAME = 14;
   /** @deprecated */
   public static final int EXTENSION_PHYSICAL_DELIVERY_ADDRESS_COMPONENTS = 15;
   /** @deprecated */
   public static final int UNFORMATTED_POSTAL_ADDRESS = 16;
   /** @deprecated */
   public static final int STREET_ADDRESS = 17;
   /** @deprecated */
   public static final int POST_OFFICE_BOX_ADDRESS = 18;
   /** @deprecated */
   public static final int POSTE_RESTANTE_ADDRESS = 19;
   /** @deprecated */
   public static final int UNIQUE_POSTAL_NAME = 20;
   /** @deprecated */
   public static final int LOCAL_POSTAL_ATTRIBUTES = 21;
   /** @deprecated */
   public static final int EXTENDED_NETWORK_ADDRESS = 22;
   /** @deprecated */
   public static final int TERMINAL_TYPE = 23;
   private int flag;
   private String stringValue;
   private TeletexPersonalName personalName;
   private String[] unitNames = new String[4];
   private int unitIndex;
   private ORName orName;
   private PDSParameter pdsParam;
   private UnformattedPostalAddress postalAddress;
   private ExtendedNetworkAddress networkAddress;
   private int terminalType;
   private TeletexDomainDefinedAttributes teletexAttr;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public ExtensionAttribute(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            EndContainer var5 = new EndContainer();
            IntegerContainer var6 = new IntegerContainer(8388608);
            EncodedContainer var7 = new EncodedContainer(8453889);
            ASN1Container[] var8 = new ASN1Container[]{var4, var6, var7, var5};
            ASN1.berDecode(var1, var2, var8);
            this.flag = var6.getValueAsInt();
            switch (this.flag) {
               case 1:
                  PrintStringContainer var9 = new PrintStringContainer(8388609, 1, 64);
                  ASN1Container[] var10 = new ASN1Container[]{var9};
                  ASN1.berDecode(var7.data, var7.dataOffset, var10);
                  this.stringValue = var9.getValueAsString();
                  break;
               case 2:
               case 3:
                  TeletexStringContainer var13 = new TeletexStringContainer(8388609, 1, 64);
                  ASN1Container[] var14 = new ASN1Container[]{var13};
                  ASN1.berDecode(var7.data, var7.dataOffset, var14);
                  this.stringValue = var13.getValueAsString();
                  break;
               case 4:
                  this.personalName = new TeletexPersonalName(var7.data, var7.dataOffset, 8388609);
                  break;
               case 5:
                  OfContainer var15 = new OfContainer(8388609, 12288, new EncodedContainer(5120));
                  ASN1Container[] var16 = new ASN1Container[]{var15};
                  ASN1.berDecode(var7.data, var7.dataOffset, var16);
                  int var17 = var15.getContainerCount();
                  if (var17 > 4) {
                     throw new NameException("Too many entries in TeletexOrganizationalUnitNames: MAX number is 4.");
                  }

                  for(int var23 = 0; var23 < var17; ++var23) {
                     ASN1Container var24 = var15.containerAt(var23);
                     TeletexStringContainer var20 = new TeletexStringContainer(0, 1, 32);
                     ASN1Container[] var21 = new ASN1Container[]{var20};
                     ASN1.berDecode(var24.data, var24.dataOffset, var21);
                     this.unitNames[this.unitIndex] = var20.getValueAsString();
                     ++this.unitIndex;
                  }

                  return;
               case 6:
                  this.teletexAttr = new TeletexDomainDefinedAttributes(var7.data, var7.dataOffset, 8388609);
                  break;
               case 7:
                  PrintStringContainer var11 = new PrintStringContainer(8388609, 1, 16);
                  ASN1Container[] var12 = new ASN1Container[]{var11};
                  ASN1.berDecode(var7.data, var7.dataOffset, var12);
                  this.stringValue = var11.getValueAsString();
                  break;
               case 8:
                  this.orName = new ORName(4, var7.data, var7.dataOffset, 8388609);
                  break;
               case 9:
                  this.orName = new ORName(3, var7.data, var7.dataOffset, 8388609);
                  break;
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
                  this.pdsParam = new PDSParameter(var7.data, var7.dataOffset, 8388609);
                  break;
               case 16:
                  this.postalAddress = new UnformattedPostalAddress(var7.data, var7.dataOffset, 8388609);
                  break;
               case 22:
                  this.networkAddress = new ExtendedNetworkAddress(var7.data, var7.dataOffset, 8388609);
                  break;
               case 23:
                  IntegerContainer var18 = new IntegerContainer(8388609);
                  ASN1Container[] var19 = new ASN1Container[]{var18};
                  ASN1.berDecode(var7.data, var7.dataOffset, var19);
                  this.terminalType = var18.getValueAsInt();
                  if (this.terminalType < 3 || this.terminalType > 8) {
                     throw new NameException("Wrong TerminalType value");
                  }
            }

         } catch (ASN_Exception var22) {
            throw new NameException("Cannot decode the BER of the ExtensionAttribute.");
         }
      }
   }

   /** @deprecated */
   public ExtensionAttribute() {
   }

   /** @deprecated */
   public void setAttribute(int var1, Object var2) throws NameException {
      if (var2 == null) {
         throw new NameException("Value is null.");
      } else if (var1 <= 23 && var1 >= 1) {
         this.flag = var1;
         switch (this.flag) {
            case 1:
               if (!(var2 instanceof String) || ((String)var2).length() >= 65) {
                  throw new NameException("Wrong value type");
               }

               this.stringValue = (String)var2;
               break;
            case 2:
            case 3:
               if (!(var2 instanceof String) || ((String)var2).length() >= 65) {
                  throw new NameException("Wrong value type");
               }

               this.stringValue = (String)var2;
               break;
            case 4:
               if (!(var2 instanceof TeletexPersonalName)) {
                  throw new NameException("Wrong value type");
               }

               this.personalName = (TeletexPersonalName)var2;
               break;
            case 5:
               if (var2 instanceof Vector && ((Vector)var2).size() < 5) {
                  for(int var3 = 0; var3 < ((Vector)var2).size(); ++var3) {
                     if (!(((Vector)var2).elementAt(var3) instanceof String)) {
                        throw new NameException("Wrong value type");
                     }

                     this.unitNames[this.unitIndex] = (String)((Vector)var2).elementAt(var3);
                     ++this.unitIndex;
                  }

                  return;
               }

               throw new NameException("Too many entries in TeletexOrganizationalUnitNames: MAX number is 4.");
            case 6:
               if (!(var2 instanceof TeletexDomainDefinedAttributes)) {
                  throw new NameException("Wrong value type");
               }

               this.teletexAttr = (TeletexDomainDefinedAttributes)var2;
               break;
            case 7:
               if (var2 instanceof String && ((String)var2).length() < 17) {
                  this.stringValue = (String)var2;
                  break;
               }

               throw new NameException("Wrong value type");
            case 8:
               if (!(var2 instanceof ORName) || ((ORName)var2).getNameType() != 4) {
                  throw new NameException("Wrong value type");
               }

               this.orName = (ORName)var2;
               break;
            case 9:
               if (!(var2 instanceof ORName) || ((ORName)var2).getNameType() != 3) {
                  throw new NameException("Wrong value type");
               }

               this.orName = (ORName)var2;
               break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
               if (!(var2 instanceof PDSParameter)) {
                  throw new NameException("Wrong value type");
               }

               this.pdsParam = (PDSParameter)var2;
               break;
            case 16:
               if (!(var2 instanceof UnformattedPostalAddress)) {
                  throw new NameException("Wrong value type");
               }

               this.postalAddress = (UnformattedPostalAddress)var2;
               break;
            case 22:
               if (!(var2 instanceof ExtendedNetworkAddress)) {
                  throw new NameException("Wrong value type");
               }

               this.networkAddress = (ExtendedNetworkAddress)var2;
               break;
            case 23:
               if (!(var2 instanceof Integer)) {
                  throw new NameException("Wrong value type");
               }

               this.terminalType = (Integer)var2;
               if (this.terminalType < 3 || this.terminalType > 8) {
                  throw new NameException("Wrong TerminalType value");
               }
         }

      } else {
         throw new NameException("Wrong type.");
      }
   }

   /** @deprecated */
   public int getAttributeType() {
      return this.flag;
   }

   /** @deprecated */
   public Object getAttribute() {
      switch (this.flag) {
         case 1:
         case 2:
         case 3:
         case 7:
            return this.stringValue;
         case 4:
            return this.personalName;
         case 5:
            Vector var1 = new Vector();

            for(int var2 = 0; var2 < this.unitIndex; ++var2) {
               var1.addElement(this.unitNames[var2]);
            }

            return var1;
         case 6:
            return this.teletexAttr;
         case 8:
         case 9:
            return this.orName;
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
            return this.pdsParam;
         case 16:
            return this.postalAddress;
         case 22:
            return this.networkAddress;
         case 23:
            return new Integer(this.terminalType);
         default:
            return null;
      }
   }

   /** @deprecated */
   public String toString() {
      switch (this.flag) {
         case 1:
         case 2:
         case 3:
         case 7:
            return this.stringValue;
         case 4:
            return this.personalName.toString();
         case 5:
            StringBuffer var1 = new StringBuffer();

            for(int var2 = 0; var2 < this.unitIndex - 1; ++var2) {
               var1.append(this.unitNames[var2]);
               var1.append(",");
            }

            var1.append(this.unitNames[this.unitIndex - 1]);
            return var1.toString();
         case 6:
            return this.teletexAttr.toString();
         case 8:
         case 9:
            return this.orName.toString();
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
            return this.pdsParam.toString();
         case 16:
            return this.postalAddress.toString();
         case 22:
            return this.networkAddress.toString();
         case 23:
            return Integer.toString(this.terminalType);
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
            if (this.asn1Template == null || var3 != this.special) {
               int var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Unable to encode ExtensionAttribute");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode ExtensionAttribute");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            SequenceContainer var1 = new SequenceContainer(this.special, true, 0);
            EndContainer var2 = new EndContainer();
            IntegerContainer var3 = new IntegerContainer(8388608, true, 0, this.flag);
            int var10;
            byte[] var24;
            switch (this.flag) {
               case 1:
                  PrintStringContainer var4 = new PrintStringContainer(8388609, true, 0, this.stringValue, 1, 64);
                  ASN1Container[] var5 = new ASN1Container[]{var1, var3, var4, var2};
                  this.asn1Template = new ASN1Template(var5);
                  break;
               case 2:
               case 3:
                  TeletexStringContainer var8 = new TeletexStringContainer(8388609, true, 0, this.stringValue, 1, 64);
                  ASN1Container[] var9 = new ASN1Container[]{var1, var3, var8, var2};
                  this.asn1Template = new ASN1Template(var9);
                  break;
               case 4:
                  var10 = this.personalName.getDERLen(8388609);
                  byte[] var11 = new byte[var10];
                  int var12 = this.personalName.getDEREncoding(var11, 0, 8388609);
                  EncodedContainer var13 = new EncodedContainer(8453889, true, 0, var11, 0, var12);
                  ASN1Container[] var14 = new ASN1Container[]{var1, var3, var13, var2};
                  this.asn1Template = new ASN1Template(var14);
                  break;
               case 5:
                  OfContainer var15 = new OfContainer(8388609, true, 0, 12288, new EncodedContainer(5120));
                  Vector var16 = new Vector();
                  var16.addElement(var15);

                  for(int var19 = 0; var19 < this.unitIndex; ++var19) {
                     TeletexStringContainer var20 = new TeletexStringContainer(0, true, 0, this.unitNames[var19], 1, 32);
                     ASN1Container[] var21 = new ASN1Container[]{var20};
                     ASN1Template var22 = new ASN1Template(var21);
                     int var23 = var22.derEncodeInit();
                     var24 = new byte[var23];
                     var23 = var22.derEncode(var24, 0);
                     EncodedContainer var17 = new EncodedContainer(5120, true, 0, var24, 0, var23);
                     var15.addContainer(var17);
                  }

                  ASN1Container[] var48 = new ASN1Container[var16.size()];
                  var16.copyInto(var48);
                  ASN1Template var49 = new ASN1Template(var48);
                  int var50 = var49.derEncodeInit();
                  byte[] var51 = new byte[var50];
                  var50 = var49.derEncode(var51, 0);
                  EncodedContainer var18 = new EncodedContainer(12288, true, 0, var51, 0, var50);
                  ASN1Container[] var52 = new ASN1Container[]{var1, var3, var18, var2};
                  this.asn1Template = new ASN1Template(var52);
                  break;
               case 6:
                  var10 = this.teletexAttr.getDERLen(8388609);
                  var24 = new byte[var10];
                  int var25 = this.teletexAttr.getDEREncoding(var24, 0, 8388609);
                  EncodedContainer var26 = new EncodedContainer(8453889, true, 0, var24, 0, var25);
                  ASN1Container[] var27 = new ASN1Container[]{var1, var3, var26, var2};
                  this.asn1Template = new ASN1Template(var27);
                  break;
               case 7:
                  PrintStringContainer var6 = new PrintStringContainer(8388609, true, 0, this.stringValue, 1, 16);
                  ASN1Container[] var7 = new ASN1Container[]{var1, var3, var6, var2};
                  this.asn1Template = new ASN1Template(var7);
                  break;
               case 8:
               case 9:
                  var10 = this.orName.getDERLen(8388609);
                  byte[] var28 = new byte[var10];
                  int var29 = this.orName.getDEREncoding(var28, 0, 8388609);
                  EncodedContainer var30 = new EncodedContainer(8453889, true, 0, var28, 0, var29);
                  ASN1Container[] var31 = new ASN1Container[]{var1, var3, var30, var2};
                  this.asn1Template = new ASN1Template(var31);
                  break;
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
                  var10 = this.pdsParam.getDERLen(8388609);
                  byte[] var32 = new byte[var10];
                  int var33 = this.pdsParam.getDEREncoding(var32, 0, 8388609);
                  EncodedContainer var34 = new EncodedContainer(8453889, true, 0, var32, 0, var33);
                  ASN1Container[] var35 = new ASN1Container[]{var1, var3, var34, var2};
                  this.asn1Template = new ASN1Template(var35);
                  break;
               case 16:
                  var10 = this.postalAddress.getDERLen(8388609);
                  byte[] var36 = new byte[var10];
                  int var37 = this.postalAddress.getDEREncoding(var36, 0, 8388609);
                  EncodedContainer var38 = new EncodedContainer(8453889, true, 0, var36, 0, var37);
                  ASN1Container[] var39 = new ASN1Container[]{var1, var3, var38, var2};
                  this.asn1Template = new ASN1Template(var39);
                  break;
               case 22:
                  var10 = this.networkAddress.getDERLen(8388609);
                  byte[] var40 = new byte[var10];
                  int var41 = this.networkAddress.getDEREncoding(var40, 0, 8388609);
                  EncodedContainer var42 = new EncodedContainer(8453889, true, 0, var40, 0, var41);
                  ASN1Container[] var43 = new ASN1Container[]{var1, var3, var42, var2};
                  this.asn1Template = new ASN1Template(var43);
                  break;
               case 23:
                  IntegerContainer var44 = new IntegerContainer(8388609, true, 0, this.terminalType);
                  ASN1Container[] var45 = new ASN1Container[]{var1, var3, var44, var2};
                  this.asn1Template = new ASN1Template(var45);
            }

            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var46) {
            return 0;
         } catch (NameException var47) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof ExtensionAttribute) {
         ExtensionAttribute var2 = (ExtensionAttribute)var1;
         if (this.flag != var2.flag) {
            return false;
         } else {
            switch (this.flag) {
               case 1:
               case 2:
               case 3:
               case 7:
                  if (!this.stringValue.equals(var2.stringValue)) {
                     return false;
                  }
                  break;
               case 4:
                  if (!this.personalName.equals(var2.personalName)) {
                     return false;
                  }
                  break;
               case 5:
                  if (this.unitIndex != var2.unitIndex) {
                     return false;
                  }

                  for(int var3 = 0; var3 < this.unitIndex; ++var3) {
                     if (!this.unitNames[var3].equals(var2.unitNames[var3])) {
                        return false;
                     }
                  }

                  return true;
               case 6:
                  if (!this.teletexAttr.equals(var2.teletexAttr)) {
                     return false;
                  }
                  break;
               case 8:
               case 9:
                  if (!this.orName.equals(var2.orName)) {
                     return false;
                  }
                  break;
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
                  if (!this.pdsParam.equals(var2.pdsParam)) {
                     return false;
                  }
                  break;
               case 16:
                  if (!this.postalAddress.equals(var2.postalAddress)) {
                     return false;
                  }
                  break;
               case 22:
                  if (!this.networkAddress.equals(var2.networkAddress)) {
                     return false;
                  }
                  break;
               case 23:
                  if (this.terminalType != var2.terminalType) {
                     return false;
                  }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.flag;
      switch (this.flag) {
         case 1:
         case 2:
         case 3:
         case 7:
            var1 ^= this.stringValue.hashCode();
            break;
         case 4:
            var1 ^= this.personalName.hashCode();
            break;
         case 5:
            for(int var2 = 0; var2 < this.unitIndex; ++var2) {
               var1 ^= this.unitNames[var2].hashCode();
            }

            return var1;
         case 6:
            var1 ^= this.teletexAttr.hashCode();
            break;
         case 8:
         case 9:
            var1 ^= this.orName.hashCode();
            break;
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
            var1 ^= this.pdsParam.hashCode();
            break;
         case 16:
            var1 ^= this.postalAddress.hashCode();
            break;
         case 22:
            var1 ^= this.networkAddress.hashCode();
            break;
         case 23:
            var1 ^= this.terminalType;
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ExtensionAttribute var1 = new ExtensionAttribute();
      var1.flag = this.flag;
      switch (this.flag) {
         case 1:
         case 2:
         case 3:
         case 7:
            var1.stringValue = this.stringValue;
            break;
         case 4:
            var1.personalName = (TeletexPersonalName)this.personalName.clone();
            break;
         case 5:
            var1.unitIndex = this.unitIndex;
            System.arraycopy(this.unitNames, 0, var1.unitNames, 0, this.unitIndex);
            break;
         case 6:
            var1.teletexAttr = (TeletexDomainDefinedAttributes)this.teletexAttr.clone();
            break;
         case 8:
         case 9:
            var1.orName = (ORName)this.orName.clone();
            break;
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
            var1.pdsParam = (PDSParameter)this.pdsParam.clone();
            break;
         case 16:
            var1.postalAddress = (UnformattedPostalAddress)this.postalAddress.clone();
            break;
         case 22:
            var1.networkAddress = (ExtendedNetworkAddress)this.networkAddress.clone();
            break;
         case 23:
            var1.terminalType = this.terminalType;
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}
