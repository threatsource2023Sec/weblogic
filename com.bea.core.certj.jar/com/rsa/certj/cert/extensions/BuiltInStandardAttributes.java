package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.NumericStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class BuiltInStandardAttributes implements Serializable, Cloneable {
   private ORName countryName;
   private ORName adminName;
   private String networkAddress;
   private String terminalId;
   private ORName privateDomainName;
   private String organizationName;
   private String numericUserId;
   private PersonalName personalName;
   private String[] organizationUnitNames = new String[4];
   private int orgUnitIndex;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public BuiltInStandardAttributes(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            EndContainer var5 = new EndContainer();
            EncodedContainer var6 = new EncodedContainer(4259841);
            EncodedContainer var7 = new EncodedContainer(4259842);
            NumericStringContainer var8 = new NumericStringContainer(8454144, 1, 16);
            PrintStringContainer var9 = new PrintStringContainer(8454145, 1, 24);
            EncodedContainer var10 = new EncodedContainer(8454146);
            PrintStringContainer var11 = new PrintStringContainer(8454147, 1, 64);
            NumericStringContainer var12 = new NumericStringContainer(8454148, 1, 32);
            EncodedContainer var13 = new EncodedContainer(8466693);
            EncodedContainer var14 = new EncodedContainer(8466438);
            ASN1Container[] var15 = new ASN1Container[]{var4, var6, var7, var8, var9, var10, var11, var12, var13, var14, var5};
            ASN1.berDecode(var1, var2, var15);
            if (var6.dataPresent) {
               this.countryName = new ORName(0, var6.data, var6.dataOffset, 0);
            }

            if (var7.dataPresent) {
               this.adminName = new ORName(1, var7.data, var7.dataOffset, 0);
            }

            if (var8.dataPresent) {
               this.networkAddress = var8.getValueAsString();
            }

            if (var9.dataPresent) {
               this.terminalId = var9.getValueAsString();
            }

            if (var10.dataPresent) {
               this.privateDomainName = new ORName(2, var10.data, var10.dataOffset, 8454146);
            }

            if (var11.dataPresent) {
               this.organizationName = var11.getValueAsString();
            }

            if (var12.dataPresent) {
               this.numericUserId = var12.getValueAsString();
            }

            if (var13.dataPresent) {
               this.personalName = new PersonalName(var13.data, var13.dataOffset, 8454149);
            }

            if (var14.dataPresent) {
               OfContainer var16 = new OfContainer(8454150, 12288, new EncodedContainer(4864));
               ASN1Container[] var17 = new ASN1Container[]{var16};
               ASN1.berDecode(var14.data, var14.dataOffset, var17);
               int var18 = var16.getContainerCount();
               if (var18 > 4) {
                  throw new NameException("Too many entries in OrganizationalUnitNames: MAX number is 4.");
               }

               for(int var19 = 0; var19 < var18; ++var19) {
                  ASN1Container var20 = var16.containerAt(var19);
                  PrintStringContainer var21 = new PrintStringContainer(0, 1, 32);
                  ASN1Container[] var22 = new ASN1Container[]{var21};
                  ASN1.berDecode(var20.data, var20.dataOffset, var22);
                  this.organizationUnitNames[this.orgUnitIndex] = var21.getValueAsString();
                  ++this.orgUnitIndex;
               }
            }

         } catch (ASN_Exception var23) {
            throw new NameException("Cannot decode the BER of the BuiltInStandardAttributes.");
         }
      }
   }

   /** @deprecated */
   public BuiltInStandardAttributes() {
   }

   /** @deprecated */
   public void setCountryName(ORName var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.getNameType() == 0) {
         this.countryName = var1;
      } else {
         throw new NameException("Specified value is of wrong type.");
      }
   }

   /** @deprecated */
   public void setAdminDomainName(ORName var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.getNameType() == 1) {
         this.adminName = var1;
      } else {
         throw new NameException("Specified value is of wrong type.");
      }
   }

   /** @deprecated */
   public void setNetworkAddress(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 16) {
         throw new NameException("Specified value is too long.");
      } else {
         this.networkAddress = var1;
      }
   }

   /** @deprecated */
   public void setTerminalIdentifier(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 24) {
         throw new NameException("Specified value is too long.");
      } else {
         this.terminalId = var1;
      }
   }

   /** @deprecated */
   public void setPrivateDomainName(ORName var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.getNameType() == 2) {
         this.privateDomainName = var1;
      } else {
         throw new NameException("Specified value is of wrong type.");
      }
   }

   /** @deprecated */
   public void setOrganizationName(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 64) {
         throw new NameException("Specified value is too long.");
      } else {
         this.organizationName = var1;
      }
   }

   /** @deprecated */
   public void setNumericUserIdentifier(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 32) {
         throw new NameException("Specified value is too long.");
      } else {
         this.numericUserId = var1;
      }
   }

   /** @deprecated */
   public void setPersonalName(PersonalName var1) {
      if (var1 != null) {
         this.personalName = var1;
      }

   }

   /** @deprecated */
   public void setOrganizationalUnitNames(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 32) {
         throw new NameException("Specified value is too long.");
      } else if (this.orgUnitIndex == 4) {
         throw new NameException("Cannot add Organizational Unit Names attribute: MAX number is 4");
      } else {
         this.organizationUnitNames[this.orgUnitIndex] = var1;
         ++this.orgUnitIndex;
      }
   }

   /** @deprecated */
   public ORName getCountryName() {
      return this.countryName;
   }

   /** @deprecated */
   public ORName getAdminDomainName() {
      return this.adminName;
   }

   /** @deprecated */
   public String getNetworkAddress() {
      return this.networkAddress;
   }

   /** @deprecated */
   public String getTerminalIdentifier() {
      return this.terminalId;
   }

   /** @deprecated */
   public ORName getPrivateDomainName() {
      return this.privateDomainName;
   }

   /** @deprecated */
   public String getOrganizationName() {
      return this.organizationName;
   }

   /** @deprecated */
   public String getNumericUserIdentifier() {
      return this.numericUserId;
   }

   /** @deprecated */
   public PersonalName getPersonalName() {
      return this.personalName;
   }

   /** @deprecated */
   public String[] getOrganizationalUnitNames() {
      return this.organizationUnitNames;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.countryName != null) {
         var1.append(this.countryName.toString());
      }

      if (this.adminName != null) {
         var1.append(",");
         var1.append(this.adminName.toString());
      }

      if (this.networkAddress != null) {
         var1.append(",");
         var1.append(this.networkAddress);
      }

      if (this.terminalId != null) {
         var1.append(",");
         var1.append(this.terminalId);
      }

      if (this.privateDomainName != null) {
         var1.append(",");
         var1.append(this.privateDomainName.toString());
      }

      if (this.organizationName != null) {
         var1.append(",");
         var1.append(this.organizationName);
      }

      if (this.numericUserId != null) {
         var1.append(",");
         var1.append(this.numericUserId);
      }

      if (this.personalName != null) {
         var1.append(",");
         var1.append(this.personalName.toString());
      }

      for(int var2 = 0; var2 < this.orgUnitIndex; ++var2) {
         var1.append(",");
         var1.append(this.organizationUnitNames[var2]);
      }

      return var1.toString();
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
                  throw new NameException("Unable to encode BuiltInStandardAttributes");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode BuiltInStandardAttributes");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            SequenceContainer var1 = new SequenceContainer(0, true, 0);
            EndContainer var2 = new EndContainer();
            Vector var3 = new Vector();
            var3.addElement(var1);
            int var4;
            byte[] var5;
            EncodedContainer var6;
            if (this.countryName != null) {
               var4 = this.countryName.getDERLen(65536);
               var5 = new byte[var4];
               var4 = this.countryName.getDEREncoding(var5, 0, 65536);
               var6 = new EncodedContainer(65536, true, 0, var5, 0, var4);
               var3.addElement(var6);
            }

            if (this.adminName != null) {
               var4 = this.adminName.getDERLen(65536);
               var5 = new byte[var4];
               var4 = this.adminName.getDEREncoding(var5, 0, 65536);
               var6 = new EncodedContainer(65536, true, 0, var5, 0, var4);
               var3.addElement(var6);
            }

            NumericStringContainer var9;
            if (this.networkAddress != null) {
               var9 = new NumericStringContainer(8454144, true, 0, this.networkAddress, 1, 16);
               var3.addElement(var9);
            }

            PrintStringContainer var10;
            if (this.terminalId != null) {
               var10 = new PrintStringContainer(8454145, true, 0, this.terminalId, 1, 24);
               var3.addElement(var10);
            }

            if (this.privateDomainName != null) {
               var4 = this.privateDomainName.getDERLen(8454146);
               var5 = new byte[var4];
               var4 = this.privateDomainName.getDEREncoding(var5, 0, 8454146);
               var6 = new EncodedContainer(8454146, true, 0, var5, 0, var4);
               var3.addElement(var6);
            }

            if (this.organizationName != null) {
               var10 = new PrintStringContainer(8454147, true, 0, this.organizationName, 1, 64);
               var3.addElement(var10);
            }

            if (this.numericUserId != null) {
               var9 = new NumericStringContainer(8454148, true, 0, this.numericUserId, 1, 32);
               var3.addElement(var9);
            }

            if (this.personalName != null) {
               var4 = this.personalName.getDERLen(8454149);
               var5 = new byte[var4];
               var4 = this.personalName.getDEREncoding(var5, 0, 8454149);
               var6 = new EncodedContainer(8454149, true, 0, var5, 0, var4);
               var3.addElement(var6);
            }

            if (this.orgUnitIndex != 0) {
               var3.addElement(this.encodeOrgUnitNames());
            }

            var3.addElement(var2);
            ASN1Container[] var11 = new ASN1Container[var3.size()];
            var3.copyInto(var11);
            this.asn1Template = new ASN1Template(var11);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var7) {
            return 0;
         } catch (NameException var8) {
            return 0;
         }
      }
   }

   private EncodedContainer encodeOrgUnitNames() throws NameException {
      Vector var3 = new Vector();

      try {
         OfContainer var4 = new OfContainer(8454150, true, 0, 12288, new EncodedContainer(4864));
         var3.addElement(var4);

         for(int var5 = 0; var5 < this.orgUnitIndex; ++var5) {
            PrintStringContainer var6 = new PrintStringContainer(0, true, 0, this.organizationUnitNames[var5], 1, 32);
            ASN1Container[] var7 = new ASN1Container[]{var6};
            ASN1Template var8 = new ASN1Template(var7);
            int var9 = var8.derEncodeInit();
            byte[] var10 = new byte[var9];
            var9 = var8.derEncode(var10, 0);
            EncodedContainer var2 = new EncodedContainer(4864, true, 0, var10, 0, var9);
            var4.addContainer(var2);
         }

         ASN1Container[] var12 = new ASN1Container[var3.size()];
         var3.copyInto(var12);
         ASN1Template var13 = new ASN1Template(var12);
         int var14 = var13.derEncodeInit();
         byte[] var15 = new byte[var14];
         var14 = var13.derEncode(var15, 0);
         EncodedContainer var1 = new EncodedContainer(12288, true, 0, var15, 0, var14);
         return var1;
      } catch (ASN_Exception var11) {
         throw new NameException(" Can't encode Organizational Unit Names.");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BuiltInStandardAttributes) {
         BuiltInStandardAttributes var2 = (BuiltInStandardAttributes)var1;
         if (this.orgUnitIndex != var2.orgUnitIndex) {
            return false;
         } else {
            if (this.countryName != null) {
               if (!this.countryName.equals(var2.countryName)) {
                  return false;
               }
            } else if (var2.countryName != null) {
               return false;
            }

            if (this.adminName != null) {
               if (!this.adminName.equals(var2.adminName)) {
                  return false;
               }
            } else if (var2.adminName != null) {
               return false;
            }

            if (this.networkAddress != null) {
               if (!this.networkAddress.equals(var2.networkAddress)) {
                  return false;
               }
            } else if (var2.networkAddress != null) {
               return false;
            }

            if (this.terminalId != null) {
               if (!this.terminalId.equals(var2.terminalId)) {
                  return false;
               }
            } else if (var2.terminalId != null) {
               return false;
            }

            if (this.privateDomainName != null) {
               if (!this.privateDomainName.equals(var2.privateDomainName)) {
                  return false;
               }
            } else if (var2.privateDomainName != null) {
               return false;
            }

            if (this.organizationName != null) {
               if (!this.organizationName.equals(var2.organizationName)) {
                  return false;
               }
            } else if (var2.organizationName != null) {
               return false;
            }

            if (this.numericUserId != null) {
               if (!this.numericUserId.equals(var2.numericUserId)) {
                  return false;
               }
            } else if (var2.numericUserId != null) {
               return false;
            }

            if (this.personalName != null) {
               if (!this.personalName.equals(var2.personalName)) {
                  return false;
               }
            } else if (var2.personalName != null) {
               return false;
            }

            for(int var3 = 0; var3 < this.orgUnitIndex; ++var3) {
               if (!this.organizationUnitNames[var3].equals(var2.organizationUnitNames[var3])) {
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
      int var1;
      try {
         var1 = this.getDERLen(0);
      } catch (NameException var5) {
         return 0;
      }

      if (var1 == 0) {
         return 0;
      } else {
         byte[] var2 = new byte[var1];

         try {
            this.getDEREncoding(var2, 0, 0);
         } catch (NameException var4) {
            return 0;
         }

         return Arrays.hashCode(var2);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      BuiltInStandardAttributes var1 = new BuiltInStandardAttributes();
      var1.orgUnitIndex = this.orgUnitIndex;
      if (this.countryName != null) {
         var1.countryName = (ORName)this.countryName.clone();
      }

      if (this.adminName != null) {
         var1.adminName = (ORName)this.adminName.clone();
      }

      if (this.networkAddress != null) {
         var1.networkAddress = this.networkAddress;
      }

      if (this.terminalId != null) {
         var1.terminalId = this.terminalId;
      }

      if (this.privateDomainName != null) {
         var1.privateDomainName = (ORName)this.privateDomainName.clone();
      }

      if (this.organizationName != null) {
         var1.organizationName = this.organizationName;
      }

      if (this.numericUserId != null) {
         var1.numericUserId = this.numericUserId;
      }

      if (this.personalName != null) {
         var1.personalName = (PersonalName)this.personalName.clone();
      }

      System.arraycopy(this.organizationUnitNames, 0, var1.organizationUnitNames, 0, this.orgUnitIndex);
      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}
