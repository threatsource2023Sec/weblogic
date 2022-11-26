package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.Serializable;

/** @deprecated */
public abstract class Control implements Serializable, Cloneable {
   /** @deprecated */
   public static final int REG_TOKEN = 0;
   /** @deprecated */
   public static final int AUTHENTICATOR = 1;
   /** @deprecated */
   public static final int PKI_PUBLICATION_INFO = 2;
   /** @deprecated */
   public static final int PKI_ARCHIVE_OPTIONS = 3;
   /** @deprecated */
   public static final int OLD_CERT_ID = 4;
   /** @deprecated */
   public static final int PROTOCOL_ENCR_KEY = 5;
   /** @deprecated */
   public static final int NON_STANDARD = 6;
   /** @deprecated */
   protected static final byte[][] OID_LIST = new byte[][]{{43, 6, 1, 5, 5, 7, 5, 1, 1}, {43, 6, 1, 5, 5, 7, 5, 1, 2}, {43, 6, 1, 5, 5, 7, 5, 1, 3}, {43, 6, 1, 5, 5, 7, 5, 1, 4}, {43, 6, 1, 5, 5, 7, 5, 1, 5}, {43, 6, 1, 5, 5, 7, 5, 1, 6}};
   /** @deprecated */
   protected int controlTypeFlag;
   byte[] theOID;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   /** @deprecated */
   protected String controlTypeString;
   /** @deprecated */
   protected static CertPathCtx theCertPathCtx;
   protected static CertJ theCertJ;
   /** @deprecated */
   protected JSAFE_PublicKey pubKey;
   /** @deprecated */
   protected JSAFE_PrivateKey privKey;

   /** @deprecated */
   public static Control getInstance(byte[] var0, int var1, int var2, CertJ var3, CertPathCtx var4, JSAFE_PublicKey var5, JSAFE_PrivateKey var6) throws CRMFException {
      if (var0 != null && var1 >= 0) {
         ASN1Container[] var7 = decodeControl(var0, var1, var2);
         int var8 = findOID(var7[1].data, var7[1].dataOffset, var7[1].dataLen);
         Object var9;
         switch (var8) {
            case 0:
               var9 = new RegistrationToken();
               break;
            case 1:
               var9 = new Authenticator();
               break;
            case 2:
               var9 = new PKIPublicationInfo();
               break;
            case 3:
               var9 = new PKIArchiveOptions();
               ((Control)var9).setEnvironment(var3, var4, var5, var6);
               break;
            case 4:
               var9 = new OldCertID();
               break;
            case 5:
               var9 = new ProtocolEncryptionKey();
               break;
            case 6:
               var9 = new NonStandardControl();
               ((Control)var9).theOID = new byte[var7[1].dataLen];
               System.arraycopy(var7[1].data, var7[1].dataOffset, ((Control)var9).theOID, 0, var7[1].dataLen);
               break;
            default:
               return null;
         }

         ((Control)var9).decodeValue(var7[2].data, var7[2].dataOffset);

         for(int var10 = 0; var10 < var7.length; ++var10) {
            var7[var10].clearSensitiveData();
         }

         return (Control)var9;
      } else {
         throw new CRMFException("Control encoding is null.");
      }
   }

   /** @deprecated */
   public static Control getInstance(byte[] var0, int var1, int var2) throws CRMFException {
      return getInstance(var0, var1, var2, (CertJ)null, (CertPathCtx)null, (JSAFE_PublicKey)null, (JSAFE_PrivateKey)null);
   }

