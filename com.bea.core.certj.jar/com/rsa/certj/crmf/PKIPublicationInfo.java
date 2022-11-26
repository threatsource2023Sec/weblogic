package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.extensions.GeneralName;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class PKIPublicationInfo extends Control {
   /** @deprecated */
   public static final int DONT_PUBLISH = 0;
   /** @deprecated */
   public static final int PLEASE_PUBLISH = 1;
   /** @deprecated */
   public static final int DONT_CARE = 0;
   /** @deprecated */
   public static final int X500 = 1;
   /** @deprecated */
   public static final int WEB = 2;
   /** @deprecated */
   public static final int LDAP = 3;
   private int action;
   private Vector[] pubInfos = this.createVectorArray(2);
   ASN1Template asn1TemplateValue;
   private int special = 0;

   /** @deprecated */
   public PKIPublicationInfo() {
      this.controlTypeFlag = 2;
      this.theOID = new byte[OID_LIST[2].length];
      System.arraycopy(OID_LIST[2], 0, this.theOID, 0, this.theOID.length);
      this.controlTypeString = "PKIPublicationInfo";
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("PKIPublicationInfo Encoding is null.");
      } else {
         try {
            SequenceContainer var3 = new SequenceContainer(this.special);
            EndContainer var4 = new EndContainer();
            EncodedContainer var5 = new EncodedContainer(77824);
            IntegerContainer var6 = new IntegerContainer(0);
            ASN1Container[] var7 = new ASN1Container[]{var3, var6, var5, var4};
            ASN1.berDecode(var1, var2, var7);
            this.action = var6.getValueAsInt();
            if (this.action != 0 && this.action != 1) {
               throw new CRMFException("Invalid action option.");
            } else {
               if (var5.dataPresent) {
                  OfContainer var8 = new OfContainer(65536, 12288, new EncodedContainer(12288));
                  ASN1Container[] var9 = new ASN1Container[]{var8};
                  ASN1.berDecode(var5.data, var5.dataOffset, var9);
                  int var10 = var8.getContainerCount();

                  for(int var11 = 0; var11 < var10; ++var11) {
                     ASN1Container var12 = var8.containerAt(var11);
                     EncodedContainer var13 = new EncodedContainer(130816);
                     IntegerContainer var14 = new IntegerContainer(0);
                     ASN1Container[] var15 = new ASN1Container[]{var3, var14, var13, var4};
                     ASN1.berDecode(var12.data, var12.dataOffset, var15);
                     if (var13.dataPresent) {
                        GeneralName var16 = new GeneralName(var13.data, var13.dataOffset, 65536);
                        this.pubInfos[1].addElement(var16);
                     } else {
                        this.pubInfos[1].addElement((Object)null);
                     }

                     int var18 = var14.getValueAsInt();
                     if (var18 != 0 && var18 != 1 && var18 != 2 && var18 != 3) {
                        throw new CRMFException("Invalid pubMethod option");
                     }

                     this.pubInfos[0].addElement(new Integer(var18));
                  }
               }

            }
         } catch (Exception var17) {
            throw new CRMFException("Cannot decode PKIPublicationInfo control.", var17);
         }
      }
   }

   private Vector[] createVectorArray(int var1) {
      Vector[] var2 = new Vector[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = new Vector();
      }

      return var2;
   }

   /** @deprecated */
   public void setAction(int var1) throws CRMFException {
      if (var1 != 0 && var1 != 1) {
         throw new CRMFException("Invalid action option.");
      } else {
         this.action = var1;
      }
   }

   /** @deprecated */
   public int getAction() {
      return this.action;
   }

   /** @deprecated */
   public void addSinglePubInfo(int var1, GeneralName var2) throws CRMFException {
      if (var1 != 0 && var1 != 1 && var1 != 2 && var1 != 3) {
         throw new CRMFException("Invalid pubMethod option");
      } else {
         this.pubInfos[0].addElement(new Integer(var1));
         if (var2 != null) {
            try {
               this.pubInfos[1].addElement(var2.clone());
            } catch (CloneNotSupportedException var4) {
               throw new CRMFException("Invalid value.", var4);
            }
         } else {
            this.pubInfos[1].addElement((Object)null);
         }

      }
   }

   /** @deprecated */
   public int getSinglePubInfoCount() {
      return this.pubInfos[0].size();
   }

   /** @deprecated */
   public int getPubMethod(int var1) throws CRMFException {
      if (var1 < this.pubInfos[0].size()) {
         return (Integer)this.pubInfos[0].elementAt(var1);
      } else {
         throw new CRMFException("Invalid index.");
      }
   }

   /** @deprecated */
   public GeneralName getPubLocation(int var1) throws CRMFException {
      if (var1 < this.pubInfos[1].size()) {
         return (GeneralName)this.pubInfos[1].elementAt(var1);
      } else {
         throw new CRMFException("Invalid index.");
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in array is null in PKIPublicationInfo control.");
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         throw new CRMFException("Cannot encode PKIPublicationInfo control.");
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            throw new CRMFException("Cannot encode PKIPublicationInfo control.", var5);
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValueInit() throws CRMFException {
      this.asn1TemplateValue = null;
      Vector var1 = new Vector();

      try {
         boolean var2 = false;
         int var3 = 0;
         byte[] var4 = null;
         EndContainer var5 = new EndContainer();
         int var6 = this.pubInfos[0].size();
         if (var6 > 0) {
            var2 = true;
            OfContainer var7 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(12288));
            var1.addElement(var7);

            for(int var8 = 0; var8 < var6; ++var8) {
               boolean var9 = false;
               if (this.pubInfos[1].elementAt(var8) != null) {
                  GeneralName var10 = (GeneralName)this.pubInfos[1].elementAt(var8);
                  var3 = var10.getDERLen(0);
                  var4 = new byte[var3];
                  var3 = var10.getDEREncoding(var4, 0, 0);
                  var9 = true;
               }

               SequenceContainer var25 = new SequenceContainer(0, true, 0);
               EncodedContainer var11 = new EncodedContainer(130816, var9, 0, var4, 0, var3);
               IntegerContainer var12 = new IntegerContainer(0, true, 0, (Integer)this.pubInfos[0].elementAt(var8));
               ASN1Container[] var13 = new ASN1Container[]{var25, var12, var11, var5};
               ASN1Template var14 = new ASN1Template(var13);
               int var15 = var14.derEncodeInit();
               byte[] var16 = new byte[var15];
               var15 = var14.derEncode(var16, 0);
               EncodedContainer var17 = new EncodedContainer(12288, true, 0, var16, 0, var15);
               var7.addContainer(var17);
            }

            ASN1Container[] var21 = new ASN1Container[var1.size()];
            var1.copyInto(var21);
            ASN1Template var23 = new ASN1Template(var21);
            var3 = var23.derEncodeInit();
            var4 = new byte[var3];
            var3 = var23.derEncode(var4, 0);
         }

         EncodedContainer var20 = new EncodedContainer(77824, var2, 0, var4, 0, var3);
         IntegerContainer var22 = new IntegerContainer(0, true, 0, this.action);
         SequenceContainer var24 = new SequenceContainer(this.special, true, 0);
         ASN1Container[] var26 = new ASN1Container[]{var24, var22, var20, var5};
         this.asn1TemplateValue = new ASN1Template(var26);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var18) {
         throw new CRMFException("Cannot encode PKIPublicationInfo control.", var18);
      } catch (NameException var19) {
         throw new CRMFException("Cannot encode PKIPublicationInfo control.", var19);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      PKIPublicationInfo var1 = new PKIPublicationInfo();
      var1.action = this.action;

      for(int var2 = 0; var2 < this.pubInfos.length; ++var2) {
         for(int var3 = 0; var3 < this.pubInfos[var2].size(); ++var3) {
            var1.pubInfos[var2].addElement(this.pubInfos[var2].elementAt(var3));
         }
      }

      var1.special = this.special;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PKIPublicationInfo) {
         PKIPublicationInfo var2 = (PKIPublicationInfo)var1;
         if (this.action != var2.action) {
            return false;
         } else {
            int var3 = this.pubInfos.length;
            int var4 = var2.pubInfos.length;
            if (var3 != var4) {
               return false;
            } else {
               for(int var5 = 0; var5 < var3; ++var5) {
                  int var6 = this.pubInfos[var5].size();
                  int var7 = var2.pubInfos[var5].size();
                  if (var6 != var7) {
                     return false;
                  }

                  for(int var8 = 0; var8 < var6; ++var8) {
                     if (this.pubInfos[var5].elementAt(var8) == null) {
                        if (var2.pubInfos[var5].elementAt(var8) != null) {
                           return false;
                        }
                     } else {
                        if (var2.pubInfos[var5].elementAt(var8) == null) {
                           return false;
                        }

                        if (!this.pubInfos[var5].elementAt(var8).equals(var2.pubInfos[var5].elementAt(var8))) {
                           return false;
                        }
                     }
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + this.action;
      var2 = var1 * var2 + Arrays.hashCode(this.pubInfos);
      return var2;
   }
}
