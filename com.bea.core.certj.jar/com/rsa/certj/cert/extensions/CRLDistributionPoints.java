package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.RDN;
import java.util.Vector;

/** @deprecated */
public class CRLDistributionPoints extends X509V3Extension implements CertExtension {
   /** @deprecated */
   public static final int REASON_FLAGS_BITS = 9;
   /** @deprecated */
   public static final int REASON_FLAGS_MASK = -8388608;
   /** @deprecated */
   public static final int UNUSED = Integer.MIN_VALUE;
   /** @deprecated */
   public static final int KEY_COMPROMISE = 1073741824;
   /** @deprecated */
   public static final int CA_COMPROMISE = 536870912;
   /** @deprecated */
   public static final int AFFILIATION_CHANGED = 268435456;
   /** @deprecated */
   public static final int SUPERSEDED = 134217728;
   /** @deprecated */
   public static final int CESSATION_OF_OPERATION = 67108864;
   /** @deprecated */
   public static final int CERTIFICATE_HOLD = 33554432;
   /** @deprecated */
   public static final int PRIVILEGE_WITHDRAWN = 16777216;
   /** @deprecated */
   public static final int AA_COMPROMISE = 8388608;
   private Vector[] crlVector = this.createVectorArray();
   private static final int DISTRIBUTION_POINT_SPECIAL = 10551296;
   private static final int REASONS_SPECIAL = 8454145;
   private static final int CRL_ISSUER_SPECIAL = 8454146;
   private static final int FULL_NAME_SPECIAL = 8388608;
   private static final int NAME_RELATIVE_SPECIAL = 8388609;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public CRLDistributionPoints() {
      this.extensionTypeFlag = 31;
      this.criticality = false;
      this.setStandardOID(31);
      this.extensionTypeString = "CRLDistributionPoints";
   }

   /** @deprecated */
   public CRLDistributionPoints(RDN var1, int var2, GeneralNames var3, boolean var4) {
      this.extensionTypeFlag = 31;
      this.criticality = var4;
      this.setStandardOID(31);
      this.crlVector[0].addElement(var1);
      this.crlVector[1].addElement(new Integer(var2));
      this.crlVector[2].addElement(var3);
      this.extensionTypeString = "CRLDistributionPoints";
   }

   /** @deprecated */
   public CRLDistributionPoints(GeneralNames var1, int var2, GeneralNames var3, boolean var4) {
      this.extensionTypeFlag = 31;
      this.criticality = var4;
      this.setStandardOID(31);
      this.crlVector[0].addElement(var1);
      this.crlVector[1].addElement(new Integer(var2));
      this.crlVector[2].addElement(var3);
      this.extensionTypeString = "CRLDistributionPoints";
   }