   /** @deprecated */
   protected static ASN1Container[] decodeControl(byte[] var0, int var1, int var2) throws CRMFException {
      if (var0 != null && var1 >= 0) {
         SequenceContainer var3 = new SequenceContainer(var2);
         EndContainer var4 = new EndContainer();
         OIDContainer var5 = new OIDContainer(16777216);
         EncodedContainer var6 = new EncodedContainer(65280);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};

         try {
            ASN1.berDecode(var0, var1, var7);
            return var7;
         } catch (ASN_Exception var9) {
            throw new CRMFException("Cannot read the BER of the control.", var9);
         }
      } else {
         throw new CRMFException("Control encoding is null.");
      }
   }

   private static int findOID(byte[] var0, int var1, int var2) {
      if (var0 == null) {
         return 6;
      } else {
         for(int var3 = 0; var3 < 6; ++var3) {
            if (var2 == OID_LIST[var3].length) {
               int var4;
               for(var4 = 0; var4 < var2 && (var0[var4 + var1] & 255) == (OID_LIST[var3][var4] & 255); ++var4) {
               }

               if (var4 >= var2) {
                  return var3;
               }
            }
         }

         return 6;
      }
   }

   /** @deprecated */
   public String getControlTypeString() {
      return this.controlTypeString;
   }

   /** @deprecated */
   protected abstract void decodeValue(byte[] var1, int var2) throws CRMFException;

   /** @deprecated */
   public byte[] getOID() {
      return this.theOID == null ? null : (byte[])((byte[])this.theOID.clone());
   }

   /** @deprecated */
   public boolean compareOID(byte[] var1) {
      if (var1 != null && this.theOID != null) {
         if (var1.length != this.theOID.length) {
            return false;
         } else {
            for(int var2 = 0; var2 < var1.length; ++var2) {
               if (var1[var2] != this.theOID[var2]) {
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
   public int getControlType() {
      return this.controlTypeFlag;
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      return this.derEncodeControlLen(var1, this.derEncodeValueInit());
   }

   /** @deprecated */
   protected abstract int derEncodeValueInit() throws CRMFException;

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 != null && var1 >= 0) {
         try {
            return var1 + ASN1Lengths.determineLength(var0, var1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Could not read the BER encoding.", var3);
         }
      } else {
         throw new CRMFException("Control encoding is null.");
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var2 >= 0) {
         return this.derEncodeControl(var1, var2, var3);
      } else {
         throw new CRMFException("Passed control array is null.");
      }
   }

   /** @deprecated */
   public boolean isControlType(int var1) {
      return var1 == this.controlTypeFlag;
   }

   /** @deprecated */
   public int derEncodeControlLen(int var1, int var2) throws CRMFException {
      this.special = var1;

      try {
         SequenceContainer var3 = new SequenceContainer(0, true, 0);
         EndContainer var4 = new EndContainer();
         OIDContainer var5 = new OIDContainer(16777216, true, 0, this.theOID, 0, this.theOID.length);
         EncodedContainer var6 = new EncodedContainer(65280, true, 0, (byte[])null, 0, var2);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
         this.asn1Template = new ASN1Template(var7);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var8) {
         throw new CRMFException("Cannot encode control. ", var8);
      }
   }

   /** @deprecated */
   protected int derEncodeControl(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed control array is null.");
      } else if ((this.asn1Template == null || this.special != var3) && this.getDERLen(var3) == 0) {
         throw new CRMFException("Could not encode, missing data");
      } else {
         int var4;
         try {
            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
         } catch (ASN_Exception var6) {
            throw new CRMFException("Cannot encode control. ", var6);
         }

         var4 += this.derEncodeValue(var1, var2 + var4);
         return var4;
      }
   }

   /** @deprecated */
   protected abstract int derEncodeValue(byte[] var1, int var2) throws CRMFException;

   /** @deprecated */
   protected void copyValues(Control var1) throws CloneNotSupportedException {
      var1.special = this.special;
      if (this.asn1Template != null) {
         try {
            var1.getDERLen(this.special);
         } catch (CRMFException var3) {
            throw new CloneNotSupportedException(var3.getMessage());
         }
      }
   }

   /** @deprecated */
   public void setEnvironment(CertJ var1, CertPathCtx var2, JSAFE_PublicKey var3, JSAFE_PrivateKey var4) throws CRMFException {
      theCertJ = var1;
      theCertPathCtx = var2;
      if (var3 != null) {
         this.pubKey = var3;
      }

      if (var4 != null) {
         this.privKey = var4;
      }

   }

   /** @deprecated */
   public abstract boolean equals(Object var1);

   /** @deprecated */
   public abstract Object clone() throws CloneNotSupportedException;
}
