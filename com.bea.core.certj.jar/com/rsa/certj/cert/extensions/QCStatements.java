package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import java.util.Vector;

/** @deprecated */
public class QCStatements extends X509V3Extension implements CertExtension {
   private Vector[] qcStatements = this.createVectorArray();
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public QCStatements() {
      this.extensionTypeFlag = 123;
      this.criticality = false;
      this.setSpecialOID(QC_STATEMENTS_OID);
      this.extensionTypeString = "QC_Statements";
   }

   /** @deprecated */
   public QCStatements(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6, boolean var7) throws CertificateException {
      this.addQCStatement(var1, var2, var3, var4, var5, var6);
      this.extensionTypeFlag = 123;
      this.criticality = var7;
      this.setSpecialOID(QC_STATEMENTS_OID);
      this.extensionTypeString = "QC_Statements";
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
      if (var1 != null && var2 >= 0) {
         try {
            OfContainer var3 = new OfContainer(0, 12288, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               SequenceContainer var8 = new SequenceContainer(0);
               EndContainer var9 = new EndContainer();
               OIDContainer var10 = new OIDContainer(16777216);
               EncodedContainer var11 = new EncodedContainer(130816);
               ASN1Container[] var12 = new ASN1Container[]{var8, var10, var11, var9};
               ASN1.berDecode(var7.data, var7.dataOffset, var12);
               if (var11.dataPresent) {
                  this.addQCStatement(var10.data, var10.dataOffset, var10.dataLen, var11.data, var11.dataOffset, var11.dataLen);
               } else {
                  this.addQCStatement(var10.data, var10.dataOffset, var10.dataLen, (byte[])null, 0, 0);
               }
            }

         } catch (ASN_Exception var13) {
            throw new CertificateException("Could not decode QCStatements extension. ", var13);
         }
      } else {
         throw new CertificateException("Encoding is null.");
      }
   }

   /** @deprecated */
   public void addQCStatement(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) throws CertificateException {
      if (var1 != null && var3 > 0 && var2 >= 0 && var1.length >= var3 + var2) {
         byte[] var7 = new byte[var3];
         System.arraycopy(var1, var2, var7, 0, var3);
         this.qcStatements[0].addElement(var7);
         if (var4 != null && var6 > 0 && var5 >= 0 && var4.length >= var6 + var5) {
            byte[] var8 = new byte[var6];
            System.arraycopy(var4, var5, var8, 0, var6);
            this.qcStatements[1].addElement(var8);
         } else {
            this.qcStatements[1].addElement((Object)null);
         }

      } else if (var1 == null) {
         throw new CertificateException("Statement ID cannot be null.");
      } else {
         throw new CertificateException("Invalid parameter");
      }
   }

   /** @deprecated */
   public byte[] getQCStatementID(int var1) throws CertificateException {
      if (this.qcStatements[0].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else {
         return (byte[])this.qcStatements[0].elementAt(var1);
      }
   }

   /** @deprecated */
   public byte[] getQCStatementInfo(int var1) throws CertificateException {
      if (this.qcStatements[1].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else {
         return (byte[])this.qcStatements[1].elementAt(var1);
      }
   }

   /** @deprecated */
   public int getQCStatementCount() {
      return this.qcStatements[0].size();
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      Vector var1 = new Vector();

      try {
         OfContainer var2 = new OfContainer(0, true, 0, 12288, new EncodedContainer(12288));
         var1.addElement(var2);

         for(int var3 = 0; var3 < this.qcStatements[0].size(); ++var3) {
            byte[] var4 = (byte[])this.qcStatements[0].elementAt(var3);
            byte[] var5;
            if (var3 < this.qcStatements[1].size()) {
               var5 = (byte[])this.qcStatements[1].elementAt(var3);
            } else {
               var5 = null;
            }

            try {
               SequenceContainer var6 = new SequenceContainer(0);
               EndContainer var7 = new EndContainer();
               OIDContainer var8 = new OIDContainer(16777216, true, 0, var4, 0, var4.length);
               EncodedContainer var9;
               if (var5 != null) {
                  var9 = new EncodedContainer(130816, true, 0, var5, 0, var5.length);
               } else {
                  var9 = new EncodedContainer(130816, false, 0, (byte[])null, 0, 0);
               }

               ASN1Container[] var10 = new ASN1Container[]{var6, var8, var9, var7};
               this.asn1Template = new ASN1Template(var10);
               int var11 = this.asn1Template.derEncodeInit();
               byte[] var12 = new byte[var11];
               var11 = this.asn1Template.derEncode(var12, 0);
               EncodedContainer var13 = new EncodedContainer(12288, true, 0, var12, 0, var11);
               var2.addContainer(var13);
            } catch (ASN_Exception var14) {
               return 0;
            }
         }

         ASN1Container[] var16 = new ASN1Container[var1.size()];
         var1.copyInto(var16);
         this.asn1TemplateValue = new ASN1Template(var16);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var15) {
         return 0;
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
         } catch (ASN_Exception var4) {
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      QCStatements var1 = new QCStatements();

      int var2;
      for(var2 = 0; var2 < this.qcStatements[0].size(); ++var2) {
         var1.qcStatements[0].addElement(((byte[])this.qcStatements[0].elementAt(var2)).clone());
      }

      for(var2 = 0; var2 < this.qcStatements[1].size(); ++var2) {
         if (this.qcStatements[1].elementAt(var2) == null) {
            var1.qcStatements[1].addElement((Object)null);
         } else {
            var1.qcStatements[1].addElement(((byte[])this.qcStatements[1].elementAt(var2)).clone());
         }
      }

      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.qcStatements = this.createVectorArray();
      this.asn1TemplateValue = null;
   }
}