   private Vector[] createVectorArray() {
      Vector[] var1 = new Vector[3];

      for(int var2 = 0; var2 < 3; ++var2) {
         var1[var2] = new Vector();
      }

      return var1;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(0, 12288, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               SequenceContainer var8 = new SequenceContainer(0);
               EndContainer var9 = new EndContainer();
               ChoiceContainer var10 = new ChoiceContainer(10551296);
               EncodedContainer var11 = new EncodedContainer(8400896);
               EncodedContainer var12 = new EncodedContainer(8401153);
               BitStringContainer var13 = new BitStringContainer(8454145);
               EncodedContainer var14 = new EncodedContainer(8466434);
               ASN1Container[] var15 = new ASN1Container[]{var8, var10, var11, var12, var9, var13, var14, var9};
               ASN1.berDecode(var7.data, var7.dataOffset, var15);
               if (var11.dataPresent) {
                  this.crlVector[0].addElement(new GeneralNames(var11.data, var11.dataOffset, 8388608));
               } else if (var12.dataPresent) {
                  this.crlVector[0].addElement(new RDN(var12.data, var12.dataOffset, 8388609));
               } else {
                  this.crlVector[0].addElement((Object)null);
               }

               if (!var13.dataPresent) {
                  this.crlVector[1].addElement(new Integer(-1));
               } else {
                  if (var13.dataLen > 4) {
                     throw new CertificateException("Could not decode CRLDistributionPoints extension.");
                  }

                  if (var13.dataLen == 0) {
                     this.crlVector[1].addElement(new Integer(0));
                  } else {
                     int var16 = 0;
                     int var17 = var13.dataOffset;

                     for(int var18 = 24; var17 < var13.dataOffset + var13.dataLen; var18 -= 8) {
                        var16 |= (var13.data[var17] & 255) << var18;
                        ++var17;
                     }

                     var16 &= -8388608;
                     this.crlVector[1].addElement(new Integer(var16));
                  }
               }

               if (var14.dataPresent) {
                  this.crlVector[2].addElement(new GeneralNames(var14.data, var14.dataOffset, 8454146));
               } else {
                  this.crlVector[2].addElement((Object)null);
               }
            }

         } catch (ASN_Exception var19) {
            throw new CertificateException("Could not decode CRLDistributionPoints extension.");
         } catch (NameException var20) {
            throw new CertificateException("Could not create new GeneralNames object.");
         }
      }
   }

   /** @deprecated */
   public void addDistributionPoints(RDN var1, int var2, GeneralNames var3) {
      this.crlVector[0].addElement(var1);
      if (var2 == -1) {
         this.crlVector[1].addElement(new Integer(-1));
      } else {
         var2 &= -8388608;
         this.crlVector[1].addElement(new Integer(var2));
      }

      this.crlVector[2].addElement(var3);
   }

   /** @deprecated */
   public void addDistributionPoints(GeneralNames var1, int var2, GeneralNames var3) {
      this.crlVector[0].addElement(var1);
      if (var2 == -1) {
         this.crlVector[1].addElement(new Integer(-1));
      } else {
         var2 &= -8388608;
         this.crlVector[1].addElement(new Integer(var2));
      }

      this.crlVector[2].addElement(var3);
   }

   /** @deprecated */
   public Object getDistributionPointName(int var1) throws NameException {
      if (this.crlVector[0].size() <= var1) {
         throw new NameException("Specified index is invalid.");
      } else {
         return this.crlVector[0] == null ? null : this.crlVector[0].elementAt(var1);
      }
   }

   /** @deprecated */
   public int getReasonFlags(int var1) throws NameException {
      if (this.crlVector[1].size() <= var1) {
         throw new NameException("Specified index is invalid.");
      } else {
         Integer var2 = (Integer)this.crlVector[1].elementAt(var1);
         return var2;
      }
   }

   /** @deprecated */
   public GeneralNames getCRLIssuer(int var1) throws NameException {
      if (this.crlVector[2].size() <= var1) {
         throw new NameException("Specified index is invalid.");
      } else {
         return (GeneralNames)this.crlVector[2].elementAt(var1);
      }
   }

   /** @deprecated */
   public int getDistributionPointCount() {
      return this.crlVector[0].size();
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      Vector var1 = new Vector();

      try {
         OfContainer var2 = new OfContainer(0, true, 0, 12288, new EncodedContainer(12288));
         EndContainer var3 = new EndContainer();
         var1.addElement(var2);

         for(int var4 = 0; var4 < this.crlVector[0].size(); ++var4) {
            try {
               byte var5 = 0;
               SequenceContainer var6 = new SequenceContainer(0, true, 0);
               ChoiceContainer var7 = null;
               BitStringContainer var8 = null;
               EncodedContainer var9 = null;
               EncodedContainer var10 = null;
               if (this.crlVector[0].elementAt(var4) != null) {
                  var7 = new ChoiceContainer(10551296, 0);
                  var10 = this.encodeName(var4);
                  var5 = 1;
               }

               int var12 = (Integer)this.crlVector[1].elementAt(var4);
               if (var12 != -1) {
                  var8 = new BitStringContainer(8454145, true, 0, var12, 9, true);
                  if (var5 == 0) {
                     var5 = 2;
                  } else {
                     var5 = 3;
                  }
               }

               if (this.crlVector[2].elementAt(var4) != null) {
                  var9 = this.encodeIssuer(var4);
                  if (var5 == 0) {
                     var5 = 4;
                  } else if (var5 == 1) {
                     var5 = 5;
                  } else if (var5 == 2) {
                     var5 = 6;
                  } else if (var5 == 3) {
                     var5 = 7;
                  }
               }

               ASN1Template var11;
               switch (var5) {
                  case 0:
                     ASN1Container[] var13 = new ASN1Container[]{var6, var3};
                     var11 = new ASN1Template(var13);
                     break;
                  case 1:
                     ASN1Container[] var14 = new ASN1Container[]{var6, var7, var10, var3, var3};
                     var11 = new ASN1Template(var14);
                     break;
                  case 2:
                     ASN1Container[] var15 = new ASN1Container[]{var6, var8, var3};
                     var11 = new ASN1Template(var15);
                     break;
                  case 3:
                     ASN1Container[] var16 = new ASN1Container[]{var6, var7, var10, var3, var8, var3};
                     var11 = new ASN1Template(var16);
                     break;
                  case 4:
                     ASN1Container[] var17 = new ASN1Container[]{var6, var9, var3};
                     var11 = new ASN1Template(var17);
                     break;
                  case 5:
                     ASN1Container[] var18 = new ASN1Container[]{var6, var7, var10, var3, var9, var3};
                     var11 = new ASN1Template(var18);
                     break;
                  case 6:
                     ASN1Container[] var19 = new ASN1Container[]{var6, var8, var9, var3};
                     var11 = new ASN1Template(var19);
                     break;
                  case 7:
                     ASN1Container[] var20 = new ASN1Container[]{var6, var7, var10, var3, var8, var9, var3};
                     var11 = new ASN1Template(var20);
                     break;
                  default:
                     return 0;
               }

               int var25 = var11.derEncodeInit();
               byte[] var26 = new byte[var25];
               var25 = var11.derEncode(var26, 0);
               EncodedContainer var27 = new EncodedContainer(12288, true, 0, var26, 0, var25);
               var2.addContainer(var27);
            } catch (ASN_Exception var21) {
               return 0;
            } catch (CertificateException var22) {
               return 0;
            }
         }

         ASN1Container[] var24 = new ASN1Container[var1.size()];
         var1.copyInto(var24);
         this.asn1TemplateValue = new ASN1Template(var24);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var23) {
         return 0;
      }
   }

   private EncodedContainer encodeName(int var1) throws CertificateException {
      EncodedContainer var2 = null;

      try {
         int var3;
         byte[] var4;
         if (this.crlVector[0].elementAt(var1) instanceof GeneralNames) {
            var3 = ((GeneralNames)this.crlVector[0].elementAt(var1)).getDERLen(8388608);
            var4 = new byte[var3];
            var3 = ((GeneralNames)this.crlVector[0].elementAt(var1)).getDEREncoding(var4, 0, 8388608);
            var2 = new EncodedContainer(12288, true, 0, var4, 0, var3);
         } else if (this.crlVector[0].elementAt(var1) instanceof RDN) {
            var3 = ((RDN)this.crlVector[0].elementAt(var1)).getDERLen(8388609);
            var4 = new byte[var3];
            var3 = ((RDN)this.crlVector[0].elementAt(var1)).getDEREncoding(var4, 0, 8388609);
            var2 = new EncodedContainer(12544, true, 0, var4, 0, var3);
         }

         return var2;
      } catch (ASN_Exception var6) {
         throw new CertificateException("Can't encode DistributionPointNames", var6);
      } catch (NameException var7) {
         throw new CertificateException("Can't encode DistributionPointNames", var7);
      }
   }

   private EncodedContainer encodeIssuer(int var1) throws CertificateException {
      try {
         int var2 = ((GeneralNames)this.crlVector[2].elementAt(var1)).getDERLen(8454146);
         byte[] var3 = new byte[var2];
         var2 = ((GeneralNames)this.crlVector[2].elementAt(var1)).getDEREncoding(var3, 0, 8454146);
         return new EncodedContainer(12288, true, 0, var3, 0, var2);
      } catch (ASN_Exception var4) {
         throw new CertificateException("Can't encode cRLIssuer", var4);
      } catch (NameException var5) {
         throw new CertificateException("Can't encode cRLIssuer", var5);
      }
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CRLDistributionPoints var1 = new CRLDistributionPoints();
      this.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void copyValues(CRLDistributionPoints var1) {
      for(int var2 = 0; var2 < this.crlVector[0].size(); ++var2) {
         if (this.crlVector[0].elementAt(var2) == null) {
            var1.crlVector[0].addElement((Object)null);
         }

         try {
            if (this.crlVector[0].elementAt(var2) instanceof GeneralNames) {
               var1.crlVector[0].addElement(((GeneralNames)this.crlVector[0].elementAt(var2)).clone());
            } else if (this.crlVector[0].elementAt(var2) instanceof RDN) {
               var1.crlVector[0].addElement(((RDN)this.crlVector[0].elementAt(var2)).clone());
            }

            var1.crlVector[1].addElement(this.crlVector[1].elementAt(var2));
            if (this.crlVector[2].elementAt(var2) == null) {
               var1.crlVector[2].addElement((Object)null);
            } else {
               var1.crlVector[2].addElement(((GeneralNames)this.crlVector[2].elementAt(var2)).clone());
            }
         } catch (CloneNotSupportedException var4) {
            throw new RuntimeException(var4);
         }
      }

      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.crlVector = null;
      this.asn1TemplateValue = null;
   }
}
